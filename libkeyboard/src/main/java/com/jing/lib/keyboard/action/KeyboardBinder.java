package com.jing.lib.keyboard.action;

import android.widget.EditText;

/**
 * 为EditText绑定数字键盘
 * @author 景阳
 *
 */
public interface KeyboardBinder {

	/**
	 * 绑定键盘
	 * @param editText
	 * @param inputable
	 */
	public void bindEditText(EditText editText, Inputable inputable);
	/**
	 * 键盘相关按键的监听
	 * @param listener
	 */
	public void setOnKeyboardActionListener(OnKeyboardActionListener listener);
	/**
	 * 隐藏键盘
	 * @return
	 */
	public boolean hideKeyboard();
}
