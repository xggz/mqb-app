package app.mly.mqb.qq.guild.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 机器人信息
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 10:01 PM
 */
@Data
public class Bot implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机器人ID
     */
    private String botId;

    /**
     * 机器人名字
     */
    private String botName;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * seq
     */
    private Integer seq;

    /**
     * 是否停止
     */
    private Boolean isStop;

    /**
     * 心跳周期
     */
    private Long heartbeatInterval;

    @Builder
    public Bot(String botId, String botName, String sessionId, Integer seq, Boolean isStop, Long heartbeatInterval) {
        this.botId = botId;
        this.botName = botName;
        this.sessionId = sessionId;
        this.seq = seq;
        this.isStop = isStop;
        this.heartbeatInterval = heartbeatInterval;
    }
}