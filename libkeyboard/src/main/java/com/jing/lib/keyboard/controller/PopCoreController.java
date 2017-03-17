package com.jing.lib.keyboard.controller;

import android.content.Context;

import com.jing.lib.keyboard.action.UIAction;

/**
 * 描述: 弹出式键盘的核心控制器，公共操作由BaseCoreController封装完成
 * Created by JingYang on 2016/6/21 0021.
 */
public class PopCoreController extends BaseCoreController {

    private final String TAG = "PopCoreController";

    private PopUIController uiController;

    public PopCoreController(Context context) {
        super(context);
    }

    @Override
    protected UIAction createUiAction(Context context) {
        uiController = new PopUIController(context);
        return uiController;
    }

    /**
     * 显示键盘（已初始化过键盘）
     */
    public void showExistedKeyboard() {
        uiController.showExistedKeyboard();
    }

    /**
     * 显示Inputable规定的默认键盘（已初始化过键盘）
     */
    public void showExistedDefaultKeyboard() {
        uiController.showExistedDefaultKeyboard();
    }

}
