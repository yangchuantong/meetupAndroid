//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;

public class SignDataEntity {
    private String privateKey;
    private String msg;
    private String pub;
    private String sig;

    public SignDataEntity() {
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPub() {
        return this.pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getSig() {
        return this.sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
