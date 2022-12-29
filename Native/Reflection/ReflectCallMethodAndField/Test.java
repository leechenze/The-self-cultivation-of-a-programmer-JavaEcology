package ReflectCallMethodAndField;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args)  {
        try {
            // 获取类的反射;
            Class clazz = Class.forName("ReflectCallMethodAndField.Student");
            // 获取类的构造方法
            Constructor con = clazz.getConstructor();
            // 通过构造方法获取类实例
            Object obj = con.newInstance();


            // 调用公有方法
            // 参数：要获取的方法名，方法的参数类型（因为可能有重载的方法）
            // 获取名称为 setInfo，参数类行为 String，String的方法
            Method setInfoFn = clazz.getMethod("setInfo", String.class, String.class);
            // 参数：需要实例化的对象，调用当前的方法的实参 (args...)
            // setInfo方法调用（通过invoke方法）
            setInfoFn.invoke(obj, "Lincoln", "宾夕法尼亚大学");


            // 调用私有方法
            Method testFn = clazz.getDeclaredMethod("test", String.class);
            // 解除私有封装以访问和调用私有方法
            testFn.setAccessible(true);
            // test方法调用（通过invoke方法）
            testFn.invoke(obj, "Clinton");


            // 调用一个重载方法
            Method setInfoFn1 = clazz.getMethod("setInfo", int.class);
            setInfoFn1.invoke(obj, 20);


            // 有返回值的方法
            Method getSchoolFn = clazz.getMethod("getSchool");
            String school = (String) getSchoolFn.invoke(obj);
            System.out.println(school);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
