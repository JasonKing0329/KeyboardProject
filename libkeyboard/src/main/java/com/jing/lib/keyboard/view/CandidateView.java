package com.jing.lib.keyboard.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jing.lib.keyboard.R;

public class CandidateView extends RelativeLayout {

	private TextView mTextView;
	
	public CandidateView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setBackgroundColor(Color.LTGRAY);
//		mTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_kbd_candidate, null);
		mTextView = new TextView(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(CENTER_VERTICAL);
		addView(mTextView, params);
	}

	public void setText(String text) {
		mTextView.setText(text);
	}

	public void makeCenter() {
		post(new Runnable() {
			
			@Override
			public void run() {
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
				params.leftMargin = (((View) getParent()).getWidth() - getWidth()) / 2;
				setLayoutParams(params);
			}
		});
	}

}
