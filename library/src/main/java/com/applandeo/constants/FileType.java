package com.applandeo.constants;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

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
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Static variables representing a types of files
 */
@StringDef({UNKNOWN, ARCHIVE, DOCUMENT, SHEET, PRESENTATION, IMAGE, VIDEO, PDF, MUSIC, TEXT,
        APK, BOOK, DIRECTORY})
@Retention(SOURCE)
public @interface FileType {
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
