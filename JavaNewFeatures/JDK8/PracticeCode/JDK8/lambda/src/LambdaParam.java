import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class LambdaParam {
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("刘德华", 58, 174));
        persons.add(new Person("张学友", 58, 176));
        persons.add(new Person("郭富城", 54, 171));
        persons.add(new Person("黎明", 53, 178));


        // Collections.sort(persons, new Comparator<Person>() {
        //     @Override
        //     public int compare(Person o1, Person o2) {
        //         return o2.getAge() - o1.getAge();
        //     }
        // });

        // Lambda表达式实现 Comparator
        Collections.sort(persons, (Person p1, Person p2) -> {
            return p1.getAge() - p2.getAge();
        });

        for (Person person : persons) {
            System.out.println(person);
        }

    }
}
