import org.testng.annotations.Test;

/**
 * 异常测试
 * 场景：预期结果就是一个异常时，需要使用到异常测试
 */
public class ExceptedException {
    //指定异常测试的类型
    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExceptions() {
        System.out.println("这是异常测试的内容");
        //抛出指定的异常
        throw new RuntimeException();
    }
}
