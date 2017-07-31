package com.gameworks.sdk.standard.beans;


/**
 * <li>文件名称: AnySdkResponse.java</li>
 * <li>文件描述: </li>
 * <li>公    司: GameWorks</li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: 2014-7-7 上午11:44:38</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0.0
 * @author  作者姓名: HooRang
 */
public class SDKKitResponse {

	private ResponseHead head; 
	
	private ResponseBody  body ;

	public ResponseHead getHead() {
		return head;
	}

	public void setHead(ResponseHead head) {
		this.head = head;
	}

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	} 
	
	public static SDKKitResponse getNew(){
		SDKKitResponse asr = new SDKKitResponse();
		asr.setBody(new ResponseBody());
		asr.setHead(new ResponseHead());
		return asr;
	}
	
	
}








