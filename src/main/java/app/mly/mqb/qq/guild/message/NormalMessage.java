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
    public NormalMessage(String content, String image) {
        this.content = content;
        this.image = image;
    }

    public static NormalMessage buildTextMessage(String content) {
        return NormalMessage.builder().content(content).build();
    }

    public static NormalMessage buildImageMessage(String image) {
        return NormalMessage.builder().image(image).build();
    }
}