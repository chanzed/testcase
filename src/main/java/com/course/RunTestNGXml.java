package com.course;

import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class RunTestNGXml {
    public static void main(String[] args){
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<String>();
        suites.add("C:\\interfaceAutoTest\\autoTest\\Chapter12\\src\\main\\resources\\testng.xml");
        testNG.setTestSuites(suites);
        testNG.run();
    }
}
