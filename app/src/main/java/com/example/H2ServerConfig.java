package com.example;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class H2ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers");
    }
}
