package com.gameworks.sdk.standard.beans;


/**
 * <li>文件名称: ResponseHead.java</li>
 * <li>文件描述: 请求响应Head</li>
 * <li>公    司: GameWorks</li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: 2014-7-7 上午10:48:19</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0.0
 * @author  作者姓名: HooRang
 */
public class ResponseHead {

	private int status; 
	
	
	private String errorMsg;
	
	
	private String code;


	private int requestCode ;
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public int getRequestCode() {
		return requestCode;
	}


	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}  
	
	@Override
	public String toString() {
		return "ResponseHead [status=" + status + ", errorMsg=" + errorMsg
				+ ", code=" + code + ", requestCode=" + requestCode + "]";
	}  
	
}


