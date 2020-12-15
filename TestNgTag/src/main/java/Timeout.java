import org.testng.annotations.Test;

/**
 * 超时测试
 * 场景：期待测试在指定时间内完成时,
 */
public class Timeout {
    //设置超时时间为3000ms,单位ms
    @Test(timeOut = 3000)
    public void testSuccess() throws InterruptedException{
        Thread.sleep(2000);
    }

    @Test(timeOut = 2000)
    public void testFail() throws InterruptedException{
        Thread.sleep(3000);
    }
}
