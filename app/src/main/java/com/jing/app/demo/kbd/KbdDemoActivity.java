package com.jing.app.demo.kbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.provider.JKeyboardParams;
import com.jing.lib.keyboard.provider.JKeyboardView;
import com.jing.lib.keyboard.provider.JKeyboardUtil;

public class KbdDemoActivity extends AppCompatActivity {

    private JKeyboardUtil keyboardUtil;
    private JKeyboardView keyboardView;
    private EditText numEdit;
    private EditText qwertyEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kbd_demo);

        numEdit = (EditText) findViewById(R.id.edit_num);
        qwertyEdit = (EditText) findViewById(R.id.edit_qwerty);

//        testPopKeyboard();
        testEmbedKeyboard();
    }

    private void testEmbedKeyboard() {
        keyboardView = (JKeyboardView) findViewById(R.id.kbd_view);
        keyboardView.setXmlResources(new int[] {
                R.xml.kbd_qwerty_buffet, R.xml.kbd_number_buffet
        });
        keyboardView.addInputArea(numEdit, new Inputable() {
            @Override
            public int getPopupKeyboardIndex() {
                return 1;
            }

            @Override
            public int getInputType() {
                return JKeyboardParams.INPUTTYPE_NUM;
            }

            @Override
            public int getShowMode() {
                return JKeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE;
            }

            @Override
            public int getMaxLength() {
                return 10;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }
        });
        keyboardView.addInputArea(qwertyEdit, new Inputable() {
            @Override
            public int getPopupKeyboardIndex() {
                return 0;
            }

            @Override
            public int getInputType() {
                return JKeyboardParams.INPUTTYPE_NORMAL;
            }

            @Override
            public int getShowMode() {
                return JKeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE;
            }

            @Override
            public int getMaxLength() {
                return 10;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }
        });
        keyboardView.show(numEdit);
    }

    private void testPopKeyboard() {
        keyboardUtil = new JKeyboardUtil(this);
        keyboardUtil.setXmlResources(new int[] {
                R.xml.kbd_qwerty, R.xml.kbd_number
        });

        keyboardUtil.bindEditText(numEdit, new Inputable() {
            @Override
            public int getPopupKeyboardIndex() {
                return 1;
            }

            @Override
            public int getInputType() {
                return JKeyboardParams.INPUTTYPE_NUM;
            }

            @Override
            public int getShowMode() {
                return JKeyboardParams.KBD_FORCE_BOTTOM_SQUEEZE;
            }

            @Override
            public int getMaxLength() {
                return 10;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }
        });

        keyboardUtil.bindEditText(qwertyEdit, new Inputable() {
            @Override
            public int getPopupKeyboardIndex() {
                return 0;
            }

            @Override
            public int getInputType() {
                return JKeyboardParams.INPUTTYPE_NORMAL;
            }

            @Override
            public int getShowMode() {
                return JKeyboardParams.KBD_BOTTOM_SQUEEZE;
            }

            @Override
            public int getMaxLength() {
                return 20;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }
        });
        keyboardUtil.showKeyboard(qwertyEdit);
    }

    @Override
    public void onBackPressed() {
        if (keyboardUtil.hideKeyboard()) {
            return;
        }
        super.onBackPressed();
    }
}
