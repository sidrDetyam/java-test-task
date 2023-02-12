package ru.nsu.gemuev.io;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.sorting.ports.DataSupplier;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

@Log4j2
public class ScannerBasedFileSupplier<T> implements DataSupplier<T> {
    private final Scanner scanner;
    private final Function<Scanner, ? extends T> getCb;
    private final Function<Scanner, ? extends Boolean> isEndOfDataCb;

    public ScannerBasedFileSupplier(@NonNull final String fileName,
                                    @NonNull final Charset charset,
                                    @NonNull final Function<Scanner, ? extends T> getCb,
                                    @NonNull final Function<Scanner, ? extends Boolean> isEndOfDataCb) throws FileNotFoundException {
        scanner = new Scanner(new BufferedInputStream(new FileInputStream(fileName)), charset);
        this.getCb = getCb;
        this.isEndOfDataCb = isEndOfDataCb;
    }

    @Override
    public @NonNull T get() throws DataException {
        try {
            return getCb.apply(scanner);
        }
        catch (NoSuchElementException e){
            IOException ioexception = scanner.ioException();
            throw new DataException("Some io exception occurred", ioexception == null? e : ioexception);
        }
    }

    @Override
    public boolean isEndOfData() {
        return isEndOfDataCb.apply(scanner);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
