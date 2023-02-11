package ru.nsu.gemuev.sorting;


import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.exceptions.EndOfData;

import java.util.*;

@Log4j2
public class DataSorter<T> {
    private final List<? extends DataSupplier<? extends T>> suppliers;
    private final DataConsumer<? super T> consumer;
    private final Comparator<? super T> comparator;
    @Getter
    private boolean isSorted = false;

    public DataSorter(@NonNull Collection<? extends DataSupplier<? extends T>> suppliers,
                      @NonNull DataConsumer<? super T> consumer,
                      @NonNull Comparator<? super T> comparator) {
        this.suppliers = new ArrayList<>(suppliers);
        this.consumer = consumer;
        this.comparator = comparator;
    }

    private void addSupplierNextValueToQueue(@NonNull PriorityQueue<SupplierValueEntry<T>> pq,
                                             @NonNull DataSupplier<? extends T> supplier) {
        try {
            final T nextValue = supplier.get();
            pq.add(new SupplierValueEntry<>(supplier, nextValue));
        } catch (EndOfData ignored) {
        } catch (DataException e) {
            log.error("Supplier`s data exception: %s".formatted(e));
        } finally {
            supplier.close();
        }
    }

    public void sort() {
        if (isSorted) {
            throw new IllegalStateException("Data flow already sorted");
        }
        isSorted = true;

        final var pq = new PriorityQueue<SupplierValueEntry<T>>(
                (a, b) -> comparator.compare(a.currentValue(), b.currentValue()));

        suppliers.forEach(supplier -> addSupplierNextValueToQueue(pq, supplier));

        while (!pq.isEmpty()) {
            final SupplierValueEntry<T> entry = pq.poll();
            try {
                consumer.accept(entry.currentValue());
                addSupplierNextValueToQueue(pq, entry.supplier());
            } catch (DataException exception) {
                pq.forEach(e -> e.supplier().close());
                consumer.close();
                log.error("Consumer`s data exception: %s".formatted(consumer));
            }
        }

        consumer.close();
    }
}
