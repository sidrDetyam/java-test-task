package ru.nsu.gemuev.sorting;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.sorting.ports.DataSupplier;

import java.util.Comparator;
import java.util.Optional;

@Log4j2
public class DropStrategy<T> implements UnsortedDataHandlingStrategy<T> {

    @Override
    public @NonNull Optional<T> getNextValue(@NonNull final DataSupplier<? extends T> supplier,
                                             @NonNull final T previousValue,
                                             @NonNull Comparator<? super T> comparator) throws DataException {
        while (!supplier.isEndOfData()) {
            final T nextValue = supplier.get();
            if (comparator.compare(previousValue, nextValue) <= 0) {
                return Optional.of(nextValue);
            }
            else{
                log.error("Unsorted data detected and dropped: %s".formatted(nextValue));
            }
        }

        return Optional.empty();
    }
}
