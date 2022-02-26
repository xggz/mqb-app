package app.mly.mqb.qq.guild.message;

import lombok.Builder;
import lombok.Data;

/**
 * 常规消息
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 9:02 PM
 */
@Data
public class NormalMessage extends MessageBase {

    private static final long serialVersionUID = 1L;

    /**
     * 消息文本内容
     */
    private String content;

    /**
     * 消息图片地址
     */
    private String image;

    @Builder
    public NormalMessage(String msg_id, String content, String image) {
        super.setMsg_id(msg_id);
        this.content = content;
        this.image = image;
    }

    public static NormalMessage buildTextMessage(String msg_id, String content) {
        return NormalMessage.builder()
                .msg_id(msg_id)
                .content(content)
                .build();
    }

    public static NormalMessage buildImageMessage(String msg_id, String image) {
        return NormalMessage.builder()
                .msg_id(msg_id)
                .image(image)
                .build();
    }
}