package com.applandeo.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.applandeo.filepicker.R;
import com.applandeo.utils.FileUtils;
import com.applandeo.utils.ImageUtils;

import java.io.File;

import static com.applandeo.constants.FileType.APK;
import static com.applandeo.constants.FileType.ARCHIVE;
import static com.applandeo.constants.FileType.BOOK;
import static com.applandeo.constants.FileType.DOCUMENT;
import static com.applandeo.constants.FileType.IMAGE;
import static com.applandeo.constants.FileType.MUSIC;
import static com.applandeo.constants.FileType.PDF;
import static com.applandeo.constants.FileType.PRESENTATION;
import static com.applandeo.constants.FileType.SHEET;
import static com.applandeo.constants.FileType.TEXT;
import static com.applandeo.constants.FileType.VIDEO;

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
        if (mFile.isDirectory()) {
            return ImageUtils.createIcon(mContext, R.drawable.folder, R.color.directory_icon_color);
        }

        switch (FileUtils.getType(mContext, Uri.fromFile(mFile))) {
            case APK:
                return ImageUtils.createIcon(mContext, R.drawable.apk, R.color.apk_icon_color);

            case ARCHIVE:
                return ImageUtils.createIcon(mContext, R.drawable.archive, R.color.archive_icon_color);

            case BOOK:
                return ImageUtils.createIcon(mContext, R.drawable.book, R.color.book_icon_color);

            case DOCUMENT:
                return ImageUtils.createIcon(mContext, R.drawable.document, R.color.document_icon_color);

            case IMAGE:
                return ImageUtils.createIcon(mContext, R.drawable.image, R.color.image_icon_color);

            case MUSIC:
                return ImageUtils.createIcon(mContext, R.drawable.music, R.color.music_icon_color);

            case PDF:
                return ImageUtils.createIcon(mContext, R.drawable.document, R.color.pdf_icon_color);

            case PRESENTATION:
                return ImageUtils.createIcon(mContext, R.drawable.document, R.color.presentation_icon_color);

            case SHEET:
                return ImageUtils.createIcon(mContext, R.drawable.document, R.color.sheet_icon_color);

            case TEXT:
                return ImageUtils.createIcon(mContext, R.drawable.document, R.color.text_icon_color);

            case VIDEO:
                return ImageUtils.createIcon(mContext, R.drawable.movie, R.color.video_icon_color);

            default:
                return ImageUtils.createIcon(mContext, R.drawable.unknown, R.color.unknown_icon_color);
        }
    }
}
