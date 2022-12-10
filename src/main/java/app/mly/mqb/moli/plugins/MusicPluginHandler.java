package app.mly.mqb.moli.plugins;

import app.mly.mqb.qq.guild.common.Message;
import app.mly.mqb.qq.guild.message.NormalMessage;
import app.mly.mqb.qq.guild.request.GuildOpenApi;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 处理茉莉云音乐插件JSON数据
 *
 * @author xggz 2022/12/10
 */
@Component
public class MusicPluginHandler implements PluginHandler {

    @Autowired
    private GuildOpenApi guildOpenApi;
    
    private static final String HR = "-----------------------------";
    private static final String RECORD1 = "🏷";
    private static final String RICE = "🌾";
    private static final String PLAYER_URL = "https://url.mlyai.com/music163/player?type=2&id=%s&auto=1&height=66";
    private static final String DETAIL_URL = "https://url.mlyai.com/music163/detail?id=%s";

    @Override
    public void reply(Message message, JSONObject replyObj) {
        if ("音乐搜索".equals(replyObj.getStr("trigger"))) {
            List<String> messageList = new ArrayList<>();
            Integer state = replyObj.getInt("state");
            if (state.equals(1)) {
                messageList.add("💡 @" + message.getMember().getNick());
                messageList.add("-----------------------------");
                messageList.addAll(buildMusicListContent(replyObj.getJSONArray("musicDetailList")));
                messageList.add("-----------------------------");
                messageList.add("🏷 当前第" + replyObj.getInt("currentPage") + "页，共搜到" + replyObj.getInt("pageCount") + "条结果。");
                messageList.add("-----------------------------");
                messageList.add("✨ 回复编号查看歌曲详情");
                messageList.add("✨ 回复“上一页”或“下一页”翻页");
            } else if (state.equals(2)) {
                messageList.add("💡 @" + message.getMember().getNick() + "，没有搜索到歌曲！");
            } else if (state.equals(3)) {
                messageList.add("💡 @" + message.getMember().getNick() + "，没有找到页啦！");
            } else if (state.equals(4)) {
                messageList.add("💡 @" + message.getMember().getNick() + "，告诉我歌曲名字哦！");
            }

            guildOpenApi.sendMessage(message.getChannel_id(),
                    NormalMessage.builder()
                            .msg_id(message.getId())
                            .content(CollUtil.join(messageList, "\n"))
                            .build());
        } else if ("音乐详情".equals(replyObj.getStr("trigger"))) {
            List<String> messageList = new ArrayList<>();
            Integer state = replyObj.getInt("state");
            if (state.equals(1)) {
                messageList.add("💡 @" + message.getMember().getNick());
                messageList.addAll(buildMusicDetailContent(replyObj));
            } else {
                messageList.add("💡 @" + message.getMember().getNick() + "，选择不正确！！");
            }
            guildOpenApi.sendMessage(message.getChannel_id(),
                    NormalMessage.builder()
                            .msg_id(message.getId())
                            .content(CollUtil.join(messageList, "\n"))
                            .build());
        }
    }

    /**
     * 构建音乐列表内容
     * 
     * @param musicDetailList
     * @return
     */
    private List<String> buildMusicListContent(JSONArray musicDetailList) {
        List<String> contentList = new ArrayList<>();
        for (int i = 0; i < musicDetailList.size(); i++) {
            JSONObject musicDetail = (JSONObject) musicDetailList.get(i);
            String content = String.format("🎵 %s、%s%s[%s-%s]", 
                    i+1, 
                    musicDetail.getStr("name"), 
                    CollUtil.join(musicDetail.getJSONArray("aliasName"), "/", "（", "）"),
                    CollUtil.join(musicDetail.getJSONArray("artistList").stream().map(artist -> ((JSONObject) artist).getStr("name")).collect(Collectors.toList()), "/"),
                    musicDetail.getJSONObject("album").getStr("name"));
            contentList.add(content);
        }
        
        return contentList;
    }

    /**
     * 构建音乐详情内容
     * 
     * @param musicDetail
     * @return
     */
    private List<String> buildMusicDetailContent(JSONObject musicDetail) {
        List<String> messageList = new ArrayList<>();
        messageList.add(HR);
        
        messageList.add(RICE + " 歌曲名称：" 
                + musicDetail.getStr("name") 
                + CollUtil.join(musicDetail.getJSONArray("aliasName"), "/", "（", "）"));
        messageList.add(RICE + " 演唱歌手：" 
                + CollUtil.join(musicDetail.getJSONArray("artistList").stream().map(artist -> ((JSONObject) artist).getStr("name")).collect(Collectors.toList()), "/"));
        messageList.add(RICE + " 专辑名称：" + musicDetail.getJSONObject("album").getStr("name"));
        
        messageList.add(HR);
        
        if (musicDetail.getBool("fee")) {
            messageList.add("🎵 播放地址：" + String.format(PLAYER_URL, musicDetail.getStr("id")));
        } else {
            messageList.add("🎵 播放地址：" + String.format(DETAIL_URL, musicDetail.getStr("id")));
            messageList.add("（应版权方要求，该歌曲仅支持试听）");
        }

        messageList.add(HR);

        JSONObject musicComments = musicDetail.getJSONObject("musicComments");
        if (CollUtil.isNotEmpty(musicComments.getJSONArray("hotCommentsList"))) {
            messageList.add("🔥 热门评论：");
            AtomicInteger index = new AtomicInteger();
            musicComments.getJSONArray("hotCommentsList").stream().map(comment -> (JSONObject) comment).forEach(comment -> {
                if (index.get() < 3) {
                    messageList.add(RECORD1 + " " + comment.getStr("userName") + "：" + comment.getStr("commentContent"));
                    index.getAndIncrement();
                }
            });
            messageList.add(HR);
        }
        if (CollUtil.isNotEmpty(musicComments.getJSONArray("commentsList"))) {
            messageList.add("🆕 最新评论：");
            musicComments.getJSONArray("commentsList").stream().map(comment -> (JSONObject) comment).forEach(comment -> {
                messageList.add(RECORD1 + " " + comment.getStr("userName") + "：" + comment.getStr("commentContent"));
            });
        }
        
        return messageList;
    }
}