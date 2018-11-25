package org.elastos.meetuplib.tool.entity;

import java.io.Serializable;

public class ContractGroupDetail extends Contract implements Serializable {

    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 商家待处理订单
     */
    private Integer waitAuditQuantity = 0;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWaitAuditQuantity() {
        return waitAuditQuantity;
    }

    public void setWaitAuditQuantity(Integer waitAuditQuantity) {
        this.waitAuditQuantity = waitAuditQuantity;
    }
}
