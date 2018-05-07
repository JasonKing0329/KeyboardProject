package com.jing.lib.keyboard.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public abstract class AbsKey {
    public String code;
    public Object tag;

    public int width;
    public int height;

    public Rect margin;

    public int backgroundResId;
    public Drawable background;

    public AbsKey() {
        width = -1;
        height = -1;
        backgroundResId = -1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract View createView(Context context);
    public abstract View getView();
    public abstract void setBackground(int resId);
    public abstract void setBackground(Drawable drawable);
    public abstract void setPadding(int left, int top, int right, int bottom);

    public void setMargin(int left, int top, int right, int bottom) {
        margin = new Rect(left, top, right, bottom);
    }

    public void setMarginTop(int top) {
        if (margin == null) {
            margin = new Rect();
        }
        margin.top = top;
    }

    public void setMarginLeft(int left) {
        if (margin == null) {
            margin = new Rect();
        }
        margin.left = left;
    }

    public void setMarginRight(int right) {
        if (margin == null) {
            margin = new Rect();
        }
        margin.right = right;
    }

    public void setMarginBottom(int bottom) {
        if (margin == null) {
            margin = new Rect();
        }
        margin.bottom = bottom;
    }
}
