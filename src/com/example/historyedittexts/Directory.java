package com.example.historyedittexts;

import java.io.Serializable;

/**
 * 通讯录bean
 * 
 * @author Jeffery<br>
 *         创建日期：2016年10月9日
 * @version 1.0
 *
 */
public class Directory implements Serializable{
	
	/**手机号码*/
	private String contactNumber;
	/**姓名*/
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