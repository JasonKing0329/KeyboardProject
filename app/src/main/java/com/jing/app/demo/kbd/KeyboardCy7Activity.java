package com.jing.app.demo.kbd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.jing.lib.keyboard.action.Inputable;
import com.jing.lib.keyboard.provider.JKeyboardParams;
import com.jing.lib.keyboard.provider.JKeyboardUtil;

public class KeyboardCy7Activity extends AppCompatActivity {

    private EditText editText;
    private EditText editText1;
    private EditText editText2;

    private JKeyboardUtil keyboardUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        editText.clearFocus();
        editText1.clearFocus();
        editText2.clearFocus();

        keyboardUtil = new JKeyboardUtil(this);
        keyboardUtil.setXmlResources(new int[] {
                R.xml.kbd_qwerty_cy7, R.xml.kbd_number_cy7
        });

        keyboardUtil.bindEditText(editText, new Inputable() {
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
                return 10;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }

            @Override
            public int getSwitchAnimTime() {
                return 0;
            }
        });

        keyboardUtil.bindEditText(editText1, new Inputable() {
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
                return JKeyboardParams.KBD_POPUP;
            }

            @Override
            public int getMaxLength() {
                return 10;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }

            @Override
            public int getSwitchAnimTime() {
                return 500;
            }
        });

        keyboardUtil.bindEditText(editText2, new Inputable() {
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
                return JKeyboardParams.KBD_POPUP;
            }

            @Override
            public int getMaxLength() {
                return 10;
            }

            @Override
            public boolean sychronizeWithEditText() {
                return true;
            }

            @Override
            public int getSwitchAnimTime() {
                return 500;
            }
        });

        keyboardUtil.showKeyboard(editText1);
    }

    @Override
    public void onBackPressed() {
        if (keyboardUtil.hideKeyboard()) {
            return;
        }
        super.onBackPressed();
    }
}
