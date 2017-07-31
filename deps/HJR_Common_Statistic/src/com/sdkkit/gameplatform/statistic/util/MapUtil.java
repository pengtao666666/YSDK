package com.sdkkit.gameplatform.statistic.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.sdkkit.gameplatform.statistic.bean.DataMap;



/**
 * <li>文件名称: MapUtils.java</li>
 * <li>文件描述:Map简单工具类</li>
 * <li>公    司: GameWorks </li>
 * <li>内容摘要: 无</li>
 * <li>新建日期: ‎2014年12月10日下午1:36:44</li>
 * <li>修改记录: 无</li>
 * @version 产品版本: 1.0.0
 * @author  作者姓名: Charlie
 */
public class MapUtil {
	/**
	 * Map或HashMap转自定义的DataMap
	 * @param params
	 * @return
	 */
	public static DataMap map2CustMap(Map<String, Object> params){
		if(params==null){
			return new DataMap();
		}
		Iterator<Entry<String, Object>> it =params.entrySet().iterator();
		DataMap dataMap=new DataMap();
		while(it.hasNext()){
			Map.Entry<String , Object>  e=(Entry<String, Object>) it.next();
			if(e.getValue()==null){
				dataMap.put(e.getKey(), "");
			}else{
				dataMap.put(e.getKey(), e.getValue());
			}
		}
		return dataMap;
	}
	
	
	/**
	 * 嵌套的JSON字符串转化为嵌套的map
	 * @param jsonStr
	 * @return
	 */
	 public static Map<String,Object>  jsonStr2Map(String jsonStr){
		if(jsonStr==null){
			return null;
		}
	    Map<String, Object> map = null;
		try {
			  map = new HashMap<String,Object>();
			  JSONObject json=new JSONObject(jsonStr);
			  @SuppressWarnings("unchecked")
			  Iterator<String> keys=json.keys();
			  while(keys.hasNext()){
				   String key=(String) keys.next();
				   String value= json.get(key).toString();
				   if(value.startsWith("{")&&value.endsWith("}")){
					   map.put(key, jsonStr2Map(value));
				   }else{
					   map.put(key, value);
				   }
		
			  }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	  return map;
	 }
	 /**
		 * map转Json串
		 * 
		 * @param map
		 * @return
		 */
		public static  String mapToJson(Map<String, Object> map) {
			if (map == null || map.isEmpty())
				return "";
			StringBuilder sBuffer = new StringBuilder();
			sBuffer.append("{");
			for (Entry<String, Object> entry : map.entrySet()) {
				sBuffer.append("\"");
				sBuffer.append(entry.getKey());
				sBuffer.append("\":");
				if (entry.getValue() instanceof Map) {
					sBuffer.append(mapToJson((Map<String, Object>) entry.getValue()));
				} else if (entry.getValue() instanceof String) {
					sBuffer.append("\"");
					sBuffer.append(entry.getValue());
					sBuffer.append("\"");
				} else if (entry.getValue() instanceof Integer) {
					sBuffer.append(entry.getValue());
				}
				sBuffer.append(",");
			}
			sBuffer.deleteCharAt(sBuffer.lastIndexOf(","));
			sBuffer.append("}");
			return sBuffer.toString();
		}
}
