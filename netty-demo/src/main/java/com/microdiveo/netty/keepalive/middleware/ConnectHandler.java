package com.microdiveo.netty.keepalive.middleware;

import com.microdiveo.netty.keepalive.model.Model;
import com.microdiveo.netty.keepalive.model.TypeData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存连接的类
 * @author xiangwei
 * @date 2019-10-24 2:08 下午
 */
public class ConnectHandler {
    private static final Integer errorCount = 5; // 允许超时的次数

    // 存放当前的连接
    private static Map<Integer, Channel> channels = new ConcurrentHashMap<Integer, Channel>();
    // 消息超时的时候记录  超过一定次数就断开客户端
    private static Map<Integer,Integer> errors = new ConcurrentHashMap<Integer,Integer>();

    /**
     * 有连接进入的时候调用
     * @param ctx
     * @throws Exception
     */
    public static void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(" ---"+ctx.channel().remoteAddress() +"----连接进入" );
        ConnectHandler.putChannel(ctx);
    }

    public static void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        System.err.println(" ---"+ctx.channel().remoteAddress() +"----连接退出");
        ConnectHandler.removeChannel(ctx.hashCode());
    }

    /**
     * 移除连接
     */
    private static void removeChannel(int hashCode) {
        channels.remove(hashCode);
    }

    /**
     * 消息超时，认为已经断开  ,关闭连接
     * @param ctx
     */
    public static void handlerReaderIdle(ChannelHandlerContext ctx) {
        System.out.println("一次收不到消息");
        if(ConnectHandler.addErrors(ctx.hashCode()) >= errorCount) {
            ctx.close();
            ConnectHandler.removeChannel(ctx.hashCode());
            System.err.println(" ---- client "+ ctx.channel().remoteAddress().toString() + " reader timeOut, --- close it");
        }
    }
    /**
     * 异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    public static void exceptionCaught(ChannelHandlerContext ctx, Throwable cause,String name)
            throws Exception {
        System.out.println(name + "exception :"+ cause.toString());
    }
    /**
     * 回复pong  内容
     * @param ctx
     */
    public static void sendPongMsg(ChannelHandlerContext ctx,String name) {

        Model model = new Model();

        model.setType(TypeData.PONG);

        ctx.channel().writeAndFlush(model);

        // 收到ping 消息  回复的时候清空 超时次数
        ConnectHandler.restErroes(ctx.hashCode());

        System.out.println(name +" 发送 pong 消息 to "+ctx.channel().remoteAddress() /*+" , count :" + heartbeatCount*/);
    }

    /**
     * 连接成功，重置错误次数
     * @param hashCode
     */
    private static void restErroes(Integer hashCode) {
        errors.put(hashCode, 0);
    }
    /**
     * 超时，计数加一
     * @param hashCode
     */
    private static Integer addErrors(Integer hashCode) {
        Integer hearCount;
        if(errors.containsKey(hashCode)) {
            hearCount = errors.get(hashCode);
            hearCount++;
        }else {
            hearCount = 1;
        }
        errors.put(hashCode,hearCount);
        return hearCount;
    }
    // 判断 连接是否在线
    public static boolean containsKey(Integer hashCode) {
        return channels.containsKey(hashCode);
    }

    /**
     * 添加连接
     */
    private static synchronized void putChannel(ChannelHandlerContext ctx) {
        Integer hashCode = ctx.hashCode();
        // 存入连接
        channels.put(hashCode, ctx.channel());
        // 存放ping 的次数
        errors.put(hashCode, 0);
    }

}
