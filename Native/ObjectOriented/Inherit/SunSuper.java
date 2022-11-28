package Inherit;

public class SunSuper extends SonSuper {
    public void SunSuper(int sunSalary) {
        super.SonSuper(100000);
        System.out.println("SunSuper Constructor");
    }
    public void sunFn() {
        System.out.println("SunFn");
    }
    int sunSalary;
    public void getSalary() {
        System.out.println(super.fatherSalary);
        System.out.println(super.sonSalary);
        super.fatherFn();
        super.sonFn();
    }
    public static void main(String[] args) {
        SunSuper sun = new SunSuper();
        sun.sunFn();
        sun.getSalary();
    }
}
