package com.incarcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incarcloud.constants.Constant;
import com.incarcloud.handle.resp.IOcppRespMsgHandlerManager;
import com.incarcloud.websocket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.incarcloud")
@RequiredArgsConstructor
@Slf4j
public class ServerBoot implements CommandLineRunner {

    private final IOcppRespMsgHandlerManager respMsgHandlerManager;

    private final ObjectMapper objectMapper;

    @Value("${app.deviceId}")
    private String deviceId;

    @Value("${app.host}")
    private String host;

    @Value("${app.chargingVal}")
    private Integer chargingVal;

    private WebSocketConnectionManager manager = null;

    public static void main(String[] args) {
        // 关闭nacos日志
        SpringApplication.run(ServerBoot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {

            log.info("----------------------->");

            Constant.deviceId = deviceId;
            Constant.chargingVal = chargingVal;
            manager = webSocketConnectionManager();
            manager.start();

            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                try {
                    if (!Constant.runningFlag) {
                        log.info("====>重连");
                        manager.stop();
                        manager.start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }, 10, 30, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public WebSocketConnectionManager webSocketConnectionManager() {
        try {
            StandardWebSocketClient client = new StandardWebSocketClient();
            MyWebSocketHandler webSocketHandler = new MyWebSocketHandler(objectMapper);
            WebSocketConnectionManager manager = new WebSocketConnectionManager(client, webSocketHandler, "ws://" + host + "/ws/CentralSystemService/" + deviceId);
            manager.setAutoStartup(true);
            WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
            headers.add("Sec-WebSocket-Protocol", "ocpp1.6");
            manager.setHeaders(headers);

            return manager;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
