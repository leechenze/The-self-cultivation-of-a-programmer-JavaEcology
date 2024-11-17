import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StreamIntro {
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "张无忌", "张三丰", "李世民", "秦始皇", "张强");
        // 1.拿到所有姓张的
        ArrayList<String> zhangList = new ArrayList<>();
        for (String name : list) {
            if (name.startsWith("张")) {
                zhangList.add(name);
            }
        }
        // 2.拿到名字长度为三个字的
        ArrayList<String> threeList = new ArrayList<>();
        for (String name : zhangList) {
            if (name.length() == 3) {
                threeList.add(name);
            }
        }
        // 3.打印这个集合
        for (String name : threeList) {
            System.out.println("name = " + name);
        }

        System.out.println("----------------------------------------");

        // Stream流实现需求:
        // 1.拿到所有姓张的 2.拿到名字长度为三个字的 3.打印这个集合
        list.stream().filter((s) -> {
            return s.startsWith("张");
        }).filter(s -> {
            return s.length() == 3;
        }).forEach(item -> {
            System.out.println("name = " + item);
        });
    }
}
