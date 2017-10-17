package com.applandeo.filepickersampleapp;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.applandeo.FilePicker;
import com.applandeo.listeners.OnSelectFileListener;
import com.applandeo.constants.FileType;

public class MainActivity extends AppCompatActivity {

    private TextView mSelectedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openPickerButton = (Button) findViewById(R.id.openPicker);
        mSelectedPath = (TextView) findViewById(R.id.selectedPath);

        openPickerButton.setOnClickListener(view -> openPicker());
    }

    private void openPicker() {
        new FilePicker.Builder(this, listener)
                //this method let you decide how far user can go up in the directories tree
                .mainDirectory(Environment.getExternalStorageDirectory().getPath())
                //this method let you choose what types of files user will see in the picker
                .fileType(FileType.IMAGE)
                //this method let you hide files, only directories will be visible for user
                .hideFiles(false)
                //this method let you decide which directory user will see after picker opening
                .directory(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS)
                .show();
    }

    //listener to handle getting file
    private OnSelectFileListener listener = file -> mSelectedPath.setText(file.getPath());

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                //using flag below you can check if user granted storage permissions for the picker
                && requestCode == FilePicker.STORAGE_PERMISSIONS) {
            openPicker();
        }
    }
}
