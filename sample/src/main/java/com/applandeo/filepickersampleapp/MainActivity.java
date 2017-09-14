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

import static com.applandeo.utils.FileUtils.FileTypes.TEXT;

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

    private OnSelectFileListener listener = file -> mSelectedPath.setText(file.getPath());

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && requestCode == FilePicker.STORAGE_PERMISSIONS) {
            openPicker();
        }
    }

    private void openPicker() {
        new FilePicker.Builder(this, listener)
                .setMainDirectory(Environment.getExternalStorageDirectory().getPath())
                .fileType(TEXT)
                .hideFiles(false)
                .directory(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS)
                .show();
    }
}
