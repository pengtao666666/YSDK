package com.sdkkit.gameplatform.statistic.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.hjr.sdkkit.framework.aes.AESUtil;
import com.sdkkit.gameplatform.statistic.bean.DataMap;

/*******************************************************************************
 * 封装字符串操作
 * 
 * @author <xingyong>xingyong@cy2009.com
 ******************************************************************************/
public class StringUtil {

	private static final String HTTP = "HTTP";
	private static final String FILE = "FILE";
	
	public static final int EMAIL= 0;
	public static final int MOBILE = 1;
	public static final int NONE= -1;

	/**
	 * 获取url的根目录
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlRoot(String url) {
		if (url.toUpperCase().startsWith(HTTP) || url.toUpperCase().startsWith(FILE)) {
			int star = url.indexOf("://") + 3;
			int index = url.indexOf('/', star);
			if (index <= 0) {
				index = url.length();
			}
			return url.substring(0, index);
		}
		return null;
	}

	/**
	 * 获得一个字符串的长度（中、英文）
	 * 
	 * @param value
	 * @return
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		if (value == null || "".equals(value)) {
			return valueLength;
		}
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 将MAP转化为key1=value&key2=vaule&key3=value格式的字符串
	 * 
	 * @param params
	 * @return
	 */
	public static String formatKeyValue(Map<String, Object> params) {
		if (params == null)
			return null;
		StringBuffer sb = new StringBuffer();
		String key = "", result = "";
		Object value = "";
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				sb.append(key);
				sb.append("=");
				sb.append(value);
				sb.append("&");
			}
			result = sb.toString();
			result = result.substring(0, (result.length() - 1)); // 截掉末尾字符&
		}
		return result;
	}
	public static boolean checkTwoPwd(Context context,String currentPwd,String newPwd){
		if(currentPwd.equals(newPwd)){
			Toast.makeText(context, "两次输入的密码不能相同", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	/**
	 * 检查邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {

		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();

	}
	/**
	 * 检查手机号
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();

	}
	
	
	/**检测只能包含数字和字母(false表示不是字母或者数字)*/
	public static boolean checkNumberAndLetter(String key) {

		Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
		Matcher matcher = pattern.matcher(key);

		return matcher.matches();

	}
	/**检测只能包含数字和字母(false表示不是数字)*/
	public static boolean checkNumber(String key) {
		
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(key);
		
		return matcher.matches();
		
	}

	/**
	 * 检查密码
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {

		Pattern pattern = Pattern.compile("[0-9a-zA-Z_]{6,16}");

		Matcher matcher = pattern.matcher(password);

		return matcher.matches();
	}
	/**
	 * 字符串Base64解密
	 * @param str
	 * @return str
	 */
	public static String decodeByBase64(String param) {
		if (param == null)
			return null;
		return new String(Base64.decode(param, Base64.DEFAULT));
	}
	/**
	 * 字符串Base64加密
	 * @param str
	 * @return str
	 */
	public static String encodeByBase64(String param) {
		if (param == null)
			return null;
		return new String(Base64.encode(param.getBytes(), Base64.DEFAULT));
	}



	
	/**根据输入的字符串，返回--干掉前面多个0后的字符串*/
	public static String clearFront0(String money){
		if(money==null||money.length()==1||!money.startsWith("0")){
			return money;
		}
	    return clearFront0( money.substring(1));		
	}
	



	
	
	


	/**检查是否包含汉字*/
    public static boolean checkChinese(String sequence) {

       final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
       Pattern pattern = Pattern.compile(format);
       Matcher matcher = pattern.matcher(sequence);
       return matcher.find();
   }
    
    /**清除小数*/
    public static String removeDecimal(String number){
    	return number.substring(0, number.lastIndexOf("."));
    }


	public static boolean checkCharacterNum(String str) {
		if(str!=null && str.length()>=6 && str.length()<=30){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String UrlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}
		return strPage;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static HashMap<String, String> getURLRequestMap(String URL) {
		HashMap<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
	
	public static boolean isvalidateString(String s){
		return (s !=null && !s.trim().equals(""));
	}
	
	/**
	 * 获取发送的map对象
	 * @param obj
	 * @return
	 */
	public static DataMap getPostDataMap(Object obj){
		byte[] bytes=(byte[]) obj;
		String str = new String(bytes);
		String mapStr = AESUtil.s_Decryption(str, str.length(), C.AES_KEY);
		StringBuilder sb=new StringBuilder(mapStr);
		Map<String,Object> map=MapUtil.jsonStr2Map(sb.toString());
		@SuppressWarnings("unchecked")
		Map<String,Object> param=(Map<String, Object>) map.get("param");
		return MapUtil.map2CustMap(param);
	}
	/**获取有@符号的字符串*/
	public static String getAtStr(Context mContext,String key) {
		if(key.equals("")||key.equals(" ")){
			return "";
		}
		if(key.contains("@")){
			return key;
		}else{
			return new StringBuffer(key).append("@").append(SPHelper.getValue(mContext, C.CHANNEL)).toString();
		}
		
	}

}
