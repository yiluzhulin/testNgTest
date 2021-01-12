package patameters;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParaTmp {
    @Test
    @Parameters({"name","age"})
    public void test(String name,int age) {
        System.out.println("name:" + name);
        System.out.println("age:" + age);
    }
}
