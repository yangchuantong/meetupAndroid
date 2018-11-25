package org.elastos.meetuplib.tool.menum;

/**
 * @author hb.nie
 * @description
 */
/**
 * @author hb.nie
 * @description
 */
public enum ApplyStates {
    APPLIED, //已申请
    AUDIT_PASS, //已通过
    AUDIT_NO_PASS,  //未通过
    BURN;// 已经使用


    @Override
    public String toString() {
        switch (this) {
            case APPLIED:
                return "1";
            case AUDIT_NO_PASS:
                return "4";
            case AUDIT_PASS:
                return "21";
            case BURN:
                return "22";
        }
        return "-1";
    }

    public Integer intValue() {
        return Integer.valueOf(this.toString());
    }
}
