import org.testng.annotations.Test;

/**
 * 依赖测试
 * 测试方法1依赖测试方法2时，执行方法1前会先执行方法2，如果方法2失败了，则方法1也不执行，否则方法1执行
 * 方法2可以看成是方法1执行的前提条件
 */
public class DependONMethod {
    @Test
    public void test1() {
        System.out.println("Test1-----");
    }

    @Test(dependsOnMethods = "test1")
    public void test2() {
        System.out.println("Test2-----");
    }

    @Test
    public void test3() {
        System.out.println("test3------");
        throw new RuntimeException();
    }

    @Test(dependsOnMethods = "test3")
    public void test4() {
        System.out.println("test4----");
    }
}
