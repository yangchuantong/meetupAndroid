//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.util.ela;

import java.io.DataOutputStream;
import java.io.IOException;

public class Uint256 {
    static final int UINT256SIZE = 32;
    byte[] Uint256 = new byte[32];

    public Uint256(byte[] b) {
        System.arraycopy(b, 0, this.Uint256, 0, 32);
    }

    public void Serialize(DataOutputStream o) throws IOException {
        o.write(this.Uint256);
    }
}
