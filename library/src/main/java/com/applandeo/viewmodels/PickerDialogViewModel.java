package com.applandeo.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.annimon.stream.Stream;
import com.applandeo.adapters.FileAdapter;
import com.applandeo.comparators.SortingOptions;
import com.applandeo.filepicker.BR;
import com.applandeo.listeners.OnRecyclerViewRowClick;
import com.applandeo.listeners.OnSelectFileListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class PickerDialogViewModel extends BaseObservable implements OnRecyclerViewRowClick {
    private static final String DEFAULT_DIR
            = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

    private Context mContext;
    private final OnSelectFileListener mOnSelectFileListener;
    private AlertDialog mAlertDialog;
    private File mCurrentFile;
    private File mMainDirectory = Environment.getExternalStorageDirectory();
    private boolean mHideFiles;

    private ObservableList<FileRowViewModel> mFilesList = new ObservableArrayList<>();
    private FileAdapter mAdapter = new FileAdapter(mFilesList);

    public PickerDialogViewModel(Context context, String path, OnSelectFileListener onSelectFileListener, boolean hideFiles, String mainDirectory) {
        mContext = context;
        mOnSelectFileListener = onSelectFileListener;
        mAdapter.setOnRecycleViewRowClick(this);
        mHideFiles = hideFiles;

        if (mainDirectory != null) {
            File mainDir = new File(mainDirectory);

            if (mainDir.exists()) {
                mMainDirectory = mainDir;
            }
        }

        if (path == null) {
            path = DEFAULT_DIR;
        }

        setCurrentFile(new File(path));

        if (!mCurrentFile.exists()) {
            setCurrentFile(new File(DEFAULT_DIR));
        }

        openDirectory(mCurrentFile);
    }

    public final View.OnClickListener onToolbarIconClickListener = v -> {
        File parent = mCurrentFile.getParentFile();

        if (mCurrentFile.equals(mMainDirectory)) {
            mAlertDialog.cancel();
            return;
        }

        if (parent != null) {
            openDirectory(parent);
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
    public File getParentDirectory() {
        return mMainDirectory;
    }

    @Bindable
    public ObservableList<FileRowViewModel> getFileList() {
        return mFilesList;
    }

    @Bindable
    public FileAdapter getAdapter() {
        return mAdapter;
    }

    public void onCancel() {
        mAlertDialog.cancel();
    }

    public void onSelect() {
        mAlertDialog.cancel();
        mOnSelectFileListener.onSelect(mCurrentFile);
    }

    private void openDirectory(File directory) {
        Single<List<FileRowViewModel>> filesList = Single.create(emitter -> {
            try {
                List<FileRowViewModel> list = getFiles(directory);
                emitter.onSuccess(list);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        filesList.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileRowViewModels -> {
                    setCurrentFile(directory);
                    mFilesList.clear();
                    mFilesList.addAll(fileRowViewModels);
                });
    }

    private List<FileRowViewModel> getFiles(File directory) {
        List<FileRowViewModel> list = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            List<File> filteredList = Stream.of(files)
                    .filter(File::exists)
                    .filter(file -> !file.isHidden()).toList();

            if (mHideFiles) {
                filteredList = Stream.of(filteredList).filter(File::isDirectory).toList();
            }

            Stream.of(filteredList).forEach(file -> list.add(new FileRowViewModel(mContext, file)));
        }

        Collections.sort(list, SortingOptions.SortByNameAscendingFolderFirst);

        return list;
    }

    @Override
    public void onClick(File file) {
        if (file.isDirectory()) {
            openDirectory(file);
            return;
        }

        mAlertDialog.cancel();
        mOnSelectFileListener.onSelect(file);
    }
}
