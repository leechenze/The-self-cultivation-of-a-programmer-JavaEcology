import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsColl {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("b");
        list.add("cd");
        list.add("ca");
        list.add("a");
        list.add("1");
        System.out.println(list);
        // 反转list
        Collections.reverse(list);
        System.out.println(list);
        // 随机排序list
        Collections.shuffle(list);
        System.out.println(list);
        // 对元素按升序排列
        Collections.sort(list);
        System.out.println(list);
        // 对元素进行指定的比较器进行排列
        Student s1 = new Student(14, "lincoln");
        Student s2 = new Student(2, "trump");
        Student s3 = new Student(8, "engels");
        Student s4 = new Student(12, "douglas");
        List<Student> stus = new ArrayList<Student>();
        stus.add(s1);
        stus.add(s2);
        stus.add(s3);
        stus.add(s4);
        for(Student stu : stus) {
            System.out.println(stu.name + " " + stu.age);
        }
        Collections.sort(stus, new Student());
        System.out.println("===========================");
        for(Student stu : stus) {
            System.out.println(stu.name + " " + stu.age);
        }
        // 替换集合中的两个元素
        Collections.swap(list, 0, 4);
        System.out.println(list);
        // 获取集合最大值
        System.out.println(Collections.max(list));
        // 获取集合最小值
        System.out.println(Collections.min(list));
        // 根据Comparator获取集合中对象的最大值
        Student maxs = Collections.max(stus, new Student());
        System.out.println(maxs.name + " " + maxs.age);
        Student mins = Collections.min(stus, new Student());
        System.out.println(mins.name + " " + mins.age);
        // 获取元素出现的次数
        list.add("a");
        list.add("a");
        System.out.println(Collections.frequency(list, "a"));
        // 元素值替换
        Collections.replaceAll(list, "a", "x");
        System.out.println(list);
    }
}

class Student implements Comparator<Student> {
    int age;
    String name;
    public Student() {

    }

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    // 更年龄升序排列
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.age > o2.age) {
            return 1;
        }else if(o1.age < o2.age) {
            return -1;
        }else{
            return 0;
        }
    }
}