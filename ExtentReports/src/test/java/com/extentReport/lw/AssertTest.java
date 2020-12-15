package com.extentReport.lw;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class AssertTest {
    @Test
    public void test1() {
        Assert.assertEquals(1,2);
    }

    @Test
    public void test2() {
        Assert.assertEquals(1,1);
    }

    @Test
    public void test3() {
        Assert.assertEquals("abc","abc");
    }

    @Test
    public void logReport() {
        Reporter.log("这是report-log中的日志信息");
    }
}
