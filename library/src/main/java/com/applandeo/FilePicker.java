package com.applandeo;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

    private final Context mContext;
    private PickerDialogViewModel mPickerViewModel;

    private FilePicker(Context context, OnSelectFileListener onSelectFileListener, String path) {
        mContext = context;
        mPickerViewModel = new PickerDialogViewModel(context, path, onSelectFileListener);
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

    private void show() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        final AlertDialog alertdialog = alertBuilder.create();
        mPickerViewModel.setAlertDialog(alertdialog);

        PickerDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.picker_dialog, null, false);
        binding.setVariable(BR.viewModel, mPickerViewModel);

        alertdialog.setView(binding.getRoot());
        alertdialog.show();
    }
}

