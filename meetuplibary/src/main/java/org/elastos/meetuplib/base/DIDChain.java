package org.elastos.meetuplib.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.elastos.meetuplib.base.entity.DIDAccount;
import org.elastos.meetuplib.base.entity.DIDInfo;
import org.elastos.meetuplib.base.entity.Sign;
import org.elastos.meetuplib.tool.util.DatatypeConverter;
import org.elastos.meetuplib.tool.util.StringUtil;
import org.elastos.meetuplib.tool.util.ela.ECKey;
import org.elastos.meetuplib.tool.util.ela.ElaSignTool;
import org.elastos.meetuplib.tool.util.ela.HttpKit;
import org.elastos.meetuplib.tool.util.ela.SignTool;
import org.elastos.meetuplib.tool.util.ela.Util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangchuantong
 * @Description:
 * @Date:Created in  2018/11/24 15:08
 */
public class DIDChain {
    private static final String CHARSET = "UTF-8";

    private static final String DID_SERVER = "http://18.179.20.67:8080";

    // private static final String DID_SERVER = "http://127.0.0.1:8091";

    /**
     * 创建did
     *
     * @return
     */
    public static DIDAccount create() {
        DIDAccount didAccount = new DIDAccount();
        return didAccount;
    }


    /**
     * 通过私钥获取did
     *
     * @param privateKey 私钥
     * @return
     */
    public static DIDAccount RetriveDID(String privateKey) {
        DIDAccount didAccount = new DIDAccount(privateKey);
        return didAccount;
    }


    /**
     * 获取余额
     *
     * @param address 地址
     * @return 余额
     */
    public static String balance(String address) {

        String result = HttpKit.get(DID_SERVER + "/api/1/balance/" + address);
        //解析返回值
        if (StringUtil.isBlank(result)) {
            return "0";
        }
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.getString("status").equals("200")) {
            return resultJson.getString("result");
        } else {
            return "0";
        }
    }


    /**
     * 设置did 信息
     *
     * @param parKey fee支付方私钥
     * @param priKey 设置did信息地址私钥
     * @param map    设置信息
     * @return txid
     */
    public static String setDID(String parKey, String priKey, Map<String, Object> map) {
        //组装参数
        Map settingsMap = new HashMap();//settingsMap
        settingsMap.put("privateKey", priKey);
        settingsMap.put("info", map);
        Map paramMap = new HashMap();//paramMap
        paramMap.put("privateKey", parKey);
        paramMap.put("settings", settingsMap);
        String param = JSON.toJSONString(paramMap);
        //发送请求
        String result = sendPost(DID_SERVER + "/api/1/setDidInfo", param);
        //解析返回值
        if (StringUtil.isBlank(result)) {
            return null;
        }
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.getString("status").equals("200")) {
            return resultJson.getString("result");
        } else {
            return null;
        }


    }


    /**
     * 获取did信息
     *
     * @param txidlist 设置信息时返回txid数组
     * @param key      信息属性
     * @return did信息
     */

    public static DIDInfo getDIDInfo(List<String> txidlist, String key) {

        DIDInfo didInfo = new DIDInfo();
        didInfo.setDid(key);
        //组装参数
        Map paramMap = new HashMap();
        paramMap.put("txIds", txidlist);
        paramMap.put("key", key);
        String param = JSON.toJSONString(paramMap);
        //发送请求
        String result = sendPost(DID_SERVER + "/api/1/setDidInfo", param);
        //解析返回值
        if (StringUtil.isBlank(result)) {
            return didInfo;
        }

        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.getString("status").equals("200")) {
            JSONObject data = resultJson.getJSONObject("result");
            didInfo.setDid(data.getString("did"));
            didInfo.setData(data.getJSONObject("data"));
            return didInfo;
        } else {
            return didInfo;
        }

    }


    /**
     * 使用私钥签名信息
     *
     * @param msg        信息
     * @param privateKey 私钥
     * @return
     */
    public static Sign sign(String msg, String privateKey) throws Exception {
        Sign sign = new Sign();
        ECKey ec = ECKey.fromPrivate(DatatypeConverter.parseHexBinary(privateKey));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.write(msg.getBytes(CHARSET));
        byte[] signature = SignTool.doSign(baos.toByteArray(), DatatypeConverter.parseHexBinary(privateKey));
        byte[] code = new byte[33];
        System.arraycopy(Util.CreateSingleSignatureRedeemScript(ec.getPubBytes(), 1), 1, code, 0, code.length);
        sign.setMsg(DatatypeConverter.printHexBinary(msg.getBytes(CHARSET)));
        sign.setPub(DatatypeConverter.printHexBinary(code));
        sign.setSig(DatatypeConverter.printHexBinary(signature));
        return sign;
    }

    /**
     * 验证签名
     *
     * @param hexMsg
     * @param hexPub
     * @param hexSig
     * @return
     */
    public static Boolean verify(String hexMsg, String hexSig, String hexPub) {
        byte[] msg = DatatypeConverter.parseHexBinary(hexMsg);
        byte[] sig = DatatypeConverter.parseHexBinary(hexSig);
        byte[] pub = DatatypeConverter.parseHexBinary(hexPub);
        boolean isVerify = ElaSignTool.verify(msg, sig, pub);
        return isVerify;
    }


    /**
     * msg转明文
     *
     * @param msg
     * @return 明文
     */
    public String msgToString(String msg) throws UnsupportedEncodingException {
        byte[] bytes = DatatypeConverter.parseHexBinary(msg);
        String s = new String(bytes, "UTF-8");
        return s;
    }



    private  static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }



}
