package com.sdkkit.gameplatform.statistic.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP帮助类
 * @author chenjun
 */
public class SPHelper {
	
	private static final String FILENAME=C.CONFIG_FILENAME;
	
	public static void putValue(Context context,String key, String value) {
		if(context==null){
			return;
		}
		try {
			SharedPreferences.Editor editor = context.getSharedPreferences(
					FILENAME, Context.MODE_PRIVATE).edit();
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return;
			
		}
	}
	
	public static void putValue(Context context, String key, Boolean value) {
	
		try {
			if(context==null){
				return;
			}
			SharedPreferences.Editor editor = context.getSharedPreferences(
					FILENAME, Context.MODE_PRIVATE).edit();
			editor.putBoolean(key, value);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static String getValue(Context context,String key) {
		if(context==null){
			return "";
		}
		return context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).getString(key, "");
	}
	
	public static String getValue(Context context,String key,String defaultValue) {
		if(context==null){
			return "";
		}
		return context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).getString(key, defaultValue);
	}
	
	public static boolean getBoolean(Context context,String key,boolean defaultValue) {
		if(context==null){
			return false;
		}
		return context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
	}	

	/** 清空*/
	public static void emptyFile(Context context) {
		
		if(context==null){
			return;
		}

		context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit().clear().commit();
	}

	/** * 获得所有*/
	public static Map getAllByFileName(Context context){
		if(context==null){
			return null;
		}
		return context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).getAll();
	}
}
