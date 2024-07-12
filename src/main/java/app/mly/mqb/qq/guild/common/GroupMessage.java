package app.mly.mqb.qq.guild.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 群消息
 *
 * @author xggz
 * @since 2024/7/12
 */
@Data
public class GroupMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private String id;

    /**
     * 群ID
     */
    private String group_id;

    /**
     * 群开放ID
     */
    private String group_openid;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String timestamp;

    /**
     * 消息发送者用户信息
     */
    private GroupUser author;

}