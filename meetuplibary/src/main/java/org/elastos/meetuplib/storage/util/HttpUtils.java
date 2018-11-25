package org.elastos.meetuplib.storage.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.elastos.meetuplib.tool.util.DateUtils;
import org.elastos.meetuplib.tool.util.JsonResult;
import org.elastos.meetuplib.tool.util.MessageConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author hb.nie
 * http Utils
 */
public class HttpUtils {

    private static final int TIME_OUT = 30;
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();

    /**
     * @param address  请求地址
     * @param file     上传的文件
     * @param callback 异步响应
     * @throws FileNotFoundException
     */
    public static void uploadFileAsync(String address, File file, Callback callback) throws FileNotFoundException {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (file.exists()) {
            String TYPE = "application/octet-stream";
            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);

            RequestBody requestBody = builder
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("getFileUrl", file.getName(), fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(address)
                    .put(requestBody)
                    .build();
            client.newCall(request).enqueue(callback);
        } else {
            throw new FileNotFoundException();
        }

    }

    /**
     * @param address  请求地址
     * @param callback 异步接受处理结果
     * @param map      需要发送的参数
     */
    public static void postAsync(String address, Callback callback, Map<String, Object> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (map == null) {
            map = new HashMap<>();
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static String post(String url) {
        return post(url, (Map<String, Object>) null);
    }

    public static String post(String url, String jsonString) {
        return post(url, JSONObject.parseObject(jsonString));
    }


    public static <u> u post(String url, String jsonString, TypeReference<u> jsonResultTypeReference) {
        String post = post(url, jsonString);
        return JSONObject.parseObject(post, jsonResultTypeReference);
    }

    public static <u> u post(String url, Map<String, Object> map, TypeReference<u> jsonResultTypeReference) {
        String post = post(url, map);
        return JSONObject.parseObject(post, jsonResultTypeReference);
    }

    public static <u> u post(String url, TypeReference<u> jsonResultTypeReference) {
        String post = post(url, (Map<String, Object>) null);
        return JSONObject.parseObject(post, jsonResultTypeReference);
    }

    /**
     * post 请求
     *
     * @param postUrl 请求地址
     * @param map     参数
     * @return String
     * @throws Exception
     */
    public static String post(String postUrl, Map<String, Object> map) {
        System.out.println(postUrl);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (map == null) {
            map = new HashMap<>();
            map.put("REQ" , DateUtils.unixTime()) ;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }

        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(requestBody)
                .build();
        final String[] responseString = new String[1];
        final Exception[] exception = new Exception[1];
        final boolean[] isExecuted = new boolean[1];
        long l = System.currentTimeMillis();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                boolean executed = call.isExecuted();
                isExecuted[0] = executed;
                exception[0] = e;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean executed = call.isExecuted();
                isExecuted[0] = executed;
                if (executed) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        responseString[0] = body.string();
                    }

                }
            }
        });

        while (!isExecuted[0] && ((System.currentTimeMillis() - l) / 1000) < TIME_OUT) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (exception[0] != null) {
            exception[0].printStackTrace();
        } else {
            return responseString[0];
        }
        return null;
    }

    public static String get(String getUrl) {
        return get(getUrl, null);
    }

    /**
     * post 请求
     *
     * @param getUrl 请求地址
     * @param map    参数
     * @return String
     * @throws Exception
     */
    public static String get(String getUrl, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        getUrl += toUrl(map);
        Request request = new Request.Builder()
                .url(getUrl)
                .get()
                .build();
        final String[] responseString = new String[1];
        final Exception[] exception = new Exception[1];
        final boolean[] isExecuted = new boolean[1];
        long l = System.currentTimeMillis();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                boolean executed = call.isExecuted();
                isExecuted[0] = executed;
                exception[0] = e;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean executed = call.isExecuted();
                isExecuted[0] = executed;
                if (executed) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        responseString[0] = body.string();
                    }

                }
            }
        });

        while (!isExecuted[0] && ((System.currentTimeMillis() - l) / 1000) < TIME_OUT) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (exception[0] != null) {
            exception[0].printStackTrace();
        } else {
            return responseString[0];
        }
        return null;
    }

    private static String toUrl(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder("?");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            stringBuilder.append(key).append("=").append(value.toString()).append("&");

        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }


    /**
     * @param uri    服务器地址
     * @param upFile 带上传的文件
     * @return 图片地址
     * @throws IOException
     */
    public static JsonResult uploadFile(String uri, File upFile) throws IOException {
        long l = System.currentTimeMillis();
        final boolean[] isExecuted = new boolean[1];
        final String[] string = new String[1];
        final Exception[] exception = new Exception[1];
        HttpUtils.uploadFileAsync(uri, upFile, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isExecuted[0] = call.isExecuted();
                exception[0] = e;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isExecuted[0] = call.isExecuted();
                ResponseBody body = response.body();
                if (body != null) {
                    string[0] = body.string();
                }
            }
        });


        while (!isExecuted[0] && ((System.currentTimeMillis() - l) / 1000) < TIME_OUT) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return JsonResult.error(MessageConstant.SYSTEM_ERROR, e.getMessage());
            }
        }
        if (exception[0] != null) {
            exception[0].printStackTrace();
        } else {
            return JSONObject.parseObject(string[0], new TypeReference<JsonResult<String>>() {
            });
        }
        return JsonResult.error(MessageConstant.SYSTEM_ERROR, "");
    }
}
