package Inherit;

public class FatherSuper {
//    public void FatherSuper() {
//        System.out.println("FatherSuper Constructor");
//    }
    public void FatherSuper(int fatherSalary) {
        this.fatherSalary = fatherSalary;
    }
    int fatherSalary;
    public void fatherFn() {
        System.out.println("FatherFn");
    }
}
