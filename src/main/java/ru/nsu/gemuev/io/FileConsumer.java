package ru.nsu.gemuev.io;

import lombok.NonNull;
import lombok.Setter;
import ru.nsu.gemuev.exceptions.DataException;
import ru.nsu.gemuev.sorting.ports.DataConsumer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.function.Function;

public class FileConsumer<T> implements DataConsumer<T> {
    private final PrintWriter printWriter;
    @Setter
    @NonNull private Function<? super T, ? extends String> dataToString = T::toString;

    public FileConsumer(@NonNull final String fileName,
                        @NonNull final Charset charset) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, charset);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        printWriter = new PrintWriter(bufferedWriter);
    }

    public FileConsumer(@NonNull final String fileName) throws IOException {
        this(fileName, Charset.defaultCharset());
    }

    @Override
    public void accept(@NonNull final T data) throws DataException {
        printWriter.println(dataToString.apply(data));
        if(printWriter.checkError()){
            throw new DataException("Some io errors occurred");
        }
    }

    @Override
    public void close() {
        printWriter.close();
    }
}
