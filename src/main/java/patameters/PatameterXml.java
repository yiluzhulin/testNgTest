package patameters;

import org.testng.annotations.*;

/**
 * 参数化测试-xml
 * 测试方法需要指定参数时，可以使用参数化测试
 */
public class PatameterXml {
    @Test
    @Parameters({"name","age"})
    public void info(String name, int age) {
        System.out.println("name=" + name + "; age=" + age);
    }
}
