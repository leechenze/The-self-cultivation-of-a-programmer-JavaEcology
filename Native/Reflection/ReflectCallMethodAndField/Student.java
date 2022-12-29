package ReflectCallMethodAndField;

public class Student extends Person implements Study, Move {


    String school = "西北工业大学";
    String name;
    int age;
    public String publicField;
    private String privateField;

    @Override
    public void moveType() {

    }

    @Override
    public void studyInfo() {

    }

    private void test(String name) {
        System.out.println("private void test(String name) 方法执行");
    }

    public String getSchool() {
        return this.school;
    }

    public void setInfo(String name, String school) {
        this.name = name;
        this.school = school;
        System.out.println("public void setInfo(String name, String school) 方法执行");
    }

    public void setInfo(int age) {
        this.age = age;
        System.out.println("public void setInfo(int age) 方法执行");
    }
}
