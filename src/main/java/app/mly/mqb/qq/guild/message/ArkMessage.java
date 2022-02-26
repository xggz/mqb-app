package app.mly.mqb.qq.guild.message;

import cn.hutool.core.collection.CollUtil;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Ark模板消息
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:58 PM
 */
@Data
public class ArkMessage extends MessageBase {

    private static final long serialVersionUID = 1L;

    /**
     * Ark消息体
     */
    private Ark ark;

    @Builder
    public ArkMessage(String msg_id, Ark ark) {
        super.setMsg_id(msg_id);
        this.ark = ark;
    }

    public static FieldObject buildFieldObject(String content) {
        List<ArkMessage.FieldString> fieldStringList = CollUtil.newArrayList(new ArkMessage.FieldString("desc", content));
        ArkMessage.FieldObject fieldObject = new ArkMessage.FieldObject(fieldStringList);
        return fieldObject;
    }

    @Data
    public static class Ark implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 模板ID
         */
        private Integer template_id;

        /**
         * 键值对
         */
        private List<Field> kv;

        @Builder
        public Ark(Integer template_id, List<Field> kv) {
            this.template_id = template_id;
            this.kv = kv;
        }
    }

    @Data
    public static class Field implements Serializable {

        private static final long serialVersionUID = 1L;

        private String key;

        public Field(String key) {
            this.key = key;
        }
    }

    @Data
    public static class FieldObject implements Serializable {

        private static final long serialVersionUID = 1L;

        private List<FieldString> obj_kv;

        public FieldObject(List<FieldString> obj_kv) {
            this.obj_kv = obj_kv;
        }
    }

    public static class FieldString extends Field {

        private static final long serialVersionUID = 1L;

        @Getter
        @Setter
        private String value;

        public FieldString(String key, String value) {
            super(key);
            this.value = value;
        }
    }

    public static class FieldList extends Field {

        private static final long serialVersionUID = 1L;

        @Getter
        @Setter
        private List<FieldObject> obj;

        public FieldList(String key, List<FieldObject> obj) {
            super(key);
            this.obj = obj;
        }
    }
}