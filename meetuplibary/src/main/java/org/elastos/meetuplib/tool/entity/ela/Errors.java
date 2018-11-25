//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;

public enum Errors {
    NOT_ENOUGH_UTXO("Not Enough UTXO"),
    DID_NO_SUCH_INFO("No such info"),
    ELA_ADDRESS_INVALID("address is not valid");

    private String str;

    private Errors(String str) {
        this.str = str;
    }

    public String val() {
        return this.str;
    }
}
