package app.mly.mqb.moli;

import app.mly.mqb.moli.common.MoliMsg;
import app.mly.mqb.moli.properties.MoliProperties;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 茉莉云开放接口
 *
 * @author xggz <yyimba@qq.com>
 * @since 2022/2/25 16:50
 */
@Component
public class MoliOpenApi {

    @Autowired
    private MoliProperties moliProperties;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用茉莉云Api获取回复
     *
     * @param moliMsg
     * @return
     */
    public JSONObject moliReply(MoliMsg moliMsg) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Api-Key", moliProperties.getApiKey());
        headers.add("Api-Secret", moliProperties.getApiSecret());

        JSONObject body = new JSONObject();
        body.set("content", moliMsg.getContent());
        body.set("type", 2);
        body.set("from", moliMsg.getFrom());
        body.set("fromName", moliMsg.getFromName());
        body.set("to", moliMsg.getTo());
        body.set("toName", moliMsg.getToName());

        HttpEntity<String> formEntity = new HttpEntity<String>(body.toString(), headers);
        return restTemplate.postForEntity(moliProperties.getBaseUrl() + "/reply", formEntity, JSONObject.class).getBody();
    }
}