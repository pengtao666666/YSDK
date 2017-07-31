package com.sdkkit.gameplatform.statistic.bean;


import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Base64;

import com.hjr.sdkkit.framework.aes.AESUtil;
import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.io.IOManager;
import com.sdkkit.gameplatform.statistic.util.C;
import com.sdkkit.gameplatform.statistic.util.L;
import com.sdkkit.gameplatform.statistic.util.RequestParamUtil;

/**
 * 1,保存所有业务逻辑数据,并对数据进行有效的利用 2,保证能对任意数据的 添加，设置，删除及释放 3,根据数据的状态分配并创建数据结构
 * 
 * @author xingyong<xingyong@cy2009.com>
 */
public class DataManager {

	private static final String TAG = "DataManager";
	public static final int TYPE_SYS_IP = 817;
	
	//整合sdk服务端数据返回处理
	public static final int TYPE_SET_SESSION = 838;
	public static final int TYPE_RECHARGE_STATUS = 839;
	public static final int TYPE_OAUTH_TOKEN = 840;
	public static final int TYPE_OAUTH_TOKEN_USERINFO = 841;
	
	private static DataManager mSingleton = null;


	private DataManager() { }

	/**
	 * 初始化数据管理器
	 */
	synchronized public static boolean init() {
		boolean result = true;
		if (mSingleton != null) {
			return true;
		}
		try {
			// 创建DataManager
			mSingleton = new DataManager();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 退出数据管理器
	 */
	public static void exit() {
		if (mSingleton == null) {
			return;
		}
		// 退出时,是不要要做数据的保存操作,是时清空所有变量
	}

	/**
	 * 多线程安全: 获取DataManager句柄
	 */
	synchronized public static final DataManager getInstance() {
		if (mSingleton == null) {
			if (!init()) {
				if (L.ERROR)
					L.e(TAG, "Failed to init itself");
			}
		}
		return mSingleton;
	}

	/**
	 * 网络响应数据校验
	 * 
	 * @param params
	 * @return
	 */
	public Result validateResult(String params) {
		params = AESUtil.s_Decryption(params, params.length(), C.AES_KEY);
		//Log.i(TAG, params);
		Result result = new Result();
		try {
			JSONObject json = new JSONObject(params);
			JSONObject infoJson = json.getJSONObject("info");
			result.setState(Integer.parseInt(infoJson.getString("result")));
			result.setMessage(infoJson.getString("errorinfo"));
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	

	


	String tmpUser;
	
	public String getTmpUser() {
		return tmpUser;
	}

	public void setTmpUser(String tmpUser) {
		this.tmpUser = tmpUser;
	}

	/**
	 * json数据解析
	 * 
	 * @param params
	 * @param type
	 * @return
	 */
	public Object jsonParse(String params, int type) {
		// AES解密网络返回来的数据
		params = AESUtil.s_Decryption(params, params.length(), C.AES_KEY);
		//Log.w(TAG, "网络返回数据: "+params);
		boolean result = false;
		
		try {
			JSONObject json = new JSONObject(params);
			JSONObject dataJson = null;
			JSONArray  dataJsonArray = null;
			if (json.has("data")){
				dataJson = json.optJSONObject("data");
				//如果取不到jsonObject则试一下jsonArray取第一个元素
				//如果以后出现jsonArray数据 则在这里做扩展 _lijianqiao 
				if(dataJson==null){
					dataJsonArray = json.getJSONArray("data");
				}
			}
			
			switch (type) {

			case TYPE_SYS_IP:
				if (dataJson != null) {
					return dataJson.getString("ip");
				}
				return "";
				/*整合sdk服务端返回数据处理********************************/
			case TYPE_SET_SESSION:
				if (dataJson != null) {
					return dataJson.optString("session");
				}
				
				
				return "";
			case TYPE_RECHARGE_STATUS:
				if (dataJson != null) {
					return dataJson.optString("status");
				}
				return "";
			case TYPE_OAUTH_TOKEN:
				if (dataJson != null) {
					return dataJson.optString("token");
				}

				return "";
			case TYPE_OAUTH_TOKEN_USERINFO:
				if (dataJson != null) {
					return dataJson.optString("original");
				}
				
				return "";
			}
					
		
		} catch (Exception e) {
			result = false;
			long timestamp = System.currentTimeMillis();
			String time = String.valueOf(timestamp).substring(0, String.valueOf(timestamp).length()-3);
			String re = "["+e.getMessage() + "#" + time +"]";
			sendExpTask(re);
		}
		return result;
	}
	
	private void sendExpTask(String msg) {
		Task task = new Task(null, C.ABNORMAL_URL, null, null, this);
		task.setPostData(getSendExpParams(msg).getBytes());
		IOManager.getInstance().addTask(task);
	}
	private String getSendExpParams(String msg) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("content", new String(Base64.encode(msg.getBytes(), Base64.DEFAULT)));
		return RequestParamUtil.getInstance().getRequestParams(params);
	}
}
