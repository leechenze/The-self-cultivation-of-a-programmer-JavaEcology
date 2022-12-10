package InernalClass;

public class InernalClass {
    int a;
    public int z;
    private int k;
    class A {
        int a = 0;
        public void setTest3Fileds(){
            InernalClass.this.a = 1;
            InernalClass.this.z = 2;
            InernalClass.this.k = 3;
        }
    }
    public void setInfo(){
        // 外部类使用内部类时首先要实例化
        new A().setTest3Fileds();
    }
    public void getInfo(){
        System.out.println(this.a);
        System.out.println(this.z);
        System.out.println(this.k);
    }
    abstract class B {
        // ...
    }
    class C extends B{}
}
