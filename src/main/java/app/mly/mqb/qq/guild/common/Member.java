package app.mly.mqb.qq.guild.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 频道成员信息
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 6:17 PM
 */
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 加入时间
     */
    private String joined_at;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 身份组
     */
    private List<String> roles;
}