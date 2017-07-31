package com.gameworks.sdk.standard.core;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.gameworks.sdk.standard.ISDKKitCallBack;
import com.gameworks.sdk.standard.ISDKKitCore;
import com.gameworks.sdk.standard.ParamsKey;
import com.gameworks.sdk.standard.beans.ResponseBody;
import com.gameworks.sdk.standard.beans.ResponseHead;
import com.gameworks.sdk.standard.beans.SDKKitResponse;
import com.gameworks.sdk.standard.utils.Constants;
import com.gameworks.sdk.standard.utils.DialogUtil;
import com.google.gson.Gson;
import com.sdkkit.gameplatform.statistic.SDKKitStatisticSDK;
import com.sdkkit.gameplatform.statistic.util.C;
import com.sdkkit.gameplatform.statistic.util.InitListener;
import com.sdkkit.gameplatform.statistic.util.ProtocolKeys;
import com.tencent.tmgp.kfbb.snake.R;
import com.tencent.ysdk.api.YSDKApi;
import com.tencent.ysdk.framework.common.eFlag;
import com.tencent.ysdk.framework.common.ePlatform;
import com.tencent.ysdk.module.pay.PayListener;
import com.tencent.ysdk.module.pay.PayRet;
import com.tencent.ysdk.module.user.UserListener;
import com.tencent.ysdk.module.user.UserLoginRet;
import com.tencent.ysdk.module.user.UserRelationRet;
import com.tencent.ysdk.module.user.WakeupRet;

public class SDKKitCore implements ISDKKitCore {

	private SDKKitStatisticSDK instance;
	private static SDKKitCore sdkInstance;
	private String TAG = "SDKKitCore";
	private Activity activity;
	private SDKKitResponse response;
	private ResponseHead head;
	private ResponseBody body;

	ISDKKitCallBack glCallBack = null;
	ISDKKitCallBack payCallBack = null;
	ISDKKitCallBack orderInfoCallBack = null;

	Bundle loginBundle = null;
	Bundle payBundle = null;

	String newOrderId;
	String ysdkOpenId;

	private boolean bCallbackfromPay = false;
	private boolean bCallbackfromNoInvokeLogin = true;
	private boolean bOnPause = false;
	private boolean bOnStop = false;

	// 逗比ysdk，经常乱给登录回调出来，只能处理成，成功给出登录回调之后，除非注销了，就不再给回调出去了
	private boolean bLoginCallbacked = false;
	private String typeString = "qq";
	private UserLoginRet userLoginRet;
	
	private static String serverId = "2";
	
	private ePlatform platform;
	// 手Q登陆时传手Q登陆回调里获取的paytoken值，微信登陆时传微信登陆回调里获取的传access_token值。
	private String openKey = "";
	Bundle metaDataBundle;

	/**
	 * 
	 * Double check to get singleton
	 * 
	 */
	public static SDKKitCore getInstance() {

		synchronized (SDKKitCore.class) {
			if (null == sdkInstance) {
				sdkInstance = new SDKKitCore();
			}
		}

		return sdkInstance;

	}

	private void initResponse() {
		response = new SDKKitResponse();
		head = new ResponseHead();
		body = new ResponseBody();
	}

	@Override
	public void init(final Activity mContext, Bundle bundle,
			final ISDKKitCallBack callBack) {

		try {
			Log.i(TAG, "init");
			initResponse();
			glCallBack = callBack;
			activity = mContext;
			YSDKApi.onCreate(activity);

			YSDKApi.setUserListener(new UserListener() {

				@Override
				public void OnWakeupNotify(WakeupRet arg0) {

				}

				@Override
				public void OnRelationNotify(UserRelationRet arg0) {

				}

				@Override
				public void OnLoginNotify(UserLoginRet ret) {
					Log.i(TAG, "OnLoginNotify#" + ret.toString());
					if (bCallbackfromPay) {
						// 由于ysdk在支付完成之后也会回调一次登录成功，所以在这里把这次无效的登录回调过滤掉
						Log.i(TAG, "bCallbackfromPay = true; return ");
						bCallbackfromPay = false;
						return;

					}
					if (bCallbackfromNoInvokeLogin) {
						// 由于ysdk初始化完成之后会自动给出一个登录成功的回调，这时候游戏并没有调用登录接口，这边把无效回调过滤掉
						Log.i(TAG, "bCallbackfromNoInvokeLogin = true; return ");
						return;

					}
					// Log.i(TAG,"bOnPause=" +bOnPause+",bOnStop=" + bOnStop);
					// if (bOnPause) {
					// //ysdk在从后台切入到前台时会给出登录成功的回调，这里过滤掉无效回调
					// Log.i(TAG,"bOnPause && bOnStop = true; return ");
					// bOnStop = false;
					// bOnPause = false;
					// return ;
					// }
					if (bLoginCallbacked) {
						Log.i(TAG, "bLoginCallbacked=true ;return");
						return;
					}
					if (ret.flag == eFlag.Succ) {
						userLoginRet = ret;
						ysdkOpenId = ret.open_id;
						JSONObject userObj = new JSONObject();
						try {
							if ("qq".equals(typeString)) {
								Log.i("PENGTAO", "qq#" + ret.getPayToken());
								openKey = ret.getPayToken();
							} else {
								Log.i("PENGTAO",
										"weixin#" + ret.getAccessToken());
								openKey = ret.getAccessToken();
							}
							userObj.put("openid", ysdkOpenId);
							userObj.put("access_token", ret.getAccessToken());
							userObj.put("type", typeString);
							Log.i(TAG, userObj.toString());
							SDKKitStatisticSDK.getInstance().doVerifyUserInfo(
									userObj.toString(),
									new EventHandlerCallBack(sHandler));
						} catch (JSONException e) {
							head.setStatus(Constants.REQUEST_FAIL);
							head.setErrorMsg("登录回调数据解析失败#" + e.getMessage());
							head.setRequestCode(Constants.RESPONSE_FLAG_LOGIN);
							response.setHead(head);
							callBack.onError(response,
									Constants.RESPONSE_FLAG_LOGIN);
						}
					} else if (ret.flag == eFlag.Login_TokenInvalid) {
						Log.i(TAG,
								"OnLoginNotify->Login_TokenInvalid#Begin logout");
						YSDKApi.logout();
						initResponse();
						head.setErrorMsg("User_LocalTokenInvalid->注销成功");
						head.setStatus(Constants.REQUEST_SUCCESS);
						head.setRequestCode(Constants.RESPONSE_FLAG_LOGINOUT);
						response.setHead(head);
						callBack.onResponse(response,
								Constants.RESPONSE_FLAG_LOGINOUT);
						islogin = false;
						// Log.i(TAG,"OnLoginNotify->User_LocalTokenInvalid#Begin relogin"
						// );
						// ysdkLogin();
					} else if (ret.flag == eFlag.WX_NotInstall) {
						Toast.makeText(mContext, "手机未安装微信，请安装后重试",
								Toast.LENGTH_SHORT).show();

					} else if (ret.flag == eFlag.WX_NotSupportApi) {
						Toast.makeText(mContext, "手机微信版本太低，请升级后重试",
								Toast.LENGTH_SHORT).show();
					} else if (ret.flag == eFlag.QQ_NotInstall) {
						Toast.makeText(mContext, "手机未安装手Q，请安装后重试",
								Toast.LENGTH_SHORT).show();
					} else if (ret.flag == eFlag.QQ_NotSupportApi) {
						Toast.makeText(mContext, "手机未安装手Q，请安装后重试",
								Toast.LENGTH_SHORT).show();
					} else {
						head.setStatus(Constants.REQUEST_FAIL);
						head.setErrorMsg("登录失败#flag:" + ret.flag);
						head.setRequestCode(Constants.RESPONSE_FLAG_LOGIN);
						response.setHead(head);
						callBack.onError(response,
								Constants.RESPONSE_FLAG_LOGIN);
					}

				}
			});
			YSDKApi.handleIntent(activity.getIntent());

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(ProtocolKeys.KEY_GAMETYPE,
					bundle.getString(ParamsKey.KEY_INIT_GAME_TYPE));
			params.put(ProtocolKeys.KEY_CP, "ysdk");
			params.put(ProtocolKeys.KEY_SDKVERSION, "1.3.2");
			instance = SDKKitStatisticSDK.getInstance();
			instance.init(activity, params, new InitListener() {

				@Override
				public void onSuccess() {

					instance.doTmpUserSession();
					head.setStatus(Constants.REQUEST_SUCCESS);
					head.setErrorMsg("初始化成功");
					head.setRequestCode(Constants.RESPONSE_FLAG_INIT);
					response.setHead(head);
					callBack.onResponse(response, Constants.RESPONSE_FLAG_INIT);

					// YSDKApi.setBuglyListener(new YSDKCallback());

				}

				@Override
				public void onFailed() {
					head.setStatus(Constants.REQUEST_FAIL);
					head.setErrorMsg("初始化失败");
					head.setRequestCode(Constants.RESPONSE_FLAG_INIT);
					response.setHead(head);
					callBack.onResponse(response, Constants.RESPONSE_FLAG_INIT);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			head.setStatus(Constants.REQUEST_FAIL);
			head.setErrorMsg("初始化失败#" + e.getMessage());
			head.setRequestCode(Constants.RESPONSE_FLAG_INIT);
			response.setHead(head);
			callBack.onError(response, Constants.RESPONSE_FLAG_INIT);
		}

	}

	private static long lastClickTime;

	private  boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 5000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	// 获取当前登录平台
	public ePlatform getPlatform() {
		Log.e("PENGTAO", "getPlatform");
		UserLoginRet ret = new UserLoginRet();
		int k = YSDKApi.getLoginRecord(ret);
		Log.e("PENGTAO", "k= " + k);
		Log.e("PENGTAO", "ret.flag= " + ret.flag);
		if (ret.flag == eFlag.Succ) {
			Log.e("PENGTAO", "userLoginRet= " + ret);
			userLoginRet = ret;
			ysdkOpenId = ret.open_id;
			Log.e("PENGTAO", "ysdkOpenId= " + ret.open_id);

			/*
			 * 07-05 17:23:11.157: E/PENGTAO(5619): userLoginRet= UserLoginRet :
			 * 07-05 17:23:11.157: E/PENGTAO(5619): ret : 0 07-05 17:23:11.157:
			 * E/PENGTAO(5619): flag : 0 07-05 17:23:11.157: E/PENGTAO(5619):
			 * msg : wx login succ！ 07-05 17:23:11.157: E/PENGTAO(5619):
			 * platform : 2 07-05 17:23:11.157: E/PENGTAO(5619): open_id :
			 * oFImO1PJKrjGLwpaoCUh--vBSApc 07-05 17:23:11.157: E/PENGTAO(5619):
			 * user_type :0 07-05 17:23:11.157: E/PENGTAO(5619): login_type :0
			 * 07-05 17:23:11.157: E/PENGTAO(5619): pf
			 * :desktop_m_wx-00000000-android-00000000-864895027029352 07-05
			 * 17:23:11.157: E/PENGTAO(5619): pf_key
			 * :9d2982068f9bb93de498352e925b6275 07-05 17:23:11.157:
			 * E/PENGTAO(5619): Token_WX_Access : ESxSJ0_SMfCDoT-6
			 * wwBV9_FJ8VzZ7QzdMtGOxe1nxICj44PfW5FJsqzjr8uJ6U_5dB8cJVwzI42EWY7141bHdNsyxQL5dOjvkQNlmFSTR6Q
			 * expiration : 1499253790 07-05 17:23:11.157: E/PENGTAO(5619):
			 * Token_WX_Refresh :
			 * X6LKFkJPg_nDlSpAy1iDl8mZ7aq4ZAqk6jfK7HerJEtc5I6CVukw5Gdp_LzrN9fvcCnReVokHr3tf5q4PlitSkOmoqjP2rrI0ZoZas6sBVs
			 * expiration : 1501838590 07-05 17:23:11.157: E/PENGTAO(5619):
			 * ysdkOpenId= oFImO1PJKrjGLwpaoCUh--vBSApc 07-05 17:23:11.161:
			 * E/PENGTAO(5619): typeString= qq 07-05 17:23:11.161:
			 * I/PENGTAO(5619): weixin#ESxSJ0_SMfCDoT-6
			 * wwBV9_FJ8VzZ7QzdMtGOxe1nxICj44PfW5FJsqzjr8uJ6U_5dB8cJVwzI42EWY7141bHdNsyxQL5dOjvkQNlmFSTR6Q
			 * 07-05 17:23:11.169: E/PENGTAO(5619): ePlatform= 2
			 */

			Log.e("PENGTAO", "typeString= " + typeString);
			JSONObject userObj = new JSONObject();
			try {
				if (1 == ret.platform) {
					typeString = "qq";
					Log.i("PENGTAO", "qq#" + ret.getPayToken());
					openKey = ret.getPayToken();
				} else if (2 == ret.platform) {
					typeString = "weixin";
					Log.i("PENGTAO", "weixin#" + ret.getAccessToken());
					openKey = ret.getAccessToken();
				}
				userObj.put("openid", ysdkOpenId);
				userObj.put("access_token", ret.getAccessToken());
				userObj.put("type", typeString);
				Log.i(TAG, userObj.toString());
				SDKKitStatisticSDK.getInstance().doVerifyUserInfo(
						userObj.toString(), new EventHandlerCallBack(sHandler));
			} catch (JSONException e) {
				head.setStatus(Constants.REQUEST_FAIL);
				head.setErrorMsg("登录回调数据解析失败#" + e.getMessage());
				head.setRequestCode(Constants.RESPONSE_FLAG_LOGIN);
				response.setHead(head);
				glCallBack.onError(response, Constants.RESPONSE_FLAG_LOGIN);
			}

			Log.e("PENGTAO", "ePlatform= " + ret.platform);
			return ePlatform.getEnum(ret.platform);
		}
		return ePlatform.None;
	}

	@Override
	public void login(Bundle bundle, final ISDKKitCallBack callBack) {
		if (isFastClick()) {
			return;
		}
		Log.i("PENGTAO", "login");
		bCallbackfromNoInvokeLogin = false;
		initResponse();
		glCallBack = callBack;
		platform = getPlatform();
		if (ePlatform.None == platform) {
			// /
			Log.e("PENGTAO", "ePlatform.None= " + platform);
			ysdkLogin();

		}
	}

	private void ysdkLogin() {
		final String[] sPayWays = { "QQ登录", "微信登录" };
		final com.tencent.ysdk.framework.common.ePlatform[] sPayWaysIndex = {
				ePlatform.QQ, ePlatform.WX };
		Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("请选择登录方式");
		builder.setCancelable(false);
		builder.setItems(sPayWays, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				typeString = (which == 0) ? "qq" : "weixin";
				YSDKApi.login(sPayWaysIndex[which]);

			}
		});
		builder.show();
		
	}

	@Override
	public void pay(Bundle bundle, ISDKKitCallBack callBack) {

		Log.i("PENGTAO", "pay");
		payCallBack = callBack;
		payBundle = bundle;
		initResponse();
		try {

			if (bundle.getInt(ParamsKey.KEY_PAY_AMOUNT) <= 0) {
				head.setErrorMsg("金额不能为负数");
				head.setStatus(Constants.REQUEST_FAIL);
				head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
				response.setHead(head);
				callBack.onError(response, Constants.RESPONSE_FLAG_PAY);
			}

			serverId = bundle.getString(ParamsKey.KEY_PAY_SERVER_ID);
			SDKKitStatisticSDK.getInstance().doPostRechargeOrder(
					bundle.getString(ParamsKey.KEY_PAY_ORDER_ID),
					bundle.getInt(ParamsKey.KEY_PAY_AMOUNT),
					bundle.getString(ParamsKey.KEY_PAY_SERVER_ID),
					bundle.getString(ParamsKey.KEY_PAY_PRODUCT_ID),
					bundle.getString(ParamsKey.KEY_PAY_PRODUCT_NUM),
					bundle.getString(ParamsKey.KEY_EXTINFO),
					bundle.getString(ParamsKey.KEY_PAY_NOTIFY_URI),
					new EventHandlerCallBack(sHandler));
		} catch (Exception e) {
			head.setErrorMsg(e.getMessage());
			head.setStatus(Constants.REQUEST_FAIL);
			head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
			response.setHead(head);
			callBack.onError(response, Constants.RESPONSE_FLAG_PAY);
		}

	}

	@Override
	public void userCenter(Bundle bundle, ISDKKitCallBack callBack) {
	}

	@Override
	public void logout(Bundle bundle, final ISDKKitCallBack callBack) {
		if (islogin) {
			Log.i("PENGTAO", "logout");
			initResponse();
			YSDKApi.logout();
			bLoginCallbacked = false;
			head.setErrorMsg("注销成功");
			head.setStatus(Constants.REQUEST_SUCCESS);
			head.setRequestCode(Constants.RESPONSE_FLAG_LOGINOUT);
			response.setHead(head);
			callBack.onResponse(response, Constants.RESPONSE_FLAG_LOGINOUT);
			islogin = false;
		}

	}

	@Override
	public void exitGame(Activity activity, final ISDKKitCallBack callBack) {
		Log.i("PENGTAO", "exitGame");
		DialogUtil.showExitDialog(activity, callBack);

	}

	@Override
	public void getOrderInfo(Bundle bundle, ISDKKitCallBack callBack) {
		orderInfoCallBack = callBack;
		initResponse();
		SDKKitStatisticSDK.getInstance().doRechargeOrderStatus(newOrderId,
				new EventHandlerCallBack(sHandler));
	}

	@Override
	@Deprecated
	public void setContext(Context mContext) {
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void switchAccount(Bundle bundle, ISDKKitCallBack callBack) {
	}

	public void onActivityResult(Integer requestCode, Integer resultCode,
			Intent data) {
		YSDKApi.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		bOnPause = true;
		if (activity != null) {
			YSDKApi.onPause(activity);
		}

	}

	@Override
	public void onResume() {

		if (!C.isActive) {
			SDKKitStatisticSDK.getInstance().saveFrontTime();
			C.isActive = true;
		}

		if (null != activity) {
			// YSDKApi.onRestart(activity);
			YSDKApi.onResume(activity);
		}

	}

	@Override
	public void onStop() {

		bOnStop = true;

		if (!SDKKitStatisticSDK.getInstance().isAppOnForeground()) {
			SDKKitStatisticSDK.getInstance().saveBackTime();
			C.isActive = false;
		}

		if (null != activity) {
			YSDKApi.onStop(activity);
		}
	}

	@Override
	public void onDestroy() {
		if (null != activity) {
			bCallbackfromNoInvokeLogin = true;
			YSDKApi.onDestroy(activity);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	}

	@Override
	public void onSaveInstanceState(Bundle bundle, ISDKKitCallBack callBack) {
	}

	@Override
	public void onNewIntent(Intent intent) {
		YSDKApi.handleIntent(intent);
	}

	@Override
	public void appOnCreate(Context ctx, Bundle bundle) {
	}

	@Override
	public void appAttachBaseContext(Context ctx, Bundle bundle) {
	}

	@Override
	public void appOnTerminate(Context ctx, Bundle bundle) {
	}

	/**
	 * Handler http response message
	 */
	private Handler sHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 1: // 生成订单号
				sHandlerNewOrderNoCallBack(msg.arg1, msg.obj.toString());
				break;
			//
			case 2: // 获取订单结果
				sHandlerOrderInfoCallBack(msg.arg1, msg.obj.toString());

				break;
			//
			case 3:// 登录返回
				sHandlerglCallBack(msg.arg1, msg.obj.toString());
				break;
			//
			case 4:// 用户验证返回
				sHandlerVerifyUserCallBack(msg.arg1, msg.obj.toString());

				break;
			case 5:// ysdk订单号生成回调

				sHandlerYSDKPay(msg.arg1, msg.obj.toString());
				break;

			default:
				break;
			}

		}

	};

	private boolean islogin = false;

	private void sHandlerYSDKPay(int arg1, String retStr) {
		// TODO Auto-generated method stub
		Log.i(TAG, "YSDK OrderCallBack#" + retStr);

		try {
			JSONObject retObj = new JSONObject(retStr);
			if (arg1 != 0) {
				String errMsg = retObj.getJSONObject("info").getString(
						"errorinfo");
				head.setStatus(Constants.REQUEST_FAIL);
				head.setErrorMsg("支付失败#" + errMsg);
				response.setHead(head);
				glCallBack.onError(response, Constants.RESPONSE_FLAG_PAY);

			} else {
				head.setStatus(Constants.REQUEST_SUCCESS);
				head.setErrorMsg("支付成功");
				head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
				response.setHead(head);
				response.setBody(body);
				glCallBack.onResponse(response, Constants.RESPONSE_FLAG_PAY);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sHandlerOrderInfoCallBack(int status, String message) {

		try {
			final JSONObject sJson = new JSONObject(message);
			if (status != 0) {
				String errMsg = sJson.getJSONObject("info").getString(
						"errorinfo");
				head.setStatus(Constants.REQUEST_FAIL);
				head.setErrorMsg(errMsg);
				response.setHead(head);
				orderInfoCallBack.onError(response,
						Constants.RESPONSE_FLAG_ORDERRESULT);

				return;
			}
			String mStatus = sJson.getJSONObject("data").getString("status");
			String desc = sJson.getJSONObject("data").getString("desc");
			if (mStatus.equals("0")) {

				head.setStatus(Constants.REQUEST_SUCCESS);
				head.setErrorMsg(desc);
				head.setCode(mStatus);
				response.setHead(head);
				orderInfoCallBack.onResponse(response,
						Constants.RESPONSE_FLAG_ORDERRESULT);

			} else {
				head.setStatus(Constants.REQUEST_FAIL);
				head.setErrorMsg(desc);
				head.setCode(mStatus);
				response.setHead(head);
				orderInfoCallBack.onResponse(response,
						Constants.RESPONSE_FLAG_ORDERRESULT);

			}
		} catch (Exception e) {
			// 异常处理
			head.setErrorMsg(e.getMessage());
			head.setStatus(Constants.REQUEST_FAIL);
			response.setHead(head);
			orderInfoCallBack.onError(response,
					Constants.RESPONSE_FLAG_ORDERRESULT);
		}

	}

	private void sHandlerVerifyUserCallBack(int status, String message) {
		Log.i("YSDK", "sHandlerVerifyUserCallBack");
		try {
			JSONObject sJson = new JSONObject(message);
			if (status != 0) {
				String errMsg = sJson.getJSONObject("info").getString(
						"errorinfo");
				head.setStatus(Constants.REQUEST_FAIL);
				head.setErrorMsg(errMsg);
				response.setHead(head);
				glCallBack.onError(response, Constants.RESPONSE_FLAG_LOGIN);

				return;
			}
			SDKKitStatisticSDK.getInstance().doOAuthToken(ysdkOpenId,
					ysdkOpenId, new EventHandlerCallBack(sHandler));
		} catch (Exception e) {
			head.setErrorMsg(e.getMessage());
			head.setStatus(-1);
			response.setHead(head);
			glCallBack.onError(response, 1);
		}

	}

	private void sHandlerglCallBack(int status, String message) {
		Log.i("YSDK", "sHandlerglCallBack");
		if (status == 0) {
			try {
				JSONObject json = new JSONObject(message);
				JSONObject dataObject;
				dataObject = json.getJSONObject("data");
				String openid = dataObject.getString("openid");
				String token = dataObject.getString("token");
				head.setStatus(Constants.REQUEST_SUCCESS);
				head.setErrorMsg("登录并确认成功");
				head.setRequestCode(Constants.RESPONSE_FLAG_LOGIN);

				body.setLoginAuthToken(token);
				body.setOpenId(openid);
				body.setLoginUserId(ysdkOpenId);
				body.setLoginUserName(ysdkOpenId);
				islogin = true;
				response.setHead(head);
				response.setBody(body);
				glCallBack.onResponse(response, Constants.RESPONSE_FLAG_LOGIN);

				bLoginCallbacked = true;

			} catch (JSONException e) {
				e.printStackTrace();
				head.setStatus(Constants.REQUEST_FAIL);
				head.setErrorMsg("登录验证异常#" + e.getMessage());
				response.setHead(head);
				response.setBody(body);
				glCallBack.onError(response, Constants.RESPONSE_FLAG_LOGIN);
			}
		} else {
			head.setStatus(Constants.REQUEST_FAIL);
			head.setErrorMsg("登陆验证失败");
			response.setHead(head);
			response.setBody(body);
			glCallBack.onResponse(response, Constants.RESPONSE_FLAG_LOGIN);
		}
	}

	private void sHandlerNewOrderNoCallBack(int status, String message) {

		try {
			JSONObject sJson = new JSONObject(message);
			if (status != 0) {
				String errMsg = sJson.getJSONObject("info").getString(
						"errorinfo");
				head.setErrorMsg(errMsg);
				head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
				response.setHead(head);
				payCallBack.onError(response, Constants.RESPONSE_FLAG_PAY);
				return;
			}

			bCallbackfromPay = true;

			// newOrderId =
			// sJson.getJSONObject("data").getString("order_number");

			newOrderId = sJson.getJSONObject("data").getString("order_number");
			body.setPayKitOrderId(newOrderId);

			Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(),
					R.drawable.ic_launcher);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] appResData = baos.toByteArray();
			Log.e("PENGTAO",
					"serviceid= "
							+ payBundle.getString(ParamsKey.KEY_PAY_SERVER_ID));
		

			YSDKApi.recharge(serverId, String.valueOf(payBundle
					.getInt(ParamsKey.KEY_PAY_AMOUNT) * 10), false, appResData,
					newOrderId, new PayListener() {

						@Override
						public void OnPayNotify(PayRet ret) {
							try {

								Log.i(TAG, "OnPayNotify#" + ret.toString());
								if (PayRet.RET_SUCC == ret.ret) {
									if (ret.payState == PayRet.PAYSTATE_PAYSUCC) {
										SDKKitStatisticSDK
												.getInstance()
												.doPostTencentYSDKOrder(
														typeString,
														newOrderId,
														ysdkOpenId,
														openKey,
														openKey,
														userLoginRet.pf,
														userLoginRet.pf_key,
														serverId,
														String.valueOf(payBundle
																.getInt(ParamsKey.KEY_PAY_AMOUNT) * 10),
														new EventHandlerCallBack(
																sHandler));
									} else {
										String strMsg = "";
										switch (ret.payState) {
										case PayRet.PAYSTATE_PAYCANCEL:
											strMsg = "取消支付";
											break;
										case PayRet.PAYSTATE_PAYUNKOWN:
											strMsg = "用户支付结果未知，建议查询余额";
											break;
										case PayRet.PAYSTATE_PAYERROR:
											strMsg = "支付异常";
											break;
										default:
											strMsg = "未知错误#" + ret.payState;
											break;
										}

										head.setStatus(Constants.REQUEST_FAIL);
										head.setErrorMsg(strMsg);
										head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
										response.setHead(head);
										response.setBody(body);
										glCallBack.onResponse(response,
												Constants.RESPONSE_FLAG_PAY);
									}
								} else {

									if (ret.flag == eFlag.Login_TokenInvalid) {
										Toast.makeText(activity, "登陆态过期，请重新登陆",
												Toast.LENGTH_SHORT).show();
										YSDKApi.logout();
										return;
									}
									switch (ret.flag) {
									case eFlag.Pay_User_Cancle:
										Log.i(TAG,
												"OnPayNotify#Pay_User_Cancle");
										// 用户取消支付
										head.setErrorMsg("用户取消支付："
												+ ret.toString());
										break;
									case eFlag.Pay_Param_Error:
										head.setErrorMsg("支付失败，参数错误"
												+ ret.toString());
										break;
									case eFlag.Error:
										head.setErrorMsg("未知异常#"
												+ ret.toString());
										break;
									default:
										head.setErrorMsg("支付异常"
												+ ret.toString());
										break;
									}
									Log.i(TAG, "OnPayNotify#begin callback");
									head.setStatus(Constants.REQUEST_FAIL);
									head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
									response.setHead(head);
									response.setBody(body);
									Log.i(TAG,
											"OnPayNotify#"
													+ new Gson()
															.toJson(response));
									glCallBack.onResponse(response,
											Constants.RESPONSE_FLAG_PAY);
									Log.i(TAG, "OnPayNotify#end callback");
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

		} catch (JSONException e) {
			e.printStackTrace();
			head.setErrorMsg(e.getMessage());
			head.setRequestCode(Constants.RESPONSE_FLAG_PAY);
			response.setHead(head);
			payCallBack.onError(response, Constants.RESPONSE_FLAG_PAY);
		}

	}

	public Handler getHandler() {

		return sHandler;
	}

	public ISDKKitCallBack getglCallBack() {
		return glCallBack;
	}
}
