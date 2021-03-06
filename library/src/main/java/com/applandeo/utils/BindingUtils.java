package com.applandeo.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.applandeo.adapters.FileAdapter;
import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Utilities class needed to use DataBinding with unsupported methods of widgets
 * <p>
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class BindingUtils {
    @BindingAdapter("app:navigationOnClickListener")
    public static void setNavigationOnClickListener(Toolbar toolbar, View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    @BindingAdapter("app:setAdapter")
    public static void setAdapter(RecyclerView recyclerView, FileAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"app:file", "app:iconResource"})
    public static void loadFileIcon(ImageView imageView, File file, Drawable placeholder) {
        Glide.with(imageView.getContext())
                .load(file)
                .transform(new GlideCircleTransformation(imageView.getContext()))
                .error(placeholder)
                .placeholder(placeholder)
                .into(imageView);
    }

    @BindingAdapter("app:setPosition")
    public static void setListPosition(RecyclerView recyclerView, int position) {
        if (recyclerView.getLayoutManager() != null) {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
        }
    }

    @BindingAdapter("app:onScrollListener")
    public static void addOnScrollListener(RecyclerView recyclerView, RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }
}
