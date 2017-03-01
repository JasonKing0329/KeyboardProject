package com.jing.lib.keyboard.controller;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

import com.jing.lib.keyboard.parser.KeyboardParser;
import com.jing.lib.keyboard.view.Keyboard;
import com.jing.lib.keyboard.view.layout.LayoutKeyboard;

public class KeyboardController {

	private KeyboardParser mParser;

	public KeyboardController() {
	}
	
	public Keyboard initKeyboard(Context context, int xmlId) {
		Keyboard keyboard = new LayoutKeyboard(context);
		if (mParser == null) {
			mParser = new KeyboardParser();
		}
		try {
			mParser.parseKeyboard(keyboard, xmlId);
			keyboard.initView();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  keyboard;
	}
}
