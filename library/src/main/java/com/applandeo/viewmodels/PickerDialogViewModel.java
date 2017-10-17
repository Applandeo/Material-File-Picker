package com.applandeo.viewmodels;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.annimon.stream.Stream;
import com.applandeo.adapters.FileAdapter;
import com.applandeo.comparators.SortingOptions;
import com.applandeo.filepicker.BR;
import com.applandeo.listeners.OnRecyclerViewRowClick;
import com.applandeo.listeners.OnSelectFileListener;
import com.applandeo.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.applandeo.constants.FileType.DIRECTORY;

/**
 * This class represents a view model of the file picker dialog
 * <p>
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class PickerDialogViewModel extends BaseObservable implements OnRecyclerViewRowClick {
    private static final String DEFAULT_DIR
            = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;

    private Activity mActivity;
    private final OnSelectFileListener mOnSelectFileListener;
    private AlertDialog mAlertDialog;
    private File mCurrentFile;
    private File mMainDirectory = Environment.getExternalStorageDirectory();
    private boolean mHideFiles;
    private String mFilesType;

    private ObservableList<FileRowViewModel> mFilesList = new ObservableArrayList<>();
    private FileAdapter mAdapter = new FileAdapter(mFilesList);

    private List<Integer> mPositions = new ArrayList<>();
    private int mCurrentListPosition;
    private int mListPosition;

    public PickerDialogViewModel(Activity activity, String path, OnSelectFileListener onSelectFileListener,
                                 boolean hideFiles, String mainDirectory, String filesType) {
        mActivity = activity;
        mOnSelectFileListener = onSelectFileListener;
        mAdapter.setOnRecycleViewRowClick(this);
        mHideFiles = hideFiles;
        mFilesType = filesType;

        if (path == null) {
            path = DEFAULT_DIR;
        }

        File file = new File(path);

        if (!file.exists()) {
            file = new File(DEFAULT_DIR);
        }

        setMainDirectory(path, mainDirectory);

        openDirectory(file, 0);
    }

    private void setMainDirectory(String path, String mainDirectory) {
        if (mainDirectory != null) {
            if (path.length() < mainDirectory.length()) {
                mMainDirectory = mCurrentFile;
            } else {
                File mainDir = new File(mainDirectory);

                if (mainDir.exists()) {
                    mMainDirectory = mainDir;
                }
            }
        }
    }

    /**
     * Listener to handle toolbar's navigation button click
     */
    public final View.OnClickListener onToolbarIconClickListener = v -> {
        File parent = mCurrentFile.getParentFile();

        if (mCurrentFile.equals(mMainDirectory)) {
            mAlertDialog.cancel();
            return;
        }

        if (parent != null) {
            int position = 0;

            if (!mPositions.isEmpty()) {
                position = mPositions.get(mPositions.size() - 1);
                mPositions.remove(mPositions.size() - 1);
            }

            openDirectory(parent, position);
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

    @Bindable
    public int getPosition() {
        return mListPosition;
    }

    private void setListPosition(int position) {
        mListPosition = position;
        notifyPropertyChanged(BR.position);
    }

    public void onCancel() {
        mAlertDialog.cancel();
    }

    public void onSelect() {
        mAlertDialog.cancel();
        mOnSelectFileListener.onSelect(mCurrentFile);
    }

    /**
     * ScrollListener needed to get current list position
     */
    public final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == SCROLL_STATE_IDLE) {
                mCurrentListPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
            }
        }
    };

    /**
     * Asynchronous method to getting files list
     *
     * @param directory An instance of parent File object
     * @param position  Position of the files list
     */
    private void openDirectory(File directory, int position) {
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
                    setListPosition(position);
                });
    }

    /**
     * This method is used to getting files list
     *
     * @param directory An instance of parent File object
     * @return A list of FileRowViewModels which contain an instance of File object
     */
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

            if (mFilesType != null) {
                filteredList = Stream.of(filteredList)
                        .filter(file -> (FileUtils.getType(mActivity, Uri.fromFile(file)).equals(mFilesType)
                                || FileUtils.getType(mActivity, Uri.fromFile(file)).equals(DIRECTORY))).toList();
            }

            Stream.of(filteredList)
                    .sorted(SortingOptions.SortByNameAscendingFolderFirst)
                    .forEach(file -> list.add(new FileRowViewModel(mActivity, file)));
        }

        return list;
    }

    /**
     * Listener to handle files list row click
     *
     * @param file Clicked file
     */
    @Override
    public void onClick(File file) {
        if (file.isDirectory()) {
            mPositions.add(mCurrentListPosition);
            openDirectory(file, 0);
            return;
        }

        mAlertDialog.cancel();
        mOnSelectFileListener.onSelect(file);
    }
}
