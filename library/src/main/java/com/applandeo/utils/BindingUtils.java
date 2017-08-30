package com.applandeo.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.applandeo.adapters.FileAdapter;
import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class BindingUtils {
    @BindingAdapter("app:navigationOnClickListener")
    public static void setNavigationOnClickListener(Toolbar toolbar, View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    @BindingAdapter({"app:setAdapter"})
    public static void setAdapter(RecyclerView recyclerView, FileAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"app:file", "app:iconResource"})
    public static void loadFileIcon(ImageView imageView, File file, int placeholder) {
        Glide.with(imageView.getContext())
                .load(file)
                .transform(new GlideCircleTransformation(imageView.getContext()))
                .error(placeholder)
                .placeholder(placeholder)
                .into(imageView);
    }
}
