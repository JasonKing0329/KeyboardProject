package com.jing.lib.keyboard.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.RelativeLayout;

import com.jing.lib.keyboard.action.OnKeyClickListener;

public abstract class Keyboard extends RelativeLayout {

	protected List<Row> mRowList;
	protected int mBgRes;
	protected int mKbdHeight = -1;
	protected int mKeyWidth = -1;
	protected int mRowHeight = -1;
	protected int mKeyBgRes = -1;
	protected int mKeyTextColor = -1;
	protected int mKeyTextSize = -1;
	protected int mKeyMargin = -1;
	protected int mDivider = -1;
	protected OnKeyClickListener mOnKeyClickListener;

	public class Row {
		public List<AbsKey> mKeyList;
		public int keyWidth = -1;
		// 允许为0
		public int marginTop;
		// 允许为0
		public int marginBottom;
	}
	
	public Keyboard(Context context) {
		super(context);
	}
	
	public abstract void initView();

	public void addRow(Row row) {
		if (mRowList == null) {
			mRowList = new ArrayList<Row>();
		}
		mRowList.add(row);
	}

	public int getKbdHeight() {
		return mKbdHeight;
	}

	public void setKbdHeight(int mHeight) {
		this.mKbdHeight = mHeight;
	}

	public int getKeyWidth() {
		return mKeyWidth;
	}

	public void setKeyWidth(int mKeyWidth) {
		this.mKeyWidth = mKeyWidth;
	}

	public int getRowHeight() {
		return mRowHeight;
	}

	public void setRowHeight(int mKeyHeight) {
		this.mRowHeight = mKeyHeight;
	}

	public void setBgRes(int res) {
		this.mBgRes = res;
	}

	public void setKeyBgRes(int mKeyBgRes) {
		this.mKeyBgRes = mKeyBgRes;
	}

	public void setKeyTextColor(int color) {
		this.mKeyTextColor = color;
	}

	public void setKeyTextSize(int size) {
		this.mKeyTextSize = size;
	}

	public void setKeyMargin(int size) {
		this.mKeyMargin = size;
	}

	public void setDivider(int size) {
		this.mDivider = size;
	}

	public void setOnKeyClickListener(OnKeyClickListener listener) {
		mOnKeyClickListener = listener;
	}
}
