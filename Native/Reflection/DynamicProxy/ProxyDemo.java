package DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 */
public class ProxyDemo implements InvocationHandler {
    // 定义一个被代理的对象obj
    Object obj;

    public ProxyDemo(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + "方法开始执行");
        // 执行指定的代理对象的指定的方法
        Object result = method.invoke(this.obj, args);
        System.out.println(method.getName() + "方法开始完毕");
        return result;
    }
}
