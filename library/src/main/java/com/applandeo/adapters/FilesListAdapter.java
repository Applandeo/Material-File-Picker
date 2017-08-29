package com.applandeo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.filepicker.R;
import com.applandeo.listeners.OnRecyclerViewRowClick;
import com.applandeo.utils.GlideCircleTransformation;
import com.applandeo.viewmodels.FileRowViewModel;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.ViewHolder> {
    private List<FileRowViewModel> mFileRowViewModels;
    private OnRecyclerViewRowClick mOnRecyclerViewRowClick;

    public FilesListAdapter(ArrayList<FileRowViewModel> fileRowViewModels) {
        mFileRowViewModels = fileRowViewModels;
    }

    public void setFileList(List<FileRowViewModel> fileRowViewModels) {
        mFileRowViewModels = fileRowViewModels;
        notifyDataSetChanged();
    }

    public void setOnRecycleViewRowClick(OnRecyclerViewRowClick onRecyclerViewRowClick) {
        mOnRecyclerViewRowClick = onRecyclerViewRowClick;
    }

    public OnRecyclerViewRowClick getOnRecyclerViewRowClickListener() {
        return mOnRecyclerViewRowClick;
    }

    private File getFile(int position) {
        return mFileRowViewModels.get(position).getFile();
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
        FileRowViewModel fileRowViewModel = mFileRowViewModels.get(position);

        Glide.with(holder.mFileIcon.getContext())
                .load(fileRowViewModel.getFile())
                .transform(new GlideCircleTransformation(holder.mFileIcon.getContext()))
                .error(fileRowViewModel.getFileIconResource())
                .placeholder(fileRowViewModel.getFileIconResource())
                .into(holder.mFileIcon);

        holder.mFileName.setText(fileRowViewModel.getFile().getName());
    }

    @Override
    public int getItemCount() {
        return mFileRowViewModels.size();
    }
}