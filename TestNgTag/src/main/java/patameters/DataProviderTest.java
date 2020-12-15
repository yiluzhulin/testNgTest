package patameters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * 参数化测试-DataProvider
 * 通过DataProvider标签获取参数
 *
 */
public class DataProviderTest {

    //指定DataProvider的名字
    @Test(dataProvider = "myData")
    public void test(String name, int age) {
        System.out.println("name=" + name + "; age=" + age);
    }

    //定义DataProvider的具体参数名和值
    @DataProvider(name = "myData")
    public Object[][] providerData() {
        Object[][] data= new Object[][]{
            {"Lucy",10},
            {"Tom",21}
        };
        return data;
    }

    //指定不同的方法获取不同的参数值，两个方法使用同一个DataProvier参数
    @Test(dataProvider = "myDataMethod")
    public void testGirl(String name, int age) {
        System.out.println("TestGirl--name=" +name + "--age=" + age);
    }

    @Test(dataProvider = "myDataMethod")
    public void testBoy(String name, int age){
        System.out.println("TestBoy--name=" +name + "--age=" + age);
    }

    //DataProvider通过Method method获取到使用参数的方法信息
    @DataProvider(name = "myDataMethod")
    public Object[][] providerDataMethod(Method method) {
        Object[][] data= null;
        //判断调用的方法名是否等于指定值
        if(method.getName().equals("testGirl")) {
            data=new Object[][]{
                    {"Lily",16},
                    {"Mary",13}

            };
        }else if (method.getName().equals("testBoy")) {
            data = new Object[][] {
                    {"Joy",15},
                    {"Simth",18}
            };
        }
        return data;
    }
}
