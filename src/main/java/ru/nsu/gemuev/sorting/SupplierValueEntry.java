package ru.nsu.gemuev.sorting;

public record SupplierValueEntry<T>(DataSupplier<? extends T> supplier, T currentValue){

}
