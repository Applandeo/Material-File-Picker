package com.applandeo.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.filepicker.BR;
import com.applandeo.filepicker.R;
import com.applandeo.listeners.OnRecyclerViewRowClick;
import com.applandeo.viewmodels.FileRowViewModel;

import java.io.File;

/**
 * Created by Mateusz Kornakiewicz on 01.08.2017.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private ObservableList<FileRowViewModel> mFileRowViewModels;
    private OnRecyclerViewRowClick mOnRecyclerViewRowClick;

    public FileAdapter(ObservableList<FileRowViewModel> fileRowViewModels) {
        mFileRowViewModels = fileRowViewModels;
        mFileRowViewModels.addOnListChangedCallback(new OnListChanged());
    }

    public void setOnRecycleViewRowClick(OnRecyclerViewRowClick onRecyclerViewRowClick) {
        mOnRecyclerViewRowClick = onRecyclerViewRowClick;
    }

    private OnRecyclerViewRowClick getOnRecyclerViewRowClickListener() {
        return mOnRecyclerViewRowClick;
    }

    private File getFile(int position) {
        return mFileRowViewModels.get(position).getFile();
    }

    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_row, parent, false);
        ViewDataBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.file_row, parent, false);
        return new ViewHolder(binding, this);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewDataBinding mBinding;
        private FileAdapter mAdapter;

        ViewHolder(ViewDataBinding binding, FileAdapter filesListAdapter) {
            super(binding.getRoot());
            mBinding = binding;
            mAdapter = filesListAdapter;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAdapter.getOnRecyclerViewRowClickListener().onClick(mAdapter.getFile(getAdapterPosition()));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FileRowViewModel rowViewModel = mFileRowViewModels.get(position);
        holder.mBinding.setVariable(BR.rowViewModel, rowViewModel);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mFileRowViewModels.size();
    }

    private class OnListChanged extends ObservableList.OnListChangedCallback {
        @Override
        public void onChanged(ObservableList sender) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }
    }
}