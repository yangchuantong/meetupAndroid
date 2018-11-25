package org.elastos.meetup.tools;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by xianxian on 2018/11/24.
 */

public class IpAdressUtils {
        /**
         * gps获取ip
         * @return
         */
        public static String getLocalIpAddress()
        {
            try
            {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
                {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                    {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress())
                        {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
            catch (Exception e) {
               e.printStackTrace();
            }
            return null;
        }

        /**
         * wifi获取ip
         * @param context
         * @return
         */
        public static String getIp(Context context){
            try {
                //获取wifi服务
                WifiManager wifiManager = (WifiManager)context. getSystemService(Context.WIFI_SERVICE);
                //判断wifi是否开启
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ip = intToIp(ipAddress);
                return ip;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 格式化ip地址（192.168.11.1）
         * @param i
         * @return
         */
        private static String intToIp(int i) {

            return (i & 0xFF ) + "." +
                    ((i >> 8 ) & 0xFF) + "." +
                    ((i >> 16 ) & 0xFF) + "." +
                    ( i >> 24 & 0xFF) ;
        }
        /**
         *  3G/4g网络IP
         */
        public static String getIpAddress() {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



    }
