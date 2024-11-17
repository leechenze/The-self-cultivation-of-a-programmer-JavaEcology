import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ClassNameRefNewConstruct {
    public static void main(String[] args) {
        // Supplier<Person> supplier = () -> {
        //     return new Person();
        // };
        Supplier<Person> supplier = Person::new;
        Person person = supplier.get();
        System.out.println("person = " + person);


        // BiFunction<String, Integer, Person> bfn = (String name, Integer age) -> {
        //     return new Person(name, age);
        // };
        BiFunction<String, Integer, Person> bfn = Person::new;
        Person douglas = bfn.apply("douglas", 26);
        System.out.println("douglas = " + douglas);
    }
}
