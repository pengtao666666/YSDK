package com.gameworks.sdk.standard.core.test;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gameworks.sdk.standard.ISDKKitCallBack;
import com.gameworks.sdk.standard.ParamsKey;
import com.gameworks.sdk.standard.beans.SDKKitResponse;
import com.gameworks.sdk.standard.core.SDKKitCore;
import com.gameworks.sdk.standard.core.SDKKitStatistic;
import com.gameworks.sdk.standard.utils.Constants;
import com.google.gson.Gson;
import com.sdkkit.gameplatform.statistic.SDKKitStatisticSDK;
import com.tencent.tmgp.kfbb.snake.R;

/**
 * <li>文件名称: MainActivity.java</li><br>
 * <li>文件描述:模拟游戏界面</li><br>
 * <li>公 司: 游戏工场</li><br>
 * <li>内容摘要:</li><br>
 * <li>新建日期: 2015-2-25 下午4:24:17</li><br>
 * <li>修改记录:</li><br>
 * 
 * @version 产品版本: 1.0.0
 * @author 作者姓名: 沈俊飞
 */
public class MainActivity extends Activity implements ISDKKitCallBack {

	SDKKitCore sdkKitCore;
	TextView tv_show;// 显示测试信息用
	String orderId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_show = (TextView) findViewById(R.id.tv_show);
		sdkKitCore = SDKKitCore.getInstance();
		Bundle bundle = new Bundle();
		// 初始化必填参数
		String gameType = "1"; // 游戏类型：1网游，0单机
		bundle.putStringArray(ParamsKey.KEY_INIT_AMOUNT, null);// 金额 没有传null
		bundle.putString(ParamsKey.KEY_INIT_APP_ID, "");// appId
		bundle.putString(ParamsKey.KEY_INIT_APP_KEY, "");// appkey
		bundle.putString(ParamsKey.KEY_INIT_APP_SECRET, "");// appSecret没有填""
		bundle.putString(ParamsKey.KEY_INIT_CHANNEL_ID, "");// 渠道ID 没有填""
		bundle.putString(ParamsKey.KEY_INIT_DEBUG_MODE, "1");// 1为正式模式,0为测试模式，没有填""
		bundle.putString(ParamsKey.KEY_INIT_GAME_ID, "");// gameId 没有填""
		bundle.putString(ParamsKey.KEY_INIT_GAME_NAME, "");// gameName 没有填""
		bundle.putString(ParamsKey.KEY_INIT_GAME_TYPE, gameType);// 1网游，0单机
		bundle.putString(ParamsKey.KEY_INIT_PACKAGE_ID, "");// 包Id 没有传""
		bundle.putString(ParamsKey.KEY_INIT_PRIVATE_KEY, "");// 私钥 没有传""
		bundle.putString(ParamsKey.KEY_INIT_PUBLIC_KEY, "");// 公钥 没有传""
		bundle.putString(ParamsKey.KEY_INIT_SCREEN_ORIENTION, "");// 1表示竖屏,0为横屏
		bundle.putString(ParamsKey.KEY_INIT_SERVER_ID, "");// 服务器ID 没有填""
		bundle.putString(ParamsKey.KEY_INIT_SHOW_TOOLBAR, "");// 浮动图标 没有调""
		bundle.putString(ParamsKey.KEY_INIT_WAY, "");// 初始化方式 没有填""。
		bundle.putString(ParamsKey.KEY_INIT_MERCHANT_ID, "");// 厂商ID 没有填""。
		bundle.putString(ParamsKey.KEY_EXTINFO, "");// 额外参数， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO2, "");// 额外参数2， 没有传""，
		bundle.putString(ParamsKey.KEY_EXTINFO3, "");// 额外参数3
		/**
		 * 整合初始化接口
		 */
		sdkKitCore.init(this, bundle, this);
	}

	// 登陆
	public void doLogin(View v) {
		Bundle bundle = new Bundle();
		// 登录方式:0.自动登录（同快速登录）;1.普通登录, 没有空
		bundle.putString(ParamsKey.KEY_LOGIN_WAY, "");
		// 游戏服务器地址(没有传"")
		bundle.putString(ParamsKey.KEY_LOGIN_SERVER_URL, "");
		// 游戏服务器端口号(没有传"")
		bundle.putString(ParamsKey.KEY_LOGIN_SERVER_PORT, "");
		// 服务器id(没有传"")
		bundle.putString(ParamsKey.KEY_LOGIN_SERVER_ID, "");
		// 是否显示区服列表
		bundle.putBoolean(ParamsKey.KEY_LOGIN_SHOW_SERVER, false);
		bundle.putString(ParamsKey.KEY_EXTINFO, "");// 额外参数， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO2, "");// 额外参数2， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO3, "");// 额外参数3
		sdkKitCore.login(bundle, this);
	}
	String oreder;
	public void dogetOrder(View v) {
		Bundle bundle = new Bundle();
		bundle.putString(ParamsKey.KEY_PAY_ORDER_ID, "");
		sdkKitCore.getOrderInfo(bundle, this);
	}

	int amount = 1; // amount 充值金额(必须传int类型)
	public void doPay(View v) {
		Bundle bundle = new Bundle();
		String notifyurl = "http://www.youxigongchang.com"; // 回传地址

		// 支付类型:乐玩多第三方游戏为0，非定额支付，1是定额支付
		bundle.putString(ParamsKey.KEY_PAY_WAY, "1");
		// 所购买商品金额, 以元为单位。， 没有传""
		bundle.putInt(ParamsKey.KEY_PAY_AMOUNT, amount);
		// 人民币与游戏充值币的默认比例， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_RATE, "");
		// 购买商品的商品id
		bundle.putString(ParamsKey.KEY_PAY_PRODUCT_ID, "");
		// 所购买商品名称，应用指定，建议中文，最大10个中文字。
		bundle.putString(ParamsKey.KEY_PAY_PRODUCT_NAME, "");
		// 购买数量 , ， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_PRODUCT_NUM, "");
		// 应用方提供的支付结果通知uri,没有先传任意测试字符串
		bundle.putString(ParamsKey.KEY_PAY_NOTIFY_URI, notifyurl);
		// 游戏或应用名称，， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_APP_NAME, "");
		// 订单号， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ORDER_ID, "");
		// 区服ID ， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_SERVER_ID, "1");
		// 区服名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_SERVER_NAME, "");
		// 游戏虚拟币名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_CONIN_NAME, "");
		// 支付公钥， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_PUBLIC_KEY, "");
		// 支付私钥， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_PRIVATE_KEY, "");
		// 游戏等级， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_GAME_LEVEL, "");
		// 角色ID， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ROLE_ID, "");
		// 角色名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ROLE_NAME, "");
		// 角色等级， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ROLE_LEVEL, "");
		// 用户ID， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_USER_ID, "");
		// 用户名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_USER_NAME, "");
		// 账户余额 ， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_BALANCE, "");
		// 额外参数， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO, "");
		// 额外参数2， 没有传""， 这里传交易标题
		bundle.putString(ParamsKey.KEY_EXTINFO2, "");
		// 额外参数3
		bundle.putString(ParamsKey.KEY_EXTINFO3, "");

		sdkKitCore.pay(bundle, this);
	}

	// 注销账户
	public void doLogout(View v) {
		Bundle bundle = new Bundle();
		bundle.putString(ParamsKey.KEY_EXTINFO, "");// 额外参数， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO2, "");// 额外参数2， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO3, "");// 额外参数3，没有传""
		sdkKitCore.logout(bundle, this);
	}

	// 退出游戏
	public void doExit(View v) {
		sdkKitCore.exitGame(this, this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (sdkKitCore != null) {
			sdkKitCore.onStop();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (sdkKitCore != null) {
			sdkKitCore.onResume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (sdkKitCore != null) {
			sdkKitCore.onPause();
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sdkKitCore != null) {
			sdkKitCore.onDestroy();
		}
	}

	
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (sdkKitCore != null) {
			sdkKitCore.onNewIntent(intent);
		}
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("ysdk", "onActivityResult");
		sdkKitCore.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onResponse(final SDKKitResponse response, int resFlag) {
		switch (resFlag) {
		case Constants.RESPONSE_FLAG_INIT:// 初始化回调
			if (response.getHead().getStatus() == Constants.REQUEST_SUCCESS) {
				// 初始化成功
			}
			break;
		case Constants.RESPONSE_FLAG_LOGIN:// 登录回调
			if (response.getHead().getStatus() == Constants.REQUEST_SUCCESS) {
				// 登录成功
				@SuppressWarnings("unused")
				String userInfo = response.getBody().getLoginUserInfo();
			}
			break;
		case Constants.RESPONSE_FLAG_PAY: // 支付回调
			orderId = response.getBody().getPayKitOrderId(); // 获取平台订单号
			if (response.getHead().getStatus() == Constants.REQUEST_SUCCESS) {
				doTjPay(orderId);
			}
			break;
		case Constants.RESPONSE_FLAG_LOGINOUT:// 登出回调
			break;
		case Constants.RESPONSE_FLAG_USERCENTER:// 用户中心回调
			break;
		case Constants.RESPONSE_FLAG_EXITGAME:// 退出游戏回调
			break;
		case Constants.RESPONSE_FLAG_CREATE_ROLE:// 创建角色回调
			break;
		default:
			break;
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				tv_show.setText(new Gson().toJson(response));
			}
		});
	}

	@Override
	public void onError(final SDKKitResponse response, int resFlag) {
		switch (resFlag) {
		case Constants.RESPONSE_FLAG_INIT:// 初始化错误回调
			break;
		case Constants.RESPONSE_FLAG_LOGIN:// 登录错误回调
			break;
		case Constants.RESPONSE_FLAG_PAY: // 支付错误回调
			if (response.getBody() != null) {
				orderId = response.getBody().getPayKitOrderId();
			}
			break;
		case Constants.RESPONSE_FLAG_LOGINOUT:// 登出错误回调
			break;
		case Constants.RESPONSE_FLAG_USERCENTER:// 用户中心错误回调
			break;
		case Constants.RESPONSE_FLAG_EXITGAME:// 退出游戏错误回调
			break;
		case Constants.RESPONSE_FLAG_CREATE_ROLE:// 创建角色错误回调
			break;
		default:
			break;
		}
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tv_show.setText(new Gson().toJson(response));
			}
		});
	}

	/**
	 * 统计登录
	 * 
	 * @param view
	 */
	public void doTjLogin(View view) {
		Bundle bundle = new Bundle();
		// 用户标识：只用添加用户ID即可
		bundle.putString(ParamsKey.KEY_ROLE_USERMARK, "123@"
				+ SDKKitStatisticSDK.getInstance().getCurrentCP());
		// 用户类型：0为临时账户，1为注册用户，2为第三方用户
		bundle.putString(ParamsKey.KEY_ROLE_USERTYPE, "1");
		// 服务器ID
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_ID, "13");
		SDKKitStatistic.getIntance(this).doTjLogin(bundle);
	}

	/**
	 * 统计服务器，角色信息
	 */
	public void doTjServerRoleInfo(View view) {
		Bundle bundle = new Bundle();
		// 角色id
		bundle.putString(ParamsKey.KEY_ROLE_ID, "1");
		// 角色等级
		bundle.putInt(ParamsKey.KEY_ROLE_LEVEL, 15);
		// 角色昵称
		bundle.putString(ParamsKey.KEY_ROLE_NAME, "角色昵称");
		// 角色所在帮派或工会名称
		bundle.putString(ParamsKey.KEY_ROLE_PARTY_NAME, "角色所在帮派或工会名称");
		// VIP等级
		bundle.putString(ParamsKey.KEY_ROLE_VIP_LEVEL, "VIP等级");

		SDKKitStatistic.getIntance(this).doTjServerRoleInfo(bundle);
		tv_show.setText(bundle.toString());
	}

	/**
	 * 统计支付
	 * 
	 * @param view
	 */
	public void doTjPay(String orderid) {
		Bundle bundle = new Bundle();
		// cp标示
		bundle.putString(ParamsKey.KEY_ROLE_USERMARK, "123@"
				+ SDKKitStatisticSDK.getInstance().getCurrentCP());
		// 支付金额
		bundle.putInt(ParamsKey.KEY_ROLE_AMOUNT, amount);
		// 支付方式，例如支付宝
		bundle.putString(ParamsKey.KEY_PAY_NAME, "支付宝");
		// 服务器id
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_ID, "2");
		// 用户标识
		bundle.putString(ParamsKey.KEY_ROLE_USERID, "123");
		// 角色唯一标识
		bundle.putString(ParamsKey.KEY_ROLE_ID, "1");
		// 订单号
		bundle.putString(ParamsKey.KEY_ROLE_ORDERNUMBER, orderid);
		// 角色等级
		bundle.putInt(ParamsKey.KEY_ROLE_LEVEL, 15);
		// 角色昵称
		bundle.putString(ParamsKey.KEY_ROLE_NAME, "角色升级昵称");
		// 服务器名称
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_NAME, "服务器名称");
		// 支付商品描述,或者商品名称
		bundle.putString(ParamsKey.KEY_ROLE_PRODUCT_DESC, "支付商品描述");

		SDKKitStatistic.getIntance(this).doTjPay(bundle);
	}

	/**
	 * 统计用户升级
	 * 
	 * @param view
	 */
	public void doTjUpgrade(View view) {
		Bundle bundle = new Bundle();
		// cp标示
		bundle.putString(ParamsKey.KEY_ROLE_USERMARK, "123@"
				+ SDKKitStatisticSDK.getInstance().getCurrentCP());
		// 角色id
		bundle.putString(ParamsKey.KEY_ROLE_ID, "1");
		// 角色账号
		bundle.putString(ParamsKey.KEY_ROLE_USERID, "lewan10086");
		// 角色等级
		bundle.putInt(ParamsKey.KEY_ROLE_LEVEL, 15);
		// 服务器ID
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_ID, "2");
		// 角色昵称
		bundle.putString(ParamsKey.KEY_ROLE_NAME, "角色升级昵称");
		// 服务器名称
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_NAME, "服务器名称");
		SDKKitStatistic.getIntance(this).doTjUpgrade(bundle);
	}

	/**
	 * 统计创建角色
	 * 
	 * @param view
	 */
	public void doTjCreateRole(View view) {
		Bundle bundle = new Bundle();
		// cp标示
		bundle.putString(ParamsKey.KEY_ROLE_USERMARK, "123@"
				+ SDKKitStatisticSDK.getInstance().getCurrentCP());
		// 角色id
		bundle.putString(ParamsKey.KEY_ROLE_ID, "1");
		// 角色账号
		bundle.putString(ParamsKey.KEY_ROLE_USERID, "lewan10086");
		// 角色等级
		bundle.putInt(ParamsKey.KEY_ROLE_LEVEL, 15);
		// 角色昵称
		bundle.putString(ParamsKey.KEY_ROLE_NAME, "创建角色昵称");
		// 服务器id
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_ID, "2");
		// 服务器名称
		bundle.putString(ParamsKey.KEY_ROLE_SERVER_NAME, "服务器名称");
		SDKKitStatistic.getIntance(this).doTjCreateRole(bundle);
	}

	/**
	 * 统计按钮点击事件
	 * 
	 * @param view
	 */
	public void doTjBtnClick(View view) {
		Bundle bundle = new Bundle();
		// cp标示
		bundle.putString(ParamsKey.KEY_ROLE_USERMARK, "123@"
				+ SDKKitStatisticSDK.getInstance().getCurrentCP());
		// 游戏名称
		bundle.putString(ParamsKey.KEY_ROLE_GAMENAME, "秦时明月2");
		SDKKitStatistic.getIntance(this).doTjClick(bundle);
	}
}