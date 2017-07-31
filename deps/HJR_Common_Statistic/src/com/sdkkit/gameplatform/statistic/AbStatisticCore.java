package com.sdkkit.gameplatform.statistic;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;

import com.sdkkit.gameplatform.statistic.util.ProtocolKeys;

public abstract class AbStatisticCore {

	//登陆统计,在登录成功之后，选择区服之前调用，使用参数如下：start
		/**
		 * 用户标识：用户ID@平台，*请注意，平台标识不能使用中文，渠道标示
		 */
		String KEY_ROLE_USERMARK = "usermark";
		/**
		 * 用户类型：0为临时账户，1为注册用户，2为第三方用户
		 */
		String KEY_ROLE_USERTYPE = "usertype";
		/**
		 * 用户accountname/或者传userid
		 */
		String KEY_ROLE_USERID = "username";
		
		//如果直接登录的可以上传serverNo  KEY_ROLE_SERVER_ID
		
		/**
		 * 用户昵称
		 */
		String KEY_ROLE_USENICK = "usernick";
		//登陆统计,在登录成功之后，选择区服之前调用，使用参数如下：end
		
		//提交服务器、角色信息,选择区服、选择完角色，点击进入游戏之后调用，使用参数如下：start
		/**
		 * 区服ID
		 */
		String KEY_ROLE_SERVER_ID="serverno";
		/**
		 * 区服名称
		 */
		String KEY_ROLE_SERVER_NAME="role_server_name";
		/**
		 * 角色ID
		 */
		String KEY_ROLE_ID="role_id";
		/**
		 * 角色名称
		 */
		String KEY_ROLE_NAME="role_name";
		/**
		 * 角色等级
		 */
		String KEY_ROLE_LEVEL="role_level";
		/**
		 * 角色升级等级
		 */
		String KEY_ROLE_GRADE="grade";
		/**
		 * 角色所在帮派或工会名称
		 */
		String KEY_ROLE_PARTY_NAME="role_party_name";
		/**
		 * VIP等级
		 */
		String KEY_ROLE_VIP_LEVEL="role_vip_level";
		//提交服务器、角色信息,选择区服、选择完角色，点击进入游戏之后调用，使用参数如下：end

		
		//在支付成功回调后调用，使用参数 start
		/**
		 * 平台统计使用
		 * 支付方式
		 */
		String KEY_PAY_NAME = "payname";
		/**
		 * 平台统计使用
		 * 充值金额
		 */
		String KEY_ROLE_AMOUNT = "amount";
		/**
		 * 平台统计使用
		 * 用户角色标示，即roleid
		 * rolemark
		 * KEY_ROLE_ID
		 */
		/**
		 * 区服ID,在服务器提交那以使用
		 * KEY_ROLE_SERVER_ID
		 */
		/**
		 * 区服ID,在服务器提交那以使用
		 * KEY_ROLE_USERMARK
		 */
		/**
		 * 订单号
		 */
		String KEY_ROLE_ORDERNUMBER = "ordernumber";
		/**
		 * 角色等级
		 * KEY_ROLE_LEVEL
		 */
		/**
		 * 商品描述
		 */
		String KEY_ROLE_PRODUCT_DESC = "productdesc";
		//在支付成功回调后调用，使用参数 end
		

		//创建角色统计， 在创建角色完成后调用 start
		
		/**
		 * cp标示
		 * KEY_ROLE_USERMARK
		 */
		/**
		 * 平台统计使用
		 * 用户角色标示，即roleid
		 * rolemark
		 * KEY_ROLE_ID
		 */
		/**
		 * 角色名称
		 * KEY_ROLE_NAME
		 */
		/**
		 * 服务器编号
		 * KEY_ROLE_SERVER_ID
		 */
		//创建角色统计， 在创建角色完成后调用 end
		
		//角色升级统计，start
		
		/**
		 * cp标示
		 * KEY_ROLE_USERMARK
		 */
		/**
		 * 角色等级
		 * KEY_ROLE_LEVEL
		 */
		
		/**
		 * 服务器编号
		 * KEY_ROLE_SERVER_ID
		 */
		
		/**
		 * 角色ID
		 * KEY_ROLE_ID
		 */
		/**
		 * 角色名称
		 * KEY_ROLE_NAME
		 */
		//角色升级统计，end
		
		//点击统计
		
		/**
		 * 游戏名称
		 */
		String KEY_ROLE_GAMENAME = "gamename";
		
		//一下是用不到的
		/**
		 * 用户余额
		 */
		String KEY_ROLE_BALANCE="role_balance";
	
		protected String getBundleString(Bundle bundle, String key) {
			String res = "";
			if(bundle.get(key)  instanceof String) {
				res = bundle.getString(key);
			};
			if (null == res) {
				res = "";
			}
			return res;
		}
		
		protected int getIntFromBundle(Bundle bundle , String key) {
			try{
				if(bundle.get(key)  instanceof Integer) {
					return bundle.getInt(key);
				};
				if(TextUtils.isEmpty(bundle.getString(key))) {
				return 1;
				}
				return Integer.parseInt(bundle.getString(key));
			} catch(Exception e) {
				e.printStackTrace();
				return 1;
			}
		}
		
	/**
	 * 登陆统计，登陆成功后统计
	 * @param bundle
	 */
	public void doTjLogin(Bundle bundle) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 用户标识：用户ID@平台，*请注意，平台标识不能使用中文
		params.put(ProtocolKeys.KEY_USERMARK, getBundleString(bundle, KEY_ROLE_USERMARK));
		// 用户类型：0为临时账户，1为注册用户，2为第三方用户
		params.put(ProtocolKeys.KEY_USERTYPE, getBundleString(bundle, KEY_ROLE_USERTYPE));
		// 服务器编号
		params.put(ProtocolKeys.KEY_SERVERNO, getBundleString(bundle, KEY_ROLE_SERVER_ID));
		SDKKitStatisticSDK.getInstance().doUserLogin(params);
	}
	
	public void doTjPay(Bundle bundle) {
		Map<String, Object> params = new HashMap<String, Object>();

		// 支付方式 ,"支付宝"
		params.put(ProtocolKeys.KEY_PAYNAME, getBundleString(bundle, KEY_PAY_NAME));

		// 充值金额
		params.put(ProtocolKeys.KEY_AMOUNT, bundle.getInt(KEY_ROLE_AMOUNT, 1));

		// 服务器ID
		params.put(ProtocolKeys.KEY_SERVERNO, getBundleString(bundle, KEY_ROLE_SERVER_ID));

		// 用户标识,cp标示
		params.put(ProtocolKeys.KEY_USERMARK, getBundleString(bundle, KEY_ROLE_USERMARK));

		// 角色唯一标识 ////////
		params.put(ProtocolKeys.KEY_ROLEMARK, getBundleString(bundle, KEY_ROLE_ID));

		// 订单号
		params.put(ProtocolKeys.KEY_ORDERNUMBER, getBundleString(bundle, KEY_ROLE_ORDERNUMBER));

		// 玩家等级
		params.put(ProtocolKeys.KEY_UPGRADE, bundle.getInt(KEY_ROLE_LEVEL, 1));

		// 商品描述
		params.put(ProtocolKeys.KEY_PRODUCT_DESC, getBundleString(bundle, KEY_ROLE_PRODUCT_DESC));

		SDKKitStatisticSDK.getInstance().doPostOrder(params);
	}
	
	public void doTjCreateRole(Bundle bundle) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 用户标识：用户ID@平台，*请注意，平台标识不能使用中文
		params.put(ProtocolKeys.KEY_USERMARK, getBundleString(bundle, KEY_ROLE_USERMARK));
		// 角色标识
		params.put(ProtocolKeys.KEY_ROLEMARK, getBundleString(bundle, KEY_ROLE_ID));
		// 服务器编号
		params.put(ProtocolKeys.KEY_SERVERNO, getBundleString(bundle, KEY_ROLE_SERVER_ID));
		SDKKitStatisticSDK.getInstance().doCreateRole(params);
	}

	public void doTjServerRoleInfo(Bundle bundle) {

	}
	
	//角色升级统计
	public void doTjUpgrade(Bundle bundle) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 用户标识
		params.put(ProtocolKeys.KEY_USERMARK, getBundleString(bundle, KEY_ROLE_USERMARK));
		// 服务器编号
		params.put(ProtocolKeys.KEY_SERVERNO, getBundleString(bundle, KEY_ROLE_SERVER_ID));
		
		// 玩家等级
		String roleLevel = "1";
		if (bundle.containsKey(KEY_ROLE_LEVEL)) {
			roleLevel = bundle.getString(KEY_ROLE_LEVEL);
		}else {
			roleLevel= bundle.getString(KEY_ROLE_GRADE);
		}
		params.put(ProtocolKeys.KEY_UPGRADE, roleLevel);

		SDKKitStatisticSDK.getInstance().doUserUpgrade(params);
	}
	
	public void doTjClick(Bundle bundle) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProtocolKeys.KEY_USERMARK, getBundleString(bundle, KEY_ROLE_USERMARK));
		params.put(ProtocolKeys.KEY_NAME, getBundleString(bundle, "name"));
		SDKKitStatisticSDK.getInstance().doGameClick(params);
	}
}
