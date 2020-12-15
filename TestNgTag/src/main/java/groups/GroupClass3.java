package groups;

import org.testng.annotations.Test;

/**
 * 类分组-3
 */
@Test(groups = "cTeacher")
public class GroupClass3 {
    public void test1() {
        System.out.println("GroupClass3-test1----");
    }

    public void test2() {
        System.out.println("GroupClass3-test2----");
    }
}
