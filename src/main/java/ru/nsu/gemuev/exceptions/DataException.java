package ru.nsu.gemuev.exceptions;

import lombok.NonNull;

public class DataException extends Exception{
    public DataException(@NonNull String message){
        super(message);
    }
}
