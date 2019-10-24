package com.microdiveo.netty.keepalive.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 编码器
 * @author xiangwei
 * @date 2019-10-24 1:50 下午
 */
public class MsgPckEncode extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // TODO Auto-generated method stub
        MessagePack pack = new MessagePack();
        byte[] write = pack.write(msg);
        out.writeBytes(write);
    }
}
