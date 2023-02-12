package ru.nsu.gemuev.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.function.Function;

@RequiredArgsConstructor
public class ScannerBasedFileSupplierBuilder<T> {
    private Charset charset;
    @NonNull private final Function<Scanner, ? extends T> getCb;
    @NonNull private final Function<Scanner, ? extends Boolean> isEndOfDataCb;

    public @NonNull ScannerBasedFileSupplierBuilder<T> setCharset(@NonNull final Charset charset){
        this.charset = charset;
        return this;
    }

    public ScannerBasedFileSupplier<T> build(@NonNull String fileName) throws FileNotFoundException {
        return new ScannerBasedFileSupplier<>(
                fileName,
                charset==null? Charset.defaultCharset() : charset,
                getCb,
                isEndOfDataCb);
    }
}
