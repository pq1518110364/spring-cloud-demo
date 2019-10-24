package com.microdiveo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class TimeClient {

    /**
     * 使用 3 个线程模拟三个客户端
     *
     * @param args
     */
    private Channel channel;

    private Bootstrap bootstrap;

    private final String ADDRESS = "localhost";
    private final Integer PORT = 8081;

    private NioEventLoopGroup worker = new NioEventLoopGroup();

    public static void main(String[] args) {
        TimeClient client = new TimeClient();
        client.start();
        System.out.println("客户端启动成功");
        client.sendData();
    }

    private void start() {
        bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // TODO Auto-generated method stub
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new IdleStateHandler(0,0,5)); // 设置客户端的机制为 5 秒触发一次ping

                        //pipeline.addLast(new MsgPckDecode()); // 编码器

                        //pipeline.addLast(new MsgPckEncode()); // 解码器
                        // 当前客户端的控制器
                        ch.pipeline().addLast(new TimeClientHandler());
                    }
                });
        doConnect();
    }
    /**
     * 连接服务端 and 重连
     */
    protected void doConnect() {

        if (channel != null && channel.isActive()){
            return;
        }
        ChannelFuture connect = bootstrap.connect(ADDRESS, PORT);
        //实现监听通道连接的方法
        connect.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture channelFuture) throws Exception {

                if(channelFuture.isSuccess()){
                    channel = channelFuture.channel();
                    System.out.println("连接成功");
                }else{
                    System.out.println("每隔2s重连....");
                    channelFuture.channel().eventLoop().schedule(new Runnable() {

                        public void run() {
                            // TODO Auto-generated method stub
                            doConnect();
                        }
                    },2, TimeUnit.SECONDS);
                }
            }
        });
    }
    /**
     * 向服务端发送消息
     */
    private void sendData() {
        for (int i = 0; i < 1000; i++) {
            if(channel != null && channel.isActive()){
                channel.writeAndFlush("TEST"+i);
            }
        }
    }


}
