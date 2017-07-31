package com.gameworks.sdk.standard.core;

import android.content.Context;
import android.os.Bundle;

import com.sdkkit.gameplatform.statistic.AbStatisticCore;

public class SDKKitStatistic extends AbStatisticCore {

	private static SDKKitStatistic instance;

	private SDKKitStatistic() {
	};

	public static synchronized SDKKitStatistic getIntance(Context context) {
		if (null == instance) {
			instance = new SDKKitStatistic();
		}
		return instance;
	}

	@Override
	public void doTjLogin(Bundle bundle) {
		super.doTjLogin(bundle);
	}

	@Override
	public void doTjPay(Bundle bundle) {
		super.doTjPay(bundle);
	}

	@Override
	public void doTjCreateRole(Bundle bundle) {
		super.doTjCreateRole(bundle);
	}

	@Override
	public void doTjServerRoleInfo(Bundle bundle) {
		super.doTjServerRoleInfo(bundle);
	}

	@Override
	public void doTjUpgrade(Bundle bundle) {
		super.doTjUpgrade(bundle);
	}

	@Override
	public void doTjClick(Bundle bundle) {
		super.doTjClick(bundle);
	}

}
