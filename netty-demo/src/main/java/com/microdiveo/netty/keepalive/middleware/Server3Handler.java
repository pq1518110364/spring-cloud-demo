package com.microdiveo.netty.keepalive.middleware;

import com.microdiveo.netty.keepalive.model.Model;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xiangwei
 * @date 2019-10-24 2:06 下午
 */
public class Server3Handler extends Middleware {

    public Server3Handler() {
        super("server");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg, Integer type) {
        // TODO Auto-generated method stub
        Model model = (Model) msg;
        System.out.println(model.toString());
        ctx.channel().writeAndFlush(model);
    }

    @Override
    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        // TODO Auto-generated method stub
        super.handlerReaderIdle(ctx);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(name + "  exception" + cause.toString());
    }

}
