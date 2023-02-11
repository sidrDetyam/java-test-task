package ru.nsu.gemuev.exceptions;

import lombok.NonNull;

public class EndOfData extends DataException{
    public EndOfData(@NonNull String message){
        super(message);
    }
}
