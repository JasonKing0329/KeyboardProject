package com.jing.lib.keyboard.provider;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.controller.EmbedCoreController;
import com.jing.lib.keyboard.view.AbsKeyboardView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 嵌入式键盘的绑定器，将JKeyboardView关联到多个EditText
 * <p/>作者：景阳
 * <p/>创建时间: 2017/3/17 11:00
 */
public class JKeyboardBinder {

    private final String TAG = "KeyboardUtil";
    private EditText mEditText;

    private Context mContext;

    private TriggerListener mTriggerListener;

    private int[] keyboardXmlRes;

    private Map<EditText, Inputable> inputableMap;

    private EmbedCoreController mController;

    public JKeyboardBinder(Context context) {
        mContext = context;
        mTriggerListener = new TriggerListener();
        inputableMap = new HashMap<>();
        mController = new EmbedCoreController(context);
    }

    /**
     * 设置可切换键盘资源
     * @param xmlIds
     */
    public void setXmlResources(int[] xmlIds) {
        keyboardXmlRes = xmlIds;
    }

    /**
     * 绑定editText与keyboardView
     * @param editText
     * @param keyboardView
     * @param inputable
     */
    public void bindEditText(EditText editText, AbsKeyboardView keyboardView, Inputable inputable) {

        mController.setKeyboardView(keyboardView);
        inputableMap.put(editText, inputable);
        editText.setOnTouchListener(mTriggerListener);
    }

    /**
     * 初始化键盘
     * @param defaultIndex
     */
    private void initKeyboard(int defaultIndex) {
        mController.setKeyboardXmlResource(keyboardXmlRes, defaultIndex);
        mController.initKeyboard();
    }

    /**
     * EditText的touch事件监听，屏蔽系统键盘的弹出
     */
    private class TriggerListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View editText, MotionEvent event) {

            EditText edit = (EditText) editText;
            edit.requestFocus();
            edit.setText("");
            mController.setEditText(edit);

            int type = edit.getInputType();
            if (android.os.Build.VERSION.SDK_INT < 14) {
                edit.setInputType(InputType.TYPE_NULL);
            }
            hideSoftInputMethod((Activity) mContext, edit);

            // 显示KeyboardView
            showKeyboard(edit);

            if (android.os.Build.VERSION.SDK_INT < 14) {
                edit.setInputType(type);
            }

            mEditText = edit;
            return false;
        }

    }

    public void showKeyboard(EditText edit) {
        // 初始化键盘
        if (mEditText == null) {
            mEditText = edit;
            mController.setEditText(edit);
            mController.setInputable(inputableMap.get(edit));
            initKeyboard(inputableMap.get(edit).getPopupKeyboardIndex());
        }
        else {
            // 重复点击当前绑定的EditText
            if (edit == mEditText) {
                // 无操作
            }
            // 切换EditText
            else {
                mController.updateEditText(edit);
                mController.setInputable(inputableMap.get(edit));
                // 刷新keyboard view，如键盘类型发生改变，重新加载
                refreshKeyboardView();
            }

        }
    }

    /**
     * 刷新键盘，重新读取键盘类型
     */
    private void refreshKeyboardView() {
        mController.refreshKeyboard();
    }

    //禁用系统输入法
    private void hideSoftInputMethod(Activity activity, EditText ed) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
