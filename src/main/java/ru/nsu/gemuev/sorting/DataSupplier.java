package ru.nsu.gemuev.sorting;

import lombok.NonNull;
import ru.nsu.gemuev.exceptions.DataException;


public interface DataSupplier<T>{
    @NonNull T get() throws DataException;

    boolean isEndOfData();

    default void close(){

    }
}
