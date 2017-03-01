package com.jing.lib.keyboard.action;

/**
 * InputAction调用接口方法，通知输入变更
 * UIAction实现接口方法，完成输入内容显示
 * @author 景阳
 *
 */
public interface CandidateListener {

	/**
	 * 输入内容变化
	 * @param text
	 */
	public void onCandidateTextChanged(String text);
	/**
	 * 输入完成
	 */
	public void onCandidateDone();
}
