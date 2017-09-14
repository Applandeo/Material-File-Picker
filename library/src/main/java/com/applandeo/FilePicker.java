package com.applandeo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.applandeo.filepicker.BR;
import com.applandeo.filepicker.R;
import com.applandeo.filepicker.databinding.PickerDialogBinding;
import com.applandeo.listeners.OnSelectFileListener;
import com.applandeo.utils.FileUtils;
import com.applandeo.viewmodels.PickerDialogViewModel;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FilePicker {
    public static final int STORAGE_PERMISSIONS = 6;

    private final Activity mActivity;
    private PickerDialogViewModel mPickerViewModel;

    private FilePicker(Activity activity, OnSelectFileListener onSelectFileListener, String path,
                       boolean hideFiles, String mainDirectory, String filesType) {
        mActivity = activity;
        mPickerViewModel = new PickerDialogViewModel(activity, path, onSelectFileListener, hideFiles,
                mainDirectory, filesType);
    }

    public static class Builder {
        private Activity mActivity;
        private OnSelectFileListener mOnSelectFileListener;
        private String mPath;
        private boolean mHideFiles;
        private String mMainDirectory;
        private String mFilesType;

        public Builder(Activity activity, OnSelectFileListener listener) {
            mActivity = activity;
            mOnSelectFileListener = listener;
        }

        /**
         * This method let you decide which directory user will see after picker opening
         *
         * @param directoryPath A desired directory path
         */
        public Builder directory(String directoryPath) {
            mPath = directoryPath;
            return this;
        }

        /**
         * This method let you hide files, only directories will be visible for user
         *
         * @param hideFiles Set if files should be hidden or not
         */
        public Builder hideFiles(boolean hideFiles) {
            mHideFiles = hideFiles;
            return this;
        }

        /**
         * This method let you decide how far user can go up in directories tree
         *
         * @param mainDirectory A main directory path
         */
        public Builder setMainDirectory(String mainDirectory) {
            mMainDirectory = mainDirectory;
            return this;
        }

        /**
         * This method let you choose what types of files user will see in the picker
         *
         * @param type A type of file. Use static variable from FileUtils.FileTypes interface.
         *             You can use "APK", "ARCHIVE", "BOOK", "DOCUMENT", "IMAGE", "MUSIC", "PDF",
         *             "PRESENTATION", "SHEET", "TEXT", "VIDEO"
         */
        public Builder fileType(@FileUtils.FileTypes String type) {
            mFilesType = type;
            return this;
        }

        FilePicker build() {
            return new FilePicker(mActivity, mOnSelectFileListener, mPath, mHideFiles, mMainDirectory,
                    mFilesType);
        }

        /**
         * This method is used to create and display file picker
         */
        public void show() {
            build().show();
        }
    }

    /**
     * This method creates and display file picker dialog
     */
    private void show() {
        if (ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSIONS);
            return;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mActivity);
        final AlertDialog alertdialog = alertBuilder.create();
        mPickerViewModel.setAlertDialog(alertdialog);

        PickerDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.picker_dialog, null, false);
        binding.setVariable(BR.viewModel, mPickerViewModel);

        alertdialog.setView(binding.getRoot());
        alertdialog.show();
    }
}

