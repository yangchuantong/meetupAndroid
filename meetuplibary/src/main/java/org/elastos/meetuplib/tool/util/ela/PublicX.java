//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.util.ela;

import org.elastos.meetuplib.tool.util.DatatypeConverter;

import java.math.BigInteger;

public class PublicX implements Comparable<PublicX> {
    private BigInteger pubX;
    private String privateKey;

    public PublicX(String privateKey) {
        ECKey ec = ECKey.fromPrivate(DatatypeConverter.parseHexBinary(privateKey));
        this.privateKey = privateKey;
        this.pubX = ec.getPublickeyX().toBigInteger();
    }

    public int compareTo(PublicX o) {
        return this.pubX.compareTo(o.pubX);
    }

    public String toString() {
        return this.privateKey;
    }
}
