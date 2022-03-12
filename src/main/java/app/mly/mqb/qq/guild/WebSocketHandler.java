package app.mly.mqb.qq.guild;

import app.mly.mqb.qq.guild.common.*;
import app.mly.mqb.qq.guild.enums.ActionType;
import app.mly.mqb.qq.guild.enums.Opcode;
import app.mly.mqb.qq.guild.event.ActionEvent;
import app.mly.mqb.qq.guild.event.MessageEvent;
import app.mly.mqb.qq.guild.properties.GuildProperties;
import app.mly.mqb.qq.guild.request.GuildOpenApi;
import app.mly.mqb.qq.guild.util.BotUtil;
import cn.hutool.json.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 频道websocket处理
 *
 * @author xggz <yyimba@qq.com>
 * @since 2022/2/25 18:58
 */
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private GuildProperties guildProperties;

    @Autowired
    private GuildOpenApi guildRestApi;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private WebSocketSession session;

    private Bot bot = Bot.builder()
            .isStop(false)
            .seq(0)
            .build();

    private Map<String, String> guildsMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("连接成功");
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("收到Socket消息：" + message.getPayload());
        this.session = session;

        // 转为Bean对象
        Payload payload = new JSONObject(message.getPayload()).toBean(Payload.class);

        // 操作代号
        Opcode opcode = Opcode.getByCode(payload.getOp());
        switch (opcode) {
            case HELLO:
                helloOperateHandler(payload);
                break;
            case DISPATCH:
                dispatchOperateHandler(payload);
                break;
            case HEARTBEAT_ACK:
                heartbeatAckOperateHandler();
                break;
            case RECONNECT:
                reconnectionOperateHandler();
                break;
        }
    }

    /**
     * 初始化数据以及鉴权
     *
     * @param payload
     */
    private void helloOperateHandler(Payload payload) {
        // 初始化心跳周期
        initHeartbeatInterval(payload);
        // 初始化频道列表
        initGuildList();
        // 发送Socket授权信息
        sendSocketAuthInfo();
    }

    /**
     * 事件调度
     *
     * @param payload
     */
    private void dispatchOperateHandler(Payload payload) {
        Integer seq = payload.getS();
        if (seq != null && seq > 0) {
            this.bot.setSeq(seq);
        }

        String event = payload.getT();
        if (event.equals("READY")) {
            JSONObject data = payload.getD();
            JSONObject user = data.getJSONObject("user");

            this.bot.setBotId(user.getStr("id"));
            this.bot.setBotName(user.getStr("username"));
            this.bot.setSessionId(user.getStr("session_id"));

            if (!this.bot.getIsStop()) {
                handlerHeartbeatInterval();
            }
            this.bot.setIsStop(false);
        } else if (event.equals("AT_MESSAGE_CREATE")) {
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setBot(this.bot);
            messageEvent.setGuildName(guildsMap.get(""));
            messageEvent.setMessage(payload.getD().toBean(Message.class));
            eventPublisher.publishEvent(messageEvent);
        } else if (event.equals("GUILD_CREATE")) {
            guildsMap.put(payload.getD().getStr("id"), payload.getD().getStr("name"));
        }
    }

    /**
     * 接收到服务端心跳回复
     */
    private void heartbeatAckOperateHandler() {
        log.info("接收到服务端心跳回复");
    }

    /**
     * 服务端通知客户端重连
     */
    private void reconnectionOperateHandler() {
        log.info("服务端通知客户端重连");
        this.bot.setIsStop(true);
        eventPublisher.publishEvent(ActionEvent.builder()
                .actionType(ActionType.RECONNECTION)
                .build());
    }

    /**
     * 处理周期心跳
     */
    private void handlerHeartbeatInterval() {
        Thread heartbeatThread = new Thread(() -> {
            while (true) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("op", 1);
                jsonObject.set("d", this.bot.getSeq());

                try {
                    Thread.sleep(this.bot.getHeartbeatInterval());
                    if (!this.bot.getIsStop()) {
                        this.session.sendMessage(new TextMessage(jsonObject.toString()));
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
        heartbeatThread.setDaemon(true);
        heartbeatThread.setName("HeartbeatThread");
        heartbeatThread.start();
    }

    /**
     * 发送socket授权信息
     */
    @SneakyThrows
    private void sendSocketAuthInfo() {
        JSONObject authData = new JSONObject();
        authData.set("token", BotUtil.buildAuthToken(guildProperties.getBotAppId(), guildProperties.getBotToken()));
        authData.set("intents", BotUtil.computeIntents(guildProperties.getIntents()));
        authData.set("shard", guildProperties.getShard());

        Payload payload = Payload.builder()
                .op(Opcode.IDENTIFY.getCode())
                .d(authData)
                .build();

        this.session.sendMessage(new TextMessage(payload.toJsonString()));
    }

    /**
     * 初始化频道列表
     */
    private void initGuildList() {
        guildsMap.putAll(guildRestApi.getGuildList());
    }

    /**
     * 初始化心跳周期
     *
     * @param payload
     */
    private void initHeartbeatInterval(Payload payload) {
        Long interval = payload.getD().getLong("heartbeat_interval");
        log.info("heartbeat_time:" + interval);
        long heartbeatInterval = new BigDecimal(interval)
                .divide(new BigDecimal(2), 0, RoundingMode.HALF_DOWN)
                .longValue();
        log.info("heartbeat_interval:" + heartbeatInterval);
        this.bot.setHeartbeatInterval(heartbeatInterval);
    }
}