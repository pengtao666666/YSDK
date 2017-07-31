package com.sdkkit.gameplatform.statistic.engine;

/**
 * 操作定义类
 * 
 * @author <zhaoyabin>zhaoyabin@cy2009.com
 */
public interface IEvent {

    public static final int OP_COMPLETE = 0;//操作完成
    public static final int OP_NONE = 999; // 无操作

    public static final int IO = 1;
    public static final int PARSER = 2;
}
