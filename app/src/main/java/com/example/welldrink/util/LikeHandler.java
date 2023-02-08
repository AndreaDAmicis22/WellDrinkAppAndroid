package com.example.welldrink.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.example.welldrink.R;

public class LikeHandler {

    public static Drawable getFilled(Resources resources, Resources.Theme theme) {
        return ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_thumb_up_alt_24, theme);
    }

    public static Drawable getUnfilled(Resources resources, Resources.Theme theme) {
        return ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_thumb_up_off_alt_24, theme);
    }
}
