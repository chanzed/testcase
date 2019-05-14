package com.course.cases;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.CaseToJson;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoginTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作")
    public void beforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.httpClient = HttpClients.createDefault();
    }

    @Test(groups = "loginTrue", description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1);

//        String jsonString = JSONObject.toJSONString(loginCase);
        String jsonString = CaseToJson.caseToJson(loginCase);
        System.out.println(JSONPath.read(jsonString, "$.userName"));
        System.out.println(jsonString + "-----------------------------------");

        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        
        //第一步发送请求
        String result = getResult(loginCase);
        //验证结果
        Assert.assertEquals(loginCase.getExpected(), result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        //创建一个CloseableHttpClient实体
        TestConfig.httpClient = HttpClients.createDefault();
        HttpClientContext context = new HttpClientContext();
        //创建一个post对象
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        //创建一个JSONObject用来存储post参数
        JSONObject param = new JSONObject();
        param.put("userName", loginCase.getUserName());
        param.put("password", loginCase.getPassword());

        //把参数放进post中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置头信息
        post.setHeader("content-type", "application/json");

        //获取返回信息
        HttpResponse response = TestConfig.httpClient.execute(post, context);
        System.out.println("返回的结果是" + response.getEntity());
        String result = EntityUtils.toString(response.getEntity(),"utf-8");

        //保存cookie信息
        TestConfig.store = context.getCookieStore();
        System.out.println("设置的cookie信息是：" + TestConfig.store);
        TestConfig.httpClient.close();
        return result;


    }

    @Test(groups = "loginFalse", description = "登录接口失败测试")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
    }
}
