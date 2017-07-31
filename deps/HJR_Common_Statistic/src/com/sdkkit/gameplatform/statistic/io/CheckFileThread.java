package com.sdkkit.gameplatform.statistic.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sdkkit.gameplatform.statistic.CacheDataCore;
import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.util.CacheFileUtils;
import com.sdkkit.gameplatform.statistic.util.HLog;
import com.sdkkit.gameplatform.statistic.util.InternetUtil;
import com.sdkkit.gameplatform.statistic.util.ProtocolKeys;

import android.content.Context;

public class CheckFileThread  extends Thread {
private static String TAG="SDKKitStatistic_CheckFileThread";
	  private  Context mContext;
	  public CheckFileThread(Context context) {
		  mContext=context;
	  }

	  @SuppressWarnings("resource")
	@Override
	  public void run() {
//	    while (!isInterrupted()) {
			HLog.i(TAG,"enter CheckFileThread.......");
			try {
				CacheFileUtils.createFile(mContext);
				String dataMapPath=CacheFileUtils.dataMapPath;
				File file=new File(dataMapPath);
					if(file.isDirectory()){
						File[] fileList=file.listFiles();
						if(fileList.length==0){
							HLog.i(TAG,"缓存文件不存在.......");
							interrupt();
							return;
						}
						//遍历本地存储里面的每个文件
						int temp=0;
						for (int i = 0; i < fileList.length; i++) {
							int j=i;
							if(!InternetUtil.checkNetWorkStatus(mContext)){
								HLog.i(TAG,"CheckFileThread.......没有网络连接");
								return;
							}
								File f=fileList[i];
								
								FileInputStream fis=new FileInputStream(f.getAbsolutePath());
								
								ObjectInputStream ois=new ObjectInputStream(fis);
								//读取缓存数据
								Object obj =ois.readObject();
								DataMap dataMap;
								if(obj instanceof DataMap){
									dataMap=(DataMap) obj;
								}else{
									return;
								}
								//读取缓存的数据是哪个请求的
								temp++;
								//第一次进来检查是否有之前重发失败的数据，有则重新置数据为第一次发送的状态
								if(temp==1){
									for (int k = 0;k < fileList.length;k++) {
										String successflag=dataMap.get(ProtocolKeys.KEY_SUCESSFLAG).toString();
										if(successflag.equals("2")){
											CacheFileUtils.writeSendFile(dataMap, f.getName(), mContext);
										}
									}
								}
								String resFlag=f.getName().substring(0, f.getName().indexOf("_"));
//								HLog.i(TAG,"CheckFileThread..readfile...."+dataMap.toString()+" resFlag:"+resFlag);
								//判断是哪个请求，重发不同请求的数据
								String successflag=dataMap.get(ProtocolKeys.KEY_SUCESSFLAG).toString();
								if(successflag.equals("2")){
									HLog.i(TAG,"数据正在再次发送中"+" ,fileName:"+f.getName()+" ,serverno:"+dataMap.get(ProtocolKeys.KEY_SERVERNO));//正在发送的数据则什么都不做
								}else if(successflag.equals("1")){
									//2为发送失败标识
									dataMap=CacheFileUtils.writeSendFailedFile(dataMap, f.getName(), mContext);
									dataMap.get(ProtocolKeys.KEY_SERVERNO);
									/*if(resFlag.equals("IP")||resFlag.equals("IP2")){
										CacheDataCore.getInstance(mContext).getIP(resFlag,dataMap);
										
									}else */if(resFlag.equals("startGameTask")){
										CacheDataCore.getInstance(mContext).startGame(dataMap);
										
									}else if(resFlag.equals("abnormal")){
										CacheDataCore.getInstance(mContext).sendExp(dataMap);
										
									}else if(resFlag.equals("login")){
										CacheDataCore.getInstance(mContext).login(dataMap);
										
									}else if(resFlag.equals("ordersubmit")){
										CacheDataCore.getInstance(mContext).orderSubmit(dataMap);
										
									}else if(resFlag.equals("gamebtnclick")){
										CacheDataCore.getInstance(mContext).gameBtnClick(dataMap);
										
									}else if(resFlag.equals("upgrade")){
										CacheDataCore.getInstance(mContext).upgrade(dataMap);
										
									}else if(resFlag.equals("createrole")){
										CacheDataCore.getInstance(mContext).createRole(dataMap);
										
									}else if(resFlag.equals("doSendTimeTask")){
										CacheDataCore.getInstance(mContext).doSendTimeTask(dataMap);
										
									}else if(resFlag.equals("startlevel")){
										CacheDataCore.getInstance(mContext).startLevel(dataMap);
										
									}else if(resFlag.equals("endlevel")){
										CacheDataCore.getInstance(mContext).endLevel(dataMap);
										
									}else if(resFlag.equals("exceptionlevel")){
										CacheDataCore.getInstance(mContext).exceptionLevel(dataMap);
										
									}else if(resFlag.equals("itemget")){
										CacheDataCore.getInstance(mContext).itemGet(dataMap);
										
									}else if(resFlag.equals("itemconsume")){
										CacheDataCore.getInstance(mContext).itemConsume(dataMap);
										
									}else if(resFlag.equals("itembuy")){
										CacheDataCore.getInstance(mContext).itemBuy(dataMap);
									}
//									HLog.i.i(TAG,resFlag+"-local->:"+j+",Time:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
									
								}
								ois.close();
								fis.close();
								}
						}else{
							HLog.i(TAG,"文件夹路径不存在......");
						}
					
			} catch (Exception e) {
				HLog.i(TAG,e.getMessage());
			} 

		
		
		
//}
	  }

}
