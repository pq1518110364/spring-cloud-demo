package com.microdiveo.netty.keepalive.model;

import org.msgpack.annotation.Message;

import java.io.Serializable;

@Message
public class Model implements Serializable {
    public Model() {}
    public Model(int type) {
        this.type = type;
    }

    private static final long serialVersionUID = 1L;

    //类型
    private int type;

    //内容
    private Object body;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Model{" +
                "type=" + type +
                ", body=" + body +
                '}';
    }
}
