package com.sdkkit.gameplatform.statistic.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

public class ProcessUtil {

	/** 程序是否在前台运行 */
	public static boolean isAppOnForeground(Context ctx) {
        return isAppRunningByProcessImportance(ctx, RunningAppProcessInfo.IMPORTANCE_FOREGROUND);
	}

	/**判定应用是否运行在指定的进程等级中*/
	public static boolean isAppRunningByProcessImportance(Context ctx, int process_importance) {

		ActivityManager activityManager = (ActivityManager) ctx
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE); 
		String packageName = ctx.getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();//也可以获取服务等信息RunningServiceInfo
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == process_importance) {
				return true;
			}
		}
		return false;
	}
}
