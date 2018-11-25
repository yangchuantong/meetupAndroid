package org.elastos.meetuplib.storage.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.elastos.meetuplib.storage.conf.MethodConstant;
import org.elastos.meetuplib.storage.util.HttpUtils;
import org.elastos.meetuplib.storage.util.ObjectUtils;
import org.elastos.meetuplib.tool.entity.Contract;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;
import org.elastos.meetuplib.tool.util.MessageConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;

/**
 * @author hb.nie
 */
@SuppressWarnings({"unchecked"})

public class ContractStorage {

    private ContractStorage() {
    }


    public static String getFileUrl(String baseString) {
        return MethodConstant.File.FILE + baseString;
    }

    /**
     * 产品图片上传存储，返回图片URL地址
     * 使用场景：
     * 创建产品，需要上传图片，图片地址上链
     *
     * @param upFile
     * @return path  返回服务器存储文件的地址
     * @throws IOException
     */

    public static JsonResult uploadFile(File upFile) throws IOException {
        return HttpUtils.uploadFile(MethodConstant.File.UPLOAD_URL, upFile);
    }


    /**
     * 创建合约
     *
     * @param gasPrice      价格
     * @param gasLimit      限制
     * @param name          合约名称
     * @param symbol        合约标志
     * @param supplyLimit   供给限制
     * @param description   合约描述
     * @param walletAddress 合约拥有者的钱包地址
     * @param address       合约地址
     * @return 合约
     */

    public static JsonResult<Contract> createContract(BigInteger gasPrice, BigInteger gasLimit, String name, String symbol, BigInteger supplyLimit, String description, String walletAddress, String address, String imgUrl) {

        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("symbol", symbol);
        paramsMap.put("description", description);
        paramsMap.put("name", name);
        paramsMap.put("address", address);
        paramsMap.put("owner", walletAddress);
        paramsMap.put("gasPrice", gasPrice);
        paramsMap.put("gasLimit", gasLimit);
        paramsMap.put("total", supplyLimit);
        paramsMap.put("imgUrl", imgUrl);
        String post = HttpUtils.post(MethodConstant.Contract.CREATE_CONTRACT, paramsMap);
        return JSONObject.parseObject(post, new TypeReference<JsonResult<Contract>>() {
        });
    }



    /**
     * 获取合约拥有者信息
     *
     * @param contractAddress 合约地址
     * @return DidUser
     */
    public static JsonResult<String> owner(String contractAddress) {
        if (ObjectUtils.hasBlank(contractAddress)) {
            return JsonResult.error(MessageConstant.PARAMS_ERROR, "all params is required ");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("contractAddress", contractAddress);
        String post = HttpUtils.post(MethodConstant.Contract.FIND_OWNER_BY_CONTRACT_ADDRESS, map);
        return JSONObject.parseObject(post, new TypeReference<JsonResult<String>>() {
        });
    }

    /**
     * 查询当前合约信息
     *
     * @param contractAddress 合约地址
     * @return Contract
     */

    public static JsonResult<ContractGroupDetail> findByContractAddress(String contractAddress) {
        if (ObjectUtils.hasBlank(contractAddress)) {
            return JsonResult.error(MessageConstant.PARAMS_ERROR, "all params is required ");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("contractAddress", contractAddress);
        String post = HttpUtils.post(MethodConstant.Contract.FIND_BY_CONTRACT_ADDRESS, map);
        return JSONObject.parseObject(post, new TypeReference<JsonResult<ContractGroupDetail>>() {
        });
    }

    /**
     * 查询当前已经发布的合约信息
     *
     * @param walletAddress 钱包地址
     * @return list
     */
    public static JsonResult<List<ContractGroupDetail>> findOwnerContractListByWalletAddress(String walletAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put("walletAddress", walletAddress);
        String post = HttpUtils.post(MethodConstant.Contract.FIND_OWNER_CONTRACT_LIST_BY_WALLET_ADDRESS, map);
        return JSONObject.parseObject(post, new TypeReference<JsonResult<List<ContractGroupDetail>>>() {
        });
    }



    public static void uploadFileAsync(File upFile, Callback callback) throws FileNotFoundException {
        HttpUtils.uploadFileAsync(MethodConstant.File.UPLOAD_URL, upFile, callback);
    }

    public static JsonResult<ContractGroupDetail> contractInfo(String contractAddress) {
        return findByContractAddress(contractAddress);
    }

    public static JsonResult<List<ContractGroupDetail>> homePage() {
        return HttpUtils.post(MethodConstant.Contract.HOME_PAGE, (Map<String, Object>) null, new TypeReference<JsonResult<List<ContractGroupDetail>>>() {
        });

    }
}
