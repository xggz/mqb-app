package app.mly.mqb.moli.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 茉莉消息发送包
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 6:25 PM
 */
@Data
@Builder
public class MoliMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息发送者标识
     */
    private String from;

    /**
     * 消息发送者昵称
     */
    private String fromName;

    /**
     * 消息接收者标识
     */
    private String to;

    /**
     * 消息接收者名称
     */
    private String toName;

    /**
     * 消息内容
     */
    private String content;
}