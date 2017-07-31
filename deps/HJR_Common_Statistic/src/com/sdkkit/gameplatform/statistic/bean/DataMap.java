package com.sdkkit.gameplatform.statistic.bean;

import java.io.Serializable;
import java.util.HashMap;


public class DataMap extends HashMap<String, Object> implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3514481533865779170L;

	@Override
	public Object get(Object key) {
		if (super.containsKey(key)&&super.get(key)!=null) {
			return super.get(key); 
		}else {
			return "";
		}
		
	}
	
	@Override
	public Object put(String key, Object value) {
		if(value==null){
			return super.put(key, "");
		}
		return super.put(key, value);
	}

	public String getString(Object key) {
		if (super.containsKey(key)&&super.get(key)!=null) {
			return super.get(key).toString(); 
		}else {
			return "";
		}
		
	}
	public int getInt(Object key) {
		
		if (super.containsKey(key)&&super.get(key)!=null&&!super.get(key).equals("")&&!super.get(key).equals(" ")) {
			return Integer.valueOf(super.get(key).toString()); 
		}else {
			return 0;
		}
		
	}
	/*@Override
	public Object remove(Object key) {
		if(super.containsKey(key)&&super.get(key)!=null){
			return super.remove(key);
		}else{
			return this;
		}
	}*/
}
