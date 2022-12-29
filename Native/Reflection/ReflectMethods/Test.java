package ReflectMethods;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args)  {
        try {
            // 获取类的反射
            Class clazz = Class.forName("ReflectMethods.Student");
            // 获取类的所有公有的方法
            // Method[] method = clazz.getMethods();
            // 获取类的所有方法：包含私有方法
            Method[] method = clazz.getDeclaredMethods();
            for (Method m : method) {
                // System.out.println("方法名：" + m.getName() + "返回值类型：" + m.getReturnType() + "修饰符：" + m.getModifiers());
                // 获取方法的参数类型
                Class[] pts = m.getParameterTypes();
                // 如果有参数的话才做参数的循环
                if (pts != null && pts.length > 0) {
                    for (Class pt : pts) {
                        // System.out.println("参数类型：" + pt.getName());
                    }
                }
            }

            // 获取类的公有属性（包含父类的属性）
            // Field[] fs = clazz.getFields();
            // 获取类的所有属性（只包含本类的属性）
            Field[] fs = clazz.getDeclaredFields();
            for (Field f : fs) {
                System.out.println("修饰符：" + f.getModifiers() + "属性类型：" + f.getType() + "属性名称：" + f.getName());
            }
            // 获取类所在的包
            Package p = clazz.getPackage();
            System.out.println("类 Student 的包名为：" + p.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
