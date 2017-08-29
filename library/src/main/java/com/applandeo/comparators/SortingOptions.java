package com.applandeo.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class SortingOptions {
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
