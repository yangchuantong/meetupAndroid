//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.entity.ela;

public class ReturnMsgEntity<V> {
    private Long status;
    private V result;

    public ReturnMsgEntity() {
    }

    public Long getStatus() {
        return this.status;
    }

    public ReturnMsgEntity setStatus(Long status) {
        this.status = status;
        return this;
    }

    public V getResult() {
        return this.result;
    }

    public ReturnMsgEntity setResult(V result) {
        this.result = result;
        return this;
    }

    public static class ELAReturnMsg<T> {
        private String Desc;
        private Long Error;
        private T Result;

        public ELAReturnMsg() {
        }

        public String getDesc() {
            return this.Desc;
        }

        public void setDesc(String desc) {
            this.Desc = desc;
        }

        public Long getError() {
            return this.Error;
        }

        public void setError(Long error) {
        }

        public T getResult() {
            return this.Result;
        }

        public void setResult(T result) {
            this.Result = result;
        }
    }
}
