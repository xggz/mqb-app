package app.mly.mqb.qq.guild.enums;

/**
 * 状态通用枚举
 *
 * @author feitao yyimba@qq.com
 * @since 2021/2/1 8:02 下午
 */
public enum State {

    /**
     * 正常的/关联的/启用的/上架的
     */
    Y(1, "正向"),

    /**
     * 非正常/未关联/禁用的/下架的
     */
    N(0, "反向"),

    /**
     * 审核中/待完成/未就绪/中间态
     */
    C(2, "中间");

    private Integer value;

    private String explan;

    State(Integer value, String explan) {
        this.value = value;
        this.explan = explan;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getExplan() {
        return explan;
    }

    public void setExplan(String explan) {
        this.explan = explan;
    }
}