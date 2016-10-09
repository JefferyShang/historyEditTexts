package com.example.historyedittexts;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 手机号码格式化 3 4 4
 * 
 * @author Jeffery<br>
 *         创建日期：2016年10月9日
 * @version 1.0
 *
 */
public class PhoneFormatTextWatcher implements TextWatcher {
	int beforeTextLength = 0;
	int onTextLength = 0;
	boolean isChanged = false;

	int location = 0;// 记录光标的位置
	private char[] tempChar;
	private StringBuffer buffer = new StringBuffer();
	int konggeNumberB = 0;
	private EditText et_input;

	public PhoneFormatTextWatcher(EditText et_input) {
		super();
		this.et_input = et_input;
	}

	public void afterTextChanged(Editable editable) {
		if (isChanged) {
			location = et_input.getSelectionEnd();
			int index = 0;
			while (index < buffer.length()) {
				if (buffer.charAt(index) == ' ') {
					buffer.deleteCharAt(index);
				} else {
					index++;
				}
			}

			index = 0;
			int konggeNumberC = 0;
			while (index < buffer.length()) {
				if (index == 3 || index == 8) {
					buffer.insert(index, ' ');
					konggeNumberC++;
				}
				index++;
			}

			if (konggeNumberC > konggeNumberB) {
				location += (konggeNumberC - konggeNumberB);
			}

			tempChar = new char[buffer.length()];
			buffer.getChars(0, buffer.length(), tempChar, 0);
			String str = buffer.toString();
			if (location > str.length()) {
				location = str.length();
			} else if (location < 0) {
				location = 0;
			}

			et_input.setText(str);
			Editable etable = et_input.getText();
			Selection.setSelection(etable, et_input.getText().length());
//			try {
//				Selection.setSelection(etable, location);
//			} catch (Exception e) {
//				Selection.setSelection(etable, et_input.getText().length());
//			}

			isChanged = false;
		}
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		onTextLength = s.length();
		buffer.append(s.toString());
		if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
			isChanged = false;
			return;
		}
		isChanged = true;
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		beforeTextLength = s.length();
		if (buffer.length() > 0) {
			buffer.delete(0, buffer.length());
		}
		konggeNumberB = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				konggeNumberB++;
			}
		}
	}
}