package com.course.cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

public class test1 {
    public static void main(String[] args) throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost post = new HttpPost("http://localhost:8888/v1/getUserInfo");
//
//        //设置参数类型
//        post.setHeader("Content-Type", "application/json");
//        //设置cookie
//        post.setHeader("Cookie", "login=true");
//
//        //传入参数
//        JSONObject param = new JSONObject();
//        param.put("userid", "2");
//        StringEntity entity = new StringEntity(param.toString(), "UTF-8");
//
//        System.out.println(entity);
//
//        post.setEntity(entity);
//
//        //执行Post并获取返回结果
//        HttpResponse response = httpClient.execute(post);
//        String result = EntityUtils.toString(response.getEntity());
//
//        System.out.println(result);
//
//        JSONArray resultArray = JSONArray.parseArray(result);
//        JSONArray resultArray1 = new JSONArray(Collections.singletonList(result));
//        System.out.println(resultArray);
//        System.out.println("这是" + resultArray1);
//
        String str = "{\"name\": \"luffy\", \"age\":\"19\"}";
        System.out.println(str);
        String json = JSONObject.toJSONString(str);
        System.out.println(json);
        JSONArray jsonArray = new JSONArray(Collections.singletonList(str));
        System.out.println(jsonArray);

        JSONObject param = new JSONObject();
        param.put("name", "luffy");
        param.put("age", "19");
        param.put("height", "182");
        System.out.println(param);
        System.out.println(param.toString());
        System.out.println(param.toJSONString());
        String str1 = (String) JSONPath.read(str, "$.");
        System.out.println(str1);

    }
}
