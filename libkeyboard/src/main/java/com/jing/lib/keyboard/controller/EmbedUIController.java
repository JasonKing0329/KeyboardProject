package com.jing.lib.keyboard.controller;

import android.content.Context;

import com.jing.lib.keyboard.action.UIAction;
import com.jing.lib.keyboard.view.AbsKeyboardView;
import com.jing.lib.keyboard.view.Keyboard;

/**
 * 描述: 嵌入界面式键盘的UI控制器，公共操作由BaseUIController完成
 * KeyboardView由provider.JKeyboardView继承，添加到任意界面的布局文件中
 * <p/>作者：景阳
 * <p/>创建时间: 2017/3/17 10:33
 */
public class EmbedUIController extends BaseUIController implements UIAction {

    public EmbedUIController(Context context) {
        super(context);
    }

    public void setKeyboardView(AbsKeyboardView keyboardView) {
        mKeyboardView = keyboardView;
    }

    @Override
    public void initKeyboard() {
        // 如果没有注入xml资源，则加载layout里注册的默认资源
        int xmlId = mKeyboardView.getXmlId();
        if (keyboardXmls != null) {
            curKbdIndex = mInputable.getPopupKeyboardIndex();
            xmlId = keyboardXmls[curKbdIndex];
        }
        Keyboard keyboard = new KeyboardController().initKeyboard(getContext(), xmlId);
        mKeyboardView.setOnKeyClickListener(onKeyClickListener);
        // 保存keyboard view实例，用于切换
        keyboardMap.put(xmlId, keyboard);
        mKeyboardView.setKeyboard(keyboard);
    }

    @Override
    public boolean hideKeyboard() {
        return false;
    }

    /**
     * 刷新键盘，重新读取键盘类型
     */
    public void refreshKeyboard() {
        int targetIndex = mInputable.getPopupKeyboardIndex();
        // 不等于当前键盘类型才切换
        if (curKbdIndex != targetIndex) {
            curKbdIndex = targetIndex;
            switchToKeyboard(keyboardXmls[targetIndex]);
        }
    }
}
