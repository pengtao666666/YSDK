package com.gameworks.sdk.standard.core;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hjr.sdkkit.framework.aes.AESUtil;
import com.sdkkit.gameplatform.statistic.bean.DataManager;
import com.sdkkit.gameplatform.statistic.bean.Result;
import com.sdkkit.gameplatform.statistic.engine.IEvent;
import com.sdkkit.gameplatform.statistic.engine.IEventHandler;
import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.util.C;

/**
 * 事件操作回调
 */
public class EventHandlerCallBack implements IEventHandler {
	private Handler mHandler;

	public EventHandlerCallBack(Handler handler) {
		this.mHandler = handler;
	}

	@Override
	public void handleTask(int initiator, Task task, int operation) {
		if (task == null || task.isFailed() || task.isCanceled()) {
			return;
		}

		String result;
		switch (initiator) {
		case IEvent.IO:
			if (task.getData() instanceof byte[]) {
				result = new String((byte[]) task.getData());
				String reqTaskFlag = task.getParameter().toString();
				Result desResult = DataManager.getInstance().validateResult(
						result);
				String retJson = AESUtil.s_Decryption(result, result.length(),
						C.AES_KEY);
				Log.i("YSDK",reqTaskFlag+":" + retJson);
				if (reqTaskFlag.equals("sendOrder")) {
					Message message = new Message();
					message.what = 1;
					message.obj = retJson;
					message.arg1 = desResult.getState();
					mHandler.sendMessage(message);
				} else if (reqTaskFlag.equals("rechargeStatus")) {
					Message message = new Message();
					message.what = 2;
					message.obj = retJson;
					message.arg1 = desResult.getState();
					mHandler.sendMessage(message);
				} else if (reqTaskFlag.equals("oauthToken")) {
					Message message = new Message();
					message.what = 3;
					message.obj = retJson;
					message.arg1 = desResult.getState();
					mHandler.sendMessage(message);
				} else if (reqTaskFlag.equals("verifyUserInfo")) {
					Message message = new Message();
					message.what = 4;
					message.obj = retJson;
					message.arg1 = desResult.getState();
					mHandler.sendMessage(message);
				}else if (reqTaskFlag.equals("sendTencentYSDKOrder")) {
					Message message = new Message();
					message.what = 5;
					message.obj = retJson;
					message.arg1 = desResult.getState();
					mHandler.sendMessage(message);
				}
			}
			break;
		default:

			break;
		}
	}
}