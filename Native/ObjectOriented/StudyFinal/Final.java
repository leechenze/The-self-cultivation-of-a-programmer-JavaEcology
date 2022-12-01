package StudyFinal;

public final class Final {
    int age;
    String name;
}


class Final1{
    int age;
    String name;
    public final void testFinal() {}
}

class Final2{
    int age;
    final String NAME = ""; // final修饰的变量是常量，既然是常量，那么必须是一个有值的量，必须显式赋值，不能是默认值；
    final String NAME_1 = "";

    // NAME = "1"; // 常量只能被赋值一次，不能被再次赋值；

}



// 不可以继承final修饰的类；
//class F0 extends Final{}

class F0 extends Final1{
    // 不可以重写final修饰的方法
    // @Override
    // public void testFinal() {}
}