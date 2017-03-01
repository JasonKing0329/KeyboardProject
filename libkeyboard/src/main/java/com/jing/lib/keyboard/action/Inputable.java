package com.jing.lib.keyboard.action;

/**
 * KeyboardParams initialization
 * @author Jing Yang
 *
 */
public interface Inputable {

	/**
	 * 弹出时默认启动键盘
	 * @return
     */
	int getPopupKeyboardIndex();
	/**
	 * KeyboardParams.INPUTTYPE_XXX, only support INPUTTYPE_NUM for now
	 * @return
	 */
	public int getInputType();
	/**
	 * KeyboardParams.KBD_XXX, only support KBD_BOTTOM_XX for now
	 * @return
	 */
	public int getShowMode();
	/**
	 * max number to input
	 * @return
	 */
	public int getMaxLength();
	/**
	 * 将输入的内容同步到EditText中显示，无特殊需求返回true
	 * @return
	 */
	public boolean sychronizeWithEditText();
}
