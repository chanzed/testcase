package com.course.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.helper.DataUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GetUserListTest {
    @Test(groups = "getUserList", dependsOnGroups = "loginTrue", description = "获取性别为男的用户信息")
    public void getUserList() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase", 1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        
        //发送请求获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

        //验证
        List<User> userList = session.selectList(getUserListCase.getExpected(), getUserListCase);
        for(User u : userList){
            System.out.println("获取到的user： " + u.toString());
        }
        //List<User>转换成JSONArray
        JSONArray userListJson = JSON.parseArray(JSON.toJSONString(userList));
        System.out.println(userListJson);

        System.out.println("1"+resultJson);
        System.out.println("2" + userListJson);
        Assert.assertEquals(userListJson.size(), resultJson.size());

        for(int i = 0; i < resultJson.size(); i++){
            JSONObject expect =(JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }

    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        HttpClientContext context = new HttpClientContext();

        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("age", getUserListCase.getAge());
        param.put("gender", getUserListCase.getGender());

        post.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        context.setCookieStore(TestConfig.store);

        HttpResponse response = TestConfig.httpClient.execute(post,context);

        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray jsonArray = new JSONArray(Collections.singletonList(result));
        System.out.println(jsonArray.toString());
        return  jsonArray;

    }
}
