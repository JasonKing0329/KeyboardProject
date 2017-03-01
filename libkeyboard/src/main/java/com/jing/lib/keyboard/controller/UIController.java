package com.jing.lib.keyboard.controller;

import com.jing.lib.keyboard.R;
import com.jing.lib.keyboard.action.CandidateListener;
import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.action.OnKeyClickListener;
import com.jing.lib.keyboard.action.UIAction;
import com.jing.lib.keyboard.provider.KeyboardParams;
import com.jing.lib.keyboard.view.AbsKeyboardView;
import com.jing.lib.keyboard.view.CandidateView;
import com.jing.lib.keyboard.view.Keyboard;
import com.jing.lib.keyboard.view.layout.LayoutKeyboardView;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * UI控制器
 * @author 景阳
 *
 */
public class UIController implements UIAction {

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
	
	private Context mContext;
	private AbsKeyboardView mKeyboardView;
	private CandidateView mCandidateView;

	private EditText mEditText;
	// DecorView的主要内容布局
	private View mContentView;
	private Inputable mInputable;

	// 键盘xml资源
	private int[] keyboardXmls;

	// 当前的键盘种类，范围在keyboardXmls的length以内
	private int curKbdIndex;

	private Map<Integer, Keyboard> keyboardMap;

	public UIController(Context context) {
		mContext = context;
		mKeyboardView = new LayoutKeyboardView(context);
		mCandidateView = new CandidateView(context);
		mKeyboardView.setCandidateView(mCandidateView);
		keyboardMap = new HashMap<>();
		
		mCandidateHeight = context.getResources().getDimensionPixelSize(R.dimen.tv_kbd_candidate_height);
		mKbdHeight = context.getResources().getDimensionPixelSize(R.dimen.tv_kbd_height);
		mScreenHeight = getScreenHeight(context);
		mScreenWidth = getScreenWidth(context);
	}

	@Override
	public void setKeyboardXmls(int[] keyboardXmlResource, int defaultIndex) {
		keyboardXmls = keyboardXmlResource;
		curKbdIndex = defaultIndex;
	}

	@Override
	public AbsKeyboardView getKeyboardView() {
		return mKeyboardView;
	}

	@Override
	public void setInputable(Inputable inputable) {
		mInputable = inputable;
	}

	@Override
	public void showExistedKeyboard() {
		if (mKeyboardView instanceof CandidateListener) {
			((CandidateListener) mKeyboardView).onCandidateTextChanged(mEditText.getText().toString());
		}
		mKeyboardView.setVisibility(View.VISIBLE);
		updateContentView();
	}

	@Override
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

		if (mInputable.getShowMode() == KeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE) {
			distance = mKbdHeight;
		}
		else {
			// editText位置不会被键盘覆盖，squeeze模式下仍然挤压屏幕，平移模式下不平移屏幕
			if (distance > mKbdHeight) {
				if (mInputable.getShowMode() == KeyboardParams.KBD_BOTTOM_SQUEEZE) {
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
		if (mInputable.getShowMode() != KeyboardParams.KBD_BOTTOM_SQUEEZE && mInputable.getShowMode() != KeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE) {
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

		Keyboard keyboard = new KeyboardController().initKeyboard(mContext, keyboardXmls[curKbdIndex]);
		// 保存keyboard view实例，用于切换
		keyboardMap.put(keyboardXmls[curKbdIndex], keyboard);

		mKeyboardView.setKeyboard(keyboard);

		View mDecorView = ((Activity) mContext).getWindow().getDecorView();
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
				View mDecorView = ((Activity) mContext).getWindow().getDecorView();
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

	@Override
	public void setEditText(EditText edit) {
		mEditText = edit;
	}

	@Override
	public void onSwitchKeyboardType() {
		curKbdIndex ++;
		if (curKbdIndex >= keyboardXmls.length) {
			curKbdIndex = 0;
		}
		switchToKeyboard(keyboardXmls[curKbdIndex]);
	}

	private void switchToKeyboard(int xmlId) {
		if (keyboardMap.get(xmlId) == null) {
			Keyboard keyboard = new KeyboardController().initKeyboard(mContext, xmlId);
			keyboardMap.put(xmlId, keyboard);
		}
		mKeyboardView.onSwitchKeyboardType(keyboardMap.get(xmlId));
	}

	@Override
	public void setOnKeyClickListener(OnKeyClickListener listener) {
		mKeyboardView.setOnKeyClickListener(listener);
	}

}
