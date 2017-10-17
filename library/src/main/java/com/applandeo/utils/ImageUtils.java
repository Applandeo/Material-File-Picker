package com.applandeo.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.applandeo.filepicker.R;

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
 * Created by Mateusz Kornakiewicz on 30.08.2017.
 */

public class ImageUtils {

    /**
     * This method returns icon for specific file
     *
     * @param context An application context needed to get resources
     * @param file File object necessary to create file icon
     * @return Created drawable icon for file
     */
    public static Drawable getFileIcon(Context context, File file) {
        if (file.isDirectory()) {
            return ImageUtils.createIcon(context, R.drawable.folder, R.color.directory_icon_color);
        }

        switch (FileUtils.getType(context, Uri.fromFile(file))) {
            case APK:
                return ImageUtils.createIcon(context, R.drawable.apk, R.color.apk_icon_color);

            case ARCHIVE:
                return ImageUtils.createIcon(context, R.drawable.archive, R.color.archive_icon_color);

            case BOOK:
                return ImageUtils.createIcon(context, R.drawable.book, R.color.book_icon_color);

            case DOCUMENT:
                return ImageUtils.createIcon(context, R.drawable.document, R.color.document_icon_color);

            case IMAGE:
                return ImageUtils.createIcon(context, R.drawable.image, R.color.image_icon_color);

            case MUSIC:
                return ImageUtils.createIcon(context, R.drawable.music, R.color.music_icon_color);

            case PDF:
                return ImageUtils.createIcon(context, R.drawable.document, R.color.pdf_icon_color);

            case PRESENTATION:
                return ImageUtils.createIcon(context, R.drawable.document, R.color.presentation_icon_color);

            case SHEET:
                return ImageUtils.createIcon(context, R.drawable.document, R.color.sheet_icon_color);

            case TEXT:
                return ImageUtils.createIcon(context, R.drawable.document, R.color.text_icon_color);

            case VIDEO:
                return ImageUtils.createIcon(context, R.drawable.movie, R.color.video_icon_color);

            default:
                return ImageUtils.createIcon(context, R.drawable.unknown, R.color.unknown_icon_color);
        }
    }

    /**
     * This method is using to create files icons
     *
     * @param context  An application context needed to get resources
     * @param icon     Resource of the foreground file icon
     * @param bcgColor Resource of the color of icon background
     * @return LayerDrawable consists of the file icon and background
     */
    private static Drawable createIcon(Context context, @DrawableRes int icon, @ColorRes int bcgColor) {
        Drawable backgroundLayer = ContextCompat.getDrawable(context, R.drawable.icon_circle_bacground);
        backgroundLayer.setColorFilter(ContextCompat.getColor(context, bcgColor), PorterDuff.Mode.SRC);

        Drawable iconLayer = ContextCompat.getDrawable(context, icon);

        Drawable[] layers = {backgroundLayer, iconLayer};

        return new LayerDrawable(layers);
    }
}
