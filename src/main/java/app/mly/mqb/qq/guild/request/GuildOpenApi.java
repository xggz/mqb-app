package app.mly.mqb.qq.guild.request;

import app.mly.mqb.qq.guild.message.MessageBase;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 频道开放接口
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/13 8:14 PM
 */
@Slf4j
@Component
public class GuildOpenApi {

    @Autowired
    private GuildRestTemplate guildRestTemplate;

    /**
     * 获取通用WSS接入点
     *
     * @return
     */
    public String getSocketUrl() {
        String result = guildRestTemplate.exchange("/gateway", HttpMethod.GET);
        if (StrUtil.isBlank(result)) {
            throw new RuntimeException("WebSocket地址获取失败");
        }
        return new JSONObject(result).getStr("url");
    }

    /**
     * 发送消息
     *
     * @param channelId
     * @param messageBase
     */
    public void sendMessage(String channelId, MessageBase messageBase) {
        guildRestTemplate.postForBodyJson("/channels/" + channelId + "/messages", messageBase.toJsonString());
    }

    /**
     * 获取频道列表
     *
     * @return
     */
    public Map<String, String> getGuildList() {
        String result = guildRestTemplate.exchange("/users/@me/guilds", HttpMethod.GET);
        if (StrUtil.isBlank(result)) {
            throw new RuntimeException("频道列表获取失败");
        }

        Map<String, String> guildsMap = new HashMap<>();
        JSONArray guildList = new JSONArray(result);
        guildList.stream().map(guild -> (JSONObject) guild).forEach(guild -> {
            guildsMap.put(guild.getStr("id"), guild.getStr("name"));
        });

        return guildsMap;
    }
}