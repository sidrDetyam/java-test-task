package ru.nsu.gemuev;

import picocli.CommandLine;

public class Main {
    public static void main(String... args) {
        System.exit(new CommandLine(new Application()).execute(args));
    }
}
