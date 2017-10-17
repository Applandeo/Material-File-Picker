package com.applandeo.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.applandeo.utils.ImageUtils;

import java.io.File;

/**
 * This class represents a view model of the file list row
 *
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class FileRowViewModel extends BaseObservable {
    private Context mContext;
    private File mFile;

    FileRowViewModel(Context context, File file) {
        mContext = context;
        mFile = file;
    }

    @Bindable
    public File getFile() {
        return mFile;
    }

    @Bindable
    public Drawable getFileIconResource() {
        return ImageUtils.getFileIcon(mContext, mFile);
    }
}
