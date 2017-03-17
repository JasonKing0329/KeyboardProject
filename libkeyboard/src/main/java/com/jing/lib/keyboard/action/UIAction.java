package com.jing.lib.keyboard.action;

import com.jing.lib.keyboard.view.AbsKeyboardView;

import android.widget.EditText;

/**
 * KeyboardManager的UI控制
 *
 * @author 景阳
 */
public interface UIAction {

    /**
     * 获取键盘布局
     *
     * @return
     */
    AbsKeyboardView getKeyboardView();

    /**
     * 设置键盘初始化相关参数
     *
     * @param inputable
     */
    void setInputable(Inputable inputable);

    /**
     * 初始化键盘界面
     */
    void initKeyboard();

    /**
     * 隐藏键盘
     *
     * @return
     */
    boolean hideKeyboard();

    /**
     * 设置当前与键盘绑定的EditText
     *
     * @param edit
     */
    void setEditText(EditText edit);

    /**
     * 切换键盘类型
     */
    void onSwitchKeyboardType();

    /**
     * 设置按键点击回调
     *
     * @param listener
     */
    void setOnKeyClickListener(OnKeyClickListener listener);

    /**
     * 设置键盘xml资源
     *
     * @param keyboardXmlResource
     */
    void setKeyboardXmls(int[] keyboardXmlResource, int defaultIndex);

}
