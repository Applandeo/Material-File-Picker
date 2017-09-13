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
import com.applandeo.viewmodels.PickerDialogViewModel;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FilePicker {

    private final Activity mActivity;
    private PickerDialogViewModel mPickerViewModel;

    private FilePicker(Activity activity, OnSelectFileListener onSelectFileListener, String path, boolean hideFiles, String mainDirectory) {
        mActivity = activity;
        mPickerViewModel = new PickerDialogViewModel(activity, path, onSelectFileListener, hideFiles, mainDirectory);
    }

    public static class Builder {
        private Activity mActivity;
        private OnSelectFileListener mOnSelectFileListener;
        private String mPath;
        private boolean mHideFiles;
        private String mMainDirectory;

        public Builder(Activity activity, OnSelectFileListener listener) {
            mActivity = activity;
            mOnSelectFileListener = listener;
        }

        public Builder directory(String directoryPath) {
            mPath = directoryPath;
            return this;
        }

        public Builder hideFiles(boolean hideFiles) {
            mHideFiles = hideFiles;
            return this;
        }

        public Builder setMainDirectory(String mainDirectory) {
            mMainDirectory = mainDirectory;
            return this;
        }

        FilePicker build() {
            return new FilePicker(mActivity, mOnSelectFileListener, mPath, mHideFiles, mMainDirectory);
        }

        public void show() {
            build().show();
        }
    }

    private void show() {
        if (ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 6);
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

