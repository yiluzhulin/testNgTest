package groups;

import org.testng.annotations.Test;

/**
 * 类分组-2
 */
@Test(groups = "cStu")
public class GroupClass2 {
    public void test1() {
        System.out.println("GroupClass2-test1----");
    }

    public void test2() {
        System.out.println("GroupClass2-test2----");
    }
}
