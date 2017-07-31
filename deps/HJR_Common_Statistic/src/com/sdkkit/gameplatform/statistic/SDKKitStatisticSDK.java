package com.sdkkit.gameplatform.statistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.sdkkit.gameplatform.statistic.bean.DataManager;
import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.bean.Result;
import com.sdkkit.gameplatform.statistic.engine.IEvent;
import com.sdkkit.gameplatform.statistic.engine.IEventHandler;
import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.exception.CrashHandler;
import com.sdkkit.gameplatform.statistic.io.IOManager;
import com.sdkkit.gameplatform.statistic.io.ThreadPool;
import com.sdkkit.gameplatform.statistic.sqlite.DBLevelManager;
import com.sdkkit.gameplatform.statistic.util.C;
import com.sdkkit.gameplatform.statistic.util.CacheFileUtils;
import com.sdkkit.gameplatform.statistic.util.HLog;
import com.sdkkit.gameplatform.statistic.util.InitListener;
import com.sdkkit.gameplatform.statistic.util.InternetUtil;
import com.sdkkit.gameplatform.statistic.util.MapUtil;
import com.sdkkit.gameplatform.statistic.util.ProcessUtil;
import com.sdkkit.gameplatform.statistic.util.ProtocolKeys;
import com.sdkkit.gameplatform.statistic.util.RequestParamUtil;
import com.sdkkit.gameplatform.statistic.util.SPHelper;
import com.sdkkit.gameplatform.statistic.util.StringUtil;
import com.sdkkit.gameplatform.statistic.util.TimeUtil;
import com.sdkkit.gameplatform.statistic.util.UIUtil;

/**
 * 
 * @version 产品版本: 1.0.0
 * @author 作者姓名: HooRang
 */
public class SDKKitStatisticSDK implements IEventHandler {
	private static String TAG = "SDKKitStatisticSDK";
	/**
	 * 对象实例
	 */
	private static SDKKitStatisticSDK sTongJi;

	/**
	 * 上下文
	 */
	private Activity mContext;
	/**
	 * 时间戳字符串
	 */
	private String timeStr;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 服务器ID
	 */
	private String serverNo = "";

	private String userMark = "";

	/**
	 * 角色唯一标识
	 */
	private String roleMark = "";
	/**
	 * 重发请求列表
	 */
	List<String> reslist;

	/**
	 * 关卡操作管理类
	 */
	private DBLevelManager dbmManager;

	ApplicationInfo appInfo = null;

	private IExtStatistic mExtStatistic;

	public void setExtStatistic(IExtStatistic extStatistic) {
		mExtStatistic = extStatistic;
	}

	private SDKKitStatisticSDK() {
	}

	public static SDKKitStatisticSDK getInstance() {
		synchronized (SDKKitStatisticSDK.class) {
			if (sTongJi == null) {
				sTongJi = new SDKKitStatisticSDK();
			}
		}
		return sTongJi;

	}

	/**
	 * 初始化变量 ， 执行游戏启动任务
	 * 
	 * @param mContext
	 */
	public void init(Activity mContext, Map<String, Object> params,
			InitListener listener) {
		this.mContext = mContext;

		try {
			// 初始化变量
			initVariables(params, listener);

			// 未捕获异常处理
			CrashHandler.getInstance(this.mContext).init(this.mContext);

			if (InternetUtil.checkNetWorkStatus(mContext)) {
				ThreadPool.addCheckFileThread(mContext);
				// 从服务器获取IP并执行游戏启动任务
				this.getIPTask("IP");
			} else {
				// 没有ip直接激活设备，对于单机游戏
				startGameTask(ip);
			}
			dbmManager = DBLevelManager.getInstance();
			// 初始化数据库
			dbmManager.initDB(mContext);

			listener.onSuccess();
		} catch (Exception e) {
			listener.onFailed();
			e.printStackTrace();
		}

	}

	/**
	 * 变量初始化
	 * 
	 * @since 1.0.0
	 */
	private void initVariables(Map<String, Object> params, InitListener listener) {
		try {
			C.DEVICEX = UIUtil.getXY(mContext)[0];
			C.DEVICEY = UIUtil.getXY(mContext)[1];

			if (params != null) {
				C.GAME_TYPE = params.get(ProtocolKeys.KEY_GAMETYPE).toString();
			}

			appInfo = mContext.getPackageManager().getApplicationInfo(
					mContext.getPackageName(), PackageManager.GET_META_DATA);

			Bundle metaDataBundle = appInfo.metaData;
			SPHelper.putValue(mContext, C.GAMEKEY,metaDataBundle.getString("HJR_GAMEKEY").trim());
			SPHelper.putValue(mContext, C.CHANNEL,metaDataBundle.get("HJR_CHANNEL").toString());
			SPHelper.putValue(mContext, "hjr_debug",metaDataBundle.getBoolean("HJR_DATA_URL_DEBUG"));
			
			HLog.DATA_DEBUG=metaDataBundle.getBoolean("HJR_DATA_URL_DEBUG");
			HLog.i(TAG,"hjr_debug:"+ SPHelper.getBoolean(mContext, "hjr_debug", false));

			// ***************整合sdk所需字段初始化传入***************
			RequestParamUtil.gamekey = metaDataBundle.getString("HJR_GAMEKEY").trim();
			RequestParamUtil.channel = String.valueOf(metaDataBundle.get("HJR_CHANNEL"));

			// 此KEY_CP给游戏用的
			String cp = params.containsKey(ProtocolKeys.KEY_CP) ? params.get(ProtocolKeys.KEY_CP).toString() : "";
			String sdkVersion = params.containsKey(ProtocolKeys.KEY_SDKVERSION) ? params.get(ProtocolKeys.KEY_SDKVERSION).toString() : "";
			SPHelper.putValue(mContext, "key_channel_cp", cp);
			
			RequestParamUtil.setCp(cp);
			RequestParamUtil.getInstance(mContext).setSdkVersion(sdkVersion);

			if (metaDataBundle.containsKey("KUAIFA_CONFIG_URL") || metaDataBundle.containsKey("DATA_CONFIG_URL")) {
				C.initConfig(metaDataBundle.getString("OFFICAL_CONFIG_URL"), //官方平台地址
							 
							 metaDataBundle.getString("KUAIFA_CONFIG_URL"),  //整合平台地址
							 
							 metaDataBundle.getString("DATA_CONFIG_URL"));   // 统计平台地址
			}else {
				
				C.initConfig(metaDataBundle.getBoolean("HJR_DATA_URL_DEBUG"));
			}

		} catch (Exception e) {
			HLog.i(TAG, "初始化传入的配置参数有问题，请检查！"+e.getMessage());
			listener.onFailed();
			e.printStackTrace();

		}

	}

	/**
	 * 用户登录
	 * 
	 * @param uid
	 * @since 1.0.0
	 */
	public void doUserLogin(Map<String, Object> param) {
		if (mExtStatistic != null) {
			mExtStatistic.doUserLogin(param);
		}
		DataMap params = MapUtil.map2CustMap(param);
		// 缓存服务器ID
		serverNo = params.get(ProtocolKeys.KEY_SERVERNO).toString();
		SPHelper.putValue(mContext, C.SERVERNO, serverNo);

		userMark = params.get(ProtocolKeys.KEY_USERMARK).toString();
		userMark = StringUtil.getAtStr(mContext, userMark);
		params.put(ProtocolKeys.KEY_USERMARK, userMark);
		SPHelper.putValue(mContext, C.USER_MARK, userMark);
		Task task = new Task(null, C.USER_LOGIN_URL, null, "login", this);

		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);

	}

	/**
	 * 提交订单
	 * 
	 * @param params
	 * @exception
	 * @since 1.0.0
	 */
	public void doPostOrder(Map<String, Object> param) {
		if (mExtStatistic != null) {
			mExtStatistic.doPostOrder(param);
		}
		DataMap params = MapUtil.map2CustMap(param);

		// 参数补充
		// params.put(ProtocolKeys.KEY_TIMESTAMP, TimeUtil.getTiemStamp()+"");

		// 为中文进行base64加密
		String payName = params.get(ProtocolKeys.KEY_PAYNAME).toString();
		params.put(ProtocolKeys.KEY_PAYNAME, StringUtil.encodeByBase64(payName));

		// 为中文进行base64加密
		String productDesc = params.get(ProtocolKeys.KEY_PRODUCT_DESC)
				.toString();
		params.put(ProtocolKeys.KEY_PRODUCT_DESC,
				StringUtil.encodeByBase64(productDesc));

		Task task = new Task(null, C.SEND_ORDER_URL, null, "ordersubmit", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);

	}

	    /**
     * 传递JJ比赛商品信息
     * @param obj
     */
    public void doPostJJOrderInfo(String goods_id,String goods_amount, String money_amount, String order_no, String PayMethodID, String AppSchemeID,String QuotationType,
    			String MoneyType, String ECASchemeID,Object obj){
    	HashMap< String, Object> map = new HashMap<String, Object>();
    	if(obj == null){
    		return;
    	}
    	map.put("GoodsID", goods_id);
    	map.put("GoodsAmount", goods_amount);
    	map.put("MoneyAmount", money_amount);
    	map.put("order_no", order_no);
    	map.put("PayMethodID", PayMethodID);
    	map.put("AppSchemeID", AppSchemeID);
    	map.put("QuotationType", QuotationType);
    	map.put("MoneyType", MoneyType);
    	map.put("ECASchemeID", ECASchemeID);
    	
    	String data = RequestParamUtil.getInstance(mContext)
                .getSDKKitRequestParams(map);
    	Task orderTask = new Task(null, C.SEND_JJSDK_ORDER_URL, null,
                "jjOrder", obj);
        orderTask.setPostData(data.getBytes());
        IOManager.getInstance().addTask(orderTask);
    }
    
	
	/**
	 * 游戏点击
	 * 
	 * @param params
	 * @exception
	 * @since 1.0.0
	 */
	public void doGameClick(Map<String, Object> param) {

		DataMap params = MapUtil.map2CustMap(param);
		// 为中文进行base64加密
		String name = params.get(ProtocolKeys.KEY_NAME).toString();
		params.put(ProtocolKeys.KEY_NAME, StringUtil.encodeByBase64(name));

		Task task = new Task(null, C.GAME_BTNCLICK_URL, null, "gamebtnclick",
				this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/**
	 * 玩家升级
	 * 
	 * @param params
	 * @exception
	 * @since 1.0.0
	 */
	public void doUserUpgrade(Map<String, Object> param) {
		if (mExtStatistic != null) {
			mExtStatistic.doUserUpgrade(param);
		}
		DataMap params = MapUtil.map2CustMap(param);
		Task task = new Task(null, C.USER_UPGRADE, null, "upgrade", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	public boolean isAppOnForeground() {
		try {
			return ProcessUtil.isAppOnForeground(mContext);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// 游戏进入后台保存时间点
	public void saveBackTime() {

		// System.out.println("xxxxx 进入后台：  time->" + timeStr);
		if (timeStr != null) {
			timeStr = timeStr + "|" + TimeUtil.getTimeStamp();
			String spValue = SPHelper.getValue(mContext, C.ONLINE_TIME);
			if (null != spValue && !"".equals(spValue)) {
				timeStr = spValue + "," + timeStr;
			}

			// System.out.println("xxxxx 缓存时间：  time->" + timeStr);
			SPHelper.putValue(mContext, C.ONLINE_TIME, timeStr);
		}
	}

	// 游戏进入前台保存时间点
	public void saveFrontTime() {
		// System.out.println("xxxxx 游戏进入前台： " + timeStr);
		if (!TextUtils.isEmpty(timeStr)) {
			String time = timeStr;
			doSendTimeTask(time);
			timeStr = TimeUtil.getTimeStamp() + "";
		} else {
			timeStr = TimeUtil.getTimeStamp() + "";
		}
	}

	/** 统计在线时长 */
	private void doSendTimeTask(String time) {
		if (!InternetUtil.checkNetWorkStatus(mContext)) {
			// 单机的没有网络不用发送
			return;
		}
		// System.out.println("xxxxx 提交数据： IP-》" + ip + ", time->" + time);
		if (null == ip) {
			/**
			 * 用户第一次进入游戏，没有成功的获取IP ，在提交统计的时间数据时，事先检查是否正确获取IP 地址 如果没有IP ，则先进行IP
			 * 地址的获取
			 */
			this.getIPTask("IP2");
			return;

		}

		DataMap params = new DataMap();
		params.put("cood", ip);
		params.put("timestamp", time);
		params.put(ProtocolKeys.KEY_SERVERNO,
				SPHelper.getValue(mContext, C.SERVERNO, serverNo));
		params.put(ProtocolKeys.KEY_USERMARK,
				SPHelper.getValue(mContext, C.USER_MARK, userMark));
		/* 新增的角色唯一标识 */
		params.put(ProtocolKeys.KEY_ROLEMARK,
				SPHelper.getValue(mContext, C.ROLE_MARK, roleMark));

		Task task = new Task(null, C.GAME_DEACTIVATE_URL, null,
				"doSendTimeTask", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/** 获取ip */
	private void getIPTask(String requestFlag) {
		Task task = new Task(null, C.SYS_NET_URL, null, requestFlag, this);
		task.setPostData(RequestParamUtil.getInstance(mContext)
				.getRequestParams(null).getBytes());
		IOManager.getInstance().addTask(task);
	}

	/**
	 * 创建角色
	 * 
	 * @param 用户标识
	 *            (usermark)、服务器编号(serverno)、角色唯一标识(rolemark) String
	 *            usermark,String serverno,String rolemark
	 */
	public void doCreateRole(Map<String, Object> param) {
		if (mExtStatistic != null) {
			mExtStatistic.doCreateRole(param);
		}
		DataMap params = MapUtil.map2CustMap(param);
		// 把游戏传入的角色标识记录下来，统计到在线时长里面
		roleMark = params.get(ProtocolKeys.KEY_ROLEMARK).toString();
		SPHelper.putValue(mContext, C.ROLE_MARK, roleMark);
		Task task = new Task(null, C.CREATE_ROLE_URL, null, "createrole", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);

	}

	/**
	 * 游戏启动
	 * 
	 * @param ip
	 * @exception
	 * @since 1.0.0
	 */
	private void startGameTask(String ip) {
		Task task = new Task(null, C.GAME_START_URL, null, "startGameTask",
				this);
		HashMap<String, Object> param = new HashMap<String, Object>();
		DataMap params = MapUtil.map2CustMap(param);
		params.put("cood", ip);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/** 游戏在线时长 */
	private void doGameOnlineTime() {

		String time = SPHelper.getValue(mContext, C.ONLINE_TIME);
		// System.out.println("xxxxx doGameOnlineTime：  time->" + time);
		if (!TextUtils.isEmpty(time)) {
			doSendTimeTask(time);

		}
	}

	/**
	 * 开始关卡
	 * 
	 * @param param
	 */
	public void doStartLevel(Map<String, Object> param) {
		DataMap params = MapUtil.map2CustMap(param);

		// 原因，为中文进行base64加密
		params.put(ProtocolKeys.KEY_STARTTIME, TimeUtil.getTimeStamp() + "");
		HLog.i("statistic",
				"doStartLevel--->starttime:"
						+ params.getString(ProtocolKeys.KEY_STARTTIME));
		// 设置结束时间没有值
		params.put(ProtocolKeys.KEY_ENDTIME, "");
		// 开始关卡标识，后台统计进入人数用
		params.put(ProtocolKeys.KEY_STATUS, "1");
		// 检查数据库有没有关卡的异常数据，有就上传服务器
		doCheckLevelData();
		DBLevelManager.getInstance().insert(params);
		Task task = new Task(null, C.LEVEL_PASS_URL, null, "startlevel", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	private void doCheckLevelData() {
		if (dbmManager == null) {
			dbmManager = DBLevelManager.getInstance();
		}
		List<DataMap> list = dbmManager.queryExceptionalLevel();
		if (list.size() != 0) {
			for (DataMap map : list) {
				doExceptionalLevel(map);
				dbmManager.deleteExceptionalLevel(map
						.getInt(ProtocolKeys.KEY_ID));
			}
		}
	}

	/**
	 * 重发只有开始关卡的异常数据
	 * 
	 * @param param
	 */
	private void doExceptionalLevel(DataMap params) {

		Task task = new Task(null, C.LEVEL_PASS_URL, null, "exceptionlevel",
				this);
		HLog.i("statistic",
				"doEndLevel--->starttime:"
						+ params.getString(ProtocolKeys.KEY_STARTTIME) + "\n"
						+ "doEndLevel--->endtime:"
						+ params.getString(ProtocolKeys.KEY_ENDTIME));
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/**
	 * 结束关卡 需要提交的数据字段： 用户标识usermark String 服务器编号usermark String 角色标识rolemark
	 * String 玩家等级grade int 关卡开始时间starttime int 关卡结束时间endtime int 关卡序号 seqno int
	 * 关卡唯一标识 levelId String 关卡难度difficulty int 关卡状态status int 0成功，
	 * 1，进入关卡，2失败，3退出，4异常 原因， 需要base64reason String
	 * 
	 * @param param
	 */
	public void doEndLevel(Map<String, Object> param) {

		DataMap params = MapUtil.map2CustMap(param);
		// 原因，为中文进行base64加密
		params.put(ProtocolKeys.KEY_ENDTIME, TimeUtil.getTimeStamp() + "");
		// 可选，失败原因
		params.put(ProtocolKeys.KEY_REASON, StringUtil.encodeByBase64(params
				.get(ProtocolKeys.KEY_REASON).toString()));
		if (params.get(ProtocolKeys.KEY_STATUS).toString().equals("1")
				|| params.get(ProtocolKeys.KEY_STATUS).toString().equals("4")) {
			params.put(ProtocolKeys.KEY_STATUS,
					"99" + params.get(ProtocolKeys.KEY_STATUS));
		}

		Task task = new Task(null, C.LEVEL_PASS_URL, null, "endlevel", this);
		if (getEndLevelFullMap(params) != null) {
			params = getEndLevelFullMap(params);
			HLog.i("statistic",
					"doEndLevel--->starttime:"
							+ params.getString(ProtocolKeys.KEY_STARTTIME)
							+ "\n" + "doEndLevel--->endtime:"
							+ params.getString(ProtocolKeys.KEY_ENDTIME));
		}

		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	private DataMap getEndLevelFullMap(DataMap map) {
		if (dbmManager == null)
			dbmManager = DBLevelManager.getInstance();
		// 获取开始关卡的id
		int id = dbmManager.getStartlevelId(map);
		if (id > 0) {
			map.put(ProtocolKeys.KEY_ID, id);
			// 有id则更新插入结束关卡的数据
			dbmManager.insert(map);
			// 结束关卡的数据插入成功，则获取完整的成功关卡的数据
			map = dbmManager.getSuccessLevelMapById(map);
			dbmManager.delete(id);
			HLog.i("datamap--->", map.toString());
			return map;
		} else {
			HLog.i("statistic", "结束关卡数据异常，没有相对应的开始关卡的数据");
			return map;
		}

	}

	/**
	 * 获得道具
	 * 
	 * @param param
	 */
	public void doItemGet(Map<String, Object> param) {
		DataMap params = MapUtil.map2CustMap(param);
		// 原因，为中文进行base64加密
		params.put(ProtocolKeys.KEY_REASON, StringUtil.encodeByBase64(params
				.get(ProtocolKeys.KEY_REASON).toString()));
		Task task = new Task(null, C.ITEM_GET_URL, null, "itemget", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/**
	 * 使用道具
	 * 
	 * @param param
	 */
	public void doItemConsume(Map<String, Object> param) {
		DataMap params = MapUtil.map2CustMap(param);
		// 原因，为中文进行base64加密
		params.put(ProtocolKeys.KEY_REASON, StringUtil.encodeByBase64(params
				.get(ProtocolKeys.KEY_REASON).toString()));
		Task task = new Task(null, C.ITEM_ITEM_CONSUME, null, "itemconsume",
				this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);

		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/**
	 * 购买道具
	 * 
	 * @param param
	 */
	public void doItemBuy(Map<String, Object> param) {
		DataMap params = MapUtil.map2CustMap(param);
		// 原因，为中文进行base64加密
		params.put(
				ProtocolKeys.KEY_CURRENCYTYPE,
				StringUtil.encodeByBase64(params.get(
						ProtocolKeys.KEY_CURRENCYTYPE).toString()));
		params.put(ProtocolKeys.KEY_POINT, StringUtil.encodeByBase64(params
				.get(ProtocolKeys.KEY_POINT).toString()));
		Task task = new Task(null, C.ITEM_ITEM_BUY, null, "itembuy", this);
		String paramsStr = RequestParamUtil.writeFile(params,
				task.getParameter().toString(), mContext).getRequestParams(
				params);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}

	/*	*//**
	 * 回收资源操作,退出游戏时调用
	 */
	/*
	 * public void onDestroy(){ ThreadPool.closePool();//关闭线程池 }
	 */

	/**
	 * 网络请求任务回调处理
	 */
	@Override
	public void handleTask(int initiator, Task task, int operation) {
		if (task == null || task.isFailed() || task.isCanceled()) {
			// 失败的数据已经在发送之前已经全部缓存到本地
			return;
		}

		String result;
		switch (initiator) {
		case IEvent.IO:
			if (task.getData() instanceof byte[]) {
				result = new String((byte[]) task.getData());
				String reqTaskFlag = task.getParameter().toString();
				Result desResult = DataManager.getInstance().validateResult(
						result);

				if (reqTaskFlag.equals("IP")) {
					parserIP(result);
				} else if (reqTaskFlag.equals("IP2")) {
					ip = (String) DataManager.getInstance().jsonParse(result,
							DataManager.TYPE_SYS_IP);
				} else if (reqTaskFlag.equals("startGameTask")) {
				} else if (reqTaskFlag.equals("doSendTimeTask")) {
					// timeStr = null;
					// timeStr = TimeUtil.getTiemStamp()+"";

					SPHelper.putValue(mContext, C.ONLINE_TIME, "");
					// System.out.println("xxxxx 成功提交时间：  time " + timeStr);
				} else if (reqTaskFlag.equals("login")) {
					// 为了兼容单机，一开始就要记下
					// SPHelper.putValue(mContext, C.USER_MARK, userMark);
					// SPHelper.putValue(mContext,C.SERVERNO, serverNo);

				} else if (reqTaskFlag.equals("ordersubmit")) {

				} else if (reqTaskFlag.equals("createrole")) {
					// SPHelper.putValue(mContext, C.ROLE_MARK,
					// roleMark);//为了兼容单机，一开始就要记下
				} else if (reqTaskFlag.equals("startlevel")) {
				} else if (reqTaskFlag.equals("exceptionlevel")) {
				} else if (reqTaskFlag.equals("endlevel")) {
				} else if (reqTaskFlag.equals("itemget")) {
				} else if (reqTaskFlag.equals("itemconsume")) {
				} else if (reqTaskFlag.equals("itembuy")) {
				} else if (reqTaskFlag.equals("getTmpUserSession")) {
					parserTmpUser(result);

				}
				// Message msg = new Message() ;
				// msg.obj = desResult.getMessage() ;
				// sHandler.sendMessage(msg);
				// 请求成功，删除本地缓存文件
				if (StringUtil.getPostDataMap(task.getPostData()) != null) {
					String fileName = StringUtil
							.getPostDataMap(task.getPostData())
							.get(ProtocolKeys.KEY_FILE_NAME).toString();
					if (!fileName.equals("")) {
						CacheFileUtils.deleteFile(fileName, mContext);
					}
				}
			}
			break;
		default:

			break;
		}
	}

	private void parserIP(String params) {
		Result result = DataManager.getInstance().validateResult(params);
		if (result != null) {
			switch (result.getState()) {
			case C.RESULT_0:
				ip = (String) DataManager.getInstance().jsonParse(params,
						DataManager.TYPE_SYS_IP);
				startGameTask(ip);
				// 统计游戏在线时长
				doGameOnlineTime();

				break;
			default:
				// Message message = new Message() ;
				// message.what = result.getState() ;
				// message.obj = result.getMessage() ;
				// sHandler.sendMessage(message);
				break;
			}
		}
	}

	private void parserTmpUser(String params) {
		Result result = DataManager.getInstance().validateResult(params);
		if (result != null) {
			switch (result.getState()) {
			case C.RESULT_0:

				String session = (String) DataManager.getInstance().jsonParse(
						params, DataManager.TYPE_SET_SESSION);
				RequestParamUtil.getInstance().setSession(session);
				SPHelper.putValue(mContext, C.CONFIG_SESSION, session);

				break;
			default:
				HLog.i("parserTmpUser", "获取临时用户session失败");
				break;
			}
		}else {
			throw new NullPointerException("Get session response: result is null!");
		}

	}

	/**
	 * 获取通知回调地址 ： 整合平台serverurl + "/notify/"+gamekey
	 * 
	 * @return
	 */
	public String sGenNotifyUrl() {

		return C.DOMAIN_SDKKIT + "/notify/" + RequestParamUtil.getCp() + "/"
				+ appInfo.metaData.getString("HJR_GAMEKEY");

	}

	public String getCurrentCP() {
		return SPHelper.getValue(mContext, "key_channel_cp");
	}

	/**
	 * SDKKit——临时用户 获取session的任务,提交订单的时候调用
	 * 
	 */
	public void doTmpUserSession() {
		HashMap<String, Object> params = new HashMap<String, Object>();

		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);

		Task sessionTask = new Task(null, C.GET_TMP_USER_URL, null,
				"getTmpUserSession", this);
		sessionTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(sessionTask);

	}

	/**
	 * SDKKit——提交订单
	 * 
	 * @param order_no
	 *            订单号
	 * @param amount
	 *            金额
	 * @param serverid
	 *            区服ID
	 * @param productId
	 *            商品ID
	 * @param productNum
	 *            商品数量
	 * @param gameextend
	 *            扩展参数
	 * @param notify_url
	 *            自定义回调地址
	 * @param obj
	 */

	private HashMap<String, Object> payParams;

    public HashMap<String, Object> getPayParams() {
        if (payParams == null) {
            payParams = new HashMap<String, Object>();
        }
        return payParams;
    }

    public void doPostRechargeOrder(String order_no, int amount,
                                    String serverid, String productId, String productNum,
                                    String gameExtend, String notifyUrl, Object obj) {

        if (payParams == null) {
            payParams = new HashMap<String, Object>();
        }
        payParams.put("amount", amount);
        payParams.put(
                "order_no",
                new String(Base64.encode(String.valueOf(order_no).getBytes(),
                        Base64.DEFAULT)));
        payParams.put("serverid", serverid);
        payParams.put("product_id", productId);
        payParams.put("product_num", productNum);
        payParams.put(
                "gameextend",
                new String(Base64.encode(String.valueOf(gameExtend).getBytes(),
                        Base64.DEFAULT)));
        payParams.put("notify_url", notifyUrl);

        String data = RequestParamUtil.getInstance(mContext)
                .getSDKKitRequestParams(payParams);

        Task orderTask = new Task(null, C.SEND_ANYSDK_ORDER_URL, null,
                "sendOrder", obj);
        orderTask.setPostData(data.getBytes());
        IOManager.getInstance().addTask(orderTask);
        payParams = null;
    }
	
	/**
	 * AnySDK——获取星月订单状态
	 */
	public void doPostStarMoonOrderStatus(String order_no, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("order_no", order_no);

		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);

		Task amigoTask = new Task(null, C.SEND_STARMOON_ORDER_URL, null,
				"sendStarMoonOrderStatus", obj);
		System.out.println("请求的地址:" + C.SEND_STARMOON_ORDER_URL);
		amigoTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(amigoTask);

	}
	
		/**
	 * SDKKit——获取九城订单号专用签名
	 * 
	 * @param order_no
	 *            order/submit接口返回的order_number
	 * @param game_userid
	 * @param cp_userid
	 */
	public void doPostThe9Order(String order_no, String openid,
			String openkey, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("game_uid", openid);
		params.put("u_id", openkey);
		System.out.println("九城参数 前: " + params.toString());
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task tencentMSDKTask = new Task(null, C.SEND_THE9_ORDER_URL,
				null, "sendThe9Order", obj);
		tencentMSDKTask.setPostData(data.getBytes());
		System.out.println("九城参数 后 : " + data);
		IOManager.getInstance().addTask(tencentMSDKTask);
	}

	/**
	 * SDKKit——获取充值订单结果
	 * 
	 */
	public void doRechargeOrderStatus(String orderId, Object obj) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", String.valueOf(orderId));
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task rechargeTask = new Task(null, C.ORDER_RESULT_URL, null,
				"rechargeStatus", obj);
		rechargeTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(rechargeTask);

	}

	/**
	 * SDKKit——生成授权Token
	 */

	public void doOAuthToken(String guid, String original, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("guid", guid);
		params.put(
				"original",
				new String(Base64.encode(String.valueOf(original).getBytes(),
						Base64.DEFAULT)));

		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);

		Task oauthTask = new Task(null, C.OAUTH_TOKEN_URL, null, "oauthToken",
				obj);
		oauthTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(oauthTask);

	}

	/**
	 * SDKKit——获取vivo订单号
	 */

	public void doPostVivoOrder(String order_no, int amount, String title,
			String desc, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("order_no", order_no);
		params.put("amount", amount);
		params.put(
				"title",
				new String(Base64.encode(String.valueOf(title).getBytes(),
						Base64.DEFAULT)));
		params.put(
				"desc",
				new String(Base64.encode(String.valueOf(desc).getBytes(),
						Base64.DEFAULT)));

		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);

		Task vivoTask = new Task(null, C.SEND_VIVO_ORDER_URL_NEW, null,
				"sendVivoOrder", obj);
		vivoTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(vivoTask);

	}

	/**
	 * SDKKit——获取amigo订单号
	 */

	public void doPostAmigoOrder(String order_no, String subject,
			String player_id, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("order_no", order_no);
		params.put(
				"subject",
				new String(Base64.encode(String.valueOf(subject).getBytes(),
						Base64.DEFAULT)));
		params.put("player_id", player_id);

		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);

		Task amigoTask = new Task(null, C.SEND_AMIGO_ORDER_URL, null,
				"sendAmigoOrder", obj);
		amigoTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(amigoTask);

	}

	/**
	 * SDKKit——获取meizu订单号
	 */

	public void doPostMeizuOrder(String order_no, String pay_type, String uid,
			String product_name, String game_name, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("pay_type", pay_type);
		params.put("uid", uid);
		params.put(
				"product_name",
				new String(Base64.encode(String.valueOf(product_name)
						.getBytes(), Base64.DEFAULT)));
		params.put(
				"game_name",
				new String(Base64.encode(String.valueOf(game_name).getBytes(),
						Base64.DEFAULT)));

		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task amigoTask = new Task(null, C.SEND_MEIZU_ORDER_URL, null,
				"sendMeizuOrder", obj);
		amigoTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(amigoTask);

	}

	/**
	 * SDKKit——获取游戏群订单号
	 * 
	 * @param order_no
	 * @param token
	 * @param obj
	 */
	public void doPostXmwOrder(String order_no, String token,
			String app_user_id, String app_subject, String app_description,
			Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("access_token", token);
		params.put("app_user_id", app_user_id);
		params.put(
				"app_subject",
				new String(Base64.encode(
						String.valueOf(app_subject).getBytes(), Base64.DEFAULT)));
		params.put(
				"app_description",
				new String(Base64.encode(String.valueOf(app_description)
						.getBytes(), Base64.DEFAULT)));
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_XMW_ORDER_URL, null,
				"sendXmwOrder", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}

	public void doPostTencentYSDKOrder(String type ,String order_no, String openid,
			String openkey, String pay_token, String pf, String pfkey,
			String zoneid, String amt, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("order_no", order_no);
		params.put("openid", openid);
		params.put("openkey", openkey);
		params.put("pay_token", pay_token);
		params.put("pf", pf);
		params.put("pfkey", pfkey);
		params.put("zoneid", zoneid);
		params.put("amt", amt);
		System.out.println("Test before encode : " + params.toString());
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Log.i("YSDK", C.SEND_TENCENTYSDKTASK_ORDER_URL);
		Task tencentMSDKTask = new Task(null, C.SEND_TENCENTYSDKTASK_ORDER_URL,
				null, "sendTencentYSDKOrder", obj);
		tencentMSDKTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(tencentMSDKTask);
	}
	/**
	 * SDKKit——获取腾讯MSDK（应用宝）订单号
	 * 
	 * @param order_no
	 *            order/submit接口返回的order_number
	 * @param openid
	 * @param openkey
	 * @param pay_token
	 * @param pf
	 * @param pfkey
	 * @param zoneid
	 * @param amt
	 */
	public void doPostTencentMSDKOrder(String order_no, String openid,
			String openkey, String pay_token, String pf, String pfkey,
			String zoneid, String amt, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("openid", openid);
		params.put("openkey", openkey);
		params.put("pay_token", pay_token);
		params.put("pf", pf);
		params.put("pfkey", pfkey);
		params.put("zoneid", zoneid);
		params.put("amt", amt);
		System.out.println("Test before encode : " + params.toString());
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task tencentMSDKTask = new Task(null, C.SEND_TENCENTMSDKTASK_ORDER_URL,
				null, "sendTencentMSDKOrder", obj);
		tencentMSDKTask.setPostData(data.getBytes());
		System.out.println("Test after encode : " + data);
		IOManager.getInstance().addTask(tencentMSDKTask);
	}

	/**
	 * SDKKit——获取斯凯订单号
	 * 
	 * @param order_no
	 *            订单号
	 * @param subject
	 *            商品描述 base64传递
	 * @param channel
	 *            渠道号
	 * @param appName
	 *            应用名称 base64
	 * @param appVersion
	 *            应用版本，
	 * @param obj
	 */
	public void doPostMaoPaoOrder(String order_no, String subject,
			String channel, String appName, String appVersion, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("channel", channel);
		params.put(
				"subject",
				new String(Base64.encode(String.valueOf(subject).getBytes(),
						Base64.DEFAULT)));
		params.put(
				"appName",
				new String(Base64.encode(String.valueOf(appName).getBytes(),
						Base64.DEFAULT)));
		params.put("appVersion", appVersion);
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task maopaoTask = new Task(null, C.SEND_MAOPAO_ORDER_URL, null,
				"sendMaoPaoOrder", obj);
		maopaoTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(maopaoTask);
	}

	/**
	 * SDKKit——获取网易订单号
	 * 
	 * @param order_no
	 * @param subject
	 * @param uid
	 * @param obj
	 */
	public void doPostWangYiOrder(String order_no, String subject, String uid,
			Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("uid", uid);
		params.put(
				"subject",
				new String(Base64.encode(String.valueOf(subject).getBytes(),
						Base64.DEFAULT)));
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task wangYiTask = new Task(null, C.SEND_WANGYI_ORDER_URL, null,
				"sendWangYiOrder", obj);
		wangYiTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(wangYiTask);
	}

	/**
	 * 网游开服助手获取订单号
	 * 
	 * @param order_no
	 * @param token
	 * @param app_user_id
	 * @param app_subject
	 * @param app_description
	 * @param obj
	 */

	public void doPostWykfzsOrder(String order_no, String token,
			String app_user_id, String app_subject, String app_description,
			Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", order_no);
		params.put("access_token", token);
		params.put("app_user_id", app_user_id);
		params.put(
				"app_subject",
				new String(Base64.encode(
						String.valueOf(app_subject).getBytes(), Base64.DEFAULT)));
		params.put(
				"app_description",
				new String(Base64.encode(String.valueOf(app_description)
						.getBytes(), Base64.DEFAULT)));
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_WYKFZS_ORDER_URL, null,
				"sendWykfzsOrder", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}

	/**
	 * 摇滚河马订单
	 * 
	 * @param OrderID
	 * @param userId
	 * @param obj
	 */

	public void doPostHippoOrder(String OrderID, String mOrderInfo, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", OrderID);
		params.put(
				"hippo_order",
				new String(Base64.encode(String.valueOf(mOrderInfo).getBytes(),
						Base64.DEFAULT)));
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_HIPPO_ORDER_URL, null,
				"sendHippoOrder", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}

	/**
	 * SDKKit——联想验证用户信息
	 */
	public void doVerifyUserInfo(String data, Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(
				"data",
				new String(Base64.encode(String.valueOf(data).getBytes(),
						Base64.DEFAULT)));

		String mData = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);

		Task oauthTask = new Task(null, C.VERIFY_USERIFNO_URL, null,
				"verifyUserInfo", obj);
		oauthTask.setPostData(mData.getBytes());
		IOManager.getInstance().addTask(oauthTask);
	}
		/**
	 * 沃商店订单
	 * 
	 * @param OrderID
	 * @param imei
	 * @param channelid
	 * @param appversion
	 */

	public void doPostUnipayOrder(String OrderID,String imei,String channelid,String appversion, Object obj){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", OrderID);
		params.put("imei", imei);
		params.put("channelid", channelid);
		params.put("appversion", appversion);
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_UNIPAY_ORDER_URL, null,
				"sendUnipayOrder", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}
		/**
	 * 狐狸订单
	 * 
	 * @param subject
	 * @param body
	 */

	public void doPostHuliOrder(String OrderID,String subject,String body, Object obj){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", OrderID);
		params.put("subject", subject);
		params.put("body", body);
		String data = RequestParamUtil.getInstance(mContext)
				.getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_HULI_ORDER_URL, null,
				"sendHuliOrder", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}
	
	/**
	 * 请求游易春秋的支付接口参数
	 * 
	 * @param subject
	 * @param body
	 */
	
	public void doPostYYCQInfo(String productName,String OrderNum,String uid,Object obj){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("subject", new String(Base64.encode(productName.getBytes(),Base64.DEFAULT)));
		params.put("order_no", OrderNum);
		params.put("icc_user_id", uid);
		String data = RequestParamUtil.getInstance(mContext).getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_YYCQ_ORDER_URL, null,"sendYYCQ", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}
	
	/**
	 * 获取钱宝登录需要的参数
	 */	
	public void doGetQianBaoLogin(String str,Object obj){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appCode", str);
		String data = RequestParamUtil.getInstance(mContext).getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_QIANBAO_LOGIN, null,"number", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}
	/**
	 * 获取钱宝支付需要的参数
	 */	
	public void doGetQianBaoPay(String appCode,String orderNo,String payCode,Object obj){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("appCode", appCode);
		params.put("orderNo", orderNo);
		params.put("payCode", payCode);
		params.put("payNotifyUrl", SDKKitStatisticSDK.getInstance().sGenNotifyUrl());
		String data = RequestParamUtil.getInstance(mContext).getSDKKitRequestParams(params);
		Task xmwTask = new Task(null, C.SEND_QIANBAO_PAY, null,"sendQianBao", obj);
		xmwTask.setPostData(data.getBytes());
		IOManager.getInstance().addTask(xmwTask);
	}

}
