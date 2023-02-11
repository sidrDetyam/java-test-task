package ru.nsu.gemuev;

import ru.nsu.gemuev.io.InMemoryConsumer;
import ru.nsu.gemuev.io.InMemorySupplier;
import ru.nsu.gemuev.sorting.DataSorter;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        var supplier1 = new InMemorySupplier<>(List.of("1", "2", "5", "8"));
        var supplier2 = new InMemorySupplier<>(List.of("1", "2", "3", "4", "6", "6", "7", "9"));
        var supplier3 = new InMemorySupplier<>(List.of("1", "99", "999"));

        var suppliers = List.of(supplier1, supplier2, supplier3);
        var consumer = new InMemoryConsumer<String>();

        Comparator<String> comp = String::compareTo;
        var sorter = new DataSorter<>(suppliers, consumer, comp);
        sorter.sort();

        System.out.println(consumer.getData());
    }
}