package org.elastos.meetuplib.base.entity;

/**
 * @Author: yangchuantong
 * @Description:
 * @Date:Created in  2018/11/24 19:20
 */
public class Sign {



    private String msg;
    private String pub;
    private String sig;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "msg='" + msg + '\'' +
                ", pub='" + pub + '\'' +
                ", sig='" + sig + '\'' +
                '}';
    }
}
