package com.jing.lib.keyboard.action;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public interface KeyboardHandler {
    public void onInputText(String text);
    public void onClear();
    public void onDelete();
    public void onDone(String text);
    public void onSwitchKeyboard();
}
