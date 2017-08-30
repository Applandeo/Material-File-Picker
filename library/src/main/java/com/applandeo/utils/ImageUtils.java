package com.applandeo.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;

import com.applandeo.filepicker.R;

/**
 * Created by Mateusz Kornakiewicz on 30.08.2017.
 */

public class ImageUtils {
    public static Drawable createIcon(Context context, int icon, int bcgColor) {
        Drawable backgroundLayer = ContextCompat.getDrawable(context, R.drawable.icon_circle_bacground);
        backgroundLayer.setColorFilter(ContextCompat.getColor(context, bcgColor), PorterDuff.Mode.SRC);

        Drawable iconLayer = ContextCompat.getDrawable(context, icon);

        Drawable[] layers = {backgroundLayer, iconLayer};

        return new LayerDrawable(layers);
    }
}
