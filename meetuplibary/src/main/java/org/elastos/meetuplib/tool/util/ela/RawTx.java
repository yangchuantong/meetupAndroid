//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.util.ela;

public class RawTx {
    private String txHash;
    private String rawTxString;

    public String getRawTxString() {
        return this.rawTxString;
    }

    public void setRawTxString(String rawTxString) {
        this.rawTxString = rawTxString;
    }

    public String getTxHash() {
        return this.txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public RawTx(String txHash, String rawTxString) {
        this.txHash = txHash;
        this.rawTxString = rawTxString;
    }
}
