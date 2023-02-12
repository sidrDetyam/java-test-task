package ru.nsu.gemuev;

import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.io.InMemoryConsumer;
import ru.nsu.gemuev.io.InMemorySupplier;
import ru.nsu.gemuev.io.ScannerBasedFileSupplierBuilder;
import ru.nsu.gemuev.io.FileConsumer;
import ru.nsu.gemuev.sorting.DataSorter;
import ru.nsu.gemuev.sorting.ports.DataSupplier;
import ru.nsu.gemuev.sorting.DropStrategy;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Log4j2
public class Main {
    public static void main(String[] args) {

        var supplier1 = new InMemorySupplier<>(List.of("9", "2", "5", "8", "."));
        var supplier2 = new InMemorySupplier<>(List.of("1", "2", "7", "4", "6", "6", "7", "9"));
        var supplier3 = new InMemorySupplier<>(List.of("1", "99", "999"));

        var suppliers = new ArrayList<DataSupplier<String>>(List.of());
        var consumer = new InMemoryConsumer<BigInteger>();

        try {
            var builder = new ScannerBasedFileSupplierBuilder<>(
                    scanner -> {
                        while(scanner.hasNextLine()) {
                            final String nextLine = scanner.nextLine();
                            try {
                                return new BigInteger(nextLine);
                            } catch (NumberFormatException e) {
                                log.error("Incorrect number: %s".formatted(nextLine));
                            }
                        }
                        throw new NoSuchElementException("There is`n any valid value");
                    },
                    scanner -> !scanner.hasNextLine())
                    .setCharset(StandardCharsets.UTF_8);

            Comparator<BigInteger> comp = BigInteger::compareTo;
            var sorter = new DataSorter<>(comp.reversed(), new DropStrategy<>());
            sorter.sort(List.of(builder.build("in1.txt")), new FileConsumer<>("out.txt"));

            System.out.println(consumer.getData());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}