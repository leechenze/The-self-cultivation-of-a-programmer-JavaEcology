package Inherit;

public class Person {
    int age;
    String name;
    int sex;
    public void showInfo() {
        System.out.println(age);
        System.out.println(name);
        System.out.println(sex);
    }

    public void setInfo(int age, String name, int sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }
}
