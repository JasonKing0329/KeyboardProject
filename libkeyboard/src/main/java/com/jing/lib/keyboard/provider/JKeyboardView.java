package com.jing.lib.keyboard.provider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.jing.lib.keyboard.R;
import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.view.layout.LayoutKeyboardView;

/**
 * 描述: 嵌入布局式键盘，直接加入到布局文件任意位置
 * <p/>作者：景阳
 * <p/>创建时间: 2017/3/1 17:16
 */
public class JKeyboardView extends LayoutKeyboardView {

    private JKeyboardBinder binder;

    public JKeyboardView(Context context) {
        super(context);
    }

    public JKeyboardView(Context context, AttributeSet attr) {
        super(context, attr);
        binder = new JKeyboardBinder(context);
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.JKeyboardView);
        xmlId = array.getResourceId(R.styleable.JKeyboardView_xml, 0);
    }

    /**
     * 添加绑定的EditText
     * @param numEdit
     * @param inputable
     */
    public void addInputArea(EditText numEdit, Inputable inputable) {
        binder.bindEditText(numEdit, this, inputable);
    }

    /**
     * 设置多个可切换的键盘资源
     * @param xmls
     */
    public void setXmlResources(int[] xmls) {
        binder.setXmlResources(xmls);
    }

    /**
     * 显示并关联到EditText
     * @param numEdit
     */
    public void show(EditText numEdit) {
        binder.showKeyboard(numEdit);
    }
}
