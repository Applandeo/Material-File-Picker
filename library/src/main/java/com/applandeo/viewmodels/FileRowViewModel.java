package com.applandeo.viewmodels;

import com.applandeo.filepicker.R;

import java.io.File;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class FileRowViewModel {
    private File mFile;

    public FileRowViewModel(File file) {
        mFile = file;
    }

    public File getFile() {
        return mFile;
    }

    public int getFileIconResource() {
        if (mFile.isDirectory()) {
            return R.drawable.folder;
        }

        return R.drawable.noname;
    }
}
