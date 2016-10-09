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
 * ��ҳ��
 * 
 * @author Jeffery<br>
 *         �������ڣ�2016��10��9��
 * @version 1.0
 *
 */
public class MainActivity extends Activity {

	private HistoryEdit edit;
	private Context mContext = this;
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID,
			Phone.CONTACT_ID };

	/** ��ϵ����ʾ���� **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** �绰���� **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** ��ϵ������ **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** ��ϵ�˺��� **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** ��ϵ�� **/
	private ArrayList<Directory> mContacts = new ArrayList<Directory>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getPhoneContacts();
		edit = (HistoryEdit) findViewById(R.id.edit);
		edit.addTextChangedListener(new PhoneFormatTextWatcher(edit));//�绰����344
		
		edit.setData(mContacts);
	}

	// ��ȡ�ֻ�����
	private void getPhoneContacts() {
		ContentResolver resolver = getContentResolver();

		// ��ȡ�ֻ���ϵ��
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// �õ��ֻ�����
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// �õ���ϵ������
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				phoneNumber = phoneNumber.replace("-", "");
				phoneNumber = phoneNumber.replace(" ", "");
				phoneNumber = phoneNumber.replace("+86", "");

				// �ж��Ƿ����ֻ���
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
