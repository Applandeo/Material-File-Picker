package com.applandeo.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * This class contains comparators using to sort files
 * <p>
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class SortingOptions {

    /**
     * This Comparator compare files using their names and types. Files are sorted ascending [A-Z],
     * directories are placed before files.
     */
    public static Comparator<File> SortByNameAscendingFolderFirst = (firstFile, secondFile) -> {
        if (firstFile.isDirectory()) {
            return secondFile.isDirectory() ? compareFileNames(firstFile, secondFile) : -1;
        } else {
            return secondFile.isDirectory() ? 1 : compareFileNames(firstFile, secondFile);
        }
    };

    private static int compareFileNames(File firstFile, File secondFile) {
        return String.valueOf(firstFile.getName().toLowerCase())
                .compareTo(secondFile.getName().toLowerCase());
    }
}
