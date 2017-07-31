package com.sdkkit.gameplatform.statistic.util;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

import com.hjr.sdkkit.framework.aes.AESUtil;
import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.io.ThreadPool;

/**
 * @ClassName: RequestParamUtil
 * @Description: 请求参数
 * @author <xingyong>xingyong@cy2009.com
 * @date 2012-7-3 下午2:35:55
 */
public class RequestParamUtil {
	
	private final static String TAG = "SDKKitStatistic_RequestParamUtil";

	private static RequestParamUtil mInstance;
	/** 游戏开发者申请的密钥 */
	//ab2d9c034cb6c62338994309a47c74a5
	public static String gamekey = "";
	public static String channel = "";
	/** 手机操作系统 */
	private String os = "android";
	/** 手机操作系统版本号 */
	private String osver = Build.VERSION.RELEASE;
	/** 后台保存的session值 */
	private String session = "";
	/** 是否刷机 */
	private String root = "0";
	/** 设备类型 */
	private String device = android.os.Build.MANUFACTURER;
	/** 支持的协议编码 */
	private String accept = "0";
	/** 网络类型 */
	private String network = "";
	private String deviceId = "";
	/** cookie */
	private String cookie = "";
	/** Wlan物理地址 */
	private String wlan = "";
	private String resolution = "800*480";
	/** Android来源平台来源平台  */
	public  static  String  source = "2e82c4eba58760463338f2951f832265";
	private String deviceX = "0";
	private String deviceY = "0";
	/** 第三方平台*/
	private static String cp = "";
	/**渠道sdk版本号*/
	private String sdkVersion="";
	
	public String getSession() {
		return session;
	}

	public String getWlan() {
		return wlan;
	}

	private Context context;

	
	public String getDeviceX() {
		return deviceX;
	}

	public void setDeviceX(String deviceX) {
		this.deviceX = deviceX;
	}

	public String getDeviceY() {
		return deviceY;
	}

	public void setDeviceY(String deviceY) {
		this.deviceY = deviceY;
	}

	public void clear(){
		if(mInstance != null)
			mInstance = null;
	}
	public static String getCp() {
		return cp;
	}

	public static  void setCp(String cp) {
		RequestParamUtil.cp = cp;
	}
	
	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {

		this.sdkVersion = sdkVersion;
	}
	
	private String getGameVersion(){
		try {  
	        PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
	        return pi.versionName;  
	    } catch (Exception e) {  
	        HLog.i(e.getMessage());
	        return "0.0.0";  
	    } 
		
		
	}
	/**
	 * 多线程安全: 初始化ActivityManager
	 */
	synchronized public static RequestParamUtil getInstance(Context context) {
		
		if (mInstance == null) {
			mInstance = new RequestParamUtil(context);
		}
		return mInstance;
	}
	synchronized public static RequestParamUtil getInstance(){
		return mInstance;
	}
	private RequestParamUtil(Context context) {
		this.context = context;
		// 初始化mac地址
		wlan = getLocalMacAddress();
		network = String.valueOf(InternetUtil.getConnectedType(context));
		resolution =C.DEVICEX+"*"+C.DEVICEY;
	}

	/**
	 * 获取请求参数（公共参数头+各请求私有参数）
	 * 
	 * @param map
	 *            各请求私有参数集合
	 * @return
	 */
	public String getRequestParams(Map map) {
	
		JSONObject json = new JSONObject();
		try {
			json.put("info", getCommonParams());
			if(map != null && !map.isEmpty()){
				json.put("param", new JSONObject(MapUtil.mapToJson(map)));
			}
		} catch (JSONException e) {
			return "";
		}
		//Log.w(TAG, "request params: "+json.toString());
	/*    if(SPHelper.getBoolean(context, "hjr_debug", false)){
	    	HLog.i(TAG," "+json.toString());
	    	}*/
		return AESUtil.s_Encryption(json.toString(), json.toString().length(), C.AES_KEY);
	}


	/**
	 * 获取公共参数
	 * 主要包装参数：
	 * version 游戏版本
	 * os 本机系统
	 * osver 系统版本
	 * wlan wlan物理地址
	 * network 连接网络类型  
	 * channel 商业渠道
	 * root 是否root
	 * device 制造商 机型
	 * session session 会话
	 * lang 是否为中文
	 * gamekey 游戏开发者申请的密钥
	 * devicetoken 设备验证码
	 * accept 支持协议的编码
	 * cookie cookie
	 * resolution 分辨率
	 * source 暂时不知道
	 */
	private JSONObject getCommonParams() {
		JSONObject json = new JSONObject();
		
		try {
			json.put("version", C.STATISTIC_VERSION);
			json.put("os", os);
			json.put("osver", osver);
			json.put("gamever", getGameVersion());
			json.put("wlan", wlan);
			json.put("network", getNetwork());
			json.put("channel",	SPHelper.getValue(context,C.CHANNEL));
			json.put("root", root);
			if(android.os.Build.MODEL.contains(android.os.Build.MANUFACTURER)){
				json.put("device", android.os.Build.MODEL);
			}else{
				json.put("device", android.os.Build.MANUFACTURER+android.os.Build.MODEL);
			}
//			json.put("session", session);
			json.put("lang", getLanguage());
			json.put("gamekey",SPHelper.getValue(context,C.GAMEKEY));
			json.put("devicetoken", getDeviceId());
			json.put("accept", accept);
			json.put("cookie", new String(Base64.encode(getCookie(C.STATISTIC_VERSION).getBytes(), Base64.DEFAULT)));
			json.put("resolution", resolution);
			json.put("source", source);
			if (isOnlyDataAPI() ) {
				json.put("single", "1");
			}
		} catch (JSONException e) {
			HLog.i(e.getMessage());
			return null;
		}
		return json;
	}

	private boolean isOnlyDataAPI() {
	try {
		AssetManager assets = context.getResources().getAssets();
		InputStream is=assets.open("hjr/hjr_channel_float_logo.png");
		return false;
	} catch (Exception e) {
		return true; 
		
	}
	}

	private String getDeviceId() {
		if(deviceId!=null && !"".equals(deviceId)){
			return deviceId;
		} else{
			deviceId = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
			return deviceId;
		}
	}

	private String getCookie(String version) {
		if(cookie!=null && !"".equals(cookie)){
			return cookie;
		} else{
			StringBuilder sb = new StringBuilder();
			sb.append(C.CHANNEL);
			sb.append("|");
			sb.append(isRootSystem()?"1":"0");
			sb.append("|");
			sb.append(resolution);
			sb.append("|");
			sb.append(os+osver);
			sb.append("|");
			sb.append(android.os.Build.MANUFACTURER);
			sb.append("|");
			sb.append(version);
			sb.append("|");
			sb.append(getLocalMacAddress());
			sb.append("|");
			sb.append(getNetwork());
			sb.append("|");
			sb.append(getOperatorName());
			sb.append("|");
			if(android.os.Build.MODEL.contains(android.os.Build.MANUFACTURER)){
				sb.append(android.os.Build.MODEL);
			}else{
				sb.append(android.os.Build.MANUFACTURER+android.os.Build.MODEL);
			}
			sb.append("|");
			sb.append(getCountryIso());
			sb.append("|");
			sb.append(Locale.getDefault().toString());
			sb.append("|");
			sb.append("");     //空串  为了和ios字段数保持一致
			sb.append("|");
			sb.append("1");		//空串  越狱非越狱  此字段android上没有用
			sb.append("|");
			sb.append("");		//空串  专服  此字段需要在特殊渠道填，360等
			sb.append("|");
			sb.append(C.GAME_TYPE); //游戏类型
			sb.append("|");
			sb.append(getGameVersion());
			
			cookie = sb.toString();
			return cookie;
		}
	}
	/**
	 * 获得网络运营商用户名
	 * @return 网络运营商用户名
	 */
	private String getOperatorName() {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		return tm.getNetworkOperatorName();
	}
	/**
	 * 获得SIM卡中的国家ISO代码
	 * @return SIM卡中的国家ISO代码
	 */
	private String getCountryIso() {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		return tm.getSimCountryIso();
	}
	public String getNetwork(){
//		if(network!=null && !"".equals(network)){
//			return network;
//		}else{
			network = String.valueOf(InternetUtil.getConnectedType(context));
			return network;
//		}
	}
	
	private final static int kSystemRootStateUnknow = -1;
	private final static int kSystemRootStateDisable = 0;
	private final static int kSystemRootStateEnable = 1;
	private static int systemRootState = kSystemRootStateUnknow;

	public static boolean isRootSystem() {
		if (systemRootState == kSystemRootStateEnable) {
			return true;
		} else if (systemRootState == kSystemRootStateDisable) {
			return false;
		}
		File f = null;
		final String kSuSearchPaths[] = { "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/" };
		try {
			for (int i = 0; i < kSuSearchPaths.length; i++) {
				f = new File(kSuSearchPaths[i] + "su");
				if (f != null && f.exists()) {
					systemRootState = kSystemRootStateEnable;
					return true;
				}
			}
		} catch (Exception e) {
		}
		systemRootState = kSystemRootStateDisable;
		return false;
	}	

	/**
	 * 设置session值
	 * 
	 * @param session
	 */
	public void setSession(String session) {
		this.session = session;
	}
	
	/**
	 * 设置游戏开发者申请的密钥
	 * @param gamekey
	 */
	public void setGamekey(String gamekey) {
		this.gamekey = gamekey;
	}
	
	/**
	 * 获取手机系统的语言，暂支持简体中文和英文
	 * 
	 * @return
	 */
	private String getLanguage() {
		//return "zh".equals(Locale.getDefault().getLanguage()) ? "zh_cn" : "en";
		return "zh".equals(Locale.getDefault().getLanguage()) ? "2" : "1";
	}

	

	/**
	 * 获取本机MAC地址
	 * 
	 * @return
	 */
	private String getLocalMacAddress() {
		if(wlan!=null && !"".equals(wlan)){
			return wlan;
		} else {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			if (info.getMacAddress() != null) {
				wlan = info.getMacAddress();
			}else {
				wlan = getDeviceSerial();
			}
			return wlan;
		}
	}
	
	
	public static String getDeviceSerial() {
		String serial = "unknown";
    	try {
			Class clazz = Class.forName("android.os.Build");
			Class paraTypes = Class.forName("java.lang.String");
			Method method = clazz.getDeclaredMethod("getString", paraTypes);
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			serial = (String)method.invoke(new Build(), "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
			serial = "";
		}
    	return serial;
	}
	
	/**整合sdkkit请求数据处理*/
	public String getSDKKitRequestParams(Map map) {
		JSONObject json = new JSONObject();
		try {
			json.put("info", getSDKKitCommonParams());
			if(map != null && !map.isEmpty()){
				json.put("param", new JSONObject(MapUtil.mapToJson(map)));
			}
		} catch (JSONException e) {
			return "";
		}
		return AESUtil.s_Encryption(json.toString(), json.toString().length(), C.AES_KEY);
	}
	private JSONObject getSDKKitCommonParams() {
		JSONObject json = new JSONObject();
		
		try {
			json.put("version", C.VERSION);
			json.put("os", os);
			json.put("osver", osver);
			json.put("wlan", wlan);
			json.put("network", getNetwork());
			json.put("channel", channel);
			json.put("root", root);
			if(android.os.Build.MODEL.contains(android.os.Build.MANUFACTURER)){
				json.put("device", android.os.Build.MODEL);
			}else{
				json.put("device", android.os.Build.MANUFACTURER+android.os.Build.MODEL);
			}
			json.put("session", SPHelper.getValue(context, C.CONFIG_SESSION));
			json.put("lang", getLanguage());
			json.put("gamekey", gamekey);
			json.put("devicetoken", getDeviceId());
			json.put("accept", accept);
			json.put("cookie", new String(Base64.encode(getCookie(C.VERSION).getBytes(), Base64.DEFAULT)));
			json.put("resolution", resolution);
			json.put("source", source);
			json.put("cp", getCp());
			json.put("sdkversion", getSdkVersion());
			
			
			
		
		} catch (JSONException e) {
			return null;
		}
		return json;
	}
	
	public static RequestParamUtil writeFile(DataMap params,String resFlag,Context mContext){
		
		if(resFlag.equals("login")){
			SPHelper.putValue(mContext, C.ROLE_MARK,"");
		}else if(resFlag.equals("abnormal")||
				 resFlag.equals("IP")||
				 resFlag.equals("IP2")||
				 resFlag.equals("startGameTask")){
		
		}else if(resFlag.equals("gamebtnclick")||resFlag.equals("createrole")){
			params.put(ProtocolKeys.KEY_USERMARK, SPHelper.getValue(mContext, C.USER_MARK));
			//必填，服务器编号，不能为中文或中文类型的特殊字符
			params.put(ProtocolKeys.KEY_SERVERNO, SPHelper.getValue(mContext,C.SERVERNO));
		}else{
			params.put(ProtocolKeys.KEY_USERMARK, SPHelper.getValue(mContext, C.USER_MARK));
			//必填，服务器编号，不能为中文或中文类型的特殊字符
			params.put(ProtocolKeys.KEY_SERVERNO, SPHelper.getValue(mContext,C.SERVERNO));
			//必填，角色唯一标识,不能为中文或中文类型的特殊字符
			params.put(ProtocolKeys.KEY_ROLEMARK, SPHelper.getValue(mContext, C.ROLE_MARK));
			
		}
		if(resFlag.equals("ordersubmit")||resFlag.equals("doSendTimeTask")){
			//服务端后台去重，暂时保留
			params.put(ProtocolKeys.KEY_CLIENTTIME, TimeUtil.getFullTime()+"");
		}
		String fileName=resFlag+"_"+TimeUtil.getFullTime();
		if(params==null){
			params=new DataMap();
		}
		params.put(ProtocolKeys.KEY_FILE_NAME, fileName);
		HLog.i(TAG,fileName);
		ThreadPool.addWriteFileThread(mContext, params, fileName);
		return RequestParamUtil.getInstance(mContext);
	}
}
