package com.gameworks.sdk.standard;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2015/11/17.
 */
public interface ISDKAdCore {

    public void init(Activity mContext, Bundle bundle, final ISDKKitCallBack callBack);

    public void config();

    public void showAdDialog();

    public void showExitDialog();

    public boolean isAvailable();

}
