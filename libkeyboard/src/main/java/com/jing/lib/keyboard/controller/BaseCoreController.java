package com.jing.lib.keyboard.controller;

import android.content.Context;
import android.widget.EditText;

import com.jing.lib.keyboard.action.InputAction;
import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.action.KeyboardHandler;
import com.jing.lib.keyboard.action.OnKeyClickListener;
import com.jing.lib.keyboard.action.OnKeyboardActionListener;
import com.jing.lib.keyboard.action.UIAction;
import com.jing.lib.keyboard.provider.JKeyboardParams;

/**
 * 描述: 核心控制器，调度UI和INPUT部分
 * 调度InputAction与UiAction的关键方法，并处理其事件回调
 * OnKeyClickListener回调UiAction触发的按键类型，将keyCode分发给InputAction处理按键事件
 * KeyboardHandler回调InputAction处理的按键事件，将Ui变化反馈给UiAction，交互变化反馈给注册的OnKeyboardActionListener
 * <p/>作者：景阳
 * <p/>创建时间: 2017/3/17 10:10
 */
public abstract class BaseCoreController implements KeyboardHandler, OnKeyClickListener {

    private final String TAG = "BaseCoreController";

    /**
     * 输入控制器
     */
    private InputAction mInputAction;
    /**
     * UI控制器
     */
    private UIAction mUiAction;

    private EditText mEditText;

    private OnKeyboardActionListener mOnKeyboardActionListener;

    public BaseCoreController(Context context) {

        // 初始化输入控制器与UI控制器，注册回调
        mUiAction = createUiAction(context);
        mUiAction.setOnKeyClickListener(this);
        mInputAction = new InputController();
        mInputAction.setKeyboardHandler(this);
    }

    protected abstract UIAction createUiAction(Context context);

    /**
     * 设置系统外需要回调的事件（比如按键"Done"）
     * @param listener
     */
    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        mOnKeyboardActionListener = listener;
    }

    /**
     * 输入控制。包括可输入长度、屏幕挤压类型等
     * @param inputable
     */
    public void setInputable(Inputable inputable) {
        mUiAction.setInputable(inputable);
        mInputAction.setInputable(inputable);
    }

    public boolean hideKeyboard() {
        return mUiAction.hideKeyboard();
    }

    public void setEditText(EditText edit) {
        mEditText = edit;
        mUiAction.setEditText(edit);
    }

    public void updateEditText(EditText edit) {
        mEditText = edit;
        mInputAction.updateEditText(edit);
    }

    /**
     * 初始化键盘
     */
    public void initKeyboard() {
        mUiAction.initKeyboard();
        mInputAction.updateEditText(mEditText);
    }

    @Override
    public void onInputText(String text) {

    }

    @Override
    public void onClear() {
        mEditText.setText("");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDone(String text) {
        if (mOnKeyboardActionListener != null) {
            mOnKeyboardActionListener.onKeyboardAction(mEditText, text, JKeyboardParams.ACTION_DONE);
        }
        mUiAction.hideKeyboard();
    }

    @Override
    public void onSwitchKeyboard() {
        mUiAction.onSwitchKeyboardType();
    }

    @Override
    public void onKeyClick(String keyCode) {
        mInputAction.onKeyClick(keyCode);
    }

    public void setKeyboardXmlResource(int[] keyboardXmlResource, int defaultIndex) {
        mUiAction.setKeyboardXmls(keyboardXmlResource, defaultIndex);
    }

}
