package com.jing.lib.keyboard.action;

import com.jing.lib.keyboard.view.AbsKeyboardView;

import android.widget.EditText;

/**
 * 
 * KeyboardManager的UI控制
 * @author 景阳
 *
 */
public interface UIAction {

	/**
	 * 获取键盘布局
	 * @return
	 */
	public AbsKeyboardView getKeyboardView();
	/**
	 * 设置键盘初始化相关参数
	 * @param inputable
	 */
	public void setInputable(Inputable inputable);
	/**
	 * 初始化键盘界面
	 */
	public void initKeyboard();
	/**
	 * 键盘已初始化，重新显示键盘
	 */
	public void showExistedKeyboard();
	/**
	 * 键盘已初始化，重新显示默认键盘
	 */
	void showExistedDefaultKeyboard();
	/**
	 * 隐藏键盘
	 * @return
	 */
	public boolean hideKeyboard();
	/**
	 * 设置当前与键盘绑定的EditText
	 * @param edit
	 */
	public void setEditText(EditText edit);

	/**
	 * 切换键盘类型 
	 */
	public void onSwitchKeyboardType();

	/**
	 * 设置按键点击回调
	 * @param listener
     */
	public void setOnKeyClickListener(OnKeyClickListener listener);

	/**
	 * 设置键盘xml资源
	 * @param keyboardXmlResource
     */
	void setKeyboardXmls(int[] keyboardXmlResource, int defaultIndex);

}
