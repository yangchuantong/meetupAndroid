package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CoinLog implements Serializable {
    private ArrayList<CoinLog> coinLogList;
    private static CoinLog coinLog;
    public static String lastTime="0";
    public static CoinLog getInstance(){
        if(coinLog ==null){
            coinLog = new CoinLog();
        }
        return coinLog;
    }

    public ArrayList<CoinLog> getCoinLogList() {
        return coinLogList;
    }

    public void setCoinLogList(ArrayList<CoinLog> coinLogList) {
        this.coinLogList = coinLogList;
    }

    private String txHash;

    private String age;

    private String from;

    private String to;

    private String token;

    private String value;
    private String isError;
    private String linkType;

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }
}