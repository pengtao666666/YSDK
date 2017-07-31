package com.gameworks.sdk.standard;



/**
 * <li>文件名称: ParamsKey.java</li>
 * <li>文件描述: </li>
 * <li>公    司: 游戏工场</li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: 2014-9-3 下午7:34:30</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0.0
 * @author  作者姓名: HooRang
 */
public interface ParamsKey {
	
	
	//============================================查询订单接口==============================================
				
	/*bundle.putString(ParamsKey.KEY_PAY_ORDER_ID, orderId);*/
	/**
	 * application 额外信息
	 */
	String KEY_APP_EXTINFO = "application_extinfo";
	/**
	 * application 额外信息
	 */
	String KEY_APP_EXTINFO2 = "application_extinfo2";
	/**
	 * application 额外信息
	 */
	String KEY_APP_EXTINFO3 = "application_extinfo3";

	/**
	//============================================所有其他模块均通用,没有参数的必须放置三个key（上面的查询订单接口只用上面一个）==============================================
	/**
	 * 额外信息
	 */
	String KEY_EXTINFO = "extInfo";
	
	/**
	 * 额外信息2
	 */
	String KEY_EXTINFO2 = "extInfo2";
	
	/**
	 * 额外信息3*
	 */
	String KEY_EXTINFO3="extInfo3";
/*	
	bundle.putString(ParamsKey.KEY_EXTINFO, "");//额外参数， 没有传""
	bundle.putString(ParamsKey.KEY_EXTINFO2, "");//额外参数2， 没有传""
	bundle.putString(ParamsKey.KEY_EXTINFO3, "");//额外参数3，没有传""
*/	
	//============================================初始化==============================================
	/**
	 * 应用ID
	 */
	String KEY_INIT_APP_ID = "appId";
	/**
	 * 应用KEY
	 */
	String KEY_INIT_APP_KEY = "appKey";
	/**
	 * 平台
	 */
	String KEY_INIT_PLATFORMS = "cp";
	/**
	 * 游戏类型： 0 ，单机  ； 1， 网游
	 */
	String KEY_INIT_GAME_TYPE = "gameType" ; 
	
	/**
	 * gameId
	 */
	String KEY_INIT_GAME_ID = "gameId";
	/**
	 * 渠道ID
	 */
	String KEY_INIT_CHANNEL_ID = "channelId";
	/**
	 * 浮标秘钥
	 */
	String KEY_INIT_PRIVATE_KEY = "privateKeyTool";
	/**
	 * 渠道公钥
	 */
	String KEY_INIT_PUBLIC_KEY = "publicKeyChannel";
	/**
	 * 屏幕方向
	 */
	String KEY_INIT_SCREEN_ORIENTION = "screen_oriention";
	/**
	 * 运行模式：正式和测试
	 */
	String KEY_INIT_DEBUG_MODE ="debugMode";
	/**
	 * 服务器ID
	 */
	String KEY_INIT_SERVER_ID = "serverId";
	/**
	 * appSecret
	 */
	String KEY_INIT_APP_SECRET = "appSecret";
	/**
	 * 浮动图标
	 */
	String KEY_INIT_SHOW_TOOLBAR = "toolbar";
	/**
	 * gameName
	 */
	String KEY_INIT_GAME_NAME = "gameName";
	/**
	 * 初始化登录类型
	 */
	String KEY_INIT_WAY = "initWay";
	/**
	 * 初始化金额(传数组)
	 */
	String KEY_INIT_AMOUNT = "initAmount";
	/**
	 * packageId
	 */
	String KEY_INIT_PACKAGE_ID = "packageId";
	/**
	 * 厂商ID*
	 */
	String KEY_INIT_MERCHANT_ID = "merchantId";
	

/*	
 *      bundle.putStringArray(ParamsKey.KEY_INIT_AMOUNT,null);//金额 没有传null
		bundle.putString(ParamsKey.KEY_INIT_APP_ID, "");//appId
		bundle.putString(ParamsKey.KEY_INIT_APP_KEY, "");//appKey
		bundle.putString(ParamsKey.KEY_INIT_APP_SECRET, "");//appSecret 没有填""
		bundle.putString(ParamsKey.KEY_INIT_CHANNEL_ID, "");//渠道ID 没有填""
		bundle.putString(ParamsKey.KEY_INIT_DEBUG_MODE, "");//1 为正式模式 ,0为测试模式，没有填""
		bundle.putString(ParamsKey.KEY_INIT_GAME_ID, "");//gameId 没有填""
		bundle.putString(ParamsKey.KEY_INIT_GAME_NAME, "");//gameName 没有填""
		bundle.putString(ParamsKey.KEY_INIT_GAME_TYPE, "1");//1网游，0单机
		bundle.putString(ParamsKey.KEY_INIT_PACKAGE_ID, "");//包Id 没有传""
		bundle.putString(ParamsKey.KEY_INIT_PRIVATE_KEY, "");//私钥 没有传""
		bundle.putString(ParamsKey.KEY_INIT_PUBLIC_KEY, "");//公钥 没有传""
		bundle.putString(ParamsKey.KEY_INIT_SCREEN_ORIENTION, "");//1表示竖屏,0为横屏
		bundle.putString(ParamsKey.KEY_INIT_SERVER_ID, "");//服务器ID 没有填""
		bundle.putString(ParamsKey.KEY_INIT_SHOW_TOOLBAR, "");//浮动图标 没有调""
		bundle.putString(ParamsKey.KEY_INIT_WAY, "");//初始化方式 没有填""。
		bundle.putString(ParamsKey.KEY_INIT_MERCHANT_ID, "");//厂商ID 没有填""。
		bundle.putString(ParamsKey.KEY_EXTINFO, "");//额外参数， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO2, "");//额外参数2， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO3, "");//额外参数3
	*/
	
	//============================================登录模块==============================================
	
	/**
	 * 登录方式
	 * 0.自动登录（同快速登录）
	 * 1.普通登录
	 */
	String KEY_LOGIN_WAY="login_type";
	
	/**
	 * 游戏服务器地址
	 */
	String KEY_LOGIN_SERVER_URL="login_server_url";
	/**
	 * 游戏服务器端口号
	 */
	String KEY_LOGIN_SERVER_PORT="login_server_port";
	
	/**
	 * 服务器id
	 */
	String KEY_LOGIN_SERVER_ID="login_server_id";
	
	/**
	 * 是否显示区服列表*
	 */
	String KEY_LOGIN_SHOW_SERVER="login_show_server";
	
   /*
 	
	bundle.putString(ParamsKey.KEY_LOGIN_WAY, "");// 登录方式:0.自动登录（同快速登录）;1.普通登录;没有传"";
	bundle.putString(ParamsKey.KEY_LOGIN_SERVER_URL,"");// 游戏服务器地址，没有传""
	bundle.putString(ParamsKey.KEY_LOGIN_SERVER_PORT,"");//游戏服务器端口号，没有传""
	bundle.putString(ParamsKey.KEY_LOGIN_SERVER_ID,"");//服务器id，没有传""
	bundle.putBoolean(ParamsKey.KEY_LOGIN_SHOW_SERVER,false);//是否显示区服列表
	bundle.putString(ParamsKey.KEY_EXTINFO, "");//额外参数， 没有传空
	bundle.putString(ParamsKey.KEY_EXTINFO2, "");//额外参数2， 没有传空
	bundle.putString(ParamsKey.KEY_EXTINFO3, "");//额外参数3， 没有传空3
	
	
	*/
	
	
	
	//============================================支付模块==============================================
	/**
	 * 支付方式： 1： 定额支付    2. 非定额支付  .3.单机支付.4.网游支付 
	 */
	String KEY_PAY_WAY = "payWay";
	/**
	 * 充值金额
	 */
	String KEY_PAY_AMOUNT = "amount";  
	
	/**
	 * 人民币与游戏充值币的默认比例
	 */
	String KEY_PAY_RATE = "rate";  
	
	/**
	 * 购买商品名称
	 */
	String KEY_PAY_PRODUCT_NAME = "productName";   
	
	/**
	 * 购买商品的商品id
	 */
	String KEY_PAY_PRODUCT_ID = "productid"; 
	
	/**
	 * 购买数量	
	 */
	String KEY_PAY_PRODUCT_NUM = "product_num"; 
	
	/**
	 * 应用方提供的支付结果通知uri
	 */
	String KEY_PAY_NOTIFY_URI = "notifyUri"; 
	
	/**
	 * 游戏或应用名称
	 */
	String KEY_PAY_APP_NAME = "appName";  
	
	/**
	 * 应用内的用户名
	 */
	String KEY_PAY_USER_NAME = "appUserName"; 
	
	/**
	 * 应用内的用户id
	 */
	String KEY_PAY_USER_ID = "appUserId"; 
	
	/**
	 * 订单号
	 */
	String KEY_PAY_ORDER_ID = "appOrderId"; 
	
	/**
	 * 角色ID
	 */
	String KEY_PAY_ROLE_ID  = "roleId";  
	/**
	 * 角色名
	 */
	String KEY_PAY_ROLE_NAME = "roleName";  
	/**
	 * 角色等级
	 */
	String KEY_PAY_ROLE_LEVEL = "roleLevel";  
	/**
	 * 游戏等级
	 */
	String KEY_PAY_GAME_LEVEL = "grade";  
	
	/**
	 * 支付私钥
	 */
	String KEY_PAY_PRIVATE_KEY = "privatekey";
	
	/**
	 * 支付公钥
	 */
	String KEY_PAY_PUBLIC_KEY = "publickey";

	/**
	 * 服务器ID
	 */
	String KEY_PAY_SERVER_ID="serviceid";
	
	/**
	 * 服务器名
	 */
	String KEY_PAY_SERVER_NAME="servicename";
	
	/**
	 * 游戏币名称
	 */
	String KEY_PAY_CONIN_NAME = "coinname";
	/**
	 * 用户余额*
	 */
	String KEY_PAY_BALANCE = "balance";

	/*
  		
		bundle.putString(ParamsKey.KEY_PAY_WAY, "");// 1： 定额支付     否则 ： 非定额支付   
		bundle.putInt(ParamsKey.KEY_PAY_AMOUNT, 1);// 所购买商品金额, 以元为单位。金额大于0
		bundle.putString(ParamsKey.KEY_PAY_RATE, "");// 人民币与游戏充值币的默认比例，例如2，代表1元人民币可以兑换2个游戏币，整数。
		bundle.putString(ParamsKey.KEY_PAY_PRODUCT_ID, "");// 购买商品的商品id
		bundle.putString(ParamsKey.KEY_PAY_PRODUCT_NAME, "");// 所购买商品名称，应用指定，建议中文，最大10个中文字。
		bundle.putString(ParamsKey.KEY_PAY_PRODUCT_NUM ,"");//购买数量 , 当商品ID 不为空时，必传
		bundle.putString(ParamsKey.KEY_PAY_NOTIFY_URI, "");// 应用方提供的支付结果通知uri，最大255字符。
		bundle.putString(ParamsKey.KEY_PAY_APP_NAME, "");//游戏或应用名称，最大16中文字。
		bundle.putString(ParamsKey.KEY_PAY_ORDER_ID ,"");//订单号， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_SERVER_ID, "");// 区服ID ， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_SERVER_NAME, "");//区服名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_CONIN_NAME, "");//游戏虚拟币名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_PUBLIC_KEY, "");//支付公钥， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_PRIVATE_KEY, "");//支付私钥， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_GAME_LEVEL, "");//游戏等级， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ROLE_ID, "");//角色ID， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ROLE_NAME, "");//角色名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_ROLE_LEVEL, "");//角色等级， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_USER_ID, "");//用户ID， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_USER_NAME, "");//用户名， 没有传""
		bundle.putString(ParamsKey.KEY_PAY_BALANCE,"");//用户余额,没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO, "");//额外参数， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO2, "");//额外参数2， 没有传""
		bundle.putString(ParamsKey.KEY_EXTINFO3, "");//额外参数3， 没有传""

		
			
			*/

	//============================================有关角色的模块通用这几个,作为第三方统计使用==============================================
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
	
	//============================================是否显示悬浮窗口==============================================


	/**
	 * 是否显示悬浮窗
	 */
	String KEY_FLOATWINDOW_ISSHOW="floatwindow_is_show";
	
	/*bundle.putBoolean(ParamsKey.KEY_FLOATWINDOW_ISSHOW, "");//必传，是否显示悬浮窗
*/

}