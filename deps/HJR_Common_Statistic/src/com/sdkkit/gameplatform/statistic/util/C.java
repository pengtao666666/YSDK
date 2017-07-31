package com.sdkkit.gameplatform.statistic.util;


/*******************************************************************************
 * 常量类
 * 
 * @author <xingyong>xingyong@cy2009.com
 * @version 2012-5-16 上午10:40:53
 ******************************************************************************/
public class C {

	/** 客户端版本 */
	public static final String VERSION = "2.0";
	public static final String STATISTIC_VERSION = "1.3.2";
	/** 商务渠道 */
	public static String CHANNEL = "channel";
	public static String GAMEKEY = "gamekey";
	/** 平台渠道的唯一标识 */
	public static String sOnline = "mobile.self";

	/** 支持的协议类型 */
	public static final String HTTP = "http://";
	public static final String SOCKET = "socket://";
	public static final String RESOURCE = "res://";

	/** 网络请求方式 */
	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";

	/** 上传文件用常量 */
	public static final String BOUNDARY = "*****";
	public static final String PREFIX = "--";
	public static final String LINEND = "\r\n";
	public static final String MULTIPART_FROM_DATA = "multipart/form-data";
	public static final String CHARSET = "UTF-8";

	/** 操作返回结果 */
	public static final int OK = 0;
	public static final int INVALID = -3;
	public static final int FAIL = -1;
	public static final int CANCEL = -2;

	public static final String CONFIG_FILENAME = "config";
	public static final String ONLINE_TIME = "time";
	public static final String USER_MARK = "userMark";
	public static final String ROLE_MARK = "roleMark";
	public static final String SERVERNO = "serverNo";
	/** sdkkit_session */
	public static final String CONFIG_SESSION = "sdkkit_session";

	/** AES算法密钥 */
	public static final String AES_KEY = "c6*#e2&(g*UjX!h*";
	public static int DEVICEX = 0;
	public static int DEVICEY = 0;
	public static boolean isActive = false;

	/** 服务器返回状态 */
	public static final int RESULT_0 = 0;
	public static final int RESULT_1 = 1;
	public static final int RESULT_2 = 2;
	public static final int RESULT_4 = 4;
	public static final int RESULT_10 = 10;
	public static final int RESULT_11 = 11;
	public static final int RESULT_12 = 12;
	public static final int RESULT_17 = 17;
	public static final int RESULT_20 = 20;
	public static final int RESULT_22 = 22;
	public static final int RESULT_23 = 23;
	public static final int RESULT_24 = 24;
	public static final int RESULT_25 = 25;
	public static final int RESULT_28 = 28;
	public static final int RESULT_29 = 29;
	public static final int RESULT_30 = 30;
	public static final int RESULT_31 = 31;
	public static final int RESULT_55 = 55;

	
	
	
	/** 
	 * 平台服务器地址 
	 * */
	public static String DOMAIN 		  = "http://test.d.kuaifazs.com";
	
	/**
	 *统计平台服务器地址 
	 */
	public static String DOMAIN_STATISTIC = "http://d.kuaifazs.com";
	/**
	 *  整合sdk服务器正式地址
	 */
	public static String DOMAIN_SDKKIT	  = "http://z.kuaifazs.com";
	
	

	public static String OAUTH_TOKEN_URL 		= DOMAIN_SDKKIT + "/oauth/request";
	public static String ORDER_RESULT_URL 		= DOMAIN_SDKKIT + "/order/result/";
	public static String GET_TMP_USER_URL 		= DOMAIN_SDKKIT + "/user/tmpuser/";
	public static String SEND_XMW_ORDER_URL 	= DOMAIN_SDKKIT + "/order/youxiqunorder/";
	public static String SEND_VIVO_ORDER_URL 	= DOMAIN_SDKKIT + "/order/vivoorder/";
	public static String SEND_HIPPO_ORDER_URL 	= DOMAIN_SDKKIT + "/order/hippoorder/";
	public static String VERIFY_USERIFNO_URL 	= DOMAIN_SDKKIT + "/user/verify/";
	public static String SEND_AMIGO_ORDER_URL 	= DOMAIN_SDKKIT + "/order/amigoorder/";
	public static String SEND_MEIZU_ORDER_URL	= DOMAIN_SDKKIT + "/order/meizuorder/";
	public static String SEND_YIXIN_ORDER_URL 	= DOMAIN_SDKKIT + "/order/yixinorder/";
	public static String SEND_ANYSDK_ORDER_URL 	= DOMAIN_SDKKIT + "/order/submit/";
	public static String SEND_MAOPAO_ORDER_URL 	= DOMAIN_SDKKIT + "/order/maopaoorder/";
	public static String SEND_WANGYI_ORDER_URL 	= DOMAIN_SDKKIT + "/order/ntesorder/";
	public static String SEND_WYKFZS_ORDER_URL 	= DOMAIN_SDKKIT + "/order/wykfzsorder/";
	public static String SEND_UNIPAY_ORDER_URL 	= DOMAIN_SDKKIT + "/order/unipayorder/";
    public static String SEND_HULI_ORDER_URL 	= DOMAIN_SDKKIT + "/order/huliorder/";
    public static String SEND_YYCQ_ORDER_URL 	= DOMAIN_SDKKIT + "/order/iccorder/";
	public static String SELECT_PAY_ORDER_URL 	= DOMAIN_SDKKIT + "/order/selectpay/";
	
	public static String GET_360_ACCESSTOKEN 		 = DOMAIN_SDKKIT+ "/oauth2/accesstoken";
	public static String SEND_STARMOON_ORDER_URL 	 = DOMAIN_SDKKIT+ "/order/starmoon/";
	public static String SEND_VIVO_ORDER_URL_NEW 	 = DOMAIN_SDKKIT+ "/order/vivoordernew/";
	public static String GET_360_USER_BY_ACCESSTOKEN = DOMAIN_SDKKIT+ "/oauth2/userinfo";
	public static String SEND_TENCENTMSDKTASK_ORDER_URL = DOMAIN_SDKKIT+ "/order/tencentmsdk/";
	public static String SEND_TENCENTYSDKTASK_ORDER_URL = DOMAIN_SDKKIT+ "/order/ysdk/";
	public static String SEND_THE9_ORDER_URL = DOMAIN_SDKKIT
			+ "/order/the9order/";
	public static String SEND_QIANBAO_LOGIN 	= DOMAIN_SDKKIT + "/user/qianbao/";
	public static String SEND_QIANBAO_PAY 	= DOMAIN_SDKKIT + "/order/qianbaoorder/";
	public static String SEND_JJSDK_ORDER_URL 	= DOMAIN_SDKKIT + "/order/jjorder/";				//jj比赛订单接口

	/**
	 * 统计sdk请求地址定义
	 */

	public static String ABNORMAL_URL = DOMAIN + "/sys/abnormal/";
	/** 网络 */
	public static String SYS_NET_URL = DOMAIN + "/sys/net/";
	/** 用户登录协议 */
	public static String USER_LOGIN_URL = DOMAIN + "/user/login/";
	/** 创建角色 */
	public static String CREATE_ROLE_URL = DOMAIN + "/user/createrole/";
	/** 获取游戏成就列表 */
	public static String SEND_ORDER_URL = DOMAIN + "/order/submit";
	/** 在线时长 */
	public static String GAME_DEACTIVATE_URL = DOMAIN + "/sys/gamedeactivate/";
	/** 启动游戏 */
	public static String GAME_START_URL = DOMAIN + "/sys/gamestart/";
	/** 游戏关卡 */
	public static String LEVEL_PASS_URL = DOMAIN + "/level/pass/";
	/** 道具 */
	public static String ITEM_GET_URL	 	= DOMAIN + "/item/get/";
	public static String ITEM_ITEM_CONSUME 	= DOMAIN + "/item/consume/";
	public static String ITEM_ITEM_BUY 		= DOMAIN + "/item/buy/";

	/** 玩家升级 */
	public static String USER_UPGRADE 		= DOMAIN + "/user/upgrade/";
	/** 游戏点击 */
	public static String GAME_BTNCLICK_URL 	= DOMAIN + "/sys/gamebtnclick/";

	/**
	 * 游戏类型 0 ： 单机， 1 ，网游
	 */
	public static String GAME_TYPE = "1";
	
	
	
	/**
	 * 初始化所有访问的url
	 * 
	 * @param debug
	 */
	public static void initConfig(boolean debug) {

		if (!debug) {
			DOMAIN = DOMAIN_STATISTIC;
		}
		
		reassigin();
	}
	
	public static void initConfig(String sConfigOfficalUrl , String sConfigKuaifaUrl , String sConfigDataUrl) {
		
		if (!"".equals(sConfigOfficalUrl)) {
			
		}
		
		if (!"".equals(sConfigKuaifaUrl)) {
			DOMAIN_SDKKIT   = sConfigKuaifaUrl;
		}
		
		if (!"".equals(sConfigDataUrl)) {
			DOMAIN	 = sConfigDataUrl;
		}
		
		reassigin();
	}
	
	
	private static void reassigin(){
		/**
		 * 统计sdk重赋新地址
		 */
		ABNORMAL_URL 	= DOMAIN + "/sys/abnormal/";
		/** 网络 */
		SYS_NET_URL 	= DOMAIN + "/sys/net/";
		/** 用户登录协议 */
		USER_LOGIN_URL 	= DOMAIN + "/user/login/";
		/** 创建角色 */
		CREATE_ROLE_URL = DOMAIN + "/user/createrole/";
		/** 获取游戏成就列表 */
		SEND_ORDER_URL 	= DOMAIN + "/order/submit";
		/** 在线时长 */
		GAME_DEACTIVATE_URL = DOMAIN + "/sys/gamedeactivate/";
		/** 启动游戏 */
		GAME_START_URL 	= DOMAIN + "/sys/gamestart/";
		/** 游戏关卡 */
		LEVEL_PASS_URL 	= DOMAIN + "/level/pass/";

		/** 道具 */
		ITEM_GET_URL 		= DOMAIN + "/item/get/";
		ITEM_ITEM_CONSUME 	= DOMAIN + "/item/consume/";
		ITEM_ITEM_BUY	 	= DOMAIN + "/item/buy/";
		/** 玩家升级 */
		USER_UPGRADE 		= DOMAIN + "/user/upgrade/";

		/** 游戏点击 */
		GAME_BTNCLICK_URL 	= DOMAIN + "/sys/gamebtnclick/";

		/**
		 * 整合sdk重赋新地址
		 */
		ORDER_RESULT_URL 			= DOMAIN_SDKKIT + "/order/result/";
		SEND_ANYSDK_ORDER_URL 		= DOMAIN_SDKKIT + "/order/submit";
		SEND_VIVO_ORDER_URL_NEW 	= DOMAIN_SDKKIT + "/order/vivoordernew";
		SEND_AMIGO_ORDER_URL 		= DOMAIN_SDKKIT + "/order/amigoorder/";
		SEND_MEIZU_ORDER_URL 		= DOMAIN_SDKKIT + "/order/meizuorder/";
		SEND_XMW_ORDER_URL 			= DOMAIN_SDKKIT + "/order/youxiqunorder/";
		SEND_MAOPAO_ORDER_URL 		= DOMAIN_SDKKIT + "/order/maopaoorder/";
		SEND_WANGYI_ORDER_URL 		= DOMAIN_SDKKIT + "/order/ntesorder/";
		SEND_WYKFZS_ORDER_URL 		= DOMAIN_SDKKIT + "/order/wykfzsorder/";
		SEND_HIPPO_ORDER_URL 		= DOMAIN_SDKKIT + "/order/hippoorder/";
		VERIFY_USERIFNO_URL 		= DOMAIN_SDKKIT + "/user/verify/";
		SEND_YIXIN_ORDER_URL 		= DOMAIN_SDKKIT + "/order/yixinorder/";
		GET_TMP_USER_URL 			= DOMAIN_SDKKIT + "/user/tmpuser/";
		OAUTH_TOKEN_URL 			= DOMAIN_SDKKIT + "/oauth/request";
		GET_360_ACCESSTOKEN 		= DOMAIN_SDKKIT + "/oauth2/accesstoken";
		GET_360_USER_BY_ACCESSTOKEN = DOMAIN_SDKKIT + "/oauth2/userinfo";
		SEND_TENCENTYSDKTASK_ORDER_URL = DOMAIN_SDKKIT+ "/order/ysdk/";
	}

	

}
