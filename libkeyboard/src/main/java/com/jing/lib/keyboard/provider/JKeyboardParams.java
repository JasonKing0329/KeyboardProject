package com.jing.lib.keyboard.provider;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class JKeyboardParams {
    public static final String TYPE_NUM = "kbd_type_num";
    public static final String TYPE_QWERTY = "kbd_type_qwerty";


    /**
     * 输入模式--普通（可切换英文键盘与数字键盘）
     */
    public final static int INPUTTYPE_NORMAL = 0;
    /**
     * 输入模式--数字键盘（可输入小数点）
     */
    public final static int INPUTTYPE_NUM = 1;

    /**
     * 键盘监听回调
     */
    public final static int ACTION_DONE = 100;


    /**
     * 键盘以PopupWindow的形式从下方弹出，覆盖在界面之上
     */
    public static final int KBD_POPUP = 0;
    /**
     * 键盘以加入至DecorView实现，出现在下方，内容界面将被往上平移键盘的高度
     */
    public static final int KBD_BOTTOM_TRANSFER = 1;
    /**
     * 键盘以加入至DecorView实现，出现在下方，内容界面将被挤压
     */
    public static final int KBD_BOTTOM_SQUEEZE = 2;
    /**
     * 将键盘Layout嵌入到布局中的任意位置
     */
    public static final int KBD_INSERT = 3;
    /**
     * 键盘以加入至DecorView实现，出现在下方，内容界面将被挤压
     */
    public static final int KBD_FORCE_BOTTOM_SQUEEZE = 4;

    /**
     * qwerty键盘
     */
    public static final int KBD_MODE_QWERTY = 0;
    /**
     * 数字键盘
     */
    public static final int KBD_MODE_NUM = 1;

    /**
     * 按键--删除键
     */
    public final static String KEYCODE_DELETE = "Delete";
    /**
     * 按键--切换键盘键
     */
    public final static String KEYCODE_SWITCH = "Switch";
    /**
     * 按键--清除键
     */
    public final static String KEYCODE_CLEAR = "Clear";
    /**
     * 按键--完成键
     */
    public final static String KEYCODE_DONE = "Done";

}
