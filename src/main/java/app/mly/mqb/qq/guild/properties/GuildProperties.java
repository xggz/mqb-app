package app.mly.mqb.qq.guild.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 频道配置参数
 *
 * @author xggz <yyimba@qq.com>
 * @since 2022/2/25 18:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "qq-guild")
public class GuildProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 频道接口地址
     */
    private String baseUrl;

    /**
     * 机器人botAppId
     */
    private String botAppId;

    /**
     * 机器人botToken
     */
    private String botToken;

    /**
     * 分片数
     */
    private List<Integer> shard;

    /**
     * 订阅事件列表
     */
    private List<Integer> intents;
}