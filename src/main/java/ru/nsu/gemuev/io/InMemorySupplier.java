package ru.nsu.gemuev.io;

import lombok.NonNull;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.exceptions.EndOfData;
import ru.nsu.gemuev.sorting.ports.DataSupplier;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class InMemorySupplier<T> implements DataSupplier<T> {
    private final Deque<T> data;

    public InMemorySupplier(@NonNull Collection<? extends T> data) {
        this.data = new ArrayDeque<>(data);
    }

    @Override
    public @NonNull T get() throws DataException {
        if (data.isEmpty()) {
            throw new EndOfData("");
        }
        return data.poll();
    }

    @Override
    public boolean isEndOfData() {
        return data.isEmpty();
    }
}
