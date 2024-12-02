package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /*
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        // 创建几个 ComplexOomage 实例，利用 powers of 256
        // 例如，创建 10 个示例，保证它们的哈希值相同或相似
        for (int i = 0; i < 10; i++) {
            // 这里我们创建的值都是 0 或某个特定组合造成碰撞
            // 每个 ComplexOomage 都会包含长度为 4 的相同元素，构造出有相同哈希值的对象
            // 利用具体的值，每个数字都是 0 或 255，使其具有相似的哈希值。
            List<Integer> values = Arrays.asList(0, 0, 0, 255); // 所有值都为 0，最后一个为 255

            // 也可以考虑如 powers of 256 的组合
            // 举例子，下面组合也很重要
            // List<Integer> values = Arrays.asList(0, 0, 256, 0); // 在这里要保证都是 0 或者特别是 256 的情况

            deadlyList.add(new ComplexOomage(values));
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
