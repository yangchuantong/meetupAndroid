//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.util.ela;

import java.io.DataOutputStream;
import java.io.IOException;

public class PayloadRecord {
    String RecordType;
    byte[] RecordData;

    public void Serialize(DataOutputStream o) throws IOException {
        Util.WriteVarUint(o, (long)this.RecordType.length());
        o.write(this.RecordType.getBytes());
        Util.WriteVarBytes(o, this.RecordData);
    }

    public PayloadRecord(String type, String data) {
        this.RecordType = type;
        this.RecordData = data.getBytes();
    }
}
