import org.testng.annotations.*;

/**
 * 介绍testNG最基础的标签
 */

public class BasicTag {

    //实际测试执行的方法
    @Test
    public void test1() {
        System.out.println("Test1-----");
    }

    //在标签后面，增加enable=false，则该方法不会被执行。
    // 不写或者=true，则表示该方法执行
    @Test(enabled = false)
    public void test2() {
        System.out.println("Test2-----");
    }

    //在测试方法执行前，运行的内容
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod-----");
    }

    //在测试方法执行后，运行的内容
    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod-----");
    }

    //在测试类执行前，执行的方法
    @BeforeClass
    public void beforeClass() {
        System.out.printf("BeforeClass------");
    }

    //在测试类执行后，执行的方法
    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass----");
    }

    //在测试套件执行前，执行的方法
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite-----");
    }

    //在测试套件执行后，执行的方法
    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite-----");
    }

    //在测试套件执行后，类执行前，执行的方法
    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest-----");
    }

    //在类执行完成后，测试套件执行完成前，执行的方法
    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest------");
    }
}
