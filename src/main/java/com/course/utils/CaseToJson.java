package com.course.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CaseToJson {
    public static  <T> String caseToJson(T t){
        String json = JSONObject.toJSONString(t);
        return json;
    }
}
