package com.sdkkit.gameplatform.statistic.io;

import com.sdkkit.gameplatform.statistic.bean.DataMap;
import com.sdkkit.gameplatform.statistic.util.CacheFileUtils;

import android.content.Context;

public class WriteFileThread  extends Thread {

	  private  Context mContext;
	  private DataMap dataMap;
	  private String resFlag;
	  public WriteFileThread(Context context,DataMap dataMap,String resFlag) {
		  mContext=context;
		  this.dataMap=dataMap;
		  this.resFlag=resFlag;
		  
	  }

	  @Override
	  public void run() {
		  CacheFileUtils.writeSendFile(dataMap, resFlag, mContext);
		  }

}
