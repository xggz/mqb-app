package app.mly.mqb.moli.plugins;

import app.mly.mqb.qq.guild.common.Message;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 茉莉云插件回复执行程序
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:07 PM
 */
@Component
public class PluginReplyExecutor {

    @Autowired
    private Map<String, PluginHandler> pluginHandlerMap;

    /**
     * 执行
     *
     * @param pluginCode
     * @param message
     * @param replyObj
     */
    public void execute(String pluginCode, Message message, JSONObject replyObj) {
        String ph = pluginCode.concat("PluginHandler");
        pluginHandlerMap.entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase(ph))
                .findFirst()
                .map(e -> e.getValue())
                .ifPresent(pluginHandler -> {
                    pluginHandler.reply(message, replyObj);
        });
    }
}