package com.sdkkit.gameplatform.statistic;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.sdkkit.gameplatform.statistic.bean.DataManager;
import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.bean.Result;
import com.sdkkit.gameplatform.statistic.engine.IEvent;
import com.sdkkit.gameplatform.statistic.engine.IEventHandler;
import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.io.IOManager;
import com.sdkkit.gameplatform.statistic.util.C;
import com.sdkkit.gameplatform.statistic.util.CacheFileUtils;
import com.sdkkit.gameplatform.statistic.util.MapUtil;
import com.sdkkit.gameplatform.statistic.util.ProtocolKeys;
import com.sdkkit.gameplatform.statistic.util.RequestParamUtil;
import com.sdkkit.gameplatform.statistic.util.SPHelper;
import com.sdkkit.gameplatform.statistic.util.StringUtil;

public class CacheDataCore implements IEventHandler{
	
	private static CacheDataCore cResend;
	private static  Context mContext;
	private CacheDataCore (){}
	
	public static CacheDataCore getInstance(Context context){
		mContext=context;
		synchronized (CacheDataCore.class) {
			if (cResend == null) {
				cResend = new CacheDataCore(); 
			}
		}
		return cResend ;
		
	}
	/** 获取ip 没有数据发送，没必要缓存*//*
	public  void getIP(String requestFlag,DataMap params) {
		Task task = new Task(null, C.SYS_NET_URL, null, requestFlag, this);
		task.setPostData(RequestParamUtil.getInstance(mContext).getRequestParams(params).getBytes());
		IOManager.getInstance().addTask(task);
	}*/
	/** 游戏启动*/ 
	public  void startGame(DataMap params) {
		Task task = new Task(null, C.GAME_START_URL, null, "startGameTask", this);
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**异常*/
	public  void sendExp(DataMap params){
		Task task = new Task(null, C.ABNORMAL_URL, null, "abnormal", this);
		task.setPostData(RequestParamUtil.getInstance(mContext).getRequestParams(params).getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**登陆*/
	public  void login(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.USER_LOGIN_URL, null, "login",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**提交订单*/
	public  void orderSubmit(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.SEND_ORDER_URL, null, "ordersubmit",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	/**游戏点击*/
	public  void gameBtnClick(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.GAME_BTNCLICK_URL, null, "gamebtnclick",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**游戏升级*/
	public  void upgrade(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.USER_UPGRADE, null, "upgrade",this);
		
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	
	/**创建角色*/
	public  void createRole(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.CREATE_ROLE_URL, null, "createrole",this);
		
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	
	/**发送在线时长*/
	public  void doSendTimeTask(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.GAME_DEACTIVATE_URL, null, "doSendTimeTask",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	/**开始关卡*/
	public  void startLevel(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.LEVEL_PASS_URL, null, "startlevel",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	
	/**结束关卡*/
	public  void endLevel(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.LEVEL_PASS_URL, null, "endlevel",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	
	/**重发只有开始关卡的异常数据 */
	public  void exceptionLevel(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.LEVEL_PASS_URL, null, "exceptionlevel",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	
		
	/**获取道具 */
	public  void itemGet(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.ITEM_GET_URL, null, "itemget",this);
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**消费道具 */
	public  void itemConsume(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.ITEM_ITEM_CONSUME, null, "itemconsume",this);
		
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**购买道具 */
	public  void itemBuy(DataMap params){
		String paramsStr =  RequestParamUtil.getInstance(mContext).getRequestParams(params);
		Task task = new Task(null, C.ITEM_ITEM_BUY, null, "itembuy",this);
		
		task.setPostData(paramsStr.getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**
	 * 网络请求任务回调处理
	 */
	@Override
	public void handleTask(int initiator, Task task, int operation) {
		if (task == null || task.isFailed() || task.isCanceled()) {
			Log.i("statistic--->","重发数据已缓存，再次重发失败!");
			return;
		}

		String result;
		switch (initiator) {
		case IEvent.IO:
			if (task.getData() instanceof byte[]) {
				result = new String((byte[]) task.getData());
				String reqTaskFlag = task.getParameter().toString(); 
				Result desResult = DataManager.getInstance().validateResult(result);
			/*	if (reqTaskFlag.equals("IP")) {
				}else if(reqTaskFlag.equals("IP2")){
				}else*/ if (reqTaskFlag.equals("startGameTask")) {
				}else if (reqTaskFlag.equals("abnormal")) {
				}else if (reqTaskFlag.equals("doSendTimeTask")) {
				}else if (reqTaskFlag.equals("login")) {
				}else if (reqTaskFlag.equals("ordersubmit")) {
				}else if (reqTaskFlag.equals("createrole")) {
				}else if (reqTaskFlag.equals("startlevel")) {
				}else if (reqTaskFlag.equals("exceptionlevel")) {
				}else if (reqTaskFlag.equals("endlevel")) {
				}else if (reqTaskFlag.equals("itemget")) {
				}else if (reqTaskFlag.equals("itemconsume")) {
				}else if (reqTaskFlag.equals("itembuy")) {
				}
			   //请求成功，删除本地缓存文件
				if(StringUtil.getPostDataMap(task.getPostData())!=null)
				{
					String fileName = StringUtil.getPostDataMap(task.getPostData()).get(ProtocolKeys.KEY_FILE_NAME).toString();
					if(!fileName.equals("")){
						CacheFileUtils.deleteFile(fileName, mContext);
					}
				}
			}
			break;
		default:
			
			
			break;
		}
	}
	
	
	
	
}
