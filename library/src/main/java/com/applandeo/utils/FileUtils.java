package com.applandeo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;

import static com.applandeo.constants.FileType.APK;
import static com.applandeo.constants.FileType.ARCHIVE;
import static com.applandeo.constants.FileType.BOOK;
import static com.applandeo.constants.FileType.DIRECTORY;
import static com.applandeo.constants.FileType.DOCUMENT;
import static com.applandeo.constants.FileType.IMAGE;
import static com.applandeo.constants.FileType.MUSIC;
import static com.applandeo.constants.FileType.PDF;
import static com.applandeo.constants.FileType.PRESENTATION;
import static com.applandeo.constants.FileType.SHEET;
import static com.applandeo.constants.FileType.TEXT;
import static com.applandeo.constants.FileType.UNKNOWN;
import static com.applandeo.constants.FileType.VIDEO;
import static com.applandeo.utils.FileUtils.FileMimeTypes.APK_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.AUDIO_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.DOCX_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.DOC_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.EPUB_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.IMAGE_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.PDF_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.PPTX_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.PPT_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.RAR_ARCHIVE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.TEXT_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.VIDEO_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.XLSX_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.XLS_FILE;
import static com.applandeo.utils.FileUtils.FileMimeTypes.ZIP_ARCHIVE;

/**
 * Created by Mateusz Kornakiewicz on 30.08.2017.
 */

public class FileUtils {

    /**
     * This method returns a String representing a type of the file using static variable from
     * the FileType interface
     *
     * @param context An application context needed to get content resolver and find type of the file
     * @param fileUri Uri of the file needed to get the file type using ContentResolver
     * @return A string representing type of file
     */
    public static String getType(Context context, Uri fileUri) {
        File file = new File(fileUri.getPath());

        if (file.isDirectory()) {
            return DIRECTORY;
        }

        String mimeType;
        if (fileUri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(fileUri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }

        if (mimeType == null) {
            return UNKNOWN;
        }

        if (mimeType.equals(ZIP_ARCHIVE) || mimeType.equals(RAR_ARCHIVE)) {
            return ARCHIVE;
        }

        if (mimeType.equals(DOC_FILE) || mimeType.equals(DOCX_FILE)) {
            return DOCUMENT;
        }

        if (mimeType.equals(XLS_FILE) || mimeType.equals(XLSX_FILE)) {
            return SHEET;
        }

        if (mimeType.equals(PPT_FILE) || mimeType.equals(PPTX_FILE)) {
            return PRESENTATION;
        }

        if (mimeType.startsWith(IMAGE_FILE)) {
            return IMAGE;
        }

        if (mimeType.startsWith(VIDEO_FILE)) {
            return VIDEO;
        }

        if (mimeType.equals(PDF_FILE)) {
            return PDF;
        }

        if (mimeType.startsWith(AUDIO_FILE)) {
            return MUSIC;
        }

        if (mimeType.startsWith(TEXT_FILE)) {
            return TEXT;
        }

        if (mimeType.equals(APK_FILE)) {
            return APK;
        }

        if (mimeType.contains(EPUB_FILE)) {
            return BOOK;
        }

        return UNKNOWN;
    }

    class FileMimeTypes {
        final static String ZIP_ARCHIVE = "application/zip";
        final static String RAR_ARCHIVE = "application/rar";
        final static String DOC_FILE = "application/msword";
        final static String DOCX_FILE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        final static String XLS_FILE = "application/vnd.ms-excel";
        final static String XLSX_FILE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        final static String PPT_FILE = "application/vnd.ms-powerpoint";
        final static String PPTX_FILE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        final static String IMAGE_FILE = "image";
        final static String VIDEO_FILE = "video";
        final static String PDF_FILE = "application/pdf";
        final static String AUDIO_FILE = "audio";
        final static String TEXT_FILE = "text";
        final static String APK_FILE = "application/vnd.android.package-archive";
        final static String EPUB_FILE = "application/epub+zip";

        private FileMimeTypes() {
        }
    }
}
