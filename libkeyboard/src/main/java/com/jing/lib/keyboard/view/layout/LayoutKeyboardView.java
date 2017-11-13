package com.jing.lib.keyboard.view.layout;

import com.jing.lib.keyboard.view.AbsKeyboardView;
import com.jing.lib.keyboard.view.Keyboard;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

public class LayoutKeyboardView extends AbsKeyboardView {

	private Keyboard oldKeyboard;

	public LayoutKeyboardView(Context context) {
		super(context);
	}

	public LayoutKeyboardView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	protected void initKeyboard() {
		addView(mKeyboard, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onSwitchKeyboardType(Keyboard keyboard, final int animTime) {

		// 防止执行动画时的快速点击
		if (keyboard.getParent() == this) {
			return;
		}

		oldKeyboard = mKeyboard;
		super.setKeyboard(keyboard);
		// 有动画
		if (animTime > 0) {
			mKeyboard.setVisibility(INVISIBLE);
			mKeyboard.post(new Runnable() {
				@Override
				public void run() {
					startRevealView(animTime);
				}
			});
		}
		// 没有动画
		else {
			removeView(oldKeyboard);
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void startRevealView(int animTime) {
		oldKeyboard.setVisibility(GONE);
		Animator anim = ViewAnimationUtils.createCircularReveal(mKeyboard, (int) mKeyboard.getX()
				, (int) mKeyboard.getY() + mKeyboard.getHeight(), 0, (float) Math.hypot(mKeyboard.getWidth(), mKeyboard.getHeight()));
		anim.setDuration(animTime);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				mKeyboard.setVisibility(VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				removeView(oldKeyboard);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		anim.start();
	}

}
