package com.applandeo;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.annimon.stream.Stream;
import com.applandeo.adapters.FilesListAdapter;
import com.applandeo.filepicker.R;
import com.applandeo.listeners.OnSelectFileListener;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FilePicker {
    private static final String DEFAULT_DIR
            = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

    private final Context mContext;
    private final OnSelectFileListener mOnSelectFileListener;
    private File mFile;

    private FilePicker(Context context, OnSelectFileListener onSelectFileListener, String path) {
        mContext = context;
        mOnSelectFileListener = onSelectFileListener;

        if (path == null) {
            path = DEFAULT_DIR;
        }

        mFile = new File(path);

        if (!mFile.exists()) {
            mFile = new File(DEFAULT_DIR);
        }
    }

    public static class Builder {
        private Context mContext;
        private OnSelectFileListener mOnSelectFileListener;
        private String mPath;

        public Builder(Context context, OnSelectFileListener listener) {
            mContext = context;
            mOnSelectFileListener = listener;
        }

        public Builder directory(String directoryPath) {
            mPath = directoryPath;
            return this;
        }

        FilePicker build() {
            return new FilePicker(mContext, mOnSelectFileListener, mPath);
        }

        public void show() {
            build().show();
        }
    }

    public void show() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View view = layoutInflater.inflate(R.layout.file_picker_dialog, null);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(mFile.getName());

        RecyclerView fileList = view.findViewById(R.id.fileList);

        fileList.setLayoutManager(new LinearLayoutManager(mContext));
        fileList.setAdapter(new FilesListAdapter(openDir(mFile)));

        Button cancelButton = view.findViewById(R.id.cancel_button);
        Button selectButton = view.findViewById(R.id.select_button);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        final AlertDialog alertdialog = alertBuilder.create();
        alertdialog.setView(view);

        toolbar.setNavigationOnClickListener(v -> alertdialog.cancel());

        cancelButton.setOnClickListener(v -> alertdialog.cancel());

        selectButton.setOnClickListener(v -> {
            alertdialog.cancel();
            mOnSelectFileListener.onSelect(mFile);
        });

        alertdialog.show();
    }

    private ArrayList<File> openDir(File directory) {
        ArrayList<File> list = new ArrayList<>();

        File[] files = directory.listFiles();

        if (files != null) {
            Stream.of(files).filter(File::exists).forEach(list::add);
        }

        return list;
    }
}

