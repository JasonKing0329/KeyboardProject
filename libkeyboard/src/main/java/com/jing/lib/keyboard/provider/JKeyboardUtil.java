package com.jing.lib.keyboard.provider;

import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.action.KeyboardBinder;
import com.jing.lib.keyboard.action.OnKeyboardActionListener;
import com.jing.lib.keyboard.controller.PopCoreController;
import com.jing.lib.keyboard.view.AbsKeyboardView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class JKeyboardUtil implements KeyboardBinder {


    private Activity mActivity;

    private final String TAG = "JKeyboardUtil";
    private EditText mEditText;
    private TriggerListener mTriggerListener;

    private boolean isKbdShowing;

    private PopCoreController mController;

    private int[] keyboardXmlRes;

    private Map<EditText, Inputable> inputableMap;

    public JKeyboardUtil(Activity activity) {
        mActivity = activity;
        mTriggerListener = new TriggerListener();
        inputableMap = new HashMap<>();
        mController = new PopCoreController(activity);
    }

    @Override
    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        mController.setOnKeyboardActionListener(listener);
    }

    @Override
    public void bindEditText(EditText editText, Inputable inputable) {

        inputableMap.put(editText, inputable);
        editText.setOnTouchListener(mTriggerListener);
    }

    @Override
    public boolean hideKeyboard() {
        Log.d(TAG, "hideKeyboard");
        if (isKbdShowing) {
            isKbdShowing = false;
            return mController.hideKeyboard();
        }
        return false;
    }

    public void setXmlResources(int[] xmlIds) {
        keyboardXmlRes = xmlIds;
    }

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
            hideSoftInputMethod(mActivity, edit);

            // 初始化键盘
            if (mEditText == null) {
                mEditText = edit;
                mController.setInputable(inputableMap.get(edit));
                initKeyboard(inputableMap.get(edit).getPopupKeyboardIndex());
            }
            else {
                // 重复点击当前绑定的EditText
                if (editText == mEditText) {
                    // 执行过返回键隐藏键盘
                    if (!isKbdShowing) {
                        showExistedKeyboard();
                    }
                }
                // 切换EditText
                else {
                    mController.updateEditText(edit);
                    mController.setInputable(inputableMap.get(edit));
                    showExistedDefaultKeyboard();
                }

            }

            if (android.os.Build.VERSION.SDK_INT < 14) {
                edit.setInputType(type);
            }

            mEditText = edit;
            return false;
        }

    }

    private void showExistedKeyboard() {
        Log.d(TAG, "showExistedKeyboard");
        isKbdShowing = true;
        mController.showExistedKeyboard();
    }

    private void showExistedDefaultKeyboard() {
        Log.d(TAG, "showExistedDefaultKeyboard");
        isKbdShowing = true;
        mController.showExistedDefaultKeyboard();
    }

    private void initKeyboard(int defaultIndex) {

        isKbdShowing = true;
        mController.setKeyboardXmlResource(keyboardXmlRes, defaultIndex);
        mController.initKeyboard();
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
