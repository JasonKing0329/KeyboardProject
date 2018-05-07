package com.jing.lib.keyboard.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;

import com.jing.lib.keyboard.tool.DensityUtil;
import com.jing.lib.keyboard.view.AbsImageKey;
import com.jing.lib.keyboard.view.AbsKey;
import com.jing.lib.keyboard.view.AbsTextKey;
import com.jing.lib.keyboard.view.Keyboard;
import com.jing.lib.keyboard.view.layout.LayoutImageKey;
import com.jing.lib.keyboard.view.layout.LayoutTextKey;

/**
 * 键盘内容解析器，读取xml文件，转换为Keyboard由KeyboardView生成UI
 */
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
					// keyHeight
					String sKeyHeight = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_HEIGHT);
					int keyHeight = parseDimen(keyboard.getContext(), sKeyHeight);
					keyboard.setKeyHeight(keyHeight);
					// divider
					String sDivider = parser.getAttributeValue(null, KeyboardTag.ATTR_DIVIDER);
					int divider = parseDimen(keyboard.getContext(), sDivider);
					keyboard.setDivider(divider);
					// keyTextColor
					String sKeyTextColor = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_COLOR);
					int keyTextColor = parseColor(keyboard.getContext(), sKeyTextColor);
					keyboard.setKeyTextColor(keyTextColor);
					// keyTextSize
					String sKeyTextSize = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_SIZE);
					int keyTextSize = parseDimen(keyboard.getContext(), sKeyTextSize);
					//px转换为dp
					if (keyTextSize != -1) {
						keyTextSize = DensityUtil.px2dip(keyboard.getContext(), keyTextSize);
					}
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
					// verticalSpace
					String sVerticalSpace = parser.getAttributeValue(null, KeyboardTag.ATTR_VERTICAL_SPACE);
					int verticalSpace = parseDimen(keyboard.getContext(), sVerticalSpace);
					keyboard.setVerticalSpace(verticalSpace);
					// horizontalSpace
					String sHorizontalSpace = parser.getAttributeValue(null, KeyboardTag.ATTR_HORIZONTAL_SPACE);
					int horizontalSpace = parseDimen(keyboard.getContext(), sHorizontalSpace);
					keyboard.setHorizongtalSpace(horizontalSpace);
					// gravity
					String sGravity = parser.getAttributeValue(null, KeyboardTag.ATTR_GRAVITY);
					int gravity = parseGravity(sGravity);
					keyboard.setGravity(gravity);
				}
				else if (name.equals(KeyboardTag.ROW)) {
					row = keyboard.new Row();
					String sKeyWidth = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_WIDTH);
					if (sKeyWidth != null) {
						int keyWidth = parseDimen(keyboard.getContext(), sKeyWidth);
						row.keyWidth = keyWidth;
					}
					String sKeyHeight = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_HEIGHT);
					if (sKeyHeight != null) {
						int keyHeight = parseDimen(keyboard.getContext(), sKeyHeight);
						row.keyHeight = keyHeight;
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

					// marginLeft
					String sMarginLeft = parser.getAttributeValue(null, KeyboardTag.ATTR_MARGIN_LEFT);
					if (sMarginLeft != null) {
						int marginLeft = parseDimen(keyboard.getContext(), sMarginLeft);
						row.marginLeft = marginLeft;
					}

					// marginRight
					String sMarginRight = parser.getAttributeValue(null, KeyboardTag.ATTR_MARGIN_RIGHT);
					if (sMarginRight != null) {
						int marginRight = parseDimen(keyboard.getContext(), sMarginRight);
						row.marginRight = marginRight;
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
							akey.textColor = parseColor(keyboard.getContext(), sKeyTextColor);
						}
						// textSize
						String sKeyTextSize = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_TEXT_SIZE);
						if (sKeyTextSize != null) {
							akey.textSize = parseDimen(keyboard.getContext(), sKeyTextSize);
							//px转换为dp
							akey.textSize = DensityUtil.px2dip(keyboard.getContext(), akey.textSize);
						}
					}
					key.setCode(parser.getAttributeValue(null, KeyboardTag.ATTR_CODE));

					// keyWidth
					String sKeyWidth = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_WIDTH);
					if (sKeyWidth != null) {
						int keyWidth = parseDimen(keyboard.getContext(), sKeyWidth);
						key.setWidth(keyWidth);
					}
					// keyHeight
					String sKeyHeight = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_HEIGHT);
					if (sKeyHeight != null) {
						int keyHeight = parseDimen(keyboard.getContext(), sKeyHeight);
						key.setHeight(keyHeight);
					}
					// keyBackground, key单独的背景颜色
					String sKeyBg = parser.getAttributeValue(null, KeyboardTag.ATTR_KEY_BACKGROUND);
					key.backgroundResId = parseResourceId(keyboard.getContext(), sKeyBg);
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

	private int parseGravity(String sGravity) {
		if (!TextUtils.isEmpty(sGravity)) {
			String array[] = sGravity.split("\\|");
			List<Integer> list = new ArrayList<>();
			for (String grav:array) {
				if (grav.equals("bottom")) {
					list.add(Gravity.BOTTOM);
				}
				else if (grav.equals("top")) {
					list.add(Gravity.TOP);
				}
				else if (grav.equals("center")) {
					list.add(Gravity.CENTER);
				}
				else if (grav.equals("center_horizontal")) {
					list.add(Gravity.CENTER_HORIZONTAL);
				}
				else if (grav.equals("center_vertical")) {
					list.add(Gravity.CENTER_VERTICAL);
				}
				else if (grav.equals("right")) {
					list.add(Gravity.RIGHT);
				}
				else if (grav.equals("left")) {
					list.add(Gravity.LEFT);
				}
				else if (grav.equals("end")) {
					list.add(Gravity.END);
				}
				else if (grav.equals("start")) {
					list.add(Gravity.START);
				}
			}
			int result = -1;
			for (int gravity:list) {
				if (result == -1) {
					result = gravity;
				}
				else {
					result = result | gravity;
				}
			}
			return result;
		}
		return -1;
	}

	/**
	 *
	 * @param context
	 * @param dimen
	 * @return 返回以px为单位
	 */
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
			else if (dimen.endsWith("dp")) {
				try {
					int index = dimen.indexOf("dp");
					float fdp = Float.parseFloat(dimen.substring(0, index));
					return DensityUtil.dip2px(context, fdp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (dimen.endsWith("dip")) {
				try {
					int index = dimen.indexOf("dip");
					float fdp = Float.parseFloat(dimen.substring(0, index));
					return DensityUtil.dip2px(context, fdp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (dimen.endsWith("sp")) {
				try {
					int index = dimen.indexOf("sp");
					float fdp = Float.parseFloat(dimen.substring(0, index));
					return DensityUtil.dip2px(context, fdp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (dimen.endsWith("px")) {
				try {
					int index = dimen.indexOf("px");
					float fdp = Float.parseFloat(dimen.substring(0, index));
					return (int) fdp;
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

	private int parseColor(Context context, String res) {
		if (res == null) {
			return  -1;
		}
		if (res.startsWith("#")) {
			return Color.parseColor(res);
		}
		else {
			return context.getResources().getColor(parseResourceId(context, res));
		}
	}

	private int parseResourceId(Context context, String res) {
		if (res == null) {
			return  -1;
		}
		if (res.startsWith("@")) {
			if (res.contains("/")) {
				String[] array = res.substring(1).split("/");
				String type = array[0];
				String resName = array[1];
				return context.getResources().getIdentifier(resName, type, context.getPackageName());
			}
			// android 26 or 27开始会将xml里引用的@dimen/color/string等资源自动全部翻译为资源id值，例如"@dimen/kbd_height"解析出来是"@214321545"
			else {
				return Integer.parseInt(res.substring(1));
			}
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
