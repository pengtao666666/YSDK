package com.sdkkit.gameplatform.statistic.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import android.util.Log;
import android.util.SparseArray;

/**
 * 一个bean的工厂,可以缓存一种Class的一个对象
 * @author chenjun
 */
public class BeanFactory {
	
	private static final Properties prop=new Properties();
	private static final SparseArray<Object> map=new SparseArray<Object>();

	static{
		InputStream in=BeanFactory.class.getClassLoader().getResourceAsStream("gw_beanfactory.properties");//source folder可以不加目录
		try {
			prop.load(in);
			in.close();
			Enumeration<Object> keys = prop.keys();
			while(keys.hasMoreElements()){
				String element = keys.nextElement().toString().trim();
				Object obj=Class.forName(prop.getProperty(element)).newInstance();
				boolean result=regist(Integer.parseInt(element),obj);
				if(result==false){
					Log.w("BeanFactory_init", "obeject regist failed , cause key "+Integer.parseInt(element)+"already exists");
				}
			}
		} catch (Exception e) {
			/**U3D工程下找不到配置文件,原因不明，固调用registAll()死注册*/
			registAll();
		}
	
	}
	/**将需要缓存的bean注册到池子中,若key已存在则返回false*/
	public static boolean regist(int key,Object bean){
		if(map.get(key)!=null){
			return false;
		}
		map.put(key, bean);
		return true;
	}
	
	private static void registAll() {
	/*	regist(CacheKey.Bean.USER, new UserBean());
		regist(CacheKey.Bean.BANNER, new BannerBean());
		regist(CacheKey.Bean.PAYMENT, new PaymentBean());
		regist(CacheKey.Bean.ORDER, new OrderBean());
		regist(CacheKey.Bean.INSTRUCTIONSCMCC, new InstructionsCMCC());*/
	}

	/**将指定的映射放入缓存中，若已存在，则替换*/
	public static void put(int key,Object bean){
		map.put(key, bean);
	}
	
	/**通用创建对象的方法.指定的class必须有无参构造*/
	public static <T>T newInstance(Class<T> clazz){		
		try {
			 return (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);			
		}	
	}
	
	/**<li>从池子中根据key获取指定class的对象,如果没有返回null,
	 * <li>严格检查指定class的对象是否存在,如果class不匹配直接异常
	 * <li>简单的方法是get(int key)(但不执行对象安全检查)
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T get(int key,Class<T> clazz){
		Object obj = map.get(key);
		if(obj==null) 
			return null;
		if(obj.getClass()!=clazz){
			throw new RuntimeException("The key to the mapping class cannot match ");
		}
		return (T)obj;
	}
	
	public static Object get(int key){
		return  map.get(key);
	}
	
	/**清空缓存*/
	public static void clear(){
		map.clear();
	}

}
