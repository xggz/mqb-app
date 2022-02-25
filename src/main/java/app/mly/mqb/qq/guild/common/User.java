package app.mly.mqb.qq.guild.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 6:17 PM
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否为机器人
     */
    private Boolean bot;

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;
}