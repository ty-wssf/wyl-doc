package org.example.netty.tcp1.server;

import org.springframework.context.annotation.Bean;

public class TcpServerConfig {

    @Bean
    public TcpServer tcpServer() {
        return new TcpServer();
    }

}
