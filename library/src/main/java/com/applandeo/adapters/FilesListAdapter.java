package com.applandeo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.filepicker.R;
import com.applandeo.listeners.OnRecyclerViewRowClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.ViewHolder> {
    private List<File> mFilesList;
    private OnRecyclerViewRowClick mOnRecyclerViewRowClick;

    public FilesListAdapter(ArrayList<File> files) {
        mFilesList = files;
    }

    public void setFileList(List<File> fileList) {
        mFilesList = fileList;
        notifyDataSetChanged();
    }

    public void setOnRecycleViewRowClick(OnRecyclerViewRowClick onRecyclerViewRowClick) {
        mOnRecyclerViewRowClick = onRecyclerViewRowClick;
    }

    public OnRecyclerViewRowClick getOnRecyclerViewRowClickListener() {
        return mOnRecyclerViewRowClick;
    }

    private File getFile(int position) {
        return mFilesList.get(position);
    }

    @Override
    public FilesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_row, parent, false);
        return new ViewHolder(view, this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mFileName;
        private ImageView mFileIcon;
        private FilesListAdapter mAdapter;

        public ViewHolder(View view, FilesListAdapter filesListAdapter) {
            super(view);
            mFileIcon = view.findViewById(R.id.fileIcon);
            mFileName = view.findViewById(R.id.fileName);
            mAdapter = filesListAdapter;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAdapter.getOnRecyclerViewRowClickListener().onClick(mAdapter.getFile(getAdapterPosition()));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = mFilesList.get(position);

        if (file.isDirectory()) {
            holder.mFileIcon.setImageResource(R.drawable.folder);
        } else {
            holder.mFileIcon.setImageResource(R.drawable.noname);
        }

        holder.mFileName.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return mFilesList.size();
    }
}