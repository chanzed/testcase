package com.course.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.CaseToJson;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 2);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        //获取返回结果
        String resultJson = getJsonResult(getUserInfoCase);

        User user = session.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);

//        List userList = new ArrayList();
//        userList.add(user);
        String expectedJson = JSONObject.toJSONString(user);
        //验证
        Assert.assertEquals(resultJson, expectedJson);
    }

    private String getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        HttpClientContext context = new HttpClientContext();
        //添加参数
        JSONObject param = new JSONObject();
        param.put("id", getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        //设置头信息
        post.setHeader("content-type","application/json");

        post.setEntity(entity);
        //设置cookie信息
        context.setCookieStore(TestConfig.store);

        //获取返回信息
        HttpResponse response = TestConfig.httpClient.execute(post,context);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        String resultJson = JSONObject.toJSONString(result);
        return  resultJson;
    }
}
