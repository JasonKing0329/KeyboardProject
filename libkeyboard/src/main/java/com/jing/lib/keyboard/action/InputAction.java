package com.jing.lib.keyboard.action;

import android.widget.EditText;

/**
 * KeyboardManager的输入控制
 * @author 景阳
 *
 */
public interface InputAction  {

	/**
	 * 按键处理后回调
	 * @param handler
	 */
	public void setKeyboardHandler(KeyboardHandler handler);

	/**
	 * 处理keyCode按键
	 * @param keyCode
     */
	public void onKeyClick(String keyCode);
	/**
	 * 更新当前EditText
	 * @param edit
	 */
	public void updateEditText(EditText edit);
	/**
	 * 
	 * @param inputable
	 */
	public void setInputable(Inputable inputable);
}
