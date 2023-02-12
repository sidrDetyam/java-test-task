package ru.nsu.gemuev.sorting;

import lombok.NonNull;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.sorting.ports.DataSupplier;

import java.util.Comparator;
import java.util.Optional;

public interface UnsortedDataHandlingStrategy<T> {
    Optional<T> getNextValue(@NonNull DataSupplier<? extends T> supplier,
                             @NonNull T previousValue,
                             @NonNull Comparator<? super T> comparator) throws DataException;
}
