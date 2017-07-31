package com.sdkkit.gameplatform.statistic.bean;

/**
 * 
 * @ClassName: Result
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author <tangzhifei>tangzhifei@cy2009.com
 * @date 2012-7-5 上午10:16:33
 *
 */
public class Result {

	private int state;
	private String message;


	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {	
		this.message = message;
	}
}