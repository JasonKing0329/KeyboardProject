package com.jing.lib.keyboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jing.lib.keyboard.action.OnKeyClickListener;

public abstract class AbsKeyboardView extends RelativeLayout {

	protected int xmlId;
	protected Keyboard mKeyboard;
	protected OnKeyClickListener mOnKeyClickListener;
	protected CandidateView mCandidateView;
	
	public AbsKeyboardView(Context context) {
		super(context);
	}

	public AbsKeyboardView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public void setKeyboard(Keyboard keyboard) {
		mKeyboard = keyboard;
		mKeyboard.setOnKeyClickListener(mOnKeyClickListener);
		initKeyboard();
	}

	protected abstract void initKeyboard();

	public abstract void onSwitchKeyboardType(Keyboard keyboard, int animTime);

	public void setCandidateView(CandidateView view) {
		mCandidateView = view;
	}

	public void setOnKeyClickListener(OnKeyClickListener listener) {
		mOnKeyClickListener = listener;
	}

	public int getXmlId() {
		return xmlId;
	}
}
