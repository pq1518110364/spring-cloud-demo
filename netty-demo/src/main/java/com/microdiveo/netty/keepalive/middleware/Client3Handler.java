package com.microdiveo.netty.keepalive.middleware;

import com.microdiveo.netty.keepalive.model.Model;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiangwei
 * @date 2019-10-24 2:10 下午
 */
public class Client3Handler extends Middleware {
    private Client client;

    public Client3Handler(Client client) {
        super("client");
        this.client = client;
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg,Integer type) {
        // TODO Auto-generated method stub
        Model model = (Model) msg;
        System.out.println("client  收到数据： " + model.toString());
    }
    /**
     * 触发时间的时候第一时间发送一个ping  让ping 机制开始执行
     */
    @Override
    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        // TODO Auto-generated method stub
        super.handlerAllIdle(ctx);
        sendPingMsg(ctx);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelInactive(ctx);
        client.doConnect(); // 断开重连
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(name + "exception :"+ cause.toString());
    }

}
