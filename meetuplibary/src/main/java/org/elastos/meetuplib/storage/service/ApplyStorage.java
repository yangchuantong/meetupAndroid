package org.elastos.meetuplib.storage.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.elastos.meetuplib.storage.conf.MethodConstant;
import org.elastos.meetuplib.storage.util.HttpUtils;
import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.util.List;

/**
 * @author hb.nie
 * @description
 */
public class ApplyStorage {
    public static JsonResult<Apply> createApply(Apply apply, String contractAddress) {
        String string = JSONObject.toJSONString(apply);
        JSONObject jsonObject = JSONObject.parseObject(string);
        jsonObject.put("contractAddress", contractAddress);
        return HttpUtils.post(MethodConstant.Apply.CREATE, jsonObject.toJSONString(), new TypeReference<JsonResult<Apply>>() {
        });
    }

    public static JsonResult<ApplyDetail> applyDetail(Apply apply) {
        return HttpUtils.post(MethodConstant.Apply.APPLY_DETAIL, JSONObject.toJSONString(apply), new TypeReference<JsonResult<ApplyDetail>>() {
        });
    }

    public static JsonResult<ApplyDetail> auditApply(Apply apply, String contractAddress) {
        String jsonString = JSONObject.toJSONString(apply);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        jsonObject.put("contractAddress", contractAddress);
        return HttpUtils.post(MethodConstant.Apply.AUDIT, jsonObject.toJSONString(), new TypeReference<JsonResult<ApplyDetail>>() {
        });
    }

    public static JsonResult<List<ApplyDetail>> ownerApplyDetailList(Apply apply) {
        return HttpUtils.post(MethodConstant.Apply.OWNER_APPLY_DETAIL_LIST, JSONObject.toJSONString(apply), new TypeReference<JsonResult<List<ApplyDetail>>>() {
        });
    }


    public static    JsonResult<ApplyDetail> burn(Apply apply, String contractAddress) {
        String string = JSONObject.toJSONString(apply);
        JSONObject jsonObject = JSONObject.parseObject(string);
        jsonObject.put("contractAddress", contractAddress);
        return HttpUtils.post(MethodConstant.Apply.BURN, jsonObject.toJSONString(), new TypeReference<JsonResult<ApplyDetail>>() {
        });
    }
}
