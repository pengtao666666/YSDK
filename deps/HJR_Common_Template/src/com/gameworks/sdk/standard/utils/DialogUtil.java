package com.gameworks.sdk.standard.utils;

import com.gameworks.sdk.standard.ISDKKitCallBack;
import com.gameworks.sdk.standard.beans.ResponseBody;
import com.gameworks.sdk.standard.beans.ResponseHead;
import com.gameworks.sdk.standard.beans.SDKKitResponse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil {

	public static void showExitDialog(Context context, final ISDKKitCallBack callBack) {
		new AlertDialog.Builder(context)
		.setTitle("退出")
		.setMessage("\n亲，您确定离开吗？\n")
		.setNegativeButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SDKKitResponse response = new SDKKitResponse();
				ResponseHead head = new ResponseHead();
				ResponseBody body = new ResponseBody();
				head.setErrorMsg("确定");
				head.setStatus(Constants.REQUEST_SUCCESS);
				response.setHead(head);
				response.setBody(body);
				callBack.onResponse(response, Constants.RESPONSE_FLAG_EXITGAME);
				dialog.dismiss();
			}
		})
		.setPositiveButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SDKKitResponse response = new SDKKitResponse();
				ResponseHead head = new ResponseHead();
				ResponseBody body = new ResponseBody();
				head.setErrorMsg("取消");
				head.setStatus(Constants.REQUEST_CANCEL);
				response.setHead(head);
				response.setBody(body);
				callBack.onResponse(response, Constants.RESPONSE_FLAG_EXITGAME);
				dialog.dismiss();
			}
		}).create().show();
	}
}
