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
    public static Comparator<File> SortByNameAscendingFolderFirst = (file1, file2) -> {
        if (file1.isDirectory()) {
            if (file2.isDirectory()) {
                return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
            } else {
                return -1;
            }
        } else {
            if (file2.isDirectory()) {
                return 1;
            } else {
                return String.valueOf(file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
            }
        }
    };
}
