package com.gameworks.sdk.standard.core;

import com.tencent.ysdk.module.bugly.BuglyListener;
import com.tencent.ysdk.module.user.UserListener;
import com.tencent.ysdk.module.user.UserLoginRet;
import com.tencent.ysdk.module.user.UserRelationRet;
import com.tencent.ysdk.module.user.WakeupRet;

public class YSDKCallback implements UserListener, BuglyListener {

	@Override
	public byte[] OnCrashExtDataNotify() {
		return null;
	}

	@Override
	public String OnCrashExtMessageNotify() {
		return null;
	}

	@Override
	public void OnLoginNotify(UserLoginRet arg0) {
		
	}

	@Override
	public void OnRelationNotify(UserRelationRet arg0) {
		
	}

	@Override
	public void OnWakeupNotify(WakeupRet arg0) {
		
	}

}
