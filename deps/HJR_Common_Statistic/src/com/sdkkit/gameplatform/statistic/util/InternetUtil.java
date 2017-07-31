package com.sdkkit.gameplatform.statistic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @ClassName: InternetUtil
 * @Description: 网络服务类
 * @author <xingyong>xingyong@cy2009.com
 * @date 2012-7-12 下午5:00:54
 */
public class InternetUtil {
	
	public static final String TAG = "InternetUtil";

	/**
	 * 检查网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean checkNetWorkStatus(Context context) {
		boolean result = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isConnected()) {
			result = true;
			L.i(TAG, "The net was connected");
		} else {
			L.e(TAG, "The net was bad!");
		}
		return result;
	}
	/**
	 * 拿到当前连接类型 
	 * @return 0 错误 1 __ 2 wifi 3 __ 
	 */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
            	if(mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            		return 2;
            	}else{
            		int type = mNetworkInfo.getSubtype();
            		switch (type) {
					case TelephonyManager.NETWORK_TYPE_UMTS:
						return 1;
					case TelephonyManager.NETWORK_TYPE_HSDPA:
						return 1;
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
						return 1;
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
						return 1;
					case TelephonyManager.NETWORK_TYPE_1xRTT:
						return 1;
					case TelephonyManager.NETWORK_TYPE_HSPA:
						return 1;
					case TelephonyManager.NETWORK_TYPE_HSUPA:
						return 1;
					case TelephonyManager.NETWORK_TYPE_IDEN:
						return 3;
					case TelephonyManager.NETWORK_TYPE_GPRS:
						return 3;
					case TelephonyManager.NETWORK_TYPE_EDGE:
						return 3;
					case TelephonyManager.NETWORK_TYPE_CDMA:
						return 3;
					case TelephonyManager.NETWORK_TYPE_UNKNOWN:
						return 0;
					default:
						return 0;
					}
            	}
            }
        }
        return 0;
    }

}
