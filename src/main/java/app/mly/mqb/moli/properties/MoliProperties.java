package app.mly.mqb.moli.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 茉莉配置参数
 *
 * @author xggz <yyimba@qq.com>
 * @since 2022/2/25 16:48
 */
@Data
@Component
@ConfigurationProperties(prefix = "moli")
public class MoliProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开放接口地址
     */
    private String baseUrl;

    /**
     * 机器人Api-Key
     */
    private String apiKey;

    /**
     * 机器人Api-Secret
     */
    private String apiSecret;

    /**
     * 资源地址
     */
    private String fileUrl;
}