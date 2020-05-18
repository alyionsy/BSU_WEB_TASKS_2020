package by.bsu.counter;

import java.io.File;
import java.util.Objects;

public class FileCounter {
    public static void standardCount(String directoryPath) {
        File files = new File(directoryPath);
        int count = Objects.requireNonNull(files.list()).length;
        System.out.println("Number of files: " + count);
    }
}