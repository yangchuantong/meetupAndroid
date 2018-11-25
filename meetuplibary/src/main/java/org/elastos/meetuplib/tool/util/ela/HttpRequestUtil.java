//
// Source code recreated from a .class FILE by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elastos.meetuplib.tool.util.ela;


import com.alibaba.fastjson.JSONObject;

public class HttpRequestUtil   {



    public static JSONObject httpGet(String url) {
        String s = HttpKit.get(url);

        return JSONObject.parseObject(s);
    }

    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        return httpPost(url, jsonParam, false);
    }

    public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
        String post = HttpKit.post(url, jsonParam.toString());
        return JSONObject.parseObject(post);}
}
