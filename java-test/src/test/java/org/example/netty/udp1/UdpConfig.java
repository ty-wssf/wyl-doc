package org.example.netty.udp1;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class UdpConfig {

    @Bean
    public UdpServer udpServer() {
        return new UdpServer();
    }

    @Bean
    public UdpServerInitializer udpServerInitializer(UdpServer udpServer) {
        return new UdpServerInitializer(udpServer);
    }

    @Bean
    public UdpServerRunner udpServerRunner(UdpServerInitializer udpServerInitializer) {
        return new UdpServerRunner(udpServerInitializer);
    }

}
