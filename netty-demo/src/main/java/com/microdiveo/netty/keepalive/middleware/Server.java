package com.microdiveo.netty.keepalive.middleware;

import com.microdiveo.netty.keepalive.encode.KyroMsgDecoder;
import com.microdiveo.netty.keepalive.encode.KyroMsgEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 服务端
 *
 * @author xiangwei
 * @date 2019-10-24 2:07 下午
 */
public class Server {
    private static final Integer PORT = 8081;

    public static void main(String[] args) {
        //2.定义工作组:boss分发请求给各个worker:boss负责监听端口请求，worker负责处理请求（读写）
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            //1.定义server启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //3.定义工作组
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //4.设置通道channel
                    .localAddress(PORT)
                    .childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            //此方法每次客户端连接都会调用，是为通道初始化的方法
                            //获得通道channel中的管道链（执行链、handler链）
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(10,0,0)); // 设置读 机制为 十秒读一次
                            //pipeline.addLast(new MsgPckDecode());
                            //pipeline.addLast(new MsgPckEncode());
                            pipeline.addLast(new KyroMsgDecoder());
                            pipeline.addLast(new KyroMsgEncoder());
                            pipeline.addLast(new Server3Handler());
                        }
                    });
            //6.设置参数
            //设置参数，TCP参数
            //连接缓冲池的大小
//            serverBootstrap.option(ChannelOption.SO_BACKLOG, 2048);
            //维持链接的活跃，清除死链接
//            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //关闭延迟发送
//            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            System.out.println("服务启动端口为： "+PORT+" --");
            //绑定ip和port
            ChannelFuture sync = serverBootstrap.bind().sync();
            //7.5.等待服务关闭，关闭后应该释放资源
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //优雅的关闭资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
