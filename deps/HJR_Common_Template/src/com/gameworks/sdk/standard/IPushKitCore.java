package com.gameworks.sdk.standard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public interface IPushKitCore {
	
	void initPush(Activity activity, Bundle bundle, ISDKKitCallBack receive);
	
	void startWork(Activity activity, ISDKKitCallBack receive);
	
}
