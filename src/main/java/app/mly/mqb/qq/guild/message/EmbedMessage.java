package app.mly.mqb.qq.guild.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Embed模板消息
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:59 PM
 */
@Data
public class EmbedMessage extends MessageBase {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息弹窗内容
     */
    private String prompt;

    /**
     * 缩略图
     */
    private Thumbnail thumbnail;

    /**
     * Embed字段数据
     */
    private List<Field> fields;

    @Builder
    public EmbedMessage(String title, String prompt, Thumbnail thumbnail, List<Field> fields) {
        this.title = title;
        this.prompt = prompt;
        this.thumbnail = thumbnail;
        this.fields = fields;
    }

    public static List<Field> buildFields(String ... names) {
        return Arrays.stream(names).map(f -> new Field(f)).collect(Collectors.toList());
    }

    @Data
    public static class Thumbnail implements Serializable {

        private static final long serialVersionUID = 1L;

        private String url;

        public Thumbnail(String url) {
            this.url = url;
        }
    }

    @Data
    public static class Field implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        public Field(String name) {
            this.name = name;
        }
    }
}