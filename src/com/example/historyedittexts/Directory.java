package com.example.historyedittexts;

import java.io.Serializable;

/**
 * ͨѶ¼bean
 * 
 * @author Jeffery<br>
 *         �������ڣ�2016��10��9��
 * @version 1.0
 *
 */
public class Directory implements Serializable{
	
	/**�ֻ�����*/
	private String contactNumber;
	/**����*/
	private String contactName;
	
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
}