package com.sdkkit.gameplatform.statistic.io;

import java.util.Vector;

import android.util.Log;

import com.sdkkit.gameplatform.statistic.engine.IEvent;
import com.sdkkit.gameplatform.statistic.engine.ITaskManager;
import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.io.core.IOProtocol;
import com.sdkkit.gameplatform.statistic.io.core.IOProtocolFactory;
import com.sdkkit.gameplatform.statistic.io.core.IOResponse;
import com.sdkkit.gameplatform.statistic.util.C;
import com.sdkkit.gameplatform.statistic.util.L;

/*******************************************************************************
 * IO管理�?
 * 
 * 1. 它提供创建新的IO请求接口 
 * 2. 它提供取消当前IO任务的接�?
 * 3. 它提供删除当前或是所有IO任务 
 * 4. 它将IO请求分发到IOTask
 * 
 * @author <xingyong>xingyong@cy2009.com
 * @version 2012-5-16 下午9:40:53
 ******************************************************************************/
public class IOManager implements ITaskManager {

	private static final String TAG = "IOManager";
	/** IOManager实例 */
	private static IOManager mSingleton = null;

	/** IO任务队列 */
	private static Vector<IOTask> mIOQueue = null;
	/** IO任务处理线程数目 (由配置文件指�? */ /*好像没有实现_lijianqiao*/
	private static int mThreadCount = 8;
	/** IO请求处理线程�?*/
	private static IOThread[] mThreadList = null;

	/***************************************************************************
	 * 基本接口
	 **************************************************************************/

	private IOManager() {
	}

	/**
	 * 初始化IO管理�?
	 */
	synchronized public static boolean init() {
		boolean result = true;
		if (mSingleton != null) {
			return true;
		}

		try {
			// 创建IOManager
			mSingleton = new IOManager();
			// 创建IO任务队列
			mIOQueue = new Vector<IOTask>();

			mThreadList = new IOThread[mThreadCount];
			for (int i = 0; i < mThreadCount; i++) {
				mThreadList[i] = new IOThread("IOThread_" + i, mIOQueue);
			}
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	/**
	 * �?��IO管理�?
	 */
	public static void exit() {
		if (mSingleton == null)
			return;
		// 清空�?��IO任务
		mSingleton.removeAllTasks();
		// 停止当前IO任务
		mSingleton.cancelCurrentTask(null);
	}

	/**
	 * 多线程安�? 获取IOManager句柄
	 */
	synchronized public static final IOManager getInstance() {
		if (mSingleton == null) {
			if (!init()) {
				if (L.ERROR)
					L.e(TAG, "Failed to init itself");
			}
		}
		return mSingleton;
	}

	/***************************************************************************
	 * 任务管理接口
	 **************************************************************************/

	@Override
	public void addTask(Task obj) {
		IOTask task;

		// ----------------------------------------------------------------------
		// 1. 解码获取到的任务
		if (obj == null)
			return;
		if (obj instanceof IOTask)
			task = (IOTask) obj;
		else
			task = new IOTask((Task) obj);/*为什么还�?��强转Task _lijianqiao*/

		// ----------------------------------------------------------------------
		// 2. 通知任务Owner, 即将�?��处理任务
		task.start();

		// ----------------------------------------------------------------------
		// 3. 获取相应协议
		Object data = task.getData();
		if (data == null || !(data instanceof String)) {
			if (L.DEBUG)
				L.d(TAG, "Unknown Protocol in " + "Task [" + data
						+ "], ignored");

			task.fail(IOResponse.FAILED, "IO: Unknown Protocol");
			doCallBack(task);
			return;
		} else {
			if (L.DEBUG)
				L.d(TAG, "New Task [" + data + "], start");
		}

		IOProtocol protocol = task.getProtocol();
		if (protocol == null) // 协议尚未指定, 则重新识�?
			
			protocol = IOProtocolFactory.getHandler((String) data);
		System.out.println("protocol = IOProtocolFactory.getHandler((String) data);"+protocol);
		//这个protocol 应该是恒 NULL啊�?�? _lijianqiao 
		if (protocol == null) {
			if (L.DEBUG)
				L.d(TAG, "Invalid Protocol in " + "Task [" + data
						+ "], ignored");

			task.fail(IOResponse.FAILED, "IO: Invalid Protocol");
			doCallBack(task);
			return;
		} else {
			task.setProtocol(protocol);
		}

		// ----------------------------------------------------------------------
		// 4. 验证IO请求的有效�?
		if (!protocol.validateRequest(task)) {
			if (L.DEBUG)
				L.d(TAG, "Invalid Task [" + data + "], ignored");

			task.fail(IOResponse.FAILED, "IO: Invalid Task");
			doCallBack(task);
			return;

		} else if (task.isCanceled()) {
			Log.i("task","task.isCanceled()");
			return; // 无需Callback
		}

		// ----------------------------------------------------------------------
		// 5. 进行任务分发
		dispatch(task);
	}
	/**
	 * @param owner 如果取消任务的owner非传�?则不取消 但传入null则取消任何当前任�?
	 * 
	 */
	@Override
	synchronized public void cancelCurrentTask(Object owner) {
		IOThread thread;
		IOTask curTask;

		for (int i = 0; i < mThreadCount; i++) {
			thread = mThreadList[i];
			curTask = (IOTask) thread.getCurTask();

			// 当前任务的Owner与指定对象一致或未指定对象，直接取消任务
			if (curTask != null) {
				if (owner == null || curTask.getOwner() == owner) {
					curTask.cancel(); // 取消当前解码任务
					thread.cancelCurTask();
				}
			}
		}
	}

	/**
	 * 直接全部清除�?��任务
	 */
	public void removeAllHandlerTasks() {
		if (mIOQueue == null)
			return;

		synchronized (mIOQueue) {
			for (int i = mIOQueue.size() - 1; i >= 0; --i) {
				mIOQueue.removeElementAt(i);
			}
		}
	}
	/**
	 * 删除无owner的任�? _lijianqiao
	 */
	@Override
	public void removeAllTasks() {
		if (mIOQueue == null)
			return;

		synchronized (mIOQueue) {
			for (int i = mIOQueue.size() - 1; i >= 0; --i) {
				IOTask task = (IOTask) mIOQueue.elementAt(i);
				// owner == null �? 直接取消该任�?
				if (task.getOwner() == null) {
					mIOQueue.removeElementAt(i);
				}
			}
		}
	}
	
	/**
	 * 删除�?��等于传入参数的任�?
	 * @param parent 任务owner
	 */
	@Override
	public void removeAllTasks(Object parent) {
		if (parent == null || mIOQueue == null)
			return;

		synchronized (mIOQueue) {
			for (int i = mIOQueue.size() - 1; i >= 0; --i) {
				IOTask task = (IOTask) mIOQueue.elementAt(i);
				if (task.getOwner() == parent) {
					mIOQueue.removeElementAt(i);
				}
			}
		}
	}
	
	/**
	 * 删除传入的任�?
	 */
	@Override
	public void removeTask(Object task) {
		if (mIOQueue == null || task == null)
			return;

		synchronized (mIOQueue) {
			mIOQueue.removeElement(task);
		}
	}


	/***************************************************************************
	 * 任务分发接口
	 **************************************************************************/
	private boolean dispatch(IOTask task) {
//		TLog.d("dispatch");
		IOProtocol protocol = task.getProtocol();
		System.out.println("dispatch(IOTask task)==="+protocol.getType());
		switch (protocol.getType()) {
		case IOProtocol.RESOURCE: // 本地资源文件的访问直接在此处�?
			//TODO 此处可调用cache机制获取缓存数据，若获取到则返回结果
			//CacheManager cache = CacheManager.getInstance();
			//if (cache.check(task)) { // 如果命中Cache, 则直接返回数�?
				//task.complete();
				//doCallBack(task);
				//break;
			//}
			protocol.handleRequest(task);
			task.complete();
			doCallBack(task);
			break;
		case IOProtocol.HTTP: // HTTP访问
//			TLog.d("IOProtocol.HTTP");
			String method = task.getType().toUpperCase();
//			TLog.d("method= " + method );
			if (method.equals(C.HTTP_GET)) { // �?��是否为POST操作
				// 若命中cache则直接返�?
//				ImageCacheManager imageCacheManager = ImageCacheManager.getInstance();
//				String url = (String) task.getData();
//				if(imageCacheManager.check(url)){
//					task.setData(imageCacheManager.getBitmapFromCache(url));
//					task.complete();
//					doCallBack(task);
//					break;
//				}
			}
			// 不命�? 则放入ioQueue, 由IOThread来处�?
			return post(task);
		default: // 其它协议的任�? 则直接放入ioQueue, 由IOThread来处�?
			return post(task);
		}

		return true;
	}

	/**
	 * 将制定IO任务添加任务队列中去, 等待IOThread的处�?
	 * 
	 * @param task
	 */
	private boolean post(IOTask task) {
		if (mIOQueue == null)
			return false;

		synchronized (mIOQueue) {
			mIOQueue.addElement(task);
			mIOQueue.notify();
		}
		return true;
	}

	/**
	 * 负责任务的后续处�?
	 * 
	 * @param task 指定任务
	 * @param param 要传递给处理者的参数
	 */
	private void doCallBack(Task task) {
		if (task == null)
			return;
		task.callback(IEvent.IO, IEvent.OP_COMPLETE);
	}
}
