package com.demo.testng;

import org.testng.annotations.*;

public class TestNgConfig {
    @BeforeGroups
    public void beforeGroups(){
        System.out.println("BeforeGroups: 配置方法将在之前运行组列表。 此方法保证在调用属于这些组中的任何一个的第一个测试方法之前不久运行。");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("BeforeSuite:在该套件的所有测试都运行在注释的方法之前，仅运行一次。 ");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("BeforeTest: 注释的方法将在属于<test>标签内的类的所有测试方法运行之前运行。");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("BeforeClass: 在调用当前类的第一个测试方法之前运行，注释方法仅运行一次。");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("BeforeMethod: 注释方法将在每个测试方法之前运行。");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("AfterMethod: 注释方法将在每个测试方法之后运行。");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterClass: 在调用当前类的第一个测试方法之后运行，注释方法仅运行一次");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("AfterTest: 注释的方法将在属于<test>标签内的类的所有测试方法运行之后运行。");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("AfterSuite: 在该套件的所有测试都运行在注释方法之后，仅运行一次。");
    }

    @AfterGroups
    public void afterGroups(){
        System.out.println("AfterGroups: 此配置方法将在之后运行组列表。该方法保证在调用属于任何这些组的最后一个测试方法之后不久运行。");
    }

    @DataProvider
    public Object[] testPrintData(){
        String[] data = new String[2];
        data[0] = "hangwu";
        data[1] = "hangwu";
        return data;
    }
}
