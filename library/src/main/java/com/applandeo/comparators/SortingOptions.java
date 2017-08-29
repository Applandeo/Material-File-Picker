package com.applandeo.comparators;

import com.applandeo.viewmodels.FileRowViewModel;

import java.util.Comparator;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class SortingOptions {
    public static Comparator<FileRowViewModel> SortByNameAscendingFolderFirst = (file1, file2) -> {
        if (file1.getFile().isDirectory()) {
            if (file2.getFile().isDirectory()) {
                return String.valueOf(file1.getFile().getName().toLowerCase()).compareTo(file2.getFile().getName().toLowerCase());
            } else {
                return -1;
            }
        } else {
            if (file2.getFile().isDirectory()) {
                return 1;
            } else {
                return String.valueOf(file1.getFile().getName().toLowerCase()).compareTo(file2.getFile().getName().toLowerCase());
            }
        }
    };
}
