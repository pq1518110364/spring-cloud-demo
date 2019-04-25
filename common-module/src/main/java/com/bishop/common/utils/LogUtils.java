package com.bishop.common.utils;

import com.bishop.common.constant.LogEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: bishop
 * Create by Poseidon on 2019-04-21
 */
@SuppressWarnings("unused")
public class LogUtils {

    private LogUtils() {
    }

    private static class LogUtilsInstant{
        private static final LogUtils LOG_UTILS = new LogUtils();
    }

    public static LogUtils getInstance(){
        return LogUtilsInstant.LOG_UTILS;
}

    /**
     * 获取业务日志logger
     */
    public  Logger getBussinessLogger() {
        return LoggerFactory.getLogger(LogEnum.BUSSINESS.getCategory());
    }

    /**
     * 获取平台日志logger
     */
    public static Logger getPlatformLogger() {
        return LoggerFactory.getLogger(LogEnum.PLATFORM.getCategory());
    }

    /**
     * 获取数据库日志logger
     */
    public static Logger getDBLogger() {
        return LoggerFactory.getLogger(LogEnum.DB.getCategory());
    }


    /**
     * 获取异常日志logger
     */
    public static Logger getExceptionLogger() {
        return LoggerFactory.getLogger(LogEnum.EXCEPTION.getCategory());
    }

}
