package com.sdkkit.gameplatform.statistic.io.core;

import com.sdkkit.gameplatform.statistic.engine.Task;


/*******************************************************************************
 * IO 通信协议处理任务的公�?���? * 
 * @author <xingyong>xingyong@cy2009.com
 ******************************************************************************/
public interface IOPrototolHandler {
    /**
     * 处理IO请求
     *
     * @param task
     * @return 响应
     */
    public IOResponse handleRequest(Task task);

    /**
     * 处理IO响应
     *
     * @param response
     * @return 成功与否
     */
    public boolean handleResponse(IOResponse response);
}
