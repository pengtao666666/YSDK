package com.sdkkit.gameplatform.statistic.io.core;

import com.sdkkit.gameplatform.statistic.io.IOTask;

/*******************************************************************************
 * IO 通信协议处理任务的公�?���? * 
 * @author <xingyong>xingyong@cy2009.com
 ******************************************************************************/
public interface IOProtocolHandler {
//    /**
//     * 处理IO请求
//     *
//     * @param task
//     * @return 响应
//     */
//    public IOResponse handleRequest(Task task);
    
    /**
     * 处理IO请求
     *
     * @param task
     * @return 响应
     */
    public IOResponse handleRequest(IOTask task);
    

    /**
     * 处理IO响应
     *
     * @param response
     * @return int 成功失败代码
     */
    public int handleResponse(IOResponse response);
    
//    /**
//     * 处理IO响应
//     *
//     * @param response
//     * @return true false
//     */
//    public boolean handleResponse(IOResponse response);
}
