//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;

public class RawTxEntity {
    private String method = "sendrawtransaction";
    private String data;
    private ChainType type;

    public RawTxEntity() {
    }

    public String getMethod() {
        return this.method;
    }

    public ChainType getType() {
        return this.type;
    }

    public void setType(ChainType type) {
        this.type = type;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
