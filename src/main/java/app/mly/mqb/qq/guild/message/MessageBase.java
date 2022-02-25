package app.mly.mqb.qq.guild.message;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息基类
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:21 PM
 */
@Data
public class MessageBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private String msg_id;

    /**
     * 转为json字符串
     *
     * @return
     */
    public String toJsonString() {
        return new JSONObject(this).toString();
    }
}