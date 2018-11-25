package org.elastos.meetuplib.base.entity;

import java.io.Serializable;

/**
 * @Author: yangchuantong
 * @Description:获取did信息对象
 * @Date:Created in  2018/11/24 15:46
 */
public class DIDInfo<T> implements Serializable {


    private String did;
    private String key;
    private T data;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
