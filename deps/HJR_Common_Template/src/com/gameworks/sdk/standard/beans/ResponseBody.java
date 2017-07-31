package com.gameworks.sdk.standard.beans;

public class ResponseBody {
	private String extInfo;//透传参数 ,所有模块都可以用此参数，把所有的返回信息，以json字符串保存到这个里面，让对接方自己去解析
	
	/**
	 * *********************登录模块(注册和注销模块***************************
	 */
	
	
	private String loginSign; //登录签名，账号验证符,用户的验证key,迅雷游戏账号验证key
	
	private String loginSignTime;//账号验证时间
	
	private String loginSid;
	
	private String loginWay;//用户登录方式
	
	private String loginRandom;//登录的随机码，参与验签的一部分
	
	private String loginChannelId;//渠道id;
	
	private String loginTime;//获取用户登录的时间戳
	
	private String loginRegistTime;//注册时间
	
	private String loginExpiredTime;//用户登录的过期时间
	
	private String loginAuthToken;//用户授权Token
	
	private String openId ;//整合平台唯一标识
	private String loginUserId;// 用户id(手盟用户唯一标识,飞流账号唯一标识)
	
	private String loginUserName;//用户名
	
	private String loginUserPwd;//用户密码
	
	private String loginPlayerId; //玩家id,金立用户的唯一标识,迅雷游戏账号
	
	private String loginUserMobile;//用户手机号
	
	private String loginUserNick;//用户昵称
	
	private String loginUserToken;//登录token，木蚂蚁用来服务器验证登录，注册是不是成功
	
	private String loginUserSession;//木蚂蚁用seesion来解签
	
	private String loginUserOpenId;//vivo的用户唯一标识
	
	private String loginUserJumpKey;//此字段迅雷未做说明
	
	private String loginUserInfo;//用户账号信息字符串
	
	private String loginExternInfo;//登录额外信息
	 
	private String loginServerId;//登录的区服id
	
	private String loginServerName;//登录的区服名称
	
	private String loginServerUrl;//登录的区服地址
	

	

	/**
	 * ********************* 支付模块***************************
	 */
	private String payKitOrderId;//整合sdk服务器的订单号
	
	private String payOrderId;//订单编码
	
	private String payTradeId;//交易码
	
	private String payGameId;
	
	private String payCurrencyId;//用户实际支付的货币种类代码
	
	private String payMerchantName;//开发者请求商户名称
	
	private String payAppKey;//金立商户的唯一标识
	
	private String payCreateTime;//订单创建时间
	
	private String paySubmitTime;//订单提交时间
	
	private String payTradeTime;//支付时间,交易时间戳
	
	private String paySuccessTime;//订单成功时间
	
	private String payLeftDay;//商品的有效期(仅租赁类型商品有效)
	
	private String payWay;//支付方式
	
	private String payWayId;//支付方式编码
	
	private String payNotifyUrl;//订单回调地址,回调地址
	
	private String payOrderAmount;//支付的订单金额
	
	private String payOrderName;//充值的订单名称，没有的用支付的用户名
	
	private String payOrderType;//订单类型
	
	private String payOrderDesc;//订单描述
	
	private String payProductDesc;//商品描述
	
	private String payProductName;//商品名称
	
	private String payProductPrice;//商品价格
	
	private String payProductCount;//商品数量
	
	private String payProductId;//商品ID
	
	private String payRate;// 兑换比例,汇率
	
	private String payExtern;//支付的扩展参数
	
	
	private String payUserId;//支付用户id
	
	private String payUserNick;//支付用户昵称
	
	private String payUserName;//支付用户名
	
	private String payUserToken;//支付的用户Token
	
	private String payServerId;//服务器id
	
	private String payPageUrl;//支付页面地址
	
	
	
	/**
	 * ********************* 用户信息模块***************************
	 */
	
	

	
	
	private String userId;//用户Id
	
	private String userNick;//用户昵称
	
	private String userName;//用户名
	
	private String userToken;//用户Token
	
	
	private String userSign;//用户验证串
	
	private String userType;//用户账号类型
	
	private String userExpiredTime;//登录过期时间
	
	private String userSignTime;//用户验证时间
	
	private String userLoginTime;//用户登录时间

	
	private String userUniqueId;//第三方 账号标识符
	
	private String userSex;//用户性别
	
	
	private String userPicUrl;//用户头像地址
	
	private String userLevel;//用户等级
	
	private String userCreateTime;//用户创建时间
	private String userMaxScore;//用户分数
	
	private String userOpenId; //切克闹为对此参数作说明
	
	private String userPlayFlag;//0，未玩过游戏；1，玩过游戏
	
	private String userEmail;//邮箱地址
	
	private String userArea;//用户地址
	private String userBalance;//用户余额
	
	private String userMobile;//手机号码
	
	private boolean userEmailStatus;//是否绑定邮箱
	
	private boolean userMobileStatus;//是否绑定手机
	
	private String userLoginStatus;//访问状态（游客，非游客），飞流用户登录状态；0 为未登录；1 为登录；2 为游客状态；

	public String getLoginSign() {
		return loginSign;
	}

	public void setLoginSign(String loginSign) {
		this.loginSign = loginSign;
	}

	public String getLoginSignTime() {
		return loginSignTime;
	}

	public void setLoginSignTime(String loginSignTime) {
		this.loginSignTime = loginSignTime;
	}

	public String getLoginSid() {
		return loginSid;
	}

	public void setLoginSid(String loginSid) {
		this.loginSid = loginSid;
	}

	public String getLoginChannelId() {
		return loginChannelId;
	}

	public void setLoginChannelId(String loginChannelId) {
		this.loginChannelId = loginChannelId;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginRegistTime() {
		return loginRegistTime;
	}

	public void setLoginRegistTime(String loginRegistTime) {
		this.loginRegistTime = loginRegistTime;
	}

	public String getLoginExpiredTime() {
		return loginExpiredTime;
	}

	public void setLoginExpiredTime(String loginExpiredTime) {
		this.loginExpiredTime = loginExpiredTime;
	}

	public String getLoginAuthToken() {
		return loginAuthToken;
	}

	public void setLoginAuthToken(String loginAuthToken) {
		this.loginAuthToken = loginAuthToken;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginPlayerId() {
		return loginPlayerId;
	}

	public void setLoginPlayerId(String loginPlayerId) {
		this.loginPlayerId = loginPlayerId;
	}

	public String getLoginUserMobile() {
		return loginUserMobile;
	}

	public void setLoginUserMobile(String loginUserMobile) {
		this.loginUserMobile = loginUserMobile;
	}

	public String getLoginUserNick() {
		return loginUserNick;
	}

	public void setLoginUserNick(String loginUserNick) {
		this.loginUserNick = loginUserNick;
	}

	public String getLoginUserToken() {
		return loginUserToken;
	}

	public void setLoginUserToken(String loginUserToken) {
		this.loginUserToken = loginUserToken;
	}

	public String getLoginUserSession() {
		return loginUserSession;
	}

	public void setLoginUserSession(String loginUserSession) {
		this.loginUserSession = loginUserSession;
	}

	public String getLoginUserOpenId() {
		return loginUserOpenId;
	}

	public void setLoginUserOpenId(String loginUserOpenId) {
		this.loginUserOpenId = loginUserOpenId;
	}

	public String getLoginUserJumpKey() {
		return loginUserJumpKey;
	}

	public void setLoginUserJumpKey(String loginUserJumpKey) {
		this.loginUserJumpKey = loginUserJumpKey;
	}


	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getPayTradeId() {
		return payTradeId;
	}

	public void setPayTradeId(String payTradeId) {
		this.payTradeId = payTradeId;
	}

	public String getPayGameId() {
		return payGameId;
	}

	public void setPayGameId(String payGameId) {
		this.payGameId = payGameId;
	}

	public String getPayCurrencyId() {
		return payCurrencyId;
	}

	public void setPayCurrencyId(String payCurrencyId) {
		this.payCurrencyId = payCurrencyId;
	}

	public String getPayMerchantName() {
		return payMerchantName;
	}

	public void setPayMerchantName(String payMerchantName) {
		this.payMerchantName = payMerchantName;
	}

	public String getPayAppKey() {
		return payAppKey;
	}

	public void setPayAppKey(String payAppKey) {
		this.payAppKey = payAppKey;
	}

	public String getPayCreateTime() {
		return payCreateTime;
	}

	public void setPayCreateTime(String payCreateTime) {
		this.payCreateTime = payCreateTime;
	}

	public String getPayTradeTime() {
		return payTradeTime;
	}

	public void setPayTradeTime(String payTradeTime) {
		this.payTradeTime = payTradeTime;
	}

	public String getPaySuccessTime() {
		return paySuccessTime;
	}

	public void setPaySuccessTime(String paySuccessTime) {
		this.paySuccessTime = paySuccessTime;
	}

	public String getPayLeftDay() {
		return payLeftDay;
	}

	public void setPayLeftDay(String payLeftDay) {
		this.payLeftDay = payLeftDay;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getPayOrderAmount() {
		return payOrderAmount;
	}

	public void setPayOrderAmount(String payOrderAmount) {
		this.payOrderAmount = payOrderAmount;
	}

	public String getPayOrderName() {
		return payOrderName;
	}

	public void setPayOrderName(String payOrderName) {
		this.payOrderName = payOrderName;
	}

	public String getPayOrderType() {
		return payOrderType;
	}

	public void setPayOrderType(String payOrderType) {
		this.payOrderType = payOrderType;
	}

	public String getPayOrderDesc() {
		return payOrderDesc;
	}

	public void setPayOrderDesc(String payOrderDesc) {
		this.payOrderDesc = payOrderDesc;
	}

	public String getPayProductDesc() {
		return payProductDesc;
	}

	public void setPayProductDesc(String payProductDesc) {
		this.payProductDesc = payProductDesc;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	public String getPayProductPrice() {
		return payProductPrice;
	}

	public void setPayProductPrice(String payProductPrice) {
		this.payProductPrice = payProductPrice;
	}

	public String getPayProductCount() {
		return payProductCount;
	}

	public void setPayProductCount(String payProductCount) {
		this.payProductCount = payProductCount;
	}

	public String getPayProductId() {
		return payProductId;
	}

	public void setPayProductId(String payProductId) {
		this.payProductId = payProductId;
	}

	public String getPayRate() {
		return payRate;
	}

	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}

	public String getPayExtern() {
		return payExtern;
	}

	public void setPayExtern(String payExtern) {
		this.payExtern = payExtern;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	public String getPayUserNick() {
		return payUserNick;
	}

	public void setPayUserNick(String payUserNick) {
		this.payUserNick = payUserNick;
	}

	public String getPayUserToken() {
		return payUserToken;
	}

	public void setPayUserToken(String payUserToken) {
		this.payUserToken = payUserToken;
	}

	public String getPayServerId() {
		return payServerId;
	}

	public void setPayServerId(String payServerId) {
		this.payServerId = payServerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getUserExpiredTime() {
		return userExpiredTime;
	}

	public void setUserExpiredTime(String userExpiredTime) {
		this.userExpiredTime = userExpiredTime;
	}

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserSignTime() {
		return userSignTime;
	}

	public void setUserSignTime(String userSignTime) {
		this.userSignTime = userSignTime;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getUserMaxScore() {
		return userMaxScore;
	}

	public void setUserMaxScore(String userMaxScore) {
		this.userMaxScore = userMaxScore;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public String getUserPlayFlag() {
		return userPlayFlag;
	}

	public void setUserPlayFlag(String userPlayFlag) {
		this.userPlayFlag = userPlayFlag;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean isUserEmailStatus() {
		return userEmailStatus;
	}

	public void setUserEmailStatus(boolean userEmailStatus) {
		this.userEmailStatus = userEmailStatus;
	}

	public String getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(String userBalance) {
		this.userBalance = userBalance;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public boolean isUserMobileStatus() {
		return userMobileStatus;
	}

	public void setUserMobileStatus(boolean userMobileStatus) {
		this.userMobileStatus = userMobileStatus;
	}

	public String getUserLoginStatus() {
		return userLoginStatus;
	}

	public void setUserLoginStatus(String userLoginStatus) {
		this.userLoginStatus = userLoginStatus;
	}

	public String getPaySubmitTime() {
		return paySubmitTime;
	}

	public void setPaySubmitTime(String paySubmitTime) {
		this.paySubmitTime = paySubmitTime;
	}

	public String getUserLoginTime() {
		return userLoginTime;
	}

	public void setUserLoginTime(String userLoginTime) {
		this.userLoginTime = userLoginTime;
	}



	public String getPayUserName() {
		return payUserName;
	}

	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}

	public String getPayPageUrl() {
		return payPageUrl;
	}

	public void setPayPageUrl(String payPageUrl) {
		this.payPageUrl = payPageUrl;
	}

	public String getLoginRandom() {
		return loginRandom;
	}

	public void setLoginRandom(String loginRandom) {
		this.loginRandom = loginRandom;
	}

	public String getLoginUserInfo() {
		return loginUserInfo;
	}

	public void setLoginUserInfo(String loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
	}

	public String getLoginUserPwd() {
		return loginUserPwd;
	}

	public void setLoginUserPwd(String loginUserPwd) {
		this.loginUserPwd = loginUserPwd;
	}

	public String getLoginExternInfo() {
		return loginExternInfo;
	}

	public void setLoginExternInfo(String loginExternInfo) {
		this.loginExternInfo = loginExternInfo;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getLoginServerId() {
		return loginServerId;
	}

	public void setLoginServerId(String loginServerId) {
		this.loginServerId = loginServerId;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserCreateTime() {
		return userCreateTime;
	}

	public void setUserCreateTime(String userCreateTime) {
		this.userCreateTime = userCreateTime;
	}

	public String getPayWayId() {
		return payWayId;
	}

	public void setPayWayId(String payWayId) {
		this.payWayId = payWayId;
	}

	public String getLoginServerUrl() {
		return loginServerUrl;
	}

	public void setLoginServerUrl(String loginServerUrl) {
		this.loginServerUrl = loginServerUrl;
	}

	public String getLoginServerName() {
		return loginServerName;
	}

	public void setLoginServerName(String loginServerName) {
		this.loginServerName = loginServerName;
	}

	public String getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(String loginWay) {
		this.loginWay = loginWay;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getPayKitOrderId() {
		return payKitOrderId;
	}

	public void setPayKitOrderId(String payKitOrderId) {
		this.payKitOrderId = payKitOrderId;
	}


	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
	
	
	
	
	

	
	
}

