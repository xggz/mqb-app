package app.mly.mqb.qq.guild.event;

import app.mly.mqb.qq.guild.enums.ActionType;
import lombok.Builder;
import lombok.Data;

/**
 * 动作事件
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/24 10:13 PM
 */
@Data
@Builder
public class ActionEvent {

    /**
     * 类型
     */
    private ActionType actionType;
}