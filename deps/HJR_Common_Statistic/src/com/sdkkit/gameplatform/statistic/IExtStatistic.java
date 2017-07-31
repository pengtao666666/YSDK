package com.sdkkit.gameplatform.statistic;

import java.util.Map;

public interface IExtStatistic {
	public void doCreateRole(Map<String, Object> params);
	public void doUserLogin(Map<String, Object> params);
	public void doUserUpgrade(Map<String, Object> params);
	public void doPostOrder(Map<String, Object> params);
}
