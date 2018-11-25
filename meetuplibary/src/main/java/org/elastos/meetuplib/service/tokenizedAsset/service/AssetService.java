package org.elastos.meetuplib.service.tokenizedAsset.service;

import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.entity.Contract;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * @author hb.nie
 */
@SuppressWarnings("unused")
public interface AssetService {
    String mintSign(String address, String contractAddress, String privateKey) throws Exception;


    /**
     * 验签
     *
     * @param address
     * @param contractAddress
     * @param pub
     * @param tokenId
     * @return
     * @throws Exception
     */
    Boolean verifySign(String address, String contractAddress, String pub, String tokenId) throws Exception;


    /**
     * tokenId
     * owner
     * @param apply
     * @param contractAddress
     * @return
     */

    Boolean burn(Apply apply, String contractAddress, String pub);

    /**
     * 异步创建合约
     *
     * @param privateKey    私钥
     * @param gasPrice      gasPrice
     * @param gasLimit      gasLimit
     * @param _name         合约的名称
     * @param _symbol       编号
     * @param _supplyLimit  供给限制
     * @param _contractInfo 合约信息
     * @param _owner        owner
     * @param imgUrl        图片地址
     * @throws Exception
     */

    void createContractAsync(String privateKey, BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol, BigInteger _supplyLimit, String _contractInfo, String _owner, String imgUrl) throws Exception;


    String ownerOf(String contractAddress, String tokenId) throws Exception;


    String owner(String contractAddress) throws Exception;

    String tokenInfo(String contractAddress, String tokenId) throws Exception;


    String balance(String address) throws IOException;


    /**
     * @param upFile
     * @return
     * @throws IOException
     */
    JsonResult<String> uploadFile(File upFile) throws IOException;

    void uploadFileAsync(File upFile, okhttp3.Callback callback) throws IOException;


    String getFileUrl(String baseString);


    /**
     * 查询合约
     *
     * @param contractAddress 合约地址
     * @return 合约
     */
    JsonResult<ContractGroupDetail> findByContractAddress(String contractAddress);


    /**
     * 查询个人创建的合约（附带imgUrl,数量）
     *
     * @param walletAddress 钱包地址（owner）
     * @return List
     */
    JsonResult<List<ContractGroupDetail>> ownerContractListDetailByWalletAddress(String walletAddress);


    /**
     * 发起申请
     *
     * @param apply
     * @param contractAddress
     * @return
     */
    JsonResult<Apply> createApply(Apply apply, String contractAddress);

    /**
     * 根据no查询
     * applyNo
     *
     * @param apply
     * @return
     */
    JsonResult<ApplyDetail> applyDetail(Apply apply);

    JsonResult<ApplyDetail> auditApply(Apply apply, String contractAddress, String owner, String privateKey, BigInteger gasPrice, BigInteger gasLimit, String _to) throws Exception;

    /**
     * 个人的参加的活动
     *
     * @param apply
     * @return
     */
    JsonResult<List<ApplyDetail>> ownerApplyDetailList(Apply apply);


    JsonResult<List<ContractGroupDetail>> homePage();


    JsonResult<ContractGroupDetail> contractInfoDetail(String contractAddress);

}
