package com.gameworks.sdk.standard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public interface ISDKKitCore {
	
	/**
	 * SDK 初始化
	 */
	public  void init(Activity mContext , Bundle bundle ,final ISDKKitCallBack callBack);
	
	
	/**
	 * SDK 登录
	 */
	public void login(Bundle bundle , final ISDKKitCallBack callBack); 
	
	
	/**
	 * SDK 支付
	 */
	public void pay(Bundle bundle ,final  ISDKKitCallBack callBack ); 
	
	
	/**
	 * SDK 用户中心
	 */
	public void userCenter(Bundle bundle ,final  ISDKKitCallBack callBack ); 
	
	/**
	 * SDK 注销
	 */
	public void logout(Bundle bundle , final ISDKKitCallBack callBack);
	
	
	
	/**
	 * SDK 退出
	 * @param callBack 回调
	 * @since  1.0.0
	 */
	public void exitGame(Activity activity ,final ISDKKitCallBack callBack);
	
	
	/**
	 * 获取订单信息
	 * @param bundle
	 * @param callBack
	  bundle.putString(ParamsKey.KEY_PAY_ORDER_ID, orderId);
	 */
	public void getOrderInfo(Bundle bundle , final ISDKKitCallBack callBack);
	
	                                                                                                    
	
	
	/**
	 * 重置上下文
	 * @param mContext
	 */
	@Deprecated
	public void setContext(Context mContext);
	
	
	
	/**
	 * SDK 切换帐号
	 */
	@Deprecated
	public void switchAccount(Bundle bundle ,final  ISDKKitCallBack callBack ); 
	

	
	
	/**
	 * Game's activity onPause
	 */
	public void onPause() ;
	
	
	/**
	 * Game's activity onResume
	 */
	public void onResume();
	
	/**
	 * Game's activity onStop
	 */
	public void onStop();
	
	/**
	 * Game's activity OnDestroy
	 */
	public void onDestroy();
	
	/**
	 * 横竖屏切换
	 * @param newConfig
	 */
	public void onConfigurationChanged(Configuration newConfig);
	
	/**
	 * 保存状态
	 * @param bundle
	 * @param callBack
	 */
	public void onSaveInstanceState(Bundle bundle ,final ISDKKitCallBack callBack);

	
	public void onNewIntent(Intent intent);
	
	/**
	 * application里面的onCreate调用
	 * @param bundle
	 * @param callBack
	 * @return 
	 */
	public void appOnCreate(Context ctx,Bundle bundle) ;
	/**
	 * application里面的attachBaseContext调用
	 * @param bundle
	 * @param callBack
	 * @return 
	 */
	public void appAttachBaseContext(Context ctx,final Bundle bundle);
	
	/**
	 * SDK application里面的onTerminate()方法里面调用
	 * @param bundle
	 * @param callBack
	 */
	public void appOnTerminate(Context ctx,final Bundle bundle);
	

	
	
	
}
