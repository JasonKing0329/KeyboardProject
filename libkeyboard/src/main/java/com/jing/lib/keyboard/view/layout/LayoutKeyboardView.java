package com.jing.lib.keyboard.view.layout;

import com.jing.lib.keyboard.view.AbsKeyboardView;
import com.jing.lib.keyboard.view.Keyboard;

import android.content.Context;

public class LayoutKeyboardView extends AbsKeyboardView {

	public LayoutKeyboardView(Context context) {
		super(context);
	}

	@Override
	protected void initKeyboard() {
		addView(mKeyboard, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onSwitchKeyboardType(Keyboard keyboard) {
		if (getChildCount() > 0) {
			for (int i = getChildCount() - 1; i >= 0; i --) {
				removeViewAt(i);
			}
		}
		super.setKeyboard(keyboard);
	}

}
