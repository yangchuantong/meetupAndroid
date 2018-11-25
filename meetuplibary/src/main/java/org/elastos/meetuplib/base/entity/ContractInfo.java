package org.elastos.meetuplib.base.entity;

/**
 * @Author: yangchuantong
 * @Description:合约信息
 * @Date:Created in  2018/11/24 18:01
 */
public class ContractInfo {


    private String name; //合约名称
    private String symbol;//名称缩写
    private String data;//合约信息
    private String totalSupply;//发行数量
    private String owner;//合约拥有者

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "ContractInfo{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", data='" + data + '\'' +
                ", totalSupply='" + totalSupply + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
