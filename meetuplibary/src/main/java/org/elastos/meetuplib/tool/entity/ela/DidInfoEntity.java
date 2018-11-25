//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;

import java.util.List;

public class DidInfoEntity<T> {
    private String privateKey;
    private T info;
    private List<String> txIds;
    private String key;

    public DidInfoEntity() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getInfo() {
        return this.info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public List<String> getTxIds() {
        return this.txIds;
    }

    public void setTxIds(List<String> txIds) {
        this.txIds = txIds;
    }
}
