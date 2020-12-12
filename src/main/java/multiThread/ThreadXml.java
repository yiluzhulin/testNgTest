package multiThread;

import org.testng.annotations.Test;

/**
 * 多线程执行
 * 在xml文件中，配置多线程运行
 */
public class ThreadXml {
    @Test
    public void test1() {
        System.out.printf("Test1-ThreadId:   %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test2() {
        System.out.printf("Test2-ThreadId:  %s%n", Thread.currentThread().getId());
    }

    @Test
    public void test3() {
        System.out.printf("Test3-ThreadId:  %s%n", Thread.currentThread().getId());
    }
}
