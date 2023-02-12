package ru.nsu.gemuev;

import ru.nsu.gemuev.io.InMemoryConsumer;
import ru.nsu.gemuev.io.InMemorySupplier;
import ru.nsu.gemuev.io.ScannerBasedFileSupplier;
import ru.nsu.gemuev.io.ScannerBasedFileSupplierBuilder;
import ru.nsu.gemuev.sorting.DataSorter;
import ru.nsu.gemuev.sorting.DataSupplier;
import ru.nsu.gemuev.sorting.DropStrategy;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var supplier1 = new InMemorySupplier<>(List.of("9", "2", "5", "8", "."));
        var supplier2 = new InMemorySupplier<>(List.of("1", "2", "7", "4", "6", "6", "7", "9"));
        var supplier3 = new InMemorySupplier<>(List.of("1", "99", "999"));

        var suppliers = new ArrayList<DataSupplier<String>>(List.of());
        var consumer = new InMemoryConsumer<BigInteger>();

        try {
            var builder = new ScannerBasedFileSupplierBuilder<>(
                    Scanner::nextBigInteger,
                    scanner -> !scanner.hasNextBigInteger())
                    .setCharset(StandardCharsets.UTF_8);

            Comparator<BigInteger> comp = BigInteger::compareTo;
            var sorter = new DataSorter<>(comp.reversed(), new DropStrategy<>());
            sorter.sort(List.of(builder.build("in1.txt")), consumer);

            System.out.println(consumer.getData());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}