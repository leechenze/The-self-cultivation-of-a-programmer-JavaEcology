import java.util.ArrayList;
import java.util.Collections;

public class LambdaOmit {
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();

        persons.add(new Person("张学友", 58, 176));
        persons.add(new Person("郭富城", 54, 171));
        persons.add(new Person("刘德华", 58, 174));
        persons.add(new Person("黎明", 53, 178));


        Collections.sort(persons, (p1, p2) -> p1.getAge() - p2.getAge());

        // for (Person person : persons) {
        //     System.out.println(person);
        // }

        persons.forEach(p -> System.out.println(p));

    }
}
