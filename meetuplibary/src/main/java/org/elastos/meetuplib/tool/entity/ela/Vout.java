package org.elastos.meetuplib.tool.entity.ela;

import java.io.Serializable;

public class Vout implements Serializable {

    private  String value;
    private Integer n;
    private String address;
    private String assetid;
    private Integer outputlock;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssetid() {
        return assetid;
    }

    public void setAssetid(String assetid) {
        this.assetid = assetid;
    }

    public Integer getOutputlock() {
        return outputlock;
    }

    public void setOutputlock(Integer outputlock) {
        this.outputlock = outputlock;
    }
}
