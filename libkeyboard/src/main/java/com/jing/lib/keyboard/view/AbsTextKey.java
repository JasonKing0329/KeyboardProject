package com.jing.lib.keyboard.view;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public abstract class AbsTextKey extends AbsKey {

    public String text;
    public int textColor;

    public int textSize;

    public AbsTextKey() {
        super();
        textColor = -1;
        textSize = -1;
    }

    public abstract void setText(String text);
    public abstract void setTextColor(int color);
    public abstract void setTextSize(int size);

}
