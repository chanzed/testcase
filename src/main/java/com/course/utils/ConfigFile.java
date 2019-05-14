package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaceName name){
        String address = bundle.getString("test.url");
        //接口路径
        String uri = "";
        //最终的测试地址
        String testUrl;

        switch(name){
            case LOGIN:
                uri = bundle.getString("login.uri");
                break;
            case ADDUSERINFO:
                uri = bundle.getString("addUser.uri");
                break;
            case GETUSERINFO:
                uri = bundle.getString("getUserInfo.uri");
                break;
            case GETUSERLIST:
                uri = bundle.getString("getUserList.uri");
                break;
            case UPDATEUSERINFO:
                uri = bundle.getString("updateUserInfo.uri");
                break;
                default:
                    System.out.println("错误的接口名字");
        }
        testUrl = address + uri;
        return testUrl;
    }
}
