package com.jing.lib.keyboard.controller;

import android.content.Context;

import com.jing.lib.keyboard.action.UIAction;
import com.jing.lib.keyboard.view.AbsKeyboardView;

/**
 * 描述: 嵌入界面式键盘的核心控制器，公共操作由BaseCoreController封装完成
 * <p/>作者：景阳
 * <p/>创建时间: 2017/3/17 10:35
 */
public class EmbedCoreController extends BaseCoreController {

    private EmbedUIController uiController;

    public EmbedCoreController(Context context) {
        super(context);
    }

    @Override
    protected UIAction createUiAction(Context context) {
        uiController = new EmbedUIController(context);
        return uiController;
    }

    public void setKeyboardView(AbsKeyboardView keyboardView) {
        uiController.setKeyboardView(keyboardView);
    }

    /**
     * 刷新键盘，重新读取键盘类型
     */
    public void refreshKeyboard() {
        uiController.refreshKeyboard();
    }
}
