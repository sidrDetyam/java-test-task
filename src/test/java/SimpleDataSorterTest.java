import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.gemuev.io.InMemoryConsumer;
import ru.nsu.gemuev.io.InMemorySupplier;
import ru.nsu.gemuev.sorting.DataSorter;
import ru.nsu.gemuev.sorting.DropStrategy;

import java.util.List;

public class SimpleDataSorterTest {

    @Test
    void strAscendingTest(){
        var supplier1 = new InMemorySupplier<>(List.of("1", "2", "6", "6"));
        var supplier2 = new InMemorySupplier<>(List.of("1", "2", "3", "5", "9"));
        var consumer = new InMemoryConsumer<>();

        var sorter = new DataSorter<>(String::compareTo, new DropStrategy<>());
        sorter.sort(List.of(supplier1, supplier2), consumer);

        Assertions.assertIterableEquals(
                consumer.getData(),
                List.of("1", "1", "2", "2", "3", "5", "6", "6", "9"));
    }
}
