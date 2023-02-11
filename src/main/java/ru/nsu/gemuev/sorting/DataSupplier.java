package ru.nsu.gemuev.sorting;

import lombok.NonNull;
import ru.nsu.gemuev.exceptions.DataException;

@FunctionalInterface
public interface DataSupplier<T>{
    @NonNull T get() throws DataException;

    default void close(){

    }
}
