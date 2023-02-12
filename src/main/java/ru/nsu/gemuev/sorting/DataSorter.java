package ru.nsu.gemuev.sorting;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.exceptions.DataException;

import java.util.Comparator;
import java.util.PriorityQueue;

@Log4j2
@AllArgsConstructor
public class DataSorter<T> {
    @Setter
    @NonNull
    private Comparator<? super T> comparator;
    @Setter
    @NonNull
    private UnsortedDataHandlingStrategy<T> dataHandlingStrategy;

    public void sort(@NonNull final Iterable<? extends DataSupplier<? extends T>> suppliers,
                     @NonNull final DataConsumer<? super T> consumer) {

        final var pq = new PriorityQueue<SupplierValueEntry<T>>(
                (a, b) -> comparator.compare(a.currentValue(), b.currentValue()));

        suppliers.forEach(supplier -> addSupplierNextValueToQueue(pq, supplier));

        while (!pq.isEmpty()) {
            try {
                final T nextValue = pq.peek().currentValue();
                consumer.accept(nextValue);
                consumeNextValueFromQueue(pq);
            } catch (DataException exception) {
                pq.forEach(e -> e.supplier().close());
                consumer.close();
                log.error("Consumer`s data exception: %s".formatted(consumer));
            }
        }

        consumer.close();
    }

    private void addSupplierNextValueToQueue(@NonNull PriorityQueue<SupplierValueEntry<T>> pq,
                                             @NonNull DataSupplier<? extends T> supplier) {
        try {
            if(supplier.isEndOfData()){
                supplier.close();
                return;
            }
            final T nextValue = supplier.get();
            pq.add(new SupplierValueEntry<>(supplier, nextValue));
        } catch (DataException e) {
            log.error("Supplier`s data exception: %s".formatted(e));
            supplier.close();
        }
    }

    private void consumeNextValueFromQueue(@NonNull final PriorityQueue<SupplierValueEntry<T>> pq) {
        final SupplierValueEntry<T> supplierEntry = pq.remove();
        try {
            dataHandlingStrategy
                    .getNextValue(supplierEntry.supplier(), supplierEntry.currentValue(), comparator)
                    .ifPresentOrElse(
                            nextValue -> pq.add(new SupplierValueEntry<>(supplierEntry.supplier(), nextValue)),
                            () -> supplierEntry.supplier().close());
        } catch (DataException e) {
            log.error("Supplier`s data exception: %s".formatted(e));
            supplierEntry.supplier().close();
        }
    }
}
