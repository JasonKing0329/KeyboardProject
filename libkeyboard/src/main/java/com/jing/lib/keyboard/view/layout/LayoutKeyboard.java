package com.jing.lib.keyboard.view.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
				LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				// divider
				if (mDivider != -1) {
					lParams.topMargin = mDivider;
				}
				// marginTop
				lParams.topMargin += mRowList.get(i).marginTop;
				lParams.bottomMargin += mRowList.get(i).marginBottom;
				// verticalSpace
				if (i > 0 && mVerticalSpace != -1) {
					lParams.topMargin += mVerticalSpace;
				}
				// marginLeft
				lParams.leftMargin += mRowList.get(i).marginLeft;
				// marginRight
				lParams.rightMargin += mRowList.get(i).marginRight;

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

					// Width优先级 Key width > Row keyWidth > Keyboard keyWidth
					int width = mKeyWidth;
					if (key.getWidth() > -1) {
						width = key.getWidth();
					}
					else if (mRowList.get(i).keyWidth > -1) {
						width = mRowList.get(i).keyWidth;
					}
					// Height优先级 Key height > Row keyHeight > Keyboard keyHeight > Keyboard rowHeight
					int height = mKeyHeight;
					if (key.getHeight() > -1) {
						height = key.getHeight();
					}
					else if (mRowList.get(i).keyHeight > -1) {
						height = mRowList.get(i).keyHeight;
					}
					if (height == -1) {
						height = mRowHeight;
					}

					// 背景需要在margin以内
					LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(width, height);
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
					if (key.margin != null) {
						cParams.leftMargin += key.margin.left;
						cParams.topMargin += key.margin.top;
						cParams.rightMargin += key.margin.right;
						cParams.bottomMargin += key.margin.bottom;
					}
					// horizontalSpace
					if (j > 0 && mHorizongtalSpace != -1) {
						cParams.leftMargin += mHorizongtalSpace;
					}
					rowLayout.addView(key.getView(), cParams);
				}

			}
		}

		switch (mGravity) {
			case Gravity.BOTTOM:
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				break;
			case Gravity.RIGHT:
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				break;
			case Gravity.CENTER:
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				break;
			case Gravity.CENTER_HORIZONTAL:
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				break;
			case Gravity.CENTER_VERTICAL:
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				break;
			case Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL:
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				break;
			case Gravity.RIGHT | Gravity.CENTER_VERTICAL:
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				break;
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
