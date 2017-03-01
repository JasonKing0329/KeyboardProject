package com.jing.lib.keyboard.view;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public abstract class AbsImageKey extends AbsKey {
    public int imgWidth;
    public int imgHeight;
    public int srcResId;

    public AbsImageKey() {
        srcResId = -1;
        imgWidth = -1;
        imgHeight = -1;
    }

    public abstract void setImageResource(int resId);
    public abstract void setImageDrawable(Drawable drawable);
    public abstract void setScaleType(int scaleType);
}
