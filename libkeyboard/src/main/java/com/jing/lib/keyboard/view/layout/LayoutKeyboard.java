package com.jing.lib.keyboard.view.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jing.lib.keyboard.view.AbsKey;
import com.jing.lib.keyboard.view.AbsTextKey;
import com.jing.lib.keyboard.view.Keyboard;

import java.util.List;

public class LayoutKeyboard extends Keyboard implements View.OnClickListener {

	public LayoutKeyboard(Context context) {
		super(context);
	}

	@Override
	public void initView() {
		setBackgroundColor(Color.TRANSPARENT);
		// kbd
		LinearLayout container = new LinearLayout(getContext());
		container.setOrientation(LinearLayout.VERTICAL);
		if (mBgRes != -1) {
			container.setBackgroundResource(mBgRes);
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mKbdHeight);

		// row by row
		int rows = mRowList.size();
		if (rows > 0) {
			for (int i = 0; i < rows; i ++) {
				LinearLayout rowLayout = new LinearLayout(getContext());
				rowLayout.setOrientation(LinearLayout.HORIZONTAL);
				int realRowHeight = mRowHeight - mRowList.get(i).marginTop - mRowList.get(i).marginBottom;
				LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, realRowHeight);
				if (mDivider != -1) {
					lParams.topMargin = mDivider;
				}
				lParams.topMargin += mRowList.get(i).marginTop;
				lParams.bottomMargin += mRowList.get(i).marginBottom;
				container.addView(rowLayout, lParams);

				List<AbsKey> mKeyList = mRowList.get(i).mKeyList;
				// key by key
				for (int j = 0; j < mKeyList.size(); j ++) {
					AbsKey key = mKeyList.get(j);
					if (key.backgroundResId == -1) {
						key.backgroundResId = mKeyBgRes;
					}
					if (key instanceof AbsTextKey) {
						AbsTextKey akey = (AbsTextKey) key;
						if (akey.textColor == -1) {
							akey.textColor = mKeyTextColor;
						}
						if (akey.textSize == -1) {
							akey.textSize = mKeyTextSize;
						}
					}

					key.createView(getContext());
					key.getView().setTag(key);
					key.getView().setOnClickListener(this);

					int width = mKeyWidth;
					if (key.getWidth() > -1) {
						width = key.getWidth();
					}
					else if (mRowList.get(i).keyWidth > -1) {
						width = mRowList.get(i).keyWidth;
					}

					// 背景需要在margin以内
					LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(width - 2*mKeyMargin, realRowHeight - 2*mKeyMargin);
					// divider
					if (mDivider != -1) {
						cParams.leftMargin = mDivider;
						if (j == mKeyList.size() - 1) {
							cParams.rightMargin = mDivider;
						}
					}
					// key margin
					if (mKeyMargin != -1) {
						cParams.leftMargin = mKeyMargin;
						cParams.topMargin = mKeyMargin;
						cParams.rightMargin = mKeyMargin;
						cParams.bottomMargin = mKeyMargin;
					}
					rowLayout.addView(key.getView(), cParams);
				}

			}
		}
		addView(container, params);
	}

	@Override
	public void onClick(View v) {
		AbsKey key = (AbsKey) v.getTag();
		if (mOnKeyClickListener != null) {
			mOnKeyClickListener.onKeyClick(key.getCode());
		}
	}
}
