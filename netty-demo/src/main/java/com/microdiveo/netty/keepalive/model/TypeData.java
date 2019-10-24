package com.microdiveo.netty.keepalive.model;
/***
 * 消息类型枚举
 */
public interface TypeData {

    byte PING = 5;

    byte PONG = 6;

    byte CONTENT = 1; // 消息
}
