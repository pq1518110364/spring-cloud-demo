package com.microdiveo.netty.keepalive.middleware;

import com.microdiveo.netty.keepalive.model.Model;
import com.microdiveo.netty.keepalive.model.TypeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 定义一个枢纽站，client 跟 server  公用的功能
 *
 * 服务父类
 * @author xiangwei
 * @date 2019-10-24 2:04 下午
 */

/*
 * ChannelInboundHandlerAdapter：ChannelInboundHandlerAdapter是ChannelInboundHandler的一个简单实现，默认情况下不会做任何处理，
 *   只是简单的将操作通过fire*方法传递到ChannelPipeline中的下一个ChannelHandler中让链中的下一个ChannelHandler去处理。
 *
 * SimpleChannelInboundHandler：SimpleChannelInboundHandler支持泛型的消息处理，默认情况下消息处理完将会被自动释放，无法提供
 *   fire*方法传递给ChannelPipeline中的下一个ChannelHandler,如果想要传递给下一个ChannelHandler需要调用ReferenceCountUtil#retain方法。
 * */
public abstract class Middleware extends ChannelInboundHandlerAdapter {
    protected String name;
    //记录次数
    private int heartbeatCount = 0;

    //获取server and client 传入的值
    public Middleware(String name) {
        this.name = name;
    }
    /**
     *继承ChannelInboundHandlerAdapter实现了channelRead就会监听到通道里面的消息
     *收到消息以后判断消息内容
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Model m = (Model) msg;
        int type = m.getType();
        switch (type) {
            case TypeData.PING:
                // 收到客户端的ping  消息，回复 pong 表示收到
                sendPongMsg(ctx);
                break;
            case TypeData.PONG:
                System.out.println(name + " 收到回复  pong  消息来自 " + ctx.channel().remoteAddress());
                break;
            default:
                handlerData(ctx,msg,type);
                break;
        }
    }
    /**
     * 收到消息回复第消息
     * @param ctx
     * @param msg
     */
    protected abstract void handlerData(ChannelHandlerContext ctx,Object msg,Integer type);
    /**
     * 客户端发送ping 消息使用
     * @param ctx
     */
    protected void sendPingMsg(ChannelHandlerContext ctx){
        Model model = new Model();

        model.setType(TypeData.PING);

        ctx.channel().writeAndFlush(model);

        heartbeatCount++;

        System.out.println(name + " 发送 ping 消息 to " + ctx.channel().remoteAddress() + "count :" + heartbeatCount);
    }
    /**
     * 服务端  回复 pong 消息使用
     * @param ctx
     */
    private void sendPongMsg(ChannelHandlerContext ctx) {
        ConnectHandler.sendPongMsg(ctx, name);
    }

    /**
     * 用户心跳，客户端
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        IdleStateEvent stateEvent = (IdleStateEvent) evt;

        switch (stateEvent.state()) {
            case READER_IDLE:
                handlerReaderIdle(ctx);
                break;
            case WRITER_IDLE:
                handlerWriterIdle(ctx);
                break;
            case ALL_IDLE:
                handlerAllIdle(ctx);
                break;
            default:
                break;
        }
    }

    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---其他消息---");
    }

    protected void handlerWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---发送消息---");
    }


    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        ConnectHandler.handlerReaderIdle(ctx);
    }
    /**
     *  有连接进入的时候触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ConnectHandler.channelActive(ctx);
    }
    /**
     * 有连接退出的时候触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }
}
