package com.jing.lib.keyboard.controller;

import android.content.Context;
import android.widget.EditText;

import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.action.OnKeyClickListener;
import com.jing.lib.keyboard.action.UIAction;
import com.jing.lib.keyboard.view.AbsKeyboardView;
import com.jing.lib.keyboard.view.Keyboard;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 完成基本的UI调度
 * <p/>作者：景阳
 * <p/>创建时间: 2017/3/17 10:36
 */
public abstract class BaseUIController implements UIAction {

    private Context mContext;

    protected AbsKeyboardView mKeyboardView;

    protected EditText mEditText;

    protected Inputable mInputable;

    // 键盘xml资源
    protected int[] keyboardXmls;

    // 当前的键盘种类，范围在keyboardXmls的length以内
    protected int curKbdIndex;

    protected Map<Integer, Keyboard> keyboardMap;

    protected OnKeyClickListener onKeyClickListener;

    public BaseUIController(Context context) {
        mContext = context;
        keyboardMap = new HashMap<>();
    }

    protected Context getContext() {
        return mContext;
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
    public void setKeyboardXmls(int[] keyboardXmlResource, int defaultIndex) {
        keyboardXmls = keyboardXmlResource;
        curKbdIndex = defaultIndex;
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

    protected void switchToKeyboard(int xmlId) {
        if (keyboardMap.get(xmlId) == null) {
            Keyboard keyboard = new KeyboardController().initKeyboard(mContext, xmlId);
            keyboardMap.put(xmlId, keyboard);
        }
        mKeyboardView.onSwitchKeyboardType(keyboardMap.get(xmlId));
    }

    @Override
    public void setOnKeyClickListener(OnKeyClickListener listener) {
        onKeyClickListener = listener;
    }

}
