package com.bishop.common.constant;

/**
 * 本地日志枚举
 *
 * @Author: bishop
 * Create by Poseidon on 2019-04-21
 */
@SuppressWarnings("unused")
public enum LogEnum {
    /** 业务*/
    BUSSINESS("bussiness"),
    /** 平台*/
    PLATFORM("platform"),
    /** db*/
    DB("db"),
    /**异常*/
    EXCEPTION("exception"),

    ;


    private String category;


    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
