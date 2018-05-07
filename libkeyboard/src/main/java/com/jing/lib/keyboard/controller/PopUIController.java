package com.jing.lib.keyboard.controller;

import com.jing.lib.keyboard.action.CandidateListener;
import com.jing.lib.keyboard.action.UIAction;
import com.jing.lib.keyboard.provider.JKeyboardParams;
import com.jing.lib.keyboard.view.CandidateView;
import com.jing.lib.keyboard.view.Keyboard;
import com.jing.lib.keyboard.view.layout.LayoutKeyboardView;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 描述: 弹出式键盘的UI控制器，公共操作由BaseUIController完成
 * @author 景阳
 * 利用往decorView添加布局的方式将键盘嵌入到当前activity所属的decorView层级中
 * 支持遮盖、挤压、平移三种弹出方式
 */
public class PopUIController extends BaseUIController implements UIAction {

	private final String TAG = "KeyboardManager";
	/**
	 * 键盘的高度
	 */
	private static int mKbdHeight;
	/**
	 * Candidate的高度
	 */
	private static int mCandidateHeight;
	/**
	 * 屏幕高度
	 */
	private static int mScreenHeight;
	/**
	 * 屏幕宽度
	 */
	private static int mScreenWidth;

	private CandidateView mCandidateView;

	// DecorView的主要内容布局
	private View mContentView;

	public PopUIController(Context context) {
		super(context);

//		mCandidateHeight = context.getResources().getDimensionPixelSize(R.dimen.tv_kbd_candidate_height);
//		mKbdHeight = context.getResources().getDimensionPixelSize(R.dimen.tv_kbd_height);
		mCandidateHeight = dp2Px(context, 30);
		mScreenHeight = getScreenHeight(context);
		mScreenWidth = getScreenWidth(context);
	}

	public void showExistedKeyboard() {
		if (mKeyboardView instanceof CandidateListener) {
			((CandidateListener) mKeyboardView).onCandidateTextChanged(mEditText.getText().toString());
		}
		mKeyboardView.setVisibility(View.VISIBLE);
		updateContentView();
	}

	public void showExistedDefaultKeyboard() {
		switchToKeyboard(keyboardXmls[mInputable.getPopupKeyboardIndex()]);
		if (mKeyboardView instanceof CandidateListener) {
			((CandidateListener) mKeyboardView).onCandidateTextChanged(mEditText.getText().toString());
		}
		mKeyboardView.setVisibility(View.VISIBLE);
		updateContentView();
	}

	/**
	 * 计算内容部分应该平移/压缩的距离
	 */
	private void updateContentView() {
		int[] pos = new int[2];
		mEditText.getLocationInWindow(pos);
		int bottom = pos[1] + mEditText.getHeight();
		int distance = mScreenHeight - bottom;

		if (mInputable.getShowMode() == JKeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE) {
			distance = mKbdHeight;
		}
		else {
			// editText位置不会被键盘覆盖，squeeze模式下仍然挤压屏幕，平移模式下不平移屏幕
			if (distance > mKbdHeight) {
				if (mInputable.getShowMode() == JKeyboardParams.KBD_BOTTOM_SQUEEZE) {
					distance = mKbdHeight;
				}
				else {
					distance = 0;
				}
			}
			// editText会被键盘覆盖，则一定执行挤压或者平移屏幕
			else {
				distance = mKbdHeight - distance;
			}
		}
		
		Log.d(TAG, "content transfer distance: " + distance);
		
		FrameLayout.LayoutParams cParams = (FrameLayout.LayoutParams) mContentView.getLayoutParams();
		cParams.height = mScreenHeight - distance;
//		cParams.bottomMargin = distance;
		if (mInputable.getShowMode() != JKeyboardParams.KBD_BOTTOM_SQUEEZE && mInputable.getShowMode() != JKeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE) {
			cParams.topMargin = -distance;
		}
		mContentView.setLayoutParams(cParams);
	}

	@Override
	public boolean hideKeyboard() {
		//平移模式的键盘要还原内容界面的位置
		FrameLayout.LayoutParams cParams = (FrameLayout.LayoutParams)
				mContentView.getLayoutParams();
		cParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
		cParams.bottomMargin = 0;
		cParams.topMargin = 0;
		mContentView.setLayoutParams(cParams);

		mKeyboardView.setVisibility(View.GONE);
		mCandidateView.setVisibility(View.GONE);
		
		return true;
	}

	@Override
	public void initKeyboard() {

		mKeyboardView = new LayoutKeyboardView(getContext());
		mCandidateView = new CandidateView(getContext());
		mKeyboardView.setCandidateView(mCandidateView);
		int xmlId = keyboardXmls[curKbdIndex];
		Keyboard keyboard = new KeyboardController().initKeyboard(getContext(), xmlId);
		mKbdHeight = keyboard.getKbdHeight();
		mKeyboardView.setOnKeyClickListener(onKeyClickListener);
		// 保存keyboard view实例，用于切换
		keyboardMap.put(xmlId, keyboard);
		mKeyboardView.setKeyboard(keyboard);
		View mDecorView = ((Activity) getContext()).getWindow().getDecorView();
		ViewGroup decorGroup = (ViewGroup) mDecorView;
		mContentView = decorGroup.getChildAt(0);

		//计算键盘的偏移量，把内容界面往上顶
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.topMargin = mScreenHeight - mKbdHeight/* - mCandidateHeight*/;
		decorGroup.addView(mKeyboardView, params);
		Log.d(TAG, "keybaord top margin: " + params.topMargin);

		//计算内容部分应该平移/压缩的距离
		updateContentView();

		mKeyboardView.post(new Runnable() {

			@Override
			public void run() {
				View mDecorView = ((Activity) getContext()).getWindow().getDecorView();
				ViewGroup decorGroup = (ViewGroup) mDecorView;
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						FrameLayout.LayoutParams.WRAP_CONTENT, mCandidateHeight);
				params.topMargin = mKeyboardView.getTop() - mCandidateHeight;
				params.leftMargin = mScreenWidth / 2;
				mCandidateView.setVisibility(View.GONE);
				decorGroup.addView(mCandidateView, params);
			}
		});
	}

	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	private int getScreenHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	private int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * dp转px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2Px(Context context, int dp) {
		return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5);
	}

}
