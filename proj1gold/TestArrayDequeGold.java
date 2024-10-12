import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Random;

import java.util.Random;

public class TestArrayDequeGold {
    @Test
    public void TestStudentArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        Random rand = new Random(); // 使用 Java 的 Random 类生成随机数
        StringBuilder log = new StringBuilder(); // 使用 StringBuilder 进行高效字符串拼接构建操作日志

        for (int i = 0; i < 1000; i++) {
            int operation;// 用于保存当前操作
            if (solutionDeque.isEmpty()) {
                int addNumber = rand.nextInt(1000);
                operation = rand.nextInt(2);
                if (operation == 0) {
                    log.append("addFirst(").append(addNumber).append(")\n");
                    studentDeque.addFirst(addNumber);
                    solutionDeque.addFirst(addNumber);
                } else {
                    log.append("addLast(").append(addNumber).append(")\n");
                    studentDeque.addLast(addNumber);
                    solutionDeque.addLast(addNumber);
                }
            } else {
                operation = rand.nextInt(4); //随机选择操作类型
                int addNumber = rand.nextInt(1000);
                Integer StudentRemoveNumber = null;
                Integer SolutionRemoveNumber = null;

                switch (operation) {
                    case 0:
                        log.append("addFirst(").append(addNumber).append(")\n");
                        studentDeque.addFirst(addNumber);
                        solutionDeque.addFirst(addNumber);
                    case 1:
                        log.append("addLast(").append(addNumber).append(")\n");
                        studentDeque.addLast(addNumber);
                        solutionDeque.addLast(addNumber);
                    case 2:
                        log.append("removeFirst()\n");
                        StudentRemoveNumber = studentDeque.removeFirst();
                        SolutionRemoveNumber = solutionDeque.removeFirst();
                        break;
                    case 3:
                        log.append("removeLast()\n");
                        StudentRemoveNumber = studentDeque.removeLast();
                        SolutionRemoveNumber = solutionDeque.removeLast();
                        break;
                }
                if (StudentRemoveNumber != null || SolutionRemoveNumber != null){
                    assertEquals(log.toString(),StudentRemoveNumber,SolutionRemoveNumber);
                }
            }
        }
    }
}
