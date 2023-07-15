package org.example.netty.udp1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.DatagramChannel;

public class UdpServerInitializer extends ChannelInitializer<DatagramChannel> {

    private final UdpServer udpServer;

    public UdpServerInitializer(UdpServer udpServer) {
        this.udpServer = udpServer;
    }

    @Override
    protected void initChannel(DatagramChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(udpServer);
    }

}
