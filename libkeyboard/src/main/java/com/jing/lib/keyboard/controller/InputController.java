package com.jing.lib.keyboard.controller;

import com.jing.lib.keyboard.action.CandidateListener;
import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.action.InputAction;
import com.jing.lib.keyboard.action.KeyboardHandler;
import com.jing.lib.keyboard.provider.KeyboardParams;
import com.jing.lib.keyboard.view.AbsKeyboardView;

import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

/**
 * 输入控制器
 * @author 景阳
 *
 */
public class InputController implements InputAction {

	private final String TAG = "InputController";
	
	private EditText mEditText;
	private Inputable mInputable;
	
	private CandidateListener mCandidateListener;
	
	private KeyboardHandler mKeyboardHandler;
	
	private StringBuffer mRecordBuffer;
	
	public InputController() {
		mRecordBuffer = new StringBuffer();
	}

	@Override
	public void setKeyboardHandler(KeyboardHandler handler) {
		mKeyboardHandler = handler;
	}

	@Override
	public void setInputable(Inputable inputable) {
		mInputable = inputable;
	}

	@Override
	public void onKeyClick(String keyCode) {
		
		Log.d(TAG, "onKeyInput " + keyCode);
		
		//一边输入一边更新EditText
		if (mInputable.sychronizeWithEditText()) {
			inputToEditText(keyCode);
		}
		//输入完成之后点击完成按钮再更新
		else {
			recordInput(keyCode);
		}
		
	}

	private void recordInput(String keyCode) {
		// 清除
		if (KeyboardParams.KEYCODE_CLEAR.equals(keyCode)) {
			mRecordBuffer = new StringBuffer();
		}
		// 删除
		else if (KeyboardParams.KEYCODE_DELETE.equals(keyCode)) {
			if (mRecordBuffer.length() > 0) {
				mRecordBuffer.deleteCharAt(mRecordBuffer.length() - 1);
			}
		}
		// 切换
		else if (KeyboardParams.KEYCODE_SWITCH.equals(keyCode)) {
			mKeyboardHandler.onSwitchKeyboard();
		}
		// 完成
		else if (KeyboardParams.KEYCODE_DONE.equals(keyCode)) {
			mKeyboardHandler.onDone(mRecordBuffer.toString());
			mRecordBuffer = new StringBuffer();
		}
		// 可输入字符
		else {
			
			// 控制最大输入长度
			if (mRecordBuffer.length() >= mInputable.getMaxLength()) {
				return;
			}

			// 数字键盘
			if (mInputable.getInputType() == KeyboardParams.INPUTTYPE_NUM) {

				// 小数点只允许出现一次
				if (".".equals(keyCode)) {
					if (mRecordBuffer.toString().contains(".") || mRecordBuffer.length() == 0) {
						return;
					}
					else {
						mRecordBuffer.append(keyCode);
					}
				}
				// 负数符号只允许出现在第一位
				else if ("-".equals(keyCode)) {
					if (mRecordBuffer.length() > 0) {
						return;
					}
					else {
						mRecordBuffer.append(keyCode);
					}
				}
				else {
					// 0作为开头只能出现一次，并且后面必须跟小数点
					String cur = mRecordBuffer.toString();
					if (cur.equals("-0") || cur.equals("0")) {
						if (!keyCode.equals(".")) {
							return;
						}
					}
					else {
						mRecordBuffer.append(keyCode);
					}
				}
			}
			// 其他
			else {
				mRecordBuffer.append(keyCode);
			}
		}

		// 通知candidate变化
		if (mCandidateListener != null) {
			mCandidateListener.onCandidateTextChanged(mRecordBuffer.toString());
		}
	}

	private void inputToEditText(String keyCode) {
		Editable editable = mEditText.getText();
		int start = mEditText.getSelectionStart();
		
		// 清除
		if (KeyboardParams.KEYCODE_CLEAR.equals(keyCode)) {
			mEditText.setText("");
		}
		// 删除
		else if (KeyboardParams.KEYCODE_DELETE.equals(keyCode)) {
			if (editable != null && editable.length() > 0) {
				if (start > 0) {
					editable.delete(start - 1, start);
				}
			}
		}
		// 切换
		else if (KeyboardParams.KEYCODE_SWITCH.equals(keyCode)) {
			mKeyboardHandler.onSwitchKeyboard();;
		}
		// 完成
		else if (KeyboardParams.KEYCODE_DONE.equals(keyCode)) {
//			if (mOnKbdActionListener != null) {
//				mOnKbdActionListener.onKeyboardDone(mEditText, null);
//			}
			mKeyboardHandler.onDone(mEditText.getText().toString());
		}
		// 可输入字符
		else {
			// 控制最大输入长度
			if (editable.length() >= mInputable.getMaxLength()) {
				return;
			}

			String text = mEditText.getText().toString();
			// 数字键盘
			if (mInputable.getInputType() == KeyboardParams.INPUTTYPE_NUM) {

				// 小数点只允许出现一次
				if (".".equals(keyCode)) {
					if (text.contains(".") || text.length() == 0) {
						return;
					}
					else {
						editable.insert(start, keyCode);
					}
				}
				// 负数符号只允许出现一次且在第一位
				else if ("-".equals(keyCode)) {
					if (text.length() > 0) {
						return;
					}
					else {
						editable.insert(start, keyCode);
					}
				}
				else {
					// 0作为开头只能出现一次，并且后面必须跟小数点
					if (text.equals("-0") || text.equals("0")) {
						if (!keyCode.equals(".")) {
							return;
						}
					}

					else {
						editable.insert(start, keyCode);
					}
				}
			}
			// 其他
			else {
				editable.insert(start, keyCode);
			}
		}

		// 通知candidate变化
		if (mCandidateListener != null) {
			mCandidateListener.onCandidateTextChanged(mEditText.getText().toString());
		}
	}

	@Override
	public void updateEditText(EditText edit) {
		mEditText = edit;
		mRecordBuffer = new StringBuffer();
	}
}
