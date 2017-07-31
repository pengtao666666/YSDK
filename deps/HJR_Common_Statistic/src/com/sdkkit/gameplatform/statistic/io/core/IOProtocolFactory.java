package com.sdkkit.gameplatform.statistic.io.core;

import com.sdkkit.gameplatform.statistic.io.HttpProtocol;
import com.sdkkit.gameplatform.statistic.util.C;

/*******************************************************************************
 * IO协议的工厂类
 * 
 * @author <xingyong>xingyong@cy2009.com
 ******************************************************************************/
public class IOProtocolFactory {
    
    /** 标识工厂已被初始化 */
    private static boolean initialized = false;
    private static final Object initLock = new Object();

    /** 支持的协议 */
    private static final String HTTP = C.HTTP;
    private static final String RESOURCE = C.RESOURCE;

    /** 支持的协议句柄 */
    private static IOProtocol http = null;
    private static IOProtocol resource = null;

    
    
    /**
     * 根据协议返回HttpProtocol
     * @param URI 根据协议返回值
     * @return IOProtocol 实际为HttpProtocol
     */
    public static IOProtocol getHandler(final String URI) {
        if (URI == null) return null;

        // 强制同步初始化过程
        if (!initialized || notValida()) { // 非常规代码, 它假设initialized只在此处被改变
            synchronized (initLock) {
                if (!initialized || notValida()) {
                    init();
                    initialized = true;
                }
            }
        }

        // 寻找对应协议
        String uri = URI.toLowerCase();
        if (uri.startsWith(HTTP)) {
        	//实际就是一个new的HttpProtocol(); _lijianqiao
        	System.out.println("getHandler(final String URI)"+http);
            return http;
        } else if (uri.startsWith(RESOURCE)) {
        	//这是在坑爹吗  返回一个貌似有值的空值  _lijianqiao
            return resource;
        } else {
            return null;
        }
    }

    private static boolean notValida() {
        return http == null;
    }

    /**
     * 工厂初始化
     */
    private static void init() {
        try {
            http = new HttpProtocol();
            if (!http.init()) http = null;
        } catch(Exception e1) {
            http = null;
//            if (L.ERROR) L.e("IOProtocolFactory", "Failed to init HttpProtocol", e1);
        }

    }

    public static void exit() {
        if (http != null) {
            http.exit();
            http = null;
        }
        initialized = false;
    }
}
