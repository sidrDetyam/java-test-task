
# Что это:
Тестовое задание Java ШИФТ. Гемуев А.Б.

# Реализация:

В пакете sorting находится логика сортировки файлов. Основной класс - DataSorter.
Он принимает данные от реализаций интерфейса DataSupplier и передает отсортированные
данные в DataConsumer. Обработку неотсортированных данных DataSorter делегирует
UnsortedDataHandlingStrategy. 

В пакете io находятся реализации интерфейсов DataConsumer и DataSupplier.



# Запуск c помощью Linux и Gradle 7.5.1:
    git clone https://github.com/sidrDetyam/java-test-task.git
Перейдите в директорию с проетом
    
    gradle wrapper

    ./gradlew :build

    ./gradlew -q :run --args="{аргументы программы}"

Пример:

    ./gradlew -q :run --args="-i -d out in1 in2 in3"