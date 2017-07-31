package com.sdkkit.gameplatform.statistic.util;

import android.R.string;


/**
 * <li>文件名称: ProtocolKeys.java</li>
 * <li>文件描述: 协议参数</li>
 * <li>公    司: GameWorks</li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: 2014-7-3 下午2:52:24</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0.0
 * @author  作者姓名: HooRang
 */
public interface ProtocolKeys {

	/**
	 * 用户标识  ， 数据格式：用户ID@平台 
	 * 如： 10@NEWGAME
	 */
	String KEY_USERMARK  = "usermark";
	/**
	 * 角色唯一标识  
	 * 
	 */
	String KEY_ROLEMARK  = "rolemark";
	
	/**
	 * 用户类型
	 */
	String KEY_USERTYPE  = "usertype";
	
	/**
	 * IP
	 */
	String KEY_COOD 	 = "cood";
	
	/**
	 * 时间戳
	 */
	String KEY_TIMESTAMP = "timestamp";
	
	String KEY_NAME		 = "name";
	
	/**
	 * 广告ID
	 */
	String KEY_AID 		 = "aid";
	
	/**
	 * 异常信息内容
	 */
	String KEY_CONTENT 	 = "content";
	
	
	/**
	 * 支付方式ID
	 */
	String KEY_PAYID = "payid";
	
	/**
	 * 渠道ID
	 */
	String KEY_CHANNELID   = "channelid";
	
	/**
	 * 验证码
	 */
	String KEY_CAPTCHA   = "captcha";
	
	/**
	 * 充值金额
	 */
	String KEY_AMOUNT   = "amount";
	
	/**
	 * 游戏ID
	 */
	String KEY_GAMEID   = "gameid";
	
	/**
	 * 手机号码
	 */
	String KEY_MOBILE   = "mobile";
	
	/**
	 * 订单发送邮箱
	 */
	String KEY_MAIL   = "mail";
	
	/**
	 * 服务器ID
	 */
	String KEY_SERVERNO   = "serverno";
	
	/**
	 * 卡号
	 */
	String KEY_CARDNO   = "cardno";
	
	/**
	 * 卡密
	 */
	String KEY_CARDKEY   = "cardkey";
	
	/**
	 * 国家码
	 */
	String KEY_COUNTRY   = "country";
	
	/**
	 * 
	 */
	String KEY_GAMEEXTEND   = "gameextend";
	
	/**
	 * 游戏房用户ID	
	 */
	String KEY_GAMEUSERID   = "gameuserid";
	
	
	
	String KEY_SESSION = "session";
	
	/**
	 * 支付方式
	 */
	String KEY_PAYNAME    = "payname";
	
	/**
	 * 订单号
	 */
	String KEY_ORDERNUMBER  =  "ordernumber";
	
	/**
	 * 商品描述
	 */
	String KEY_PRODUCT_DESC = "productdesc";	
	
	/**
	 * 游戏类型： 0 ，单机  ； 1， 网游
	 */
	String KEY_GAMETYPE = "gameType" ; 
	
	
	
	String KEY_UPGRADE = "grade";	
	/**
	 * 重发文件名称
	 */
	String KEY_FILE_NAME="filename_mark";
	/**
	 * 原因
	 */
	String KEY_REASON="reason";
	/**
	 * 玩家等级
	 */
	String KEY_GRADE="grade";
	/**
	 * 关卡开始时间
	 */
	String KEY_STARTTIME="starttime";
	/**
	 * 关卡结束时间
	 */
	String KEY_ENDTIME="endtime";
	/**
	 * 关卡序号
	 */
	String KEY_SEQNO="seqno";
	/**
	 * 关卡唯一标识
	 */
	String KEY_LEVELID="levelId";
	/**
	 * 关卡难度
	 */
	String KEY_DIFFICULTY="difficulty";
	/**
	 * 关卡状态
	 */
	String KEY_STATUS="status";
	/**
	 * 玩家等级
	 */
	String KEY_ITEMID="itemid";
	/**
	 * 道具类型
	 */
	String KEY_ITEMTYPE="itemtype";
	/**
	 * 道具数量
	 */
	String KEY_ITEMCOUNT="itemcnt";
	/**
	 * 虚拟币类型
	 */
	String KEY_CURRENCYTYPE="currencytype";
	
	/**
	 * 计费点
	 */
	String KEY_POINT="point";
	
	/**
	 * 虚拟币数量
	 */
	String KEY_CURRENCYCOUNT="currency";
	/**
	 * 表的行id
	 */
	String KEY_ID="id";
	//****************************整合sdk新添加字段*********************************
	/**
	 * cp_key
	 */
	String KEY_CP = "cp";
	/**
	 * 第三方渠道sdk版本号
	 */
	String KEY_SDKVERSION="sdk_version";
	/**
	 * 发送状态标识
	 */
	String KEY_SUCESSFLAG="success_flag";
	/**
	 * 客户端发送每一条数据的时间
	 */
	String KEY_CLIENTTIME="clienttime";
}












