package org.elastos.meetuplib.tool.util;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 结果集工具类
 */
@SuppressWarnings("unchecked")
public class JsonResult<T> implements Serializable {

    private String code;
    private Boolean success;
    private String message;
    private T data;

    public JsonResult() {

    }

    public JsonResult(boolean success, String code, String message) {
        this(success, code, message, null);
    }

    public JsonResult(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;

    }


    public static JsonResult success() {
        return new JsonResult<>(true, MessageConstant.SUCCESS_CODE, MessageConstant.SUCCESS_MESSAGE);
    }

    public static <U> JsonResult<U> success(U data) {
        return new JsonResult<>(true, MessageConstant.SUCCESS_CODE, MessageConstant.SUCCESS_MESSAGE, data);
    }

    public static JsonResult error() {
        return new JsonResult<>(false, MessageConstant.ERROR_CODE, MessageConstant.ERROR_MESSAGE);
    }

    public static <U> JsonResult<U> error(U data) {
        return new JsonResult<>(false, MessageConstant.ERROR_CODE, MessageConstant.ERROR_MESSAGE, data);

    }


    public static JsonResult error(String code, String message) {
        return new JsonResult<>(false, code, message);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
