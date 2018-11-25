package org.elastos.meetuplib.service.tokenizedAsset.service.impl;

import com.alibaba.fastjson.JSON;

import org.elastos.meetuplib.base.DIDChain;
import org.elastos.meetuplib.base.EthChain;
import org.elastos.meetuplib.base.entity.Sign;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.storage.service.ApplyStorage;
import org.elastos.meetuplib.storage.service.ContractStorage;
import org.elastos.meetuplib.storage.util.ObjectUtils;
import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.entity.Contract;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.menum.ApplyStates;
import org.elastos.meetuplib.tool.util.DatatypeConverter;
import org.elastos.meetuplib.tool.util.JsonResult;
import org.elastos.meetuplib.tool.util.MessageConstant;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author hb.nie
 * @description
 */
@SuppressWarnings({"unchecked"})
public class AssetServiceImpl implements AssetService {

    private EthChain ethChain = new EthChain();


    /**
     * 发布资产时保存签名信息
     *
     * @param address         接收方地址
     * @param contractAddress 合约地址
     * @param privateKey      合约拥有者私钥
     * @return 签名信息
     */
    @Override
    public String mintSign(String address, String contractAddress, String privateKey) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("contractAddress", contractAddress);
        String msg = JSON.toJSONString(map);
        Sign sign = DIDChain.sign(msg, privateKey);
        return sign.getSig();
    }


    /**
     * 验票
     *
     * @param address         被验证方钱包地址
     * @param contractAddress 合约地址
     * @param pub             验票方公钥
     * @param tokenId
     * @return 验证结果
     */
    @Override
    public Boolean verifySign(String address, String contractAddress, String pub, String tokenId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("contractAddress", contractAddress);
        String msg = JSON.toJSONString(map);
        msg = DatatypeConverter.printHexBinary(msg.getBytes());
        String sig = EthChain.tokenInfo(contractAddress, tokenId);
        return DIDChain.verify(msg, sig, pub);
    }

    /**
     * 活动核销
     *
     * @param apply
     * @param contractAddress
     * @param pub
     * @return
     */
    @Override
    public Boolean burn(Apply apply, String contractAddress, String pub) {
        String tokenId = apply.getTokenId();
        Boolean aBoolean = false;
        try {
            aBoolean = verifySign(apply.getOwner(), contractAddress, pub, tokenId);
            if (aBoolean) {
                apply.setStatus(ApplyStates.BURN.intValue());
                JsonResult<ApplyDetail> burn = ApplyStorage.burn(apply, contractAddress);
                return burn.getSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aBoolean;
    }

    /**
     * @param privateKey  私钥
     * @param gasPrice    价格
     * @param gasLimit    限制
     * @param name        合约名称
     * @param symbol      合约编号
     * @param supplyLimit 限制
     * @param description 合约描述
     * @param owner       合约拥有者
     * @return contract
     * @throws Exception
     */
    @Override
    public void createContractAsync(final String privateKey, final BigInteger gasPrice, final BigInteger gasLimit, final String name, final String symbol, final BigInteger supplyLimit, final String description, final String owner, final String imgUrl) throws Exception {
        Runnable runnable = () -> {
            String contractAddress;
            try {
                contractAddress = ethChain.deploy(privateKey, gasPrice, gasLimit, name, symbol, supplyLimit, description, owner);
                JsonResult<Contract> contract = ContractStorage.createContract(gasPrice, gasLimit, name, symbol, supplyLimit, description, owner, contractAddress, imgUrl);
                Contract data = contract.getData();
                data.setImgUrl(getFileUrl(data.getImgUrl()));
                contract.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(runnable).start();
    }


    /**
     * 根据资产id查询资产拥有者
     *
     * @param contractAddress 合约地址
     * @param tokenId         资产id
     * @return String 拥有者地址
     */
    @Override
    public String ownerOf(String contractAddress, String tokenId) throws Exception {
        return ethChain.ownerOf(contractAddress, tokenId);
    }

    @Override
    public String owner(String contractAddress) throws Exception {
        return ethChain.owner(contractAddress);
    }


    /**
     * 根据资产id查询资产信息
     *
     * @param contractAddress 合约地址
     * @param tokenId         资产id
     * @return String 资产信息
     */
    @Override
    public String tokenInfo(String contractAddress, String tokenId) throws Exception {
        return tokenInfo(contractAddress, tokenId);
    }


    @Override
    public String balance(String address) throws IOException {
        String balance = ethChain.balance(address);
        return balance;
    }


    /**
     * @param upFile 需要上传的文件
     * @return response
     * @throws IOException
     */
    @Override
    public JsonResult<String> uploadFile(File upFile) throws IOException {
        return ContractStorage.uploadFile(upFile);
    }

    @Override
    public void uploadFileAsync(File upFile, okhttp3.Callback callback) throws IOException {
        ContractStorage.uploadFileAsync(upFile, callback);
    }


    /**
     * 服务器返回的地址（例如：this.getFileUrl(jsonResult.getData())）
     *
     * @param baseString 图片
     * @return url
     */
    @Override
    public String getFileUrl(String baseString) {
        return ContractStorage.getFileUrl(baseString);
    }

    /**
     * 根据合约地址查询合约信息
     *
     * @param contractAddress 合约地址
     * @return contract
     */
    @Override
    public JsonResult<ContractGroupDetail> findByContractAddress(String contractAddress) {
        JsonResult<ContractGroupDetail> byContractAddress = ContractStorage.findByContractAddress(contractAddress);
        Contract data = byContractAddress.getData();
        data.setImgUrl(getFileUrl(data.getImgUrl()));
        return byContractAddress;
    }


    @Override
    public JsonResult<List<ContractGroupDetail>> ownerContractListDetailByWalletAddress(String walletAddress) {
        JsonResult<List<ContractGroupDetail>> ownerContractListByWalletAddress = ContractStorage.findOwnerContractListByWalletAddress(walletAddress);
        List<ContractGroupDetail> data = ownerContractListByWalletAddress.getData();

        for (Contract contract : data) {
            contract.setImgUrl(getFileUrl(contract.getImgUrl()));
        }
        return ownerContractListByWalletAddress;
    }

    @Override
    public JsonResult<Apply> createApply(Apply apply, String contractAddress) {

        return ApplyStorage.createApply(apply, contractAddress);
    }

    @Override
    public JsonResult<ApplyDetail> applyDetail(Apply apply) {
        return ApplyStorage.applyDetail(apply);
    }


    @Override
    public JsonResult<ApplyDetail> auditApply(Apply apply, String contractAddress, String owner, String privateKey, BigInteger gasPrice, BigInteger gasLimit, String _to) throws Exception {
        if (apply.getStatus() == null) {
            return JsonResult.error(MessageConstant.PARAMS_ERROR, "status can not be empty");
        }
        if (ObjectUtils.hasBlank(apply.getApplyNo()) && ObjectUtils.hasBlank(apply.getId())) {
            return JsonResult.error(MessageConstant.PARAMS_ERROR, "no is null");
        }
        if (apply.getStatus().equals(ApplyStates.AUDIT_PASS.intValue())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String info = mintSign(owner, contractAddress, privateKey);
                        String mint = ethChain.mint(contractAddress, privateKey, gasPrice, gasLimit, _to, info);
                        apply.setTokenId(mint);
                        apply.setTxId(info);
                        ApplyStorage.auditApply(apply, contractAddress);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return JsonResult.success();
        } else {
            return ApplyStorage.auditApply(apply, contractAddress);
        }
    }

    @Override
    public JsonResult<List<ApplyDetail>> ownerApplyDetailList(Apply apply) {
        JsonResult<List<ApplyDetail>> listJsonResult = ApplyStorage.ownerApplyDetailList(apply);
        if(listJsonResult.getSuccess()){
            List<ApplyDetail> data = listJsonResult.getData();
            for (ApplyDetail applyDetail :data){
                applyDetail.setContractImgUrl(getFileUrl(applyDetail.getContractImgUrl()));
            }
        }
        return listJsonResult;
    }

    @Override
    public JsonResult<List<ContractGroupDetail>> homePage() {
        JsonResult<List<ContractGroupDetail>> listJsonResult = ContractStorage.homePage();
        if(listJsonResult.getSuccess()){
           for (ContractGroupDetail contractGroupDetail : listJsonResult.getData()){
               contractGroupDetail.setImgUrl(getFileUrl(contractGroupDetail.getImgUrl()));
           }
        }
        return listJsonResult;
    }

    @Override
    public JsonResult<ContractGroupDetail> contractInfoDetail(String contractAddress) {
        JsonResult<ContractGroupDetail> contractGroupDetailJsonResult = ContractStorage.contractInfo(contractAddress);
        if(contractGroupDetailJsonResult.getSuccess()){
            contractGroupDetailJsonResult.getData().setImgUrl(getFileUrl(contractGroupDetailJsonResult.getData().getImgUrl()));
        }
        return contractGroupDetailJsonResult;
    }


}
