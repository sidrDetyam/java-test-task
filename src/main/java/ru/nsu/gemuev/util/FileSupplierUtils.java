package ru.nsu.gemuev.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.io.ScannerBasedFileSupplierBuilder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

@UtilityClass
@Log4j2
public class FileSupplierUtils {

    public static @NonNull ScannerBasedFileSupplierBuilder<String> getStringFileSupplierBuilder() {
        return new ScannerBasedFileSupplierBuilder<>(
                FileSupplierUtils::getStringFromScanner,
                scanner -> !scanner.hasNextLine())
                .setCharset(StandardCharsets.UTF_8);
    }

    public static @NonNull ScannerBasedFileSupplierBuilder<BigInteger> getBigIntFileSupplierBuilder() {
        return new ScannerBasedFileSupplierBuilder<>(
                FileSupplierUtils::getBigIntFromScanner,
                scanner -> !scanner.hasNextLine())
                .setCharset(StandardCharsets.UTF_8);
    }

    private static @NonNull String getStringFromScanner(@NonNull final Scanner scanner) {
        final String nextString = scanner.nextLine();
        if (nextString.contains(" ")) {
            log.error("Input line contains space char: %s".formatted(nextString));
        }
        return nextString;
    }

    private static @NonNull BigInteger getBigIntFromScanner(@NonNull final Scanner scanner) {
        while (scanner.hasNextLine()) {
            final String nextLine = scanner.nextLine();
            try {
                return new BigInteger(nextLine);
            } catch (NumberFormatException e) {
                log.error("Incorrect number: %s".formatted(nextLine));
            }
        }
        throw new NoSuchElementException("There is`n any valid value");
    }
}
