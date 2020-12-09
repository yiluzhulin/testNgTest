package groups;

import org.testng.annotations.Test;

/**
 * 类分组-1
 */
@Test(groups = "cStu")
public class GroupClass1 {
    public void test1() {
        System.out.println("GroupClass1-test1----");
    }

    public void test2() {
        System.out.println("GroupClass1-test2----");
    }
}
