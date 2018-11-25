//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;

public class HdTxEntity {
    private String[] inputs;
    private HdTxEntity.Output[] outputs;
    private String memo;

    public HdTxEntity() {
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String[] getInputs() {
        return this.inputs;
    }

    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    public HdTxEntity.Output[] getOutputs() {
        return this.outputs;
    }

    public void setOutputs(HdTxEntity.Output[] outputs) {
        this.outputs = outputs;
    }

    public static class Output {
        private String addr;
        private Long amt;

        public Output() {
        }

        public String getAddr() {
            return this.addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public Long getAmt() {
            return this.amt;
        }

        public void setAmt(Long amt) {
            this.amt = amt;
        }
    }
}
