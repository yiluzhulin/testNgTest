package multiThread;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 多线程执行
 * 通过标签设置多线程运行的次数
 */
public class ThreadTag {

    @BeforeMethod
    public void beforeMethod() {
        Long threadId = Thread.currentThread().getId();
        System.out.println("BeforeMethod--ThreadId:" + threadId);
    }

    @AfterMethod
    public void afterMethod() {
        Long threadId = Thread.currentThread().getId();
        System.out.println("After--ThreadId:" + threadId);
    }

    /**
     * threadPoolSize:线程池中的线程个数
     * invocationCount：执行的次数
     * timeOut：超时时间（毫秒）
     * ）
     */
    @Test(threadPoolSize = 2, invocationCount = 3, timeOut = 1000)
    public void test1() {
        //获取当前线程的线程ID
        Long threadId = Thread.currentThread().getId();
        System.out.println("Test1--ThreadId:" + threadId);
    }
}
