package org.elastos.meetuplib.tool.entity;

import java.io.Serializable;

public class ApplyDetail extends Apply implements Serializable {

    private String contractAddress;
    private String contractName;
    private String contractInfo;
    private String contractOwner;

    private String contractImgUrl;

    public String getContractOwner() {
        return contractOwner;
    }

    public void setContractOwner(String contractOwner) {
        this.contractOwner = contractOwner;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }



    public String getContractImgUrl() {
        return contractImgUrl;
    }

    public void setContractImgUrl(String contractImgUrl) {
        this.contractImgUrl = contractImgUrl;
    }
}
