package app.mly.mqb.qq.guild.util;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * 机器人工具类
 *
 * @author xggz <yyimba@qq.com>
 * @since 2022/2/25 13:57
 */
@UtilityClass
public class BotUtil {

    /**
     * 构建授权Token
     *
     * @param botId
     * @param token
     * @return
     */
    public String buildAuthToken(String botId, String token) {
        return "Bot ".concat(botId).concat(".").concat(token);
    }

    /**
     * 计算事件订阅列表的值
     *
     * @param intents
     * @return
     */
    public int computeIntents(List<Integer> intents) {
        int intentsValue = 0;
        for (int i = 0; i < intents.size(); i++) {
            intentsValue = intentsValue|1<<intents.get(i);
        }
        return intentsValue;
    }
}