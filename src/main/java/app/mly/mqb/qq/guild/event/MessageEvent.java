package app.mly.mqb.qq.guild.event;

import app.mly.mqb.qq.guild.common.Bot;
import app.mly.mqb.qq.guild.common.Message;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息事件
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 10:14 PM
 */
@Data
public class MessageEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机器人信息
     */
    private Bot bot;

    /**
     * 频道名
     */
    private String guildName;

    /**
     * 消息
     */
    private Message message;
}