package org.example.netty.tcp1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.example.netty.tcp1.client.TcpNettyClientHandler;
import org.example.netty.tcp1.server.TcpServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TcpServerConfig.class)
public class TcpServerTest {

    @Test
    public void sendSimpleDataToTcpServerTest() throws Exception {
        ChannelHandlerContext ctx;
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new TcpNettyClientHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect("localhost", Integer.parseInt("8081")).sync();
        ctx = future.channel().pipeline().lastContext();

        for (int i = 0; i < 10; i++) {
            String message = "Hello, world!";
            ByteBuf buf = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
            // Add a delimiter to the end of each message to separate them
            ByteBuf delimiter = Unpooled.copiedBuffer("\n", CharsetUtil.UTF_8);
            ctx.write(buf);
            ctx.write(delimiter);
        }
        ctx.flush();

        ctx.disconnect();
        ctx.close();
    }

}
