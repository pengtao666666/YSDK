package com.sdkkit.gameplatform.statistic.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

public class UIUtil {

	
	
	private static int[] pxy = { 0, 0 };

	
	/**
	 * 获取屏幕宽和高 **这里现在动态判定为获取横屏的宽高
	 */
	public static int[] getXY(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		
		int o = act.getWindowManager().getDefaultDisplay().getOrientation();
//		TLog.d(3, "Orientation = " + o );
		
//		竖屏取反 锁屏的时候会判定为竖屏
		if(o==0){
			pxy[1] = dm.widthPixels;
			pxy[0] = dm.heightPixels;
		}else{
			pxy[0] = dm.widthPixels;
			pxy[1] = dm.heightPixels;
		}

		
//		TLog.d(3, "pxy[0] = " + pxy[0] + " pxy[1]= " + pxy[1] );
		
		
		return pxy;
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
