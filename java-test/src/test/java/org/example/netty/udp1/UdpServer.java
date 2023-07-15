package org.example.netty.udp1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

public class UdpServer extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof DatagramPacket) {
            /*DatagramPacket packet = (DatagramPacket) msg;
            ByteBuf buf = packet.content();
            String received = buf.toString(CharsetUtil.UTF_8);
            System.out.println("Received: " + received);
            buf.release();*/

            DatagramPacket packet = (DatagramPacket) msg;
            ByteBuf buf = packet.content();
            byte[] receivedData = new byte[13];
            buf.readBytes(receivedData);
            byte[] data = new byte[4];
            buf.readBytes(data);
            int value = buf.readInt();
            System.out.println("Received: " + new String(receivedData));
            System.out.println("Data: " + bytesToHex(data));
            System.out.println("Value: " + value);
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

}
