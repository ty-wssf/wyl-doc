package org.example.netty.udp1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetSocketAddress;

@SpringBootTest(classes = UdpConfig.class)
public class UdpServerTest {

    @Test
    void randomPortTest() {
        System.out.println("123");
    }

    /**
     * 发送数据到 UDP 服务器测试
     * ch: 是一个 Channel 对象，表示与服务器之间的通信通道。
     * writeAndFlush(...): 是一个方法，用于将数据写入通道并刷新缓冲区，以便发送给服务器。
     * new DatagramPacket(...): 是一个 DatagramPacket 对象，用于封装要发送的数据和目标服务器的地址。
     * Unpooled.copiedBuffer(message, CharsetUtil.UTF_8): 是一个静态方法，用于创建一个包含指定消息的字节缓冲区。
     * message: 是要发送的消息内容。
     * CharsetUtil.UTF_8: 是指定消息的字符编码方式，这里使用的是 UTF-8 编码。
     * new InetSocketAddress("127.0.0.1", 8081): 是目标服务器的地址，这里使用的是本地地址（127.0.0.1）和端口号 8081。
     */
    @Test
    public void sendSimpleDataToUdpServerTest() throws Exception {
        // 创建一个新的 Bootstrap 实例
        Bootstrap bootstrap = new Bootstrap();
        // 创建一个新的 NioEventLoopGroup 实例
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 设置 Bootstrap 的 group 和 channel 类
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());

            // 将通道绑定到本地地址
            Channel ch = bootstrap.bind(0).sync().channel();

            // 向服务器发送一条消息
            String message = "Hello, world!";
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(message, CharsetUtil.UTF_8),
                    new InetSocketAddress("127.0.0.1", 8081))).sync();

            // 关闭通道
            ch.close();
        } finally {
            // 关闭事件循环组
            group.shutdownGracefully();
        }
    }

    @SneakyThrows
    @Test
    public void sendComplexDataToUdpServerTest() {
        // 创建一个新的 Bootstrap 实例
        Bootstrap bootstrap = new Bootstrap();
        // 创建一个新的 NioEventLoopGroup 实例
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 设置 Bootstrap 的 group 和 channel 类
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());

            // 将通道绑定到本地地址
            Channel ch = bootstrap.bind(0).sync().channel();

            // 向服务器发送一条消息
            String message = "Hello, world!";
            byte[] data = {0x01, 0x02, 0x03, 0x04};
            int value = 12345;
            sendComplexData(ch, message, data, value);

            // 关闭通道
            ch.close();
        } finally {
            // 关闭事件循环组
            group.shutdownGracefully();
        }
    }

    /**
     * 发送更加复杂的数据格式
     */
    private void sendComplexData(Channel ch, String message, byte[] data, int value) throws InterruptedException {
        // 创建一个 ByteBuf 对象，用于封装要发送的数据
        ByteBuf buf = Unpooled.buffer();
        // 将消息内容写入 ByteBuf 对象
        buf.writeBytes(message.getBytes(CharsetUtil.UTF_8));
        // 将字节数组写入 ByteBuf 对象
        buf.writeBytes(data);
        // 将整数写入 ByteBuf 对象
        buf.writeInt(value);
        // 创建一个 DatagramPacket 对象，用于封装要发送的数据和目标服务器的地址
        DatagramPacket packet = new DatagramPacket(buf, new InetSocketAddress("127.0.0.1", 8081));
        // 将数据写入通道并刷新缓冲区，以便发送给服务器
        ch.writeAndFlush(packet).sync();
    }


}
