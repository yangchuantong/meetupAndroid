package org.elastos.meetup.entity;

import java.io.Serializable;

public class TransactionInfoDto implements Serializable{
    private static TransactionInfoDto transactionInfoDto;
    public static TransactionInfoDto getInstance(){
        if(transactionInfoDto==null){
            transactionInfoDto = new TransactionInfoDto();
        }
        return transactionInfoDto;
    }
    private String gasPrice;
    private String from;
    private String to;
    private String blockNumber;
    private  String txHash;
    private String remarks;

    public static TransactionInfoDto getTransactionInfoDto() {
        return transactionInfoDto;
    }

    public static void setTransactionInfoDto(TransactionInfoDto transactionInfoDto) {
        TransactionInfoDto.transactionInfoDto = transactionInfoDto;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
