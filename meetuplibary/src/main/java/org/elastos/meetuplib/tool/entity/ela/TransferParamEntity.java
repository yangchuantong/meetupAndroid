//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;


public class TransferParamEntity<T, V, M> {
    private V sender;
    private String memo;
    private T receiver;
    private ChainType type;
    private String payload;

    public TransferParamEntity() {
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public V getSender() {
        return this.sender;
    }

    public void setSender(V sender) {
        this.sender = sender;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public T getReceiver() {
        return this.receiver;
    }

    public void setReceiver(T receiver) {
        this.receiver = receiver;
    }

    public ChainType getType() {
        return this.type;
    }

    public void setType(ChainType type) {
        this.type = type;
    }
}
