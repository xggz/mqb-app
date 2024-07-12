package app.mly.mqb.qq.guild.event;

import app.mly.mqb.qq.guild.common.Bot;
import app.mly.mqb.qq.guild.common.GroupMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * 群消息事件
 *
 * @author xggz
 * @since 2024/7/12
 */
@Data
public class GroupMessageEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机器人信息
     */
    private Bot bot;

    /**
     * 群消息
     */
    private GroupMessage groupMessage;
}