package org.elastos.meetup.config;


import android.util.Log;

import java.math.BigInteger;

/**
 * 功能说明: 美好广场项目系统配置
 * @author wangbin
 * @since 2015-08-18 15:05:34
 * @version 1.0.0
 */
public class SystemConfig {


    //全局日志级别
    public static final int LOG_LEVEL = Log.DEBUG;
    public static final boolean IS_DEBUG = true;
    public static boolean haswallet=false;
    public static String address="";
    public static String ethAddress="";
    public static String walletName="";
    public static String privatekey="";
    public static String did="";
    public static BigInteger gasPrice= BigInteger.valueOf(3000000000L);
    public static  BigInteger gasLimit= BigInteger.valueOf(2602513L);
    public static String linkType="ETH";
    public static String currencyUnit="CNY";//货币单位


    public class Keys{
        public static final String NATIVE_INFO_USER_PRIVATE = "native_info_user_private";
        public static final String NATIVE_INFO_APP_SYSTEM = "native_info_app_system";
        public static final String NATIVE_INFO_WALLET = "native_info_wallet";




    }




}
