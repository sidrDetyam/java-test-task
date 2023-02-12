package ru.nsu.gemuev;

import lombok.NonNull;
import lombok.Setter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import ru.nsu.gemuev.io.FileConsumer;
import ru.nsu.gemuev.io.ScannerBasedFileSupplier;
import ru.nsu.gemuev.io.ScannerBasedFileSupplierBuilder;
import ru.nsu.gemuev.sorting.DataSorter;
import ru.nsu.gemuev.sorting.DropStrategy;
import ru.nsu.gemuev.util.FileSupplierUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "sort-program", mixinStandardHelpOptions = true,
        description = "File sorting program", version = "1.0")
public class Application implements Callable<Integer> {
    @Option(names = "-s", description = "Sorting strings")
    @Setter
    private boolean isString;
    @Option(names = "-i", description = "Sorting ints")
    @Setter
    private boolean isInt;
    @Option(names = "-a", description = "Ascending sort")
    @Setter
    private boolean isAscending;
    @Setter
    @Option(names = "-d", description = "Descending sort")
    private boolean isDescending;
    @Parameters(description = "First parameter is output file, others input")
    @Setter
    private List<String> fileNames;

    @Override
    public Integer call() {
        if (!checkParams()) {
            return 0;
        }

        if (isInt) {
            sort(FileSupplierUtils.getBigIntFileSupplierBuilder(), BigInteger::compareTo);
        }
        else {
            sort(FileSupplierUtils.getStringFileSupplierBuilder(), String::compareTo);
        }
        return 0;
    }

    private <T> void sort(@NonNull ScannerBasedFileSupplierBuilder<T> suppliersBuilder,
                         @NonNull Comparator<T> comparator) {
        final ArrayList<ScannerBasedFileSupplier<T>> suppliers = new ArrayList<>();

        final String outputFileName = fileNames.remove(0);
        try {
            final FileConsumer<T> fileConsumer = new FileConsumer<>(outputFileName);
            for (String fileName : fileNames) {
                try {
                    suppliers.add(suppliersBuilder.build(fileName));
                } catch (FileNotFoundException e) {
                    System.err.printf("Can`t find file: %s%n", fileName);
                    suppliers.forEach(ScannerBasedFileSupplier::close);
                    return;
                }
            }

            if (isDescending) {
                comparator = comparator.reversed();
            }

            DataSorter<T> dataSorter = new DataSorter<>(comparator, new DropStrategy<>());
            dataSorter.sort(suppliers, fileConsumer);

        } catch (IOException e) {
            System.err.println("Can`t create output file");
        }
    }

    private boolean checkParams() {
        if (isString == isInt) {
            System.err.println("Incorrect args types");
            return false;
        }
        if (isAscending && isDescending) {
            System.err.println("Incorrect sorting order arg");
            return false;
        }
        if (fileNames.size() < 2) {
            System.err.println("Incorrect files list");
            return false;
        }
        return true;
    }
}
