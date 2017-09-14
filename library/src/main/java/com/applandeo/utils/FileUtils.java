package com.applandeo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.StringDef;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.lang.annotation.Retention;

import static com.applandeo.utils.FileUtils.FileTypes.APK;
import static com.applandeo.utils.FileUtils.FileTypes.ARCHIVE;
import static com.applandeo.utils.FileUtils.FileTypes.BOOK;
import static com.applandeo.utils.FileUtils.FileTypes.DIRECTORY;
import static com.applandeo.utils.FileUtils.FileTypes.DOCUMENT;
import static com.applandeo.utils.FileUtils.FileTypes.IMAGE;
import static com.applandeo.utils.FileUtils.FileTypes.MUSIC;
import static com.applandeo.utils.FileUtils.FileTypes.PDF;
import static com.applandeo.utils.FileUtils.FileTypes.PRESENTATION;
import static com.applandeo.utils.FileUtils.FileTypes.SHEET;
import static com.applandeo.utils.FileUtils.FileTypes.TEXT;
import static com.applandeo.utils.FileUtils.FileTypes.UNKNOWN;
import static com.applandeo.utils.FileUtils.FileTypes.VIDEO;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Mateusz Kornakiewicz on 30.08.2017.
 */

public class FileUtils {

    @StringDef({UNKNOWN, ARCHIVE, DOCUMENT, SHEET, PRESENTATION, IMAGE, VIDEO, PDF, MUSIC, TEXT,
            APK, BOOK, DIRECTORY})
    @Retention(SOURCE)
    public @interface FileTypes {
        String UNKNOWN = "unknown";
        String ARCHIVE = "archive";
        String DOCUMENT = "document";
        String SHEET = "sheet";
        String PRESENTATION = "presentation";
        String IMAGE = "image";
        String VIDEO = "video";
        String PDF = "pdf";
        String MUSIC = "music";
        String TEXT = "text";
        String APK = "apk";
        String BOOK = "book";
        String DIRECTORY = "directory";
    }

    public static String getType(Context context, Uri uri) {
        File file = new File(uri.getPath());

        if (file.isDirectory()) {
            return DIRECTORY;
        }

        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }

        if (mimeType == null) {
            return UNKNOWN;
        }

        if (mimeType.equals("application/zip") || mimeType.equals("application/rar")) {
            return ARCHIVE;
        }

        if (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            return DOCUMENT;
        }

        if (mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return SHEET;
        }

        if (mimeType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
            return PRESENTATION;
        }

        if (mimeType.startsWith("image")) {
            return IMAGE;
        }

        if (mimeType.startsWith("video")) {
            return VIDEO;
        }

        if (mimeType.equals("application/pdf")) {
            return PDF;
        }

        if (mimeType.startsWith("audio")) {
            return MUSIC;
        }

        if (mimeType.startsWith("text")) {
            return TEXT;
        }

        if (mimeType.equals("application/vnd.android.package-archive")) {
            return APK;
        }

        if (mimeType.contains("application/epub+zip")) {
            return BOOK;
        }

        return UNKNOWN;
    }
}
