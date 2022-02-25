package app.mly.mqb.qq.guild.listener;

import app.mly.mqb.qq.guild.WebSocketStarter;
import app.mly.mqb.qq.guild.enums.ActionType;
import app.mly.mqb.qq.guild.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 动作事件监听
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 10:15 PM
 */
@Component
public class ActionEventListener {

    @Autowired
    private WebSocketStarter webSocketStarter;

    @EventListener(ActionEvent.class)
    public void handlerResult(ActionEvent actionEvent) {
        if (ActionType.RECONNECTION.equals(actionEvent.getActionType())) {
            webSocketStarter.reconnection();
        }
    }
}