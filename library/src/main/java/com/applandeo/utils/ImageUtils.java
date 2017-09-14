package com.applandeo.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.applandeo.filepicker.R;

/**
 * Created by Mateusz Kornakiewicz on 30.08.2017.
 */

public class ImageUtils {

    /**
     * This method is using to create files icons
     *
     * @param context  An application context needed to get resources
     * @param icon     Resource of the foreground file icon
     * @param bcgColor Resource of the color of icon background
     * @return LayerDrawable consists of the file icon and background
     */
    public static Drawable createIcon(Context context, @DrawableRes int icon, @ColorRes int bcgColor) {
        Drawable backgroundLayer = ContextCompat.getDrawable(context, R.drawable.icon_circle_bacground);
        backgroundLayer.setColorFilter(ContextCompat.getColor(context, bcgColor), PorterDuff.Mode.SRC);

        Drawable iconLayer = ContextCompat.getDrawable(context, icon);

        Drawable[] layers = {backgroundLayer, iconLayer};

        return new LayerDrawable(layers);
    }
}
