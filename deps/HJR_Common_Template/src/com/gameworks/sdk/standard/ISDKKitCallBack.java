package com.gameworks.sdk.standard;

import com.gameworks.sdk.standard.beans.SDKKitResponse;


public interface ISDKKitCallBack {

	/**
	 * 接口网络请求回调函数
	 * @param response 	   返回数据
	 * @param resFlag    返回请求标识
	 * @exception 
	 * @since  1.0.0
	 */
	void onResponse(SDKKitResponse response , int resFlag) ; 
	 
	
	/**
	 * 网络请求发生异常
	 * @param response  返回数据
	 * @param resFlag   返回请求标识
	 * @exception 
	 * @since  1.0.0
	 */
	void onError (SDKKitResponse response, int resFlag);  
}
