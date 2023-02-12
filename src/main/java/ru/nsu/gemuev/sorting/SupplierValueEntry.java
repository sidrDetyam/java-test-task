package ru.nsu.gemuev.sorting;

import ru.nsu.gemuev.sorting.ports.DataSupplier;

public record SupplierValueEntry<T>(DataSupplier<? extends T> supplier, T currentValue) {

}
