package com.gameworks.sdk.standard.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class ProductIDUtil {
	 public static String getProductID(Context mContext,String id){
			
		 //如果找到游戏传过来的id对应那么返回渠道的id，如果没有找到返回游戏id
			InputStream inputStream =mContext.getResources().openRawResource(getRawId(mContext, "product_id_list"));
			try {
				String result = "";
				List<ProductIDBean> list = readXml(inputStream);
				for (ProductIDBean product : list) {
					if(id.equals(product.getKey())){
						result = product.getValue();
						return result;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return id;
			}
		 return id;
	 }
	 
 
	 private static List<ProductIDBean> readXml(InputStream inputStream) throws Exception{
			XmlPullParser xpp = Xml.newPullParser(); // 创建一个xml解析器对象
			xpp.setInput(inputStream, "UTF-8");  // 设置输入流以及编码方式
			int eventType = xpp.getEventType(); // 获得事件的类型
			List<ProductIDBean> list = new ArrayList<ProductIDBean>();
			ProductIDBean p = null;
			 while (eventType != XmlPullParser.END_DOCUMENT) { // 判断事件类型是否为结束文档事件 如果不是就执行循环中的代码
				 if(eventType == XmlPullParser.START_TAG) {
					 if("ProductId".equals(xpp.getName())){ // 判断标签名字是否为 ProductId
						 p = new ProductIDBean();
						 String key = xpp.getAttributeValue(0);
						 String value = xpp.getAttributeValue(1);
						 p.setKey(key);
						 p.setValue(value);
						 list.add(p);
					 }
				 }
				 
				 eventType = xpp.next(); // 获取下一个事件类型
			 }
			 
			 return list;
		}
	 
	 private static int getRawId(Context paramContext, String paramString) {
			return paramContext.getResources().getIdentifier(paramString, "raw",
					paramContext.getPackageName());
		}
	 
 
 

}
