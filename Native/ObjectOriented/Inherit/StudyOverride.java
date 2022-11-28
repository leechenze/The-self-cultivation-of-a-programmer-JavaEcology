package Inherit;

public class StudyOverride extends Person {

    String school;

    @Override
    public void showInfo() {
        System.out.println("子类中showInfo方法的重写");
    }

    @Override
    public void setInfo(int age, String name, int sex) {
        System.out.println("子类中setInfo方法的重写");
    }

    public static void main(String[] args) {
        StudyOverride stuoverride = new StudyOverride();
        stuoverride.showInfo();
        stuoverride.setInfo(20, "李四", 1);
    }
}
