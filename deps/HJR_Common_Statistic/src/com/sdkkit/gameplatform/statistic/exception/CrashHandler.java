package com.sdkkit.gameplatform.statistic.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;

import android.content.Context;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.sdkkit.gameplatform.statistic.bean.DataManager;
import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.bean.Result;
import com.sdkkit.gameplatform.statistic.engine.IEvent;
import com.sdkkit.gameplatform.statistic.engine.IEventHandler;
import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.io.IOManager;
import com.sdkkit.gameplatform.statistic.util.C;
import com.sdkkit.gameplatform.statistic.util.MapUtil;
import com.sdkkit.gameplatform.statistic.util.RequestParamUtil;
import com.sdkkit.gameplatform.statistic.util.SPHelper;

public class CrashHandler implements UncaughtExceptionHandler , IEventHandler {

	/** Debug Log tag */
	public static final String TAG = "CrashHandler";
	/**
	 * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
	 * */
	public static final boolean DEBUG = false;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private Context mContext;

	/** 保证只有一个CrashHandler实例 */
//	private CrashHandler() {
//	}

	
	/**
	 * 2014年4月28日16:55:43
	 *	李剑桥  预改动
 	 * 我觉得这里的INIT方法没有必要 应该直接写到constructor里面表明意图
	 */
	
	
	/** 获取CrashHandler实例 ,单例模式 */
	/**
	 * @return
	 */
	public static CrashHandler getInstance(Context ctx) {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler(ctx);
		}
		return INSTANCE;
	}
	
	
	

	private CrashHandler(Context ctx) {
		super();
		this.mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}


	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e(TAG, ex.getMessage(), ex);
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			// Sleep一会后结束程序
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Log.e(TAG, "Error : ", e);
			}
//			android.os.Process.killProcess(android.os.Process.myPid());
//			System.exit(10);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			Log.w(TAG, "handleException --- ex==null");
			return true;
		}
		final String msg = ex.getLocalizedMessage();
		if (msg == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				/*Toast toast = Toast.makeText(mContext, "程序出错，即将退出:\r\n" + msg, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();*/
				// MsgPrompt.showMsg(mContext, "程序出错啦", msg+"\n点确认退出");
				Looper.loop();
			}
		}.start();
		// 保存错误报告文件
		saveCrashInfoToFile(ex);
		// 发送错误报告到服务器
		sendCrashReportsToServer(mContext);
		return true;
	}

	/**
	 * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
	 */
	public void sendPreviousReportsToServer() {
		sendCrashReportsToServer(mContext);
	}

	/**
	 * 把错误报告发送给服务器,包含新产生的和以前没发送的.
	 * 
	 * @param ctx
	 */
	private void sendCrashReportsToServer(Context ctx) {
		String msg = SharedPreferencesHelper.getValue(ctx, C.CONFIG_FILENAME,  Context.MODE_PRIVATE, "exmsg");
		if(msg ==null || "".equals(msg)){
			
		}else{
			sendExpTask(msg);
		}
	}
	private void sendExpTask(String msg) {
		Task task = new Task(null, C.ABNORMAL_URL, null, "abnormal", this);
		task.setPostData(getSendExpParams(msg,task).getBytes());
		IOManager.getInstance().addTask(task);
	}
	
	/**
	 * 本方法对错误信息的处理步骤
	 * 1.被BASE64转化
	 * 2.加入本机INFO转化成JSON信息
	 * 3.被AESUtil类执行加密转化成string 
	 * @param msg
	 * @return
	 */
	private String getSendExpParams(String msg,Task task) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		DataMap params=MapUtil.map2CustMap(param);
		params.put("content", new String(Base64.encode(msg.getBytes(), Base64.DEFAULT)));
		return RequestParamUtil.writeFile(params, task.getParameter().toString(), mContext).getRequestParams(params);
	}

	/**
	 * 错误信息通过SharedPreferencesHelper保存到文件中
	 * 
	 * @param ex
	 * @return
	 */
	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		String re = info.toString();
		printWriter.close();
		long timestamp = System.currentTimeMillis();
		String time = String.valueOf(timestamp).substring(0, String.valueOf(timestamp).length()-3);
		String result = "["+re + "#" + time +"]";
		SPHelper.putValue(mContext, "exmsg", result);
//		SharedPreferencesHelper.putValue(mContext, C.CONFIG_FILENAME, Context.MODE_PRIVATE, "exmsg", result);
		return null;
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
				Result desResult = DataManager.getInstance().validateResult(result);
				
				if (reqTaskFlag.equals("abnormal")) { //发送异常报告
					System.out.println(" Enter abnormal CallBack ....");
					SPHelper.putValue(mContext, "exmsg", "");
					
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(10);
				}
			}
			break;
		default:
			
			
			break;
		}
	}
}