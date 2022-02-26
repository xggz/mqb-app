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
 * 处理茉莉云用户中心插件JSON数据
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:06 PM
 */
@Component
public class UCenterPluginHandler implements PluginHandler {

    @Autowired
    private GuildOpenApi guildOpenApi;

    @Override
    public void reply(Message message, JSONObject replyObj) {
        if ("个人中心".equals(replyObj.getStr("trigger"))) {
            EmbedMessage.Embed embed = EmbedMessage.Embed.builder()
                    .prompt("个人中心")
                    .title("个人中心")
                    .thumbnail(new EmbedMessage.Thumbnail(message.getAuthor().getAvatar()))
                    .fields(EmbedMessage.buildFields("@" + message.getMember().getNick(),
                            "🌟 等级：" + replyObj.getInt("level"),
                            "👑 级别：" + replyObj.getStr("rankName"),
                            "🍀 经验：" + replyObj.getInt("exp"),
                            "💰 金币：" + replyObj.getInt("point")))
                    .build();
            guildOpenApi.sendMessage(message.getChannel_id(),
                    EmbedMessage.builder()
                            .msg_id(message.getId())
                            .embed(embed)
                            .build());
        } else if ("财富榜".equals(replyObj.getStr("trigger"))) {
            ArkMessage.Ark ark = ArkMessage.Ark.builder()
                    .template_id(23)
                    .build();

            List<ArkMessage.Field> fieldArray = new ArrayList<>();
            fieldArray.add(new ArkMessage.FieldString("#PROMPT#", "财富排行榜"));
            fieldArray.add(new ArkMessage.FieldString("#DESC#", "财富排行榜"));

            List<ArkMessage.FieldObject> fieldObjectList = new ArrayList<>();
            fieldObjectList.add(ArkMessage.buildFieldObject("财富排行榜（金币）"));

            JSONArray rankList = replyObj.getJSONArray("rankList");
            for (int k = 0; k < rankList.size(); k++) {
                JSONObject rankObj = (JSONObject) rankList.get(k);
                fieldObjectList.add(ArkMessage.buildFieldObject((k+1) + ". " + rankObj.getStr("memberName") + "，金币" + rankObj.getInt("point") + "个"));
            }

            ArkMessage.FieldList fieldList = new ArkMessage.FieldList("#LIST#", fieldObjectList);
            fieldArray.add(fieldList);

            ark.setKv(fieldArray);

            guildOpenApi.sendMessage(message.getChannel_id(),
                    ArkMessage.builder()
                            .msg_id(message.getId())
                            .ark(ark)
                            .build());
        } else if ("等级榜".equals(replyObj.getStr("trigger"))) {
            ArkMessage.Ark ark = ArkMessage.Ark.builder()
                    .template_id(23)
                    .build();

            List<ArkMessage.Field> fieldArray = new ArrayList<>();
            fieldArray.add(new ArkMessage.FieldString("#PROMPT#", "等级排行榜"));
            fieldArray.add(new ArkMessage.FieldString("#DESC#", "等级排行榜"));

            List<ArkMessage.FieldObject> fieldObjectList = new ArrayList<>();
            fieldObjectList.add(ArkMessage.buildFieldObject("等级排行榜（经验）"));

            JSONArray rankList = replyObj.getJSONArray("rankList");
            for (int k = 0; k < rankList.size(); k++) {
                JSONObject rankObj = (JSONObject) rankList.get(k);
                fieldObjectList.add(ArkMessage.buildFieldObject((k+1) + ". " + rankObj.getStr("memberName") + "：Lv" + rankObj.getInt("level") + " " + rankObj.getStr("rankName") + "(" + rankObj.getInt("exp") + ")"));
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