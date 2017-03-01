package com.jing.lib.keyboard.view.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jing.lib.keyboard.view.AbsImageKey;

/**
 * 为了便于控制image的大小与缩放，采用RelativeLayout嵌套ImageView的方式
 */
public class LayoutImageKey extends AbsImageKey {

	private RelativeLayout mLayout;
	private ImageView mImageView;

	@Override
	public View createView(Context context) {
		mLayout = new RelativeLayout(context);
		mImageView = new ImageView(context);

		int width = imgWidth;
		int height = imgHeight;
		if (width == -1) {
			width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		}
		if (height == -1) {
			height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		mLayout.addView(mImageView, params);

		if (srcResId != -1) {
			mImageView.setImageResource(srcResId);
		}
		if (backgroundResId != -1) {
			mLayout.setBackgroundResource(backgroundResId);
		}
		if (background != null) {
			mLayout.setBackgroundDrawable(background);
		}
		return mImageView;
	}

	@Override
	public View getView() {
		return mLayout;
	}

	@Override
	public void setBackground(int resId) {

	}

	@Override
	public void setBackground(Drawable drawable) {
		
	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImageResource(int resId) {
		mImageView.setImageResource(resId);
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		mImageView.setImageDrawable(drawable);
	}

	@Override
	public void setScaleType(int scaleType) {
		mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
	}
}
