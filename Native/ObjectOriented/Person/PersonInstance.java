package Person;

public class PersonInstance {
    public static void main(String[] args) {
        Person person = new Person();
        person.name = "zhangsan";
        person.showName();
        int age = person.getAge();
        System.out.println(age);
        person.age = 20;
        int age1 = person.getAge();
        System.out.println(age1);
    }

}
