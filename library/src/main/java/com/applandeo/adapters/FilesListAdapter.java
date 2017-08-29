package com.applandeo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.filepicker.R;
import com.applandeo.listeners.OnRecycleViewRowClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.ViewHolder> {
    private List<File> mFilesList;
    private OnRecycleViewRowClick mOnRecycleViewRowClick;

    public FilesListAdapter(ArrayList<File> files) {
        mFilesList = files;
    }

    public void setOnRecycleViewRowClick(OnRecycleViewRowClick onRecycleViewRowClick) {
        mOnRecycleViewRowClick = onRecycleViewRowClick;
    }

    @Override
    public FilesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_row, parent, false);
        return new ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.fileName);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = mFilesList.get(position);
        holder.mTextView.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return mFilesList.size();
    }
}