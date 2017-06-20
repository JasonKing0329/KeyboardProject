package com.jing.lib.keyboard.view.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jing.lib.keyboard.view.AbsTextKey;

public class LayoutTextKey extends AbsTextKey {

	private TextView mTextView;
	
	@Override
	public View getView() {

		return mTextView;
	}
	
	@Override
	public View createView(Context context) {
		mTextView = new TextView(context);
		mTextView.setGravity(Gravity.CENTER);
		if (textColor != -1) {
			mTextView.setTextColor(textColor);
		}
		if (text != null) {
			mTextView.setText(text);
		}
		if (textSize != -1) {
			mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
		}
		if (backgroundResId != -1) {
			mTextView.setBackgroundResource(backgroundResId);
		}
		if (background != null) {
			mTextView.setBackgroundDrawable(background);
		}
		return mTextView;
	}

	@Override
	public void setBackground(int resId) {
		backgroundResId = resId;
		if (backgroundResId != -1 && mTextView != null) {
			mTextView.setBackgroundResource(backgroundResId);
		}
	}

	@Override
	public void setBackground(Drawable drawable) {
		background = drawable;
		if (mTextView != null) {
			mTextView.setBackgroundDrawable(drawable);
		}
	}

//	@Override
//	public void setMargin(int left, int top, int right, int bottom) {
//		LayoutParams params = mTextView.getLayoutParams();
//		if (params != null && params instanceof MarginLayoutParams) {
//			MarginLayoutParams mParams = (MarginLayoutParams) params;
//			mParams.leftMargin = left;
//			mParams.topMargin = top;
//			mParams.rightMargin = right;
//			mParams.bottomMargin = bottom;
//		}
//	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		mTextView.setPadding(left, top, right, bottom);
	}

	@Override
	public void setText(String text) {
		mTextView.setText(text);
		this.text = text;
	}

	@Override
	public void setTextColor(int color) {
		mTextView.setTextColor(color);
		textColor = color;
	}

	@Override
	public void setTextSize(int size) {
		mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
		textSize = size;
	}
}
