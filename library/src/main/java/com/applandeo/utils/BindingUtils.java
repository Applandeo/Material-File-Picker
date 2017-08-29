package com.applandeo.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.applandeo.adapters.RecyclerAdapter;

/**
 * Created by Mateusz Kornakiewicz on 29.08.2017.
 */

public class BindingUtils {
    @BindingAdapter("app:navigationOnClickListener")
    public static void setNavigationOnClickListener(Toolbar toolbar, View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    @BindingAdapter({"app:setAdapter"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }
}
