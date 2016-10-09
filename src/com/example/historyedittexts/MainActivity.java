package com.example.historyedittexts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

/**
 * 主页面
 * 
 * @author Jeffery<br>
 *         创建日期：2016年10月9日
 * @version 1.0
 *
 */
public class MainActivity extends Activity {

	private HistoryEdit edit;
	private Context mContext = this;
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID,
			Phone.CONTACT_ID };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** 联系人号码 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** 联系人 **/
	private ArrayList<Directory> mContacts = new ArrayList<Directory>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getPhoneContacts();
		edit = (HistoryEdit) findViewById(R.id.edit);
		edit.addTextChangedListener(new PhoneFormatTextWatcher(edit));//电话号码344
		
		edit.setData(mContacts);
	}

	// 获取手机号码
	private void getPhoneContacts() {
		ContentResolver resolver = getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				phoneNumber = phoneNumber.replace("-", "");
				phoneNumber = phoneNumber.replace(" ", "");
				phoneNumber = phoneNumber.replace("+86", "");

				// 判断是否是手机号
				boolean mobileNO = PhoneUtils.isMobileNO(phoneNumber);

				if (mobileNO) {

					String format344 = PhoneUtils.format344(phoneNumber);
					if (format344!=null) {
						mContactsName.add(contactName);
						mContactsNumber.add(format344);
					}
				}

			}
			phoneCursor.close();
			if (mContactsName.size() <= 0) {
			} else {
				for (int i = 0; i < mContactsName.size(); i++) {
					Directory directory = new Directory();
					directory.setContactName(mContactsName.get(i));
					directory.setContactNumber(mContactsNumber.get(i));
					mContacts.add(directory);
				}
			}
		}
	}

}
