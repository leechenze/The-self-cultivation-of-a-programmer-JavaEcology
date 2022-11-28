package Inherit;

public class SonSuper extends FatherSuper {
//    public SonSuper() {
//        System.out.println("SonSuper Constructor");
//    }
    public void SonSuper(int sonSalary) {
        this.sonSalary = sonSalary;
    }
    int sonSalary;
    public void sonFn() {
        System.out.println("SonFn");
    }
}
