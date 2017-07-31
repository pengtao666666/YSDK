package com.sdkkit.gameplatform.statistic.io.core;

import com.sdkkit.gameplatform.statistic.io.IOTask;

/*******************************************************************************
 * 基本�? IO通用协议
 * 
 * @author <xingyong>xingyong@cy2009.com
 ******************************************************************************/
public abstract class IOProtocol {
    
    /** 协议类型 */
    private int mType;

    /** 本地获取*/
    public static final int RESOURCE   = 1;
    /** 网络获取 */
	public static final int HTTP       = 2;

    /** 协议初始�?*/ 
    public abstract boolean init() ;

    /**
     * �?��任务的有效�?
     */
    /*
     * 这里这个方法被子类HttpProtocol重写 本身这个方法并没有判断力
     * 为什么不写为抽象的方法让子类实现_lijianqiao
     */
    public abstract boolean validateRequest(IOTask task) ;

    /**
     * 处理IO请求
     * 
     * @param task
     * @return 响应
     */
    /*
     * 同上_lijianqiao
     */
    public abstract IOResponse handleRequest(IOTask task) ;

    /**
     * 处理IO响应
     * 
     * @param response
     * @return INVALID = -3,OK = 0,FAILED = -1;
     */
    /*
     * 同上_lijianqiao
     */
    public abstract int handleResponse(IOResponse response);

    /**
     * 处理IO响应
     *
     * @param response
     * @return 成功与否
     */
    /*
     * 同下_lijianqiao
     */
    public abstract void finishRequest(IOTask task);
    
    /**
     * 取消IO请求
     *
     * @param task
     * @return 响应
     */
    public   abstract void cancelRequest(IOTask task) ;
    
    /**
     * 重新尝试处理IO请求
     *
     * @param task
     * @return true - 成功重试; false - 重试失败
     */
    public boolean retryRequest(IOTask task) {
        return false;
    }

    /**
     * �?��处理相关资源回收
     *
     */
    /*
     * 此方法还没有实现意义  lijianqiao  
     */
    public boolean exit() {
        return true;
    }

    /**
     * 设置协议类型
     */
    public void setType(int type) {
        mType = type;
    }

    /**
     * 获取协议类型
     */
    public int getType() {
        return mType;
    }
}
