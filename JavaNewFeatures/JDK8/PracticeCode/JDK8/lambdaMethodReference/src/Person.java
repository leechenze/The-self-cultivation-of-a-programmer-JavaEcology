public class Person {
    private String name;
    private Integer age;

    public Person() {
        System.out.println("执行无参构造");
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        System.out.println("执行有参构造: " + name + ", " + age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
