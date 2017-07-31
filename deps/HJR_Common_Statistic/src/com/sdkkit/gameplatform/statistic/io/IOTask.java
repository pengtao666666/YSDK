package com.sdkkit.gameplatform.statistic.io;

import com.sdkkit.gameplatform.statistic.engine.Task;
import com.sdkkit.gameplatform.statistic.io.core.IOProtocol;
import com.sdkkit.gameplatform.statistic.io.core.IOResponse;

/*******************************************************************************
 * IO任务相关的特殊属�?
 * 
 * @author <xingyong>xingyong@cy2009.com
 * @version 2012-5-16 下午9:40:53
 ******************************************************************************/
public class IOTask extends Task {
	
    /** 任务的协议句�? */
    protected IOProtocol mProtocol;
    /** 任务处理过程中的响应 */
    protected IOResponse mResponse;
    /** 服务器告知的文件总长�?*/
    private int mContentLength;
    /** 已经下载了的数据长度 */
    private int mDataCurrentSize;
    /** 记录真实的任务信�?*/
    private Task mTask;

    public void reset() {
        mContentLength = 0;
        mDataCurrentSize = 0;
        mProtocol = null;
        
        super.reset();
    }
    
    public IOTask(Task task) {
        super(task);
        mTask = task;
    }

    public void setProtocol(IOProtocol protocol) {
        mProtocol = protocol;
    }
    
    public IOProtocol getProtocol() {
        return mProtocol;
    }

    public int getContentLength() {
        return mContentLength;
    }

    public void setContentLength(int dataLength) {
        mContentLength = dataLength;
    }

    public int getCurDataSize() {
        return mDataCurrentSize;
    }

    public void setCurDataSize(int size) {
        mDataCurrentSize = size;
    }

    public void setResponse(IOResponse response) {
        mResponse = response;
    }

    public IOResponse getResponse() {
        return mResponse;
    }

    /**========== mTask相关接口,根据状�?将mTask的信息拷贝到IOTask�?=========*/
    /**但是只拷贝mTask的state信息 并且没有考虑STATE_INPROGRESS  待�?�? _lijianqiao   */
    /**这里为什么把mTask STATE_INPROGRESS的信息拷�?�� 直接掉不就行�? _lijianqiao  */
    public void complete() {
        if (mTask != null) {
            if (mTask.isCanceled()) {
                super.cancel();
            } else if (mTask.isFailed()) {
                super.fail();
            } else {
                super.complete();
            }            
        } else {
            super.complete();
        }
    }

    public void fail(){
        super.fail();
        if (mTask != null) {
            mTask.fail();
        }
    }

    public void cancel(){
        super.cancel();
        if (mTask != null) {
            mTask.cancel();
        }
    }
}
