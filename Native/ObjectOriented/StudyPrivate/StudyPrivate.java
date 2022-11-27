package StudyPrivate;

public class StudyPrivate {
    private int age;
    public void printAge(){
        System.out.println(age);
    }
    public void setAge(int a){
        if(a <= 150 && a >= 0){
            age = a;
        }else{
            System.out.println("输入的年龄范围不符");
        }
    }
}
