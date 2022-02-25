package app.mly.mqb.moli.plugins;

import app.mly.mqb.qq.guild.common.Message;
import app.mly.mqb.qq.guild.enums.State;
import app.mly.mqb.qq.guild.message.EmbedMessage;
import app.mly.mqb.qq.guild.request.GuildOpenApi;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理茉莉云天气预报插件JSON数据
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 8:06 PM
 */
@Component
public class WeatherPluginHandler implements PluginHandler {

    @Autowired
    private GuildOpenApi guildOpenApi;

    @Override
    public void reply(Message message, JSONObject replyObj) {
        EmbedMessage embedMessage = EmbedMessage.builder()
                .prompt("天气预报")
                .title(replyObj.getStr("place") + "天气")
                .build();

        if (State.Y.getValue().equals(replyObj.getInt("state"))) {
            List<EmbedMessage.Field> fieldList = new ArrayList<>();
            fieldList.add(new EmbedMessage.Field("⏱ " + replyObj.getStr("week") + "  " + replyObj.getStr("month") + "/" + replyObj.getStr("days")));
            if (replyObj.containsKey("nowInfo") && !replyObj.isNull("nowInfo")) {
                int hour = LocalTime.now().getHour();
                String type = hour > 18 || hour < 5 ? "night" : "day";
                String icon = hour > 18 || hour < 5 ? "🌒 " : "☀ ";
                JSONObject nowInfoObj = replyObj.getJSONObject("nowInfo");
                fieldList.add(new EmbedMessage.Field(icon + nowInfoObj.getStr("weather") + "  " + nowInfoObj.getStr("temperature") + "℃  " + nowInfoObj.getStr("windDirection")));
                if (replyObj.containsKey("changeInfo") && !replyObj.isNull("changeInfo")) {
                    JSONObject changeInfo = replyObj.getJSONObject("changeInfo");
                    fieldList.add(new EmbedMessage.Field(" "));
                    fieldList.add(new EmbedMessage.Field(replyObj.getInt("changeHours") + "小时后" + nowInfoObj.getStr("weather") + "转" + changeInfo.getStr("weather")));
                }
                embedMessage.setThumbnail(new EmbedMessage.Thumbnail("https://files.molicloud.com/weather/icon/" + type + "/" + nowInfoObj.getStr("weatherCode") + ".png"));
            } else {
                JSONObject dayInfoObj = replyObj.getJSONObject("dayInfo");
                JSONObject nightInfoObj = replyObj.getJSONObject("nightInfo");

                fieldList.add(new EmbedMessage.Field("☀ " + dayInfoObj.getStr("weather") + "  " + dayInfoObj.getStr("temperature") + "℃  " + dayInfoObj.getStr("windDirection")));
                fieldList.add(new EmbedMessage.Field("🌒 " + nightInfoObj.getStr("weather") + "  " + nightInfoObj.getStr("temperature") + "℃  " + nightInfoObj.getStr("windDirection")));
                embedMessage.setThumbnail(new EmbedMessage.Thumbnail("https://files.molicloud.com/weather/icon/day/" + dayInfoObj.getStr("weatherCode") + ".png"));
            }

            embedMessage.setFields(fieldList);
        } else if (State.C.getValue().equals(replyObj.getInt("state"))) {
            embedMessage.setThumbnail(new EmbedMessage.Thumbnail(message.getAuthor().getAvatar()));
            embedMessage.setFields(EmbedMessage.buildFields("@" + message.getMember().getNick(),
                    "你要查询哪个城市的天气呢！"));
        } else if (State.N.getValue().equals(replyObj.getInt("state"))) {
            embedMessage.setThumbnail(new EmbedMessage.Thumbnail(message.getAuthor().getAvatar()));
            embedMessage.setFields(EmbedMessage.buildFields("@" + message.getMember().getNick(),
                    "抱歉，不支持查询此地点！"));
        }

        guildOpenApi.sendMessage(message.getChannel_id(), embedMessage);
    }
}