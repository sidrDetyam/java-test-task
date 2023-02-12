package ru.nsu.gemuev.sorting;

import lombok.NonNull;
import ru.nsu.gemuev.exceptions.DataException;

public interface DataConsumer<T>{
    void accept(@NonNull T data) throws DataException;

    default void close(){

    }
}
