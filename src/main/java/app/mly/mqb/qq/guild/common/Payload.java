package app.mly.mqb.qq.guild.common;

import cn.hutool.json.JSONObject;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据包
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 4:47 PM
 */
@Data
@Builder
public class Payload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作码opcode
     */
    private Integer op;

    /**
     * 序列号
     */
    private Integer s;

    /**
     * 事件类型
     */
    private String t;

    /**
     * 事件内容
     */
    private JSONObject d;

    /**
     * 对象信息转为Json字符串
     *
     * @return
     */
    public String toJsonString() {
        return new JSONObject(this).toString();
    }
}