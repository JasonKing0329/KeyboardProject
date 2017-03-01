package com.jing.lib.keyboard.action;

import android.widget.EditText;

/**
 * Created by Jing Yang on 2016/6/21 1521.
 * 外界用于获取键盘上的点击事件、如：完成、下一步
 */
public interface OnKeyboardActionListener {
    /**
     *
     * @param editText
     * @param text
     * @param action
     */
    public void onKeyboardAction(EditText editText, String text, int action);
}
