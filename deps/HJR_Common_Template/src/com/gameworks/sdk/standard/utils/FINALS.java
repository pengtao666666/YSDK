package com.gameworks.sdk.standard.utils;

public interface FINALS {
	
	/**
	 * 平台中心接口分发标识
	 * @author Administrator
	 *
	 */
	public static class KIT_CENTER {
		/**
		 * 注册
		 */
		public static final int FUNCTION_REGISTER =  0X1; 
		/**
		 * 修改用户信息
		 */
		public static final int FUNCTION_MODIFY_USERINFO =  0X2; 
		/**
		 * 重置上下文
		 */
		public static final int FUNCTION_RECONTEXT =  0X3; 
		/**
		 * 横竖屏切换
		 */
		public static final int FUNCTION_CONGFIG_CHANGE =  0X4; 
//		/**
//		 * 升级
//		 */
//		public static final int FUNCTION_UPGRADE =  0X5; 
		/**
		 * 排行榜
		 */
		public static final int FUNCTION_LEADERBOARD =  0X6; 
		/**
		 * 邀请好友
		 */
		public static final int FUNCTION_FRIENDS =  0X7; 
		/**
		 * 积分墙
		 */
		public static final int FUNCTION_SCORE =  0X8; 
		/**
		 * 礼包
		 */
		public static final int FUNCTION_GIFT =  0X9; 
		/**
		 * 成就
		 */
		public static final int FUNCTION_ACHIEVEMENT =  0X10; 
		/**
		 * 论坛
		 */
		public static final int FUNCTION_BBS =  0X11; 
//		/**
//		 * 保存状态
//		 */
//		public static final int FUNCTION_SAVE_STATUS =  0X12; 
		/**
		 * 扩展资源
		 */
		public static final int FUNCTION_EXTEND_RESOURCES =  0X13; 
		/**
		 * 平台中心
		 */
		public static final int FUNCTION_PLATFORM_CENTER =  0X14; 
		/**
		 * 更多游戏
		 */
		public static final int FUNCTION_MORE_GAME =  0X15; 
		/**
		 * 分享
		 */
		public static final int FUNCTION_SHARE =  0X16; 
		
	}
	
	/**
	 * 第三方统计数据接口
	 * @author Administrator
	 *
	 */
	public static class POST_DATAS {
		
		/**
		 * 角色升级
		 */
		public static final int POST_ROLE_UPGRADE = 1;

		/**
		 * 角色创建
		 */
		public static final int POST_ROLE_CREATE = 2;

		/**
		 * 角色信息
		 */
		public static final int POST_ROLE_INFO = 3;

		/**
		 * 服务器信息
		 */
		public static final int POST_SERVER_INFO = 4;

	}
	
}
