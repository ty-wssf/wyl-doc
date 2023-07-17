package org.example.netty.udp1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

public class UdpServerRunner implements CommandLineRunner {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final UdpServerInitializer udpServerInitializer;

    public UdpServerRunner(UdpServerInitializer udpServerInitializer) {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.udpServerInitializer = udpServerInitializer;
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    bootstrap.group(group)
                            .channel(NioDatagramChannel.class)
                            .handler(udpServerInitializer);

                    Channel ch = bootstrap.bind(8081).sync().channel();
                    ch.closeFuture().await();
                } finally {
                    group.shutdownGracefully();
                }
            }
        }).start();

    }

}
