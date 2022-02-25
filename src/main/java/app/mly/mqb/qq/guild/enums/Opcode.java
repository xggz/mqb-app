package app.mly.mqb.qq.guild.enums;

import java.util.Arrays;

/**
 * 操作代号
 *
 * @author feitao yyimba@qq.com
 * @since 2022/2/12 5:14 PM
 */
public enum Opcode {

    /**
     * 服务端进行消息推送
     */
    DISPATCH(0),

    /**
     * 客户端或服务端发送心跳
     */
    HEARTBEAT(1),

    /**
     * 客户端发送鉴权
     */
    IDENTIFY(2),

    /**
     * 客户端恢复连接
     */
    RESUME(6),

    /**
     * 服务端通知客户端重新连接
     */
    RECONNECT(7),

    /**
     * 当identify或resume的时候，如果参数有错，服务端会返回该消息
     */
    INVALID(9),

    /**
     * 当客户端与网关建立ws连接之后，网关下发的第一条消息
     */
    HELLO(10),

    /**
     * 当发送心跳成功之后，就会收到该消息
     */
    HEARTBEAT_ACK(11);

    private Integer code;

    Opcode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 根据代号获取枚举
     *
     * @param code
     * @return
     */
    public static Opcode getByCode(Integer code) {
        return Arrays.stream(Opcode.values())
                .filter(op -> op.getCode().equals(code))
                .findFirst()
                .get();
    }
}