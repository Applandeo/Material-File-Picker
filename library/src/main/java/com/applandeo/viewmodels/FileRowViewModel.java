package com.applandeo.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;

import com.applandeo.filepicker.R;
import com.applandeo.utils.FileUtils;

import java.io.File;

import static com.applandeo.utils.FileUtils.FileTypes.APK;
import static com.applandeo.utils.FileUtils.FileTypes.ARCHIVE;
import static com.applandeo.utils.FileUtils.FileTypes.BOOK;
import static com.applandeo.utils.FileUtils.FileTypes.DOCUMENT;
import static com.applandeo.utils.FileUtils.FileTypes.IMAGE;
import static com.applandeo.utils.FileUtils.FileTypes.MUSIC;
import static com.applandeo.utils.FileUtils.FileTypes.PDF;
import static com.applandeo.utils.FileUtils.FileTypes.PRESENTATION;
import static com.applandeo.utils.FileUtils.FileTypes.SHEET;
import static com.applandeo.utils.FileUtils.FileTypes.TEXT;
import static com.applandeo.utils.FileUtils.FileTypes.VIDEO;

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

        switch (FileUtils.getType(mContext, Uri.fromFile(mFile))) {
            case APK:
                return R.drawable.apk;

            case ARCHIVE:
                return R.drawable.archive;

            case BOOK:
                return R.drawable.book;

            case DOCUMENT:
                return R.drawable.doc;

            case IMAGE:
                return R.drawable.image;

            case MUSIC:
                return R.drawable.music;

            case PDF:
                return R.drawable.pdf;

            case PRESENTATION:
                return R.drawable.ppt;

            case SHEET:
                return R.drawable.xls;

            case TEXT:
                return R.drawable.text;

            case VIDEO:
                return R.drawable.video;

            default:
                return R.drawable.noname;
        }
    }
}
