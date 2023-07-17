package org.example.netty.tcp1.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpNettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // Read the incoming message from the client
        String message = msg.toString(CharsetUtil.UTF_8);
        log.info("Read the incoming message from the client：{}", message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Handle any exceptions that occur during message processing
        cause.printStackTrace();
        ctx.close();
    }

}
