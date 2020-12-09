package suite;

import org.testng.annotations.*;

/**
 * 测试套件中，实际执行的方法集合
 */

public class SuiteTest1 {
    @Test
    public void test1() {
        System.out.println("SuiteTest1--Test1-----");
    }

    @Test
    public void test2() {
        System.out.println("SuiteTest1---Test2--------");
    }
}
