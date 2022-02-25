package app.mly.mqb.qq.guild;

import app.mly.mqb.qq.guild.request.GuildOpenApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * 频道websocket管理
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 4:08 PM
 */
@Slf4j
@Component
public class WebSocketStarter implements SmartInitializingSingleton {

    @Autowired
    private GuildOpenApi guildRestApi;

    @Autowired
    private WebSocketHandler webSocketHandler;

    private WebSocketConnectionManager webSocketConnectionManager;

    @Override
    public void afterSingletonsInstantiated() {
        log.info("init");
        this.initSocketConnection();
    }

    /**
     * 重连
     */
    public void reconnection() {
        log.info("reconnection");
        webSocketConnectionManager.stop();
        this.initSocketConnection();
    }

    /**
     * 初始化连接
     */
    private void initSocketConnection() {
        String socketUrl = guildRestApi.getSocketUrl();
        log.info("socket url:" + socketUrl);

        StandardWebSocketClient socketClient = new StandardWebSocketClient();
        webSocketConnectionManager = new WebSocketConnectionManager(socketClient, webSocketHandler, socketUrl);
        webSocketConnectionManager.start();
    }
}