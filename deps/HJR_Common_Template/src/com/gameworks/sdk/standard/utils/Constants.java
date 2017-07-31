package com.gameworks.sdk.standard.utils;

import android.R.integer;


/**
 * <li>文件名称: Constant.java</li>
 * <li>文件描述: 常量类</li>
 * <li>公    司: GameWorks</li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: 2014-7-7 上午11:30:59</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0X.0X
 * @author  作者姓名: HooRang
 */
public interface Constants {

	/** 支持的协议类型 */
	public static final String HTTP = "http://";
	public static final String SOCKET = "socket://";
	public static final String RESOURCE = "res://";
	/** 网络请求方式 */
	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";
	
	/**
	 * 成功
	 */
	int REQUEST_SUCCESS = 1 ; 

	/**
	 * 失败
	 */
	int REQUEST_FAIL   = -1 ; 

	/**
	 * 取消
	 */
	int REQUEST_CANCEL = 0 ;
	/**
	 * 进行中
	 */
	int REQUEST_EXECUTED = 2;
	/**
	 * 未登录
	 */
	int REQUEST_UNLOGIN = 3;
	/**
	 * 初始化
	 */
	int RESPONSE_FLAG_INIT = 0X0 ;
	
	/**
	 * 登录
	 */
	int RESPONSE_FLAG_LOGIN = 0X1 ;
	/**
	 * 快速登录
	 */
	int RESPONSE_FLAG_QUICK_LOGIN = 0X2;

	/**
	 * 注销
	 */
	int RESPONSE_FLAG_LOGINOUT = 0X3 ;
	

	/**
	 * 注册
	 */	
	int RESPONSE_FLAG_REGISTER = 0X4 ;
	

	/**
	 * 切换账户
	 */
	int RESPONSE_FLAG_SWITCHACCOUNT = 0X5 ;
	

	/**
	 * 支付
	 */
	int RESPONSE_FLAG_PAY = 0X6 ;

	

	/**
	 * 用户中心
	 */
	int RESPONSE_FLAG_USERCENTER = 0X7 ;
	

	/**
	 * 退出游戏
	 */
	int RESPONSE_FLAG_EXITGAME = 0X8 ;
	
	

	/**
	 * 用户信息
	 */
	int RESPONSE_FLAG_USERINFO = 0X9 ;
	
	/**
	 * 支付结果
	 */
	int RESPONSE_FLAG_ORDERRESULT = 0X10;
	/**
	 *卓动专用， 获取扩展文件资源
	 */
	int RESPONSE_FLAG_GET_RESOURCES = 0X11;
	/**
	 * 更多游戏
	 */
	int RESPONSE_FLAG_MOREGAME = 0X12;
	/**
	 * 修改密码
	 */
	int RESPONSE_FLAG_SET_PASSWORD = 0X13;
	/**
	 * 获取token
	 */
	int RESPONSE_FLAG_GET_TOKEN = 0X14;
	
	/**
	 * 更新完善帐号信息
	 */
	int RESPONSE_FLAG_MODIFY_ACCOUNT = 0X15; 
	
	/**
	 * 补单
	 */
	int RESPONSE_FLAG_REPAIR_ORDER = 0X16; 
	
	/**
	 * 返回键（安智渠道）
	 */
	int RESPONSE_FLAG_BACK_KEY = 0X17;
	/**
	 * 游戏排行榜
	 */
	int RESPONSE_FLAG_LEADERBOARD=0X18;
	/**
	 * 邀请好友
	 */
	int RESPONSE_FLAG_INVITE_FRIEND=0X19;
	/**
	 * 上传角色信息
	 */
	int RESPONSE_FLAG_SUBMIT_ROLE=0X20;
	/**
	 * 创建角色
	 */
	int RESPONSE_FLAG_CREATE_ROLE=0X21;
	/**
	 * 游戏区服
	 */
	int RESPONSE_FLAG_GAME_SERVER = 0X22;
	/**
	 * 闪屏启动页标识
	 */
	int RESPONSE_FLAG_SPLASHSTART = 0X23;
	/**
	 * 角色升级
	 */
	int RESPONSE_FLAG_UPLEVEL = 0X24;
	/**
	 * 分享
	 */
	int RESPONSE_FLAG_SHARE = 0X25;
	
	
	
	
	/**
	 * 订单历史
	 */
	int RESPONSE_FLAG_ORDERHISTORY = 0X26;
	/**
	 * 显示浮标
	 */
	int RESPONSE_FLAG_SHOWFLOATION = 0X27;
	/**
	 * 清楚缓存
	 */
	int RESPONSE_FLAG_CLEARCACHE = 0X28;
	/**
	 * 是否登录
	 */
	int RESPONSE_FLAG_ISLOGIN = 0X29;
	/**
	 * 积分墙
	 */
	
	int RESPONSE_FLAG_SCORE = 0X30;
	/**
	 * 礼包
	 */
	
	int RESPONSE_FLAG_GAMEGIFT = 0x31;
	/**
	 * 展示成就
	 */
	int RESPONSE_FLAG_SHOWACHIEVEMENT = 0X32;
	/**
	 * 保存状态
	 */
	
	int RESPONSE_FLAG_SAVEINSTANCESTATE = 0X33;
	/**
	 * kit_center
	 */
	
	int RESPONSE_FLAG_KIT_CENTER = 0X34;
	
	/**
	 * kit_center
	 */
	
	int RESPONSE_FLAG_PUSH = 0X35;
	
	
}


