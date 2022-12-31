package DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ITest {
    public static void main(String[] args) {
        ITestDemo test = new ITestDemoImplement();
        /**
         * 注意：如果一个对象想要通过Proxy.newProxyInstance方法被代理
         * 那么这个对象的类一定要有相应的接口
         * 就像本次例子中的ITestDemo接口和实现类ITestDemoImplement
         */
        test.test1();
        test.test2();
        System.out.println("========================");
        // 需求：在执行test1和test2方法时，在执行方法前打印test1或test2开始执行
        // 然后在执行方法后，打印test1和test2方法执行完毕
        InvocationHandler handler = new ProxyDemo(test);
        // 参数：(代理对象的类加载器，被代理的对象的接口, 代理对象)
        // 返回值：成功被代理后的对象,返回的事Object类型，需要进行转换下ITestDemo类型
        ITestDemo t = (ITestDemo)Proxy.newProxyInstance(handler.getClass().getClassLoader(), test.getClass().getInterfaces(), handler);
        t.test1();
        System.out.println("------------------------");
        t.test2();

    }
}
