package app.mly.mqb.qq.guild.listener;

import app.mly.mqb.moli.MoliOpenApi;
import app.mly.mqb.moli.common.MoliMsg;
import app.mly.mqb.moli.plugins.PluginReplyExecutor;
import app.mly.mqb.moli.properties.MoliProperties;
import app.mly.mqb.qq.guild.common.Message;
import app.mly.mqb.qq.guild.event.MessageEvent;
import app.mly.mqb.qq.guild.message.NormalMessage;
import app.mly.mqb.qq.guild.request.GuildOpenApi;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息事件监听
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 10:10 PM
 */
@Component
public class MessageEventListener {

    @Autowired
    private GuildOpenApi guildOpenApi;

    @Autowired
    private MoliOpenApi moliOpenApi;

    @Autowired
    private PluginReplyExecutor pluginReplyExecutor;

    @Autowired
    private MoliProperties moliProperties;

    @Async
    @EventListener(MessageEvent.class)
    public void handlerResult(MessageEvent messageEvent) {
        String content = String.valueOf(messageEvent.getMessage().getContent());

        String botCode = "<@!"+ messageEvent.getBot().getBotId() + ">\u00a0";
        String botCode2 = "<@!"+ messageEvent.getBot().getBotId() + "> ";

        // 判断是否有@机器人
        if (!StrUtil.contains(content, botCode) && !StrUtil.contains(content, botCode2)) {
            return;
        }

        content = content.replace(botCode, "");
        content = content.replace(botCode2, "");
        if (content.indexOf(" ") == 0) {
            content = content.substring(1);
        }
        if (content.indexOf("/") == 0) {
            content = content.substring(1);
        }

        MoliMsg mollyMsg = MoliMsg.builder()
                .from(messageEvent.getMessage().getAuthor().getId())
                .fromName(messageEvent.getMessage().getMember().getNick())
                .to(messageEvent.getMessage().getGuild_id())
                .toName(messageEvent.getGuildName())
                .content(content)
                .build();

        handlerMessage(messageEvent.getMessage(), mollyMsg);
    }

    @SneakyThrows
    private void handlerMessage(Message message, MoliMsg mollyMsg) {
        JSONObject mollyReply = moliOpenApi.moliReply(mollyMsg);
        if ("00000".equals(mollyReply.getStr("code"))) {
            String plugin = mollyReply.getStr("plugin");
            JSONArray dataArray = mollyReply.getJSONArray("data");
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject data = (JSONObject) dataArray.get(i);
                Integer typed = data.getInt("typed");
                if (typed == 1) {
                    guildOpenApi.sendMessage(message.getChannel_id(), NormalMessage.buildTextMessage(message.getId(), data.getStr("content")));
                } else if (typed == 2) {
                    guildOpenApi.sendMessage(message.getChannel_id(), NormalMessage.buildImageMessage(message.getId(), moliProperties.getFileUrl() + data.getStr("content")));
                } else if (typed == 8) {
                    String msg = data.getStr("content");
                    JSONObject replyObj = new JSONObject(msg);
                    pluginReplyExecutor.execute(plugin, message, replyObj);
                }
            }
        }
    }
}