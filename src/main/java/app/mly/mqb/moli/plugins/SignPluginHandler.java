package app.mly.mqb.moli.plugins;

import app.mly.mqb.qq.guild.common.Message;
import app.mly.mqb.qq.guild.message.ArkMessage;
import app.mly.mqb.qq.guild.message.EmbedMessage;
import app.mly.mqb.qq.guild.request.GuildOpenApi;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理茉莉云签到插件JSON数据
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:06 PM
 */
@Component
public class SignPluginHandler implements PluginHandler {

    @Autowired
    private GuildOpenApi guildOpenApi;

    @Override
    public void reply(Message message, JSONObject replyObj) {
        if ("签到".equals(replyObj.getStr("trigger"))) {
            EmbedMessage.Embed embed = EmbedMessage.Embed.builder()
                    .prompt("签到提醒")
                    .thumbnail(new EmbedMessage.Thumbnail(message.getAuthor().getAvatar()))
                    .build();
            if (replyObj.getBool("success")) {
                embed.setTitle("签到成功");
                embed.setFields(EmbedMessage.buildFields("@" + message.getMember().getNick() + " 你今天第" + replyObj.getInt("top") + "个签到，明天继续呦！",
                        "🍀 获取经验：" + replyObj.getInt("exp"),
                        "💰 获取金币：" + replyObj.getInt("point"),
                        "💧 连续签到：" + replyObj.getInt("seriesDays") + "天",
                        "💦 累计签到：" + replyObj.getInt("totalDays") + "天"
                ));
            } else {
                embed.setTitle("签到失败");
                embed.setFields(EmbedMessage.buildFields("@" + message.getMember().getNick(), "今天已经签到过啦，明天再来吧！"));
            }
            guildOpenApi.sendMessage(message.getChannel_id(),
                    EmbedMessage.builder()
                            .msg_id(message.getId())
                            .embed(embed)
                            .build());
        } else if ("签到榜".equals(replyObj.getStr("trigger"))) {
            ArkMessage.Ark ark = ArkMessage.Ark.builder()
                    .template_id(23)
                    .build();

            List<ArkMessage.Field> fieldArray = new ArrayList<>();
            fieldArray.add(new ArkMessage.FieldString("#PROMPT#", "签到排行榜"));
            fieldArray.add(new ArkMessage.FieldString("#DESC#", "签到排行榜"));

            List<ArkMessage.FieldObject> fieldObjectList = new ArrayList<>();
            fieldObjectList.add(ArkMessage.buildFieldObject("签到排行榜（总签到次数）"));

            JSONArray rankList = replyObj.getJSONArray("rankList");
            for (int k = 0; k < rankList.size(); k++) {
                JSONObject rankObj = (JSONObject) rankList.get(k);
                fieldObjectList.add(ArkMessage.buildFieldObject((k+1) + ". " + rankObj.getStr("memberName") + "，总签到" + rankObj.getInt("totalDays") + "天，连续" + rankObj.getInt("seriesDays") + "天"));
            }

            ArkMessage.FieldList fieldList = new ArkMessage.FieldList("#LIST#", fieldObjectList);
            fieldArray.add(fieldList);

            ark.setKv(fieldArray);

            guildOpenApi.sendMessage(message.getChannel_id(),
                    ArkMessage.builder()
                            .msg_id(message.getId())
                            .ark(ark)
                            .build());
        }
    }
}