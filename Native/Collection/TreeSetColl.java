import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetColl {
    public static void main(String[] args) {
        // 自然排序
//        Set<Integer> set = new TreeSet<Integer>();
//        set.add(5);
//        set.add(2);
//        set.add(3);
//        set.add(4);
//        System.out.println(set);
        // 定制排序
        Person p1 = new Person("trump", 18);
        Person p2 = new Person("obama", 20);
        Person p3 = new Person("clinton", 28);
        Person p4 = new Person("lincoln", 34);
        Set<Person> set = new TreeSet<Person>(new Person());
        set.add(p1);
        set.add(p2);
        set.add(p3);
        set.add(p4);
        for(Person p : set) {
            System.out.println(p.name + "   " + p.age);
        }
    }
}

// TreeSet定制排序
class Person implements Comparator<Person> {
    int age;
    String name;
    public Person() {};
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public int compare(Person o1, Person o2) {
        if(o1.age > o2.age){
            return 1;
        } else if (o1.age < o2.age) {
            return -1;
        }else{
            return 0;
        }
    }
}