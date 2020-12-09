package suite;

import org.testng.annotations.*;

/**
 * 测试套件中的配置文件，包括测试前的准备，以及测试后的结果处理
 */

public class SuiteConfig {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite------------");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest--------");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite------");
    }

}
