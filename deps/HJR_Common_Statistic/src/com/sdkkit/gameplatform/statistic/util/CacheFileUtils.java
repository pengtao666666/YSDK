package com.sdkkit.gameplatform.statistic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import com.sdkkit.gameplatform.statistic.bean.DataMap;

import android.content.Context;

/**
 * <li>文件名称: CacheFileUtils.java</li>
 * <li>文件描述: 缓存文件自定义类</li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: ‎2014年12月10日下午2:29:53</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0.0
 * @author  作者姓名: Charlie
 */
public  class CacheFileUtils {
	private static String TAG="SDKKitStatistic_CacheFileUtils";
	public static String dataMapPath="";
	public static final int MODE_WORLD_WRITEABLE=0x0002;
		/**缓存文件到本地存储,写入第一次发送中的数据到本地,状态置为1，表示正在发送中*/
		public static DataMap  writeSendFile(DataMap dataMap,String fileName,Context mContext){
			try {
				if(dataMap==null){
					dataMap=new DataMap();
				}
				createFile(mContext);
//				HLog.i(TAG,"dataMap_write_localPath:"+dataMapPath);
				FileOutputStream fos=new FileOutputStream(dataMapPath+"/"+fileName);
				dataMap.put(ProtocolKeys.KEY_SUCESSFLAG, "1");//2表示第一次正在发送中，1.表示再次发送中
				HLog.i(TAG,"first send...write fileName:"+fileName);
				ObjectOutputStream oos=new ObjectOutputStream(fos);
				oos.writeObject(dataMap);
				oos.flush();
				oos.close();
				fos.flush();
				fos.close();
			} catch (Exception e) {
				HLog.i(TAG,"Exception thrown during serializeToLocalPath"+e.getMessage());
//				e.printStackTrace();
			} 
			return dataMap;
		}
	
		public static void createFile(Context mContext) {
			dataMapPath=mContext.getFilesDir().getAbsolutePath()+"/cacheFiles";
			File destDir=new File(dataMapPath);
			if(!destDir.exists()){
				destDir.mkdirs();
			}else{
				return;
			}
			
		}

		/**缓存文件到本地存储，启动sdk查询有没有失败数据，有则上传至服务器，状态置为再次发送状态*/
		public static DataMap  writeSendFailedFile(DataMap dataMap,String fileName,Context mContext){
			try {
				if(dataMap==null){
					dataMap=new DataMap();
				}
				createFile(mContext);
//				HLog.i("dataMap_write_localPath",mContext.getFilesDir().getAbsolutePath());
				FileOutputStream fos=new FileOutputStream(dataMapPath+"/"+fileName);
				dataMap.put(ProtocolKeys.KEY_FILE_NAME, fileName);
				dataMap.put(ProtocolKeys.KEY_SUCESSFLAG, "2");
				HLog.i(TAG,"Second send...write fileName: "+fileName);
				ObjectOutputStream oos=new ObjectOutputStream(fos);
				oos.writeObject(dataMap);
				oos.flush();
				oos.close();
				fos.flush();fos.close();
			} catch (Exception e) {
				HLog.i(TAG,"Exception thrown during serializeToLocalPath"+e.getMessage());
			} 
			return dataMap;
		}
		/**删除本地缓存数据*/
		public static void deleteFile(String fileName,Context mContext){
			createFile(mContext);
					File file=new File(dataMapPath);
					if(file.isDirectory()){
						File[] fileList=file.listFiles();
						//遍历本地存储里面的每个文件
						for (int i= 0; i < fileList.length; i++) {
								if(fileList[i].getName().equals(fileName)){
									fileList[i].delete();
									HLog.i(TAG,"del......"+fileList[i].getName());
								}
						}
						
				
				}
			
			}
		/**
		 * 查询本地缓存数据
		 * @param mContext
		 */
		private static String queryFile(Context mContext){
			createFile(mContext);
			File file=new File(dataMapPath);
			StringBuilder sb=null;
			if(file.isDirectory()){
				String[] fileList=file.list();
					 sb=new StringBuilder();
					for (String string : fileList) {
						sb.append(new File(string).getName());
					}
					HLog.i(TAG,"return:"+sb.toString());
			}
			return sb.toString();
		}
}
