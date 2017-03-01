package com.jing.lib.keyboard.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.jing.lib.keyboard.tool.DensityUtil;
import com.jing.lib.keyboard.view.AbsImageKey;
import com.jing.lib.keyboard.view.AbsKey;
import com.jing.lib.keyboard.view.AbsTextKey;
import com.jing.lib.keyboard.view.Keyboard;
import com.jing.lib.keyboard.view.layout.LayoutImageKey;
import com.jing.lib.keyboard.view.layout.LayoutTextKey;

public class KeyboardParser {

	public void parseKeyboard(Keyboard keyboard, int xmlId) throws XmlPullParserException, IOException {
		
		List<AbsKey> list = null;
		AbsKey key = null;
		Keyboard.Row row = null;
		
		XmlPullParser parser = keyboard.getContext().getResources().getXml(xmlId);
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equals(KeyboardTag.KEYBOARD)) {
					// background
					String bg = parser.getAttributeValue(null, KeyboardTag.ATTR_BG);
					int bgId = parseResourceId(keyboard.getContext(), bg);
					keyboard.setBgRes(bgId);
					// height
					String sHeight = parser.getAttributeValue(null, KeyboardTag.ATTR_HEIGHT);
					int height = parseDimen(keyboard.getContext(), sHeight);
					keyboard.setKbdHeight(height);
					// keyWidth
					String sKeyWidth = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_WIDTH);
					int keyWidth = parseDimen(keyboard.getContext(), sKeyWidth);
					keyboard.setKeyWidth(keyWidth);
					// divider
					String sDivider = parser.getAttributeValue(null, KeyboardTag.ATTR_DIVIDER);
					int divider = parseDimen(keyboard.getContext(), sDivider);
					keyboard.setDivider(divider);
					// keyTextColor
					String sKeyTextColor = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_COLOR);
					int keyTextColor = parseResourceId(keyboard.getContext(), sKeyTextColor);
					keyboard.setKeyTextColor(keyboard.getContext().getResources().getColor(keyTextColor));
					// keyTextSize
					String sKeyTextSize = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_SIZE);
					int keyTextSize = parseDimen(keyboard.getContext(), sKeyTextSize);
					keyboard.setKeyTextSize(keyTextSize);
					// keyMargin
					String sKeyMargin = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_MARGIN);
					int keyMargin = parseDimen(keyboard.getContext(), sKeyMargin);
					keyboard.setKeyMargin(keyMargin);
					// rowHeight
					String sRowHeight = parser.getAttributeValue(null, KeyboardTag.ATTR_ROW_HEIGHT);
					int rowHeight = parseDimen(keyboard.getContext(), sRowHeight);
					keyboard.setRowHeight(rowHeight);
					// keyBackground
					String sKeyBg = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_BACKGROUND);
					int keyBg = parseResourceId(keyboard.getContext(), sKeyBg);
					keyboard.setKeyBgRes(keyBg);
				}
				else if (name.equals(KeyboardTag.ROW)) {
					row = keyboard.new Row();
					String sKeyWidth = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_WIDTH);
					if (sKeyWidth != null) {
						int keyWidth = parseDimen(keyboard.getContext(), sKeyWidth);
						row.keyWidth = keyWidth;
					}
					// marginTop
					String sMarginTop = parser.getAttributeValue(null, KeyboardTag.ATTR_MARGIN_TOP);
					if (sMarginTop != null) {
						int marginTop = parseDimen(keyboard.getContext(), sMarginTop);
						row.marginTop = marginTop;
					}

					// marginBottom
					String sMarginBottom = parser.getAttributeValue(null, KeyboardTag.ATTR_MARGIN_BOTTOM);
					if (sMarginBottom != null) {
						int marginBottom = parseDimen(keyboard.getContext(), sMarginBottom);
						row.marginBottom = marginBottom;
					}
				}
				else if (name.equals(KeyboardTag.KEY)) {
					// parser部分仅仅解析并赋予属性值，关联到view由Keyboard初始化完成设置
					if (list == null) {
						list = new ArrayList<AbsKey>();
					}

					// 判断键属性
					String text = null;
					try {
						text = parser.getAttributeValue(null, KeyboardTag.ATTR_TEXT);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// Image key
					if (text == null) {
						key = new LayoutImageKey();
						AbsImageKey akey = (AbsImageKey) key;

						// src
						String src = parser.getAttributeValue(null, KeyboardTag.ATTR_SRC);
						akey.srcResId = parseResourceId(keyboard.getContext(), src);
						// imgWidth
						String sImgWidth = parser.getAttributeValue(null, KeyboardTag.ATTR_IMG_WIDTH);
						akey.imgWidth = parseDimen(keyboard.getContext(), sImgWidth);
						// imgHeight
						String sImgHeight = parser.getAttributeValue(null, KeyboardTag.ATTR_IMG_HEIGHT);
						akey.imgHeight = parseDimen(keyboard.getContext(), sImgHeight);
					}
					// text key
					else {
						key = new LayoutTextKey();
						AbsTextKey akey = (AbsTextKey) key;

						// text
						int resId = parseResourceId(keyboard.getContext(), text);
						if (resId != -1) {
							text = keyboard.getContext().getString(resId);
						}
						akey.text = text;
						// textColor
						String sKeyTextColor = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_COLOR);
						if (sKeyTextColor != null) {
							akey.textColor = parseResourceId(keyboard.getContext(), sKeyTextColor);
							akey.textColor = keyboard.getContext().getResources().getColor(akey.textColor);
						}
						// textSize
						String sKeyTextSize = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_SIZE);
						if (sKeyTextSize != null) {
							akey.textSize = parseDimen(keyboard.getContext(), sKeyTextSize);
						}
					}
					key.setCode(parser.getAttributeValue(null, KeyboardTag.ATTR_CODE));

					// keyWidth
					String sKeyWidth = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_WIDTH);
					if (sKeyWidth != null) {
						int keyWidth = parseDimen(keyboard.getContext(), sKeyWidth);
						key.setWidth(keyWidth);
					}
				}
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();
				if (name.equals(KeyboardTag.KEYBOARD)) {
					
				}
				else if (name.equals(KeyboardTag.ROW)) {
					keyboard.addRow(row);
					row.mKeyList = list;
					list = null;
				}
				else if (name.equals(KeyboardTag.KEY)) {
					list.add(key);
				}
				break;
			default:
				break;
			}
			event = parser.next();
		}
	}

	private int parseDimen(Context context, String dimen) {
		if (dimen == null) {
			return  -1;
		}

		int resId = parseResourceId(context, dimen);
		if (resId == -1) {
			//FIXME dp/sp/%p等转换
			if (dimen.endsWith("%p")) {
				float rate = Float.parseFloat(dimen.split("%")[0]) / 100f;
				return (int) (DensityUtil.getScreenWidth(context) * rate);
			}
			if (dimen.endsWith("dp")) {
				try {
					int index = dimen.indexOf("dp");
					float fdp = Float.parseFloat(dimen.substring(0, index));
					return DensityUtil.dip2px(context, fdp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			if (resId > 0) {
				return context.getResources().getDimensionPixelOffset(resId);
			}
		}
		return 0;
	}

	private int parseResourceId(Context context, String res) {
		if (res == null) {
			return  -1;
		}
		if (res.startsWith("@") && res.contains("/")) {
			String[] array = res.substring(1).split("/");
			String type = array[0];
			String resName = array[1];
			return context.getResources().getIdentifier(resName, type, context.getPackageName());
		}
//		try{  
//			 Field field=R.drawable.class.getField("icon");  
//			 int i= field.getInt(new R.drawable());  
//			  Log.d("icon",i+"");  
//			}catch(Exception e){  
//			 Log.e("icon",e.toString());  
//			}
		return -1;
	}
}