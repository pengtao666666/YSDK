package com.gameworks.sdk.standard.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.gameworks.sdk.standard.ISDKKitCallBack;
import com.gameworks.sdk.standard.ParamsKey;
import com.gameworks.sdk.standard.beans.SDKKitResponse;
import com.gameworks.sdk.standard.utils.Constants;
import com.sdkkit.gameplatform.statistic.util.HLog;

public class SDKKitCoreProxy {
	
	private static final String TAG = "SDKKitCoreProxy";
	
	private SDKKitCore sdkkitCore;
	private static SDKKitCoreProxy sInstance = null;
	private Class<?> clzz = null;
	private Class<?> callBackHandlerClzz = null;
	
	private Activity mGlActivity;
	private Object callBackHandleInstance = null;
	
	private boolean isBackFromPay = false; 
	private String cachePayOrderId = "";
	public SDKKitCoreProxy() {
		sdkkitCore = SDKKitCore.getInstance();
	}

	
	
	/**
	 * 全局回调对象
	 */
	private ISDKKitCallBack sdkCallback = new ISDKKitCallBack() {

		@Override
		public void onResponse(SDKKitResponse arg0, int arg1) {
			HLog.i(TAG,"Enter onResponse ,begin callback to game#" + arg1 );
			try {
				newCallBackInstance();
				int retStatus = Constants.REQUEST_SUCCESS;
				if (arg1 == Constants.RESPONSE_FLAG_ORDERRESULT) {
					String code = arg0.getHead().getCode(); 
					if ("0".equals(code) || "4".equals(code)) {
						retStatus = Constants.REQUEST_SUCCESS;
					}else {
						retStatus = Constants.REQUEST_FAIL;
					}
				}else {
					retStatus = arg0.getHead().getStatus() == Constants.REQUEST_SUCCESS 
								? Constants.REQUEST_SUCCESS 
								: Constants.REQUEST_FAIL;
				}
				startRefMessage2Game(arg1, retStatus,arg0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onError(SDKKitResponse arg0, int arg1) {
			try {
				newCallBackInstance();
				String msg = "onError";
				if (arg0 != null & arg0.getHead() !=null ) {
					msg = arg0.getHead().getErrorMsg();
				}
				HLog.i(TAG,"Holy shit , occur error#" +  arg1+",message#" + msg);
				
				
				startRefMessage2Game(arg1, -1,arg0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	};
	
	private void startRefMessage2Game(int flag,int retStatus, SDKKitResponse response){
		
		try {
			String retMessage = response.getHead() == null ? "callback message is null" : response.getHead().getErrorMsg();
			
			switch (flag) {
				case Constants.RESPONSE_FLAG_INIT:
					refMessageCallBack("initCallBack",retStatus,retMessage);
					break;
				case Constants.RESPONSE_FLAG_LOGIN:
					String loginUserId = "";
					String loginUserName = "";
					String loginAuthToken = "";
					String loginOpenId = "";
					if (response.getBody() != null) {
						 loginUserId = response.getBody().getLoginUserId();
						 loginUserName = response.getBody().getLoginUserName();
						 loginAuthToken = response.getBody().getLoginAuthToken();
						 loginOpenId = response.getBody().getOpenId();
					}
					refMessageCallBack("loginCallBack",retStatus,retMessage,new String[]{loginUserId,loginUserName,loginAuthToken,loginOpenId});
					break; 
				case Constants.RESPONSE_FLAG_LOGINOUT:
					refMessageCallBack("logoutCallBack",retStatus,retMessage);
					break; 
				case Constants.RESPONSE_FLAG_PAY:
					
					cachePayOrderId = (null == response.getBody()) ? "" : response.getBody().getPayKitOrderId();
					
					if (retStatus == Constants.REQUEST_SUCCESS) {
						// Confirm Order 
						HLog.i(TAG,"Pay return success , Begin confirm order information");
						isBackFromPay = true ;
					//	Bundle bundle = new Bundle();
					//	bundle.putString(ParamsKey.KEY_PAY_ORDER_ID, cachePayOrderId);
					//	this.getOrderInfo(bundle);
					refMessageCallBack("payCallBack",retStatus,retMessage,new String[]{cachePayOrderId});
					}else {
						
						refMessageCallBack("payCallBack",retStatus,retMessage,new String[]{cachePayOrderId});
					}
					break; 
				case Constants.RESPONSE_FLAG_ORDERRESULT:
					if (isBackFromPay) {
						isBackFromPay = false; 
						HLog.i(TAG,"Confirm order information return ");
						refMessageCallBack("payCallBack",retStatus,retMessage,new String[]{cachePayOrderId});
						
					}else {
						String orderStatus = response.getHead().getCode();
						refMessageCallBack("getOrderResultCallBack",retStatus,retMessage,new String[]{orderStatus});
					}
					break;
				case Constants.RESPONSE_FLAG_EXITGAME:
					refMessageCallBack("exitGameCallBack",retStatus,retMessage);
					break; 
				default:
					HLog.i(TAG,"No such callback method be found  " + flag);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	private void newCallBackInstance(){
		try {
			if (null != callBackHandlerClzz) {
				return ;
			}
			callBackHandlerClzz = Class.forName("com.hjr.sdkkit.framework.mw.openapi.HJRSDKKitPlateformCore");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Method mtd = null;
		try {
			mtd = callBackHandlerClzz.getMethod("getPlatformCallBack", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		try {
			callBackHandleInstance = mtd.invoke(null, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	private void refMessageCallBack(String methodName, int retStatus, String retMessage){
		Method method = null ;
		try {
			 HLog.i(TAG, "methodName#" + methodName+",retStatus#" + retStatus +",retMessage#" +retMessage);
			 method = clzz.getMethod(methodName, new Class[]{ int.class ,String.class});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			method.invoke(callBackHandleInstance, new Object[]{retStatus,retMessage});
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	private void refMessageCallBack(String methodName, int retStatus, String retMessage,String... arguments){
		Method method = null ;
		try {
			HLog.i(TAG, "methodName#" + methodName+",retStatus#" + retStatus +",retMessage#" +retMessage+",arguments#"+prtArray(arguments));
			Class<?>[] clzzObj = new Class[arguments.length + 2]; 
			for (int i = 0; i < clzzObj.length; i++) {
				clzzObj[i] = String.class;
			}
			clzzObj[arguments.length] = int.class;
			clzzObj[arguments.length+1] = String.class;
			method = clzz.getMethod(methodName, clzzObj);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			
			Object[] paramsObj = new Object[arguments.length + 2];
			for (int i = 0; i < paramsObj.length - 2; i++) {
				paramsObj[i] = arguments[i];
			}
			
			paramsObj[arguments.length] = retStatus;
			paramsObj[arguments.length+1] = retMessage;
			
			method.invoke(callBackHandleInstance, paramsObj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	public static SDKKitCoreProxy getInstance() {
		if (sInstance == null) {
			sInstance = new SDKKitCoreProxy();
		}
		return sInstance;
	}

	public void init(Activity activity, Bundle data,Class<?> clzzClass) {
		if (data.containsKey(ParamsKey.KEY_INIT_DEBUG_MODE)) {
			HLog.DEBUG = "0".equals(data.getString(ParamsKey.KEY_INIT_DEBUG_MODE)) 
						? true : false; 
		}
		HLog.i(TAG, "init#" + data.toString());
		clzz = clzzClass;
		mGlActivity = activity;
		sdkkitCore.setContext(activity);
		sdkkitCore.init(activity, data, sdkCallback);
	}
	
	public void login(Bundle  data) {
		HLog.i(TAG, "login#" + data.toString());
		sdkkitCore.login(data, sdkCallback);
	}

	public void pay(Bundle bundle) {
		HLog.i(TAG, "pay#" + bundle.toString());
		sdkkitCore.pay(bundle, sdkCallback);
	}
	
	/**
	 * SDK 用户中心
	 */
	public void userCenter(Bundle bundle){

		HLog.i(TAG, "userCenter#" + bundle.toString());
		sdkkitCore.userCenter(bundle, sdkCallback);
	}
	
	/**
	 * SDK logout
	 */
	public void logout(Bundle bundle ){

		HLog.i(TAG, "logout#" + bundle.toString());
		sdkkitCore.logout(bundle, sdkCallback);
	}
	
	
	
	
	/**
	 * SDK exitgame , just show a dialog
	 * @param callBack 回调
	 * @since  1.0.0
	 */
	public void exitGame(Activity activity ){
		HLog.i(TAG, "exitGame#" + activity.getClass().getName());
		mGlActivity = activity;
		sdkkitCore.setContext(activity);
		sdkkitCore.exitGame(activity, sdkCallback);
	}
	
	
	
	/**
	 * get order information
	 * @param bundle
	 * @param callBack
	  bundle.putString(ParamsKey.KEY_PAY_ORDER_ID, orderId);
	 */
	public void getOrderInfo(Bundle bundle){

		HLog.i(TAG, "getOrderInfo#" + bundle.toString());
		sdkkitCore.getOrderInfo(bundle, sdkCallback);
	}
	
	
	                                                                                                    
	
	
	/**
	 * Reassign contex value
	 * @param mContext
	 */
	@Deprecated
	public void setContext(Context mContext){
		HLog.i(TAG, "setContext#" + mContext.getClass().getName());
		try {
			mGlActivity = (Activity)mContext;
			sdkkitCore.setContext(mContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Switchaccount , deprecated
	 */
	@Deprecated
	public void switchAccount(Bundle bundle ){

		HLog.i(TAG, "switchAccount#" + bundle.toString());
		sdkkitCore.switchAccount(bundle, sdkCallback);
	}
	 
	

	
	
	/**
	 * Game's activity onPause
	 */
	public void onPause() {
		HLog.i(TAG, "onPause#" + sdkkitCore);
		if (null != sdkkitCore) {
			sdkkitCore.onPause();
		}
	}
	
	
	
	/**
	 * Game's activity onResume
	 */
	public void onResume(){
		HLog.i(TAG, "onResume#" + sdkkitCore);
		if (null != sdkkitCore) {
			sdkkitCore.onResume();
		}
	}
	
	
	/**
	 * Game's activity onStop
	 */
	public void onStop(){
		HLog.i(TAG, "onStop#" + sdkkitCore);
		if (null != sdkkitCore) {
			sdkkitCore.onStop();
		}
	}
	
	
	/**
	 * Game's activity OnDestroy
	 */
	public void onDestroy(){
		HLog.i(TAG, "onDestroy#" + sdkkitCore);
		if (null != sdkkitCore) {
			sdkkitCore.onDestroy();
		}
	}
	
	
	/**
	 * 横竖屏切换
	 * @param newConfig
	 */
	public void onConfigurationChanged(Configuration newConfig){
		HLog.i(TAG, "onResume#" + sdkkitCore);
		if (null != sdkkitCore) {
			sdkkitCore.onConfigurationChanged(newConfig);
		}
	}
	
	
	/**
	 * 保存状态
	 * @param bundle
	 * @param callBack
	 */
	public void onSaveInstanceState(Bundle bundle){
		HLog.i(TAG, "onSaveInstanceState#" + sdkkitCore + ",bundle#" + bundle.toString());
		if (null != sdkkitCore) {
			sdkkitCore.onSaveInstanceState(bundle,sdkCallback);
		}
	}
	

	
	public void onNewIntent(Intent intent){
		HLog.i(TAG, "onNewIntent#" + sdkkitCore + ",intent#" + intent);
		if (null !=sdkkitCore) {
			sdkkitCore.onNewIntent(intent);
		}
	}
	
	
	/**
	 * application里面的onCreate调用
	 * @param bundle
	 * @param callBack
	 * @return 
	 */
	public void appOnCreate(Context ctx,Bundle bundle) {
		HLog.i(TAG,"appOnCreate#" + ctx + ",bundle#" + bundle.toString());
		SDKKitCore.getInstance().appOnCreate(ctx, bundle);
	}
	
	/**
	 * application里面的attachBaseContext调用
	 * @param bundle
	 * @param callBack
	 * @return 
	 */
	public void appAttachBaseContext(Context ctx,final Bundle bundle){
		HLog.i(TAG,"appAttachBaseContext#" + ctx + ",bundle#" + bundle.toString());
		SDKKitCore.getInstance().appAttachBaseContext(ctx, bundle);
	}
	
	
	/**
	 * SDK application里面的onTerminate()方法里面调用
	 * @param bundle
	 * @param callBack
	 */
	public void appOnTerminate(Context ctx,final Bundle bundle){
		HLog.i(TAG,"appOnTerminate#" + ctx + ",bundle#" + bundle.toString());
		SDKKitCore.getInstance().appOnTerminate(ctx, bundle);
	}
	
	
	public void onLogin(Bundle bundle){
		HLog.i(TAG, "onLogin#" + bundle.toString());
		SDKKitStatistic.getIntance(mGlActivity).doTjLogin(bundle);
		
	}
	
	public void onPay(Bundle bundle){
		HLog.i(TAG, "onPay#" + bundle.toString());
		SDKKitStatistic.getIntance(mGlActivity).doTjPay(bundle);
		
	}
	
	public void onUpgrade(Bundle bundle){
		HLog.i(TAG, "onUpgrade#" + bundle.toString());
		SDKKitStatistic.getIntance(mGlActivity).doTjUpgrade(bundle);
		
	}
	
	public void onCreateRole(Bundle bundle){
		HLog.i(TAG, "onCreateRole#" + bundle.toString());
		SDKKitStatistic.getIntance(mGlActivity).doTjCreateRole(bundle);
		
	}
	
	public void onServerRoleInfo(Bundle bundle){
		HLog.i(TAG, "onServerRoleInfo#" + bundle.toString());
		SDKKitStatistic.getIntance(mGlActivity).doTjServerRoleInfo(bundle);
		
	}
	
	public void onButtonClick(Bundle bundle){
		HLog.i(TAG, "onButtonClick#" + bundle.toString());
		SDKKitStatistic.getIntance(mGlActivity).doTjClick(bundle);
		
	}
	
	private String prtArray(String[] strMessage){
		String message = "";
		if (null == strMessage) {
			return "";
		}
		
		for(String tempMsg: strMessage){
			message +="," + tempMsg;
		}
		
		
		return message;
		
	}
	
	
	
}
