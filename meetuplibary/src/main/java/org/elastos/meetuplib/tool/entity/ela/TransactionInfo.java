package org.elastos.meetuplib.tool.entity.ela;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: yangchuantong
 * @Description:
 * @Date:Created in  2018/11/19 16:46
 */
public class TransactionInfo implements Serializable {

    private String txid;
    private String hash;
    private Integer size;
    private Integer vsize;
    private Integer version;
    private Integer locktime;
    private List<Vin> vin;
    private List<Vout> vout;
    private  String blockhash;
    private Integer confirmations;
    private Long time;
    private String fee;


    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getVsize() {
        return vsize;
    }

    public void setVsize(Integer vsize) {
        this.vsize = vsize;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLocktime() {
        return locktime;
    }

    public void setLocktime(Integer locktime) {
        this.locktime = locktime;
    }

    public List<Vin> getVin() {
        return vin;
    }

    public void setVin(List<Vin> vin) {
        this.vin = vin;
    }

    public List<Vout> getVout() {
        return vout;
    }

    public void setVout(List<Vout> vout) {
        this.vout = vout;
    }

    public String getBlockhash() {
        return blockhash;
    }

    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "TransactionInfo{" +
                "txid='" + txid + '\'' +
                ", hash='" + hash + '\'' +
                ", size=" + size +
                ", vsize=" + vsize +
                ", version=" + version +
                ", locktime=" + locktime +
                ", vin=" + vin +
                ", vout=" + vout +
                ", blockhash='" + blockhash + '\'' +
                ", confirmations=" + confirmations +
                ", time=" + time +
                ", fee='" + fee + '\'' +
                '}';
    }
}
