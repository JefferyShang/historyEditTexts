package com.example.historyedittexts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码工具类
 * 
 * @author Jeffery<br>
 *         创建日期：2016年10月9日
 * @version 1.0
 *
 */
public class PhoneUtils {

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//        Pattern p = Pattern.compile("^1[3|5|6|7|8]\\\\d{9}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
    
    /**
     * 格式化数字为3 4 4
     * phone的格式为11位数字
     * @param phone
     */
	public static String format344(String phone) {
		if (phone.length() == 11) {//判断数字长度
			StringBuffer sb = new StringBuffer();

			char[] charArray = phone.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (i == 3 || i == 7) {
					sb.append(" " + charArray[i]);
				} else {
					sb.append(charArray[i]);
				}
			}
			return sb.toString();
		}
		return null;
	}

}
