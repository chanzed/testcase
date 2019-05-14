package com.course.cases;

import com.alibaba.fastjson.JSONObject;
import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UpdateUserInfoTest {
    @Test(dependsOnGroups = "loginTrue", description = "更新用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        int result = getResult(updateUserInfoCase);
        Thread.sleep(10000);
        System.out.println(updateUserInfoCase.getExpected());
        System.out.println(updateUserInfoCase.getUserId());
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        User user1 = session.selectOne("getUpdateUserInfo",updateUserInfoCase);
        System.out.println(user1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }



    @Test(dependsOnGroups = "loginTrue", description = "删除用户信息")
    public void deleteUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        int result = getResult(updateUserInfoCase);
        Thread.sleep(10000);
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }
    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id", updateUserInfoCase.getUserId());
        param.put("userName", updateUserInfoCase.getUserName());
        param.put("password", updateUserInfoCase.getPassword());
        param.put("gender", updateUserInfoCase.getGender());
        param.put("age", updateUserInfoCase.getAge());
        param.put("isDelete", updateUserInfoCase.getIsDelete());
        param.put("permission", updateUserInfoCase.getPermission());

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Cookie", String.valueOf(TestConfig.store));
        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);
        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity());

        return Integer.parseInt(result);
    }
}
