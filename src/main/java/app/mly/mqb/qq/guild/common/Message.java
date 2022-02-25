package app.mly.mqb.qq.guild.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 频道原始消息包
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 6:16 PM
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private String id;

    /**
     * 子频道ID
     */
    private String channel_id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 频道ID
     */
    private String guild_id;

    /**
     * 发送时间
     */
    private String timestamp;

    /**
     * 消息发送者用户信息
     */
    private User author;

    /**
     * 消息发送者频道信息
     */
    private Member member;
}