package com.sdkkit.gameplatform.statistic.util;

import android.util.Log;

/**
 * Log Helper
 * @author HooRang
 *
 */
public class HLog {

	public static boolean DEBUG = false; 
	public static boolean DATA_DEBUG = false; 
	
	
	private static final String DEFAULT_TAG = "HJR_Framework_Middleware";
	private static final String DATA_DEFAULT_TAG = "HJR_DATA";
	
	public static void i(String msg ){
		
		if (DEBUG) {
			Log.i(DEFAULT_TAG, msg);
		}
		if(DATA_DEBUG){
			Log.i(DATA_DEFAULT_TAG, msg);
		}
		
	}
	
	public static void i(String tag ,String msg ){
		
		if (DEBUG) {
			Log.i(tag, msg);
		}
		if(DATA_DEBUG){
			Log.i(tag, msg);
		}
	}
	
	
}
