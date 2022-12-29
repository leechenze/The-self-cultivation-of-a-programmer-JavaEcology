package ReflectConstructors;

public class Student extends Person1 {
    public String school;
    public String name;
    public int age;


    public Student() {
        System.out.println("调用的是 public Student()");
    }

    public Student(String school) {
        this.school = school;
        System.out.println("调用的是 public Student(String school)");
    }

    private Student(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("调用的是 private Student(String name, int age)");
    }

}
