package com.microdiveo.netty.keepalive.middleware;

import com.microdiveo.netty.keepalive.encode.KyroMsgDecoder;
import com.microdiveo.netty.keepalive.encode.KyroMsgEncoder;
import com.microdiveo.netty.keepalive.model.Model;
import com.microdiveo.netty.keepalive.model.TypeData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 客户端
 *
 * @author xiangwei
 * @date 2019-10-24 2:11 下午
 */
public class Client {
    private final String ADDRESS = "localhost";
    private final Integer PORT = 8081;

    private NioEventLoopGroup worker = new NioEventLoopGroup();

    private Channel channel;

    private Bootstrap bootstrap;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        System.out.println("客户端启动成功");

    }

    private void start() {
        //定义服务类
        bootstrap = new Bootstrap();
        //设置工作组
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)//设置通道
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // 添加Handler
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new IdleStateHandler(0, 0, 5));
                        // 设置客户端的机制为 5 秒触发一次ping

                        //pipeline.addLast(new MsgPckDecode()); // 编码器

                        //pipeline.addLast(new MsgPckEncode()); // 解码器
                        pipeline.addLast(new KyroMsgDecoder());
                        pipeline.addLast(new KyroMsgEncoder());

                        pipeline.addLast(new Client3Handler(Client.this));   // 当前客户端的控制器
                    }
                });
        doConnect();
    }

    /**
     * 连接服务端 and 重连
     */
    protected void doConnect() {

        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture connect = bootstrap.connect(ADDRESS, PORT);
        //实现监听通道连接的方法
        connect.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture channelFuture) throws Exception {

                if (channelFuture.isSuccess()) {
                    channel = channelFuture.channel();
                    System.out.println("连接成功");
                    sendData();
                } else {
                    System.out.println("每隔2s重连....");
                    channelFuture.channel().eventLoop().schedule(new Runnable() {

                        public void run() {
                            // TODO Auto-generated method stub
                            doConnect();
                        }
                    }, 2, TimeUnit.SECONDS);
                }
            }
        });
    }

    /**
     * 向服务端发送消息
     */
    private void sendData() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 1000; i++) {
            if (channel != null && channel.isActive()) {
                //获取一个键盘扫描器
                System.err.println("请输入要发送的消息");
                String nextLine = sc.nextLine();
                Model model = new Model();

                model.setType(TypeData.CONTENT);

                model.setBody(nextLine);

                channel.writeAndFlush(model);
            }
        }
    }
}
