package org.elastos.meetuplib.base;


import com.alibaba.fastjson.JSON;

import org.elastos.meetuplib.base.entity.ContractInfo;
import org.elastos.meetuplib.base.entity.MeetupContract;
import org.elastos.meetuplib.base.entity.Sign;
import org.elastos.meetuplib.tool.util.DatatypeConverter;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangchuantong
 * @Description:
 * @Date:Created in  2018/11/24 15:08
 */
public class EthChain {


    private static Web3j web3j = new JsonRpc2_0Web3j(new HttpService("https://ropsten.infura.io/v2GFVi4kHWGp6Yfxfr47"));


    /**
     * 获取余额
     *
     * @param address 地址
     * @return 余额
     */
    public static String balance(String address) throws IOException {
        String balance = web3j.ethGetBalance(address,DefaultBlockParameterName.LATEST).send().getBalance().toString();
        balance = Convert.fromWei(balance, Convert.Unit.ETHER).toPlainString();
        return balance;
    }


    /**
     * 合约信息
     *
     * @param contractAddress 合约地址
     */
    public static ContractInfo contractInfo(String contractAddress) throws Exception {
        ContractInfo contractInfo = new ContractInfo();
        MeetupContract meetupContract = load(contractAddress);
        String name = meetupContract.name().send().toString();
        String symbol = meetupContract.symbol().send().toString();
        String data = meetupContract.data().send().toString();
        BigInteger totalSupply = meetupContract.totalSupply().send().getValue();
        String owner = meetupContract.owner().send().getValue();
        contractInfo.setName(name);
        contractInfo.setOwner(owner);
        contractInfo.setSymbol(symbol);
        contractInfo.setTotalSupply(totalSupply.toString());
        contractInfo.setData(data);
        return contractInfo;

    }


    /**
     * 根据资产id查询资产拥有者
     *
     * @param contractAddress 合约地址
     * @param tokenId         资产id
     * @return String 拥有者地址
     */
    public static String ownerOf(String contractAddress, String tokenId) throws Exception {
        MeetupContract meetupContract = load(contractAddress);
        String address = meetupContract.ownerOf(new Uint256(new BigInteger(tokenId))).send().getValue();
        return address;
    }

    public static String owner(String owner ) throws Exception {
        MeetupContract load = load(owner);
        String value = load.owner().send().getValue();
        return value ;
    }

    /**
     * 根据资产id查询资产信息
     *
     * @param contractAddress 合约地址
     * @param tokenId         资产id
     * @return String 资产信息
     */
    public static String tokenInfo(String contractAddress, String tokenId) throws Exception {
        MeetupContract meetupContract = load(contractAddress);
        String tokenURI = meetupContract.tokenURI(new Uint256(new BigInteger(tokenId))).send().getValue();
        return tokenURI;
    }


    /**
     * 发布
     *
     * @param contractAddress 合约地址
     * @param privateKey      私钥
     * @param gasPrice
     * @param gasLimit
     * @param _to             资产归属地址
     * @param _info           资产信息
     * @return String tokenid
     */
    public static String mint(String contractAddress, String privateKey, BigInteger gasPrice, BigInteger gasLimit, String _to, String _info) throws Exception {
        Credentials credentials = Credentials.create(privateKey);
        MeetupContract meetupContract = load(contractAddress, credentials, gasPrice, gasLimit);
        TransactionReceipt transactionReceipt = meetupContract.mint(new Address(_to), new Utf8String(_info), BigInteger.valueOf(0)).send();
        List<MeetupContract.TransferEventResponse> list = meetupContract.getTransferEvents(transactionReceipt);
        MeetupContract.TransferEventResponse transferEventResponse = list.get(0);
        return transferEventResponse._tokenId.getValue().toString();
    }




    /**
     * 创建合约
     *
     * @param privateKey    私钥
     * @param gasPrice
     * @param gasLimit
     * @param _name         合约名称
     * @param _symbol       合约标志
     * @param _supplyLimit  供给限制
     * @param _contractInfo 合约描述
     * @param _owner        合约拥有者
     * @return String 合约地址
     */
    public static String deploy(String privateKey, BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol, BigInteger _supplyLimit, String _contractInfo, String _owner) throws Exception {
        Credentials credentials = Credentials.create(privateKey);
        MeetupContract meetupContract = MeetupContract.deploy(web3j, credentials, gasPrice, gasLimit, new Utf8String(_name), new Utf8String(_symbol), new Uint256(_supplyLimit), new Utf8String(_contractInfo), new Address(_owner)).send();
        String contractAddress = meetupContract.getContractAddress();
        return contractAddress;
    }


    private static MeetupContract load(String contractAddress) {
        TransactionManager transactionManager = new ClientTransactionManager(web3j, null);
        MeetupContract asset = MeetupContract.load(contractAddress, web3j, transactionManager, BigInteger.valueOf(9000000000L), BigInteger.valueOf(60000));
        return asset;
    }

    private static MeetupContract load(String contractAddress, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        MeetupContract asset = MeetupContract.load(contractAddress, web3j, credentials, gasPrice, gasLimit);
        return asset;
    }

}

