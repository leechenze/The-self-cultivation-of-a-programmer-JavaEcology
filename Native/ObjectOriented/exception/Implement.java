package exception;

public class Implement {
    public static void main(String[] args) {
        // 数组越界异常
        // java.lang.ArrayIndexOutOfBoundsException
//        String[] strArr = new String[]{"a", "b", "c"};
//        for(int i = 0; i < strArr.length + 1; i++) {
//            System.out.println(strArr[i]);
//        }

        // 空指针异常
        // java.lang.NullPointerException
//        A a = new A();
//        A a = null;
//        System.out.println(a.i);

        // 算术异常
        // java.lang.ArithmeticException
//        int i = 0;
//        System.out.println(3/i);


        // 捕获异常
//        try{
//            System.out.println(3 / 0);
//        }catch(Exception e) {
//            // Exception是所有异常的父类
//            // 打印异常时发生执行堆栈的内容
//            e.printStackTrace();
//            // 输出异常信息
//            System.out.println(e.getMessage());
//        }finally{
//            // finally最终执行的代码，无论是否有错误都会执行finally错误
//        }
//        System.out.println("异常之后代码打印");



        // 抛出异常
//        B b = new B();
//        try {
//            b.test();
//        }catch(Exception e) {
//            e.printStackTrace();
//        }

        // 人工抛出异常test1 & 自定义异常类test2
//        B b = new B();
//        try{
//            // b.test1(-1);
//            b.test2(-1);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }
}


class A{
    int i;
}
// 抛出异常
class B{
    int i;
    public void test() throws NullPointerException {
        B b = null;
        System.out.println(b.i);
    }
    int age;
    public void test1 (int age) throws Exception {
        if(age >= 0 && age <= 150) {
            this.age = age;
            System.out.println("符合正确的年龄范围:" + this.age);
        }else{
            // 人工抛出异常
            throw new Exception("年龄在1-150之间");
        }
    }
    // 自定义异常
    public void test2 (int age) throws MyException {
        if(age >= 0 && age <= 150) {
            this.age = age;
            System.out.println("符合正确的年龄范围:" + this.age);
        }else{
            // 人工抛出异常
            throw new MyException("年龄在1-150之间");
        }
    }
}
class C extends B{
    // 重写方法不能抛出比父类原方法范围更大的异常类型
    // 也就是说 父类是NullPointerException 子类重写的话就不能是Exception
    // 因为Exception是NullPointerException的父类
    @Override
//    public void test() throws Exception {
    public void test() throws NullPointerException {

    }
}

// 用户自定义异常类
class MyException extends Exception{
    public MyException(String msg) {
        super(msg);
    }
}





