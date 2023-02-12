package ru.nsu.gemuev.sorting;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.sorting.ports.DataSupplier;

import java.util.Comparator;
import java.util.Optional;

@Log4j2
public class FirstComeStrategy<T> implements UnsortedDataHandlingStrategy<T>{
    @Override
    public Optional<T> getNextValue(@NonNull DataSupplier<? extends T> supplier,
                                    @NonNull T previousValue, 
                                    @NonNull Comparator<? super T> comparator) throws DataException {
        if (supplier.isEndOfData()) {
            return Optional.empty();
        }

        final T nextValue = supplier.get();
        if (comparator.compare(previousValue, nextValue) > 0) {
            log.error("Unsorted data detected, but consumed: %s".formatted(nextValue));
        }
        return Optional.of(nextValue);
    }
}
