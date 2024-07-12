package app.mly.mqb.qq.guild.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 群用户
 *
 * @author xggz
 * @since 2024/7/12
 */
@Data
public class GroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String member_openid;
}
