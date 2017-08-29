package com.applandeo.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.annimon.stream.Stream;
import com.applandeo.adapters.RecyclerAdapter;
import com.applandeo.comparators.SortingOptions;
import com.applandeo.filepicker.BR;
import com.applandeo.listeners.OnRecyclerViewRowClick;
import com.applandeo.listeners.OnSelectFileListener;

import java.io.File;
import java.util.Collections;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class PickerDialogViewModel extends BaseObservable implements OnRecyclerViewRowClick {
    private static final String DEFAULT_DIR
            = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

    private final OnSelectFileListener mOnSelectFileListener;
    private AlertDialog mAlertDialog;
    private File mCurrentFile;

    private ObservableList<FileRowViewModel> mFilesList = new ObservableArrayList<>();
    private RecyclerAdapter mAdapter = new RecyclerAdapter(mFilesList);

    public PickerDialogViewModel(String path, OnSelectFileListener onSelectFileListener) {
        mOnSelectFileListener = onSelectFileListener;
        mAdapter.setOnRecycleViewRowClick(this);

        if (path == null) {
            path = DEFAULT_DIR;
        }

        setCurrentFile(new File(path));

        if (!mCurrentFile.exists()) {
            setCurrentFile(new File(DEFAULT_DIR));
        }

        openDir(mCurrentFile);
    }

    public final View.OnClickListener onToolbarIconClickListener = v -> {
        File parent = mCurrentFile.getParentFile();

        if (parent != null) {
            openDir(parent);
        }
    };

    public void setAlertDialog(AlertDialog alertDialog) {
        mAlertDialog = alertDialog;
    }

    @Bindable
    public File getCurrentFile() {
        return mCurrentFile;
    }

    private void setCurrentFile(File currentFile) {
        mCurrentFile = currentFile;
        notifyPropertyChanged(BR.currentFile);
    }

    @Bindable
    public RecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public void onCancel() {
        mAlertDialog.cancel();
    }

    public void onSelect() {
        mAlertDialog.cancel();
        mOnSelectFileListener.onSelect(mCurrentFile);
    }

    private void openDir(File directory) {
        setCurrentFile(directory);
        mFilesList.clear();

        File[] files = directory.listFiles();

        if (files != null) {
            Stream.of(files).filter(File::exists).forEach(file -> mFilesList.add(new FileRowViewModel(file)));
        }

        Collections.sort(mFilesList, SortingOptions.SortByNameAscendingFolderFirst);
    }

    @Override
    public void onClick(File file) {
        if (file.isDirectory()) {
            openDir(file);
            return;
        }

        mAlertDialog.cancel();
        mOnSelectFileListener.onSelect(file);
    }
}
