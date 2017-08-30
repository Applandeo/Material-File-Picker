package com.applandeo.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.applandeo.filepicker.R;
import com.applandeo.utils.FileUtils;

import java.io.File;

/**
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
    public int getFileIconResource() {
        if (mFile.isDirectory()) {
            return R.drawable.folder;
        }

        System.out.println(mFile.getName() + " " + FileUtils.getMimeType(mContext, Uri.fromFile(mFile)));

        return R.drawable.noname;
    }
}
