package app.mly.mqb.moli.plugins;

import app.mly.mqb.qq.guild.common.Message;
import cn.hutool.json.JSONObject;

/**
 * 茉莉插件处理
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:07 PM
 */
public interface PluginHandler {

    /**
     * 回复消息
     *
     * @param message
     * @param replyObj
     */
    void reply(Message message, JSONObject replyObj);
}