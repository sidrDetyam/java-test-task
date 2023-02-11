package ru.nsu.gemuev.io;

import lombok.Getter;
import lombok.NonNull;
import ru.nsu.gemuev.sorting.DataConsumer;

import java.util.ArrayList;

public class InMemoryConsumer<T> implements DataConsumer<T> {
    @Getter
    private final ArrayList<T> data = new ArrayList<>();

    @Override
    public void accept(@NonNull T s) {
        data.add(s);
    }
}
