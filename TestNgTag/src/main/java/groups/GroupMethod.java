package groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * 方法分组
 */
public class GroupMethod {
    @Test(groups = "stu")
    public void stuTest1() {
        System.out.println("GroupMethod-StuTest1----");
    }

    @Test(groups = "stu")
    public void stuTest2() {
        System.out.println("GroupMethod-StuTest2-----");
    }

    @Test(groups = "teacher")
    public void teacherTest1() {
        System.out.println("GroupMethod-TeacherTest1-----");
    }

    @Test
    public void test() {
        System.out.println("Test----");
    }

    @BeforeGroups("stu")
    public void beforeGroupsStu() {
        System.out.println("GroupMethod-beforeGroupStu----");
    }

    @AfterGroups("stu")
    public void afterGroupStu() {
        System.out.println("GroupMethod-afterGroupStu------");
    }
}
