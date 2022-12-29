package ReflectConstructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args)  {
        try {
            // 获取Student类的反射
            Class clazz = Class.forName("ReflectConstructors.Student");
            /**
             * getConstructors()
             */
            Constructor[] cons = clazz.getConstructors();
            for(Constructor con : cons) {
                System.out.println("构造方法名称为：" + con.getName() + "   con的修饰符为：" + con.getModifiers());
                Class[] paramClazz = con.getParameterTypes();
                for(Class pc : paramClazz) {
                    System.out.println("构造方法名称为：" + con.getName() + "   的参数类型是: " + pc.getName());
                }
            }
            /**
             * getDeclaredConstructors()
             */
            Constructor[] cons1 = clazz.getDeclaredConstructors();
            for(Constructor con1 : cons1) {
                System.out.println("构造方法名称为：" + con1.getName() + "   con1的修饰符为：" + con1.getModifiers());
                Class[] paramClazz1 = con1.getParameterTypes();
                for(Class pc1 : paramClazz1) {
                    System.out.println("构造方法名称为：" + con1.getName() + "   的参数类型是: " + pc1.getName());
                }
            }

            /**
             * 通过反射创建一个对象
             */
            // 默认执行无参构造方法
            Object obj = clazz.newInstance();
            Student stu = (Student) obj;

            // 指定执行只有一个参数类型为String的公有的构造方法
            Constructor c = clazz.getConstructor(String.class);
            Student stu1 = (Student) c.newInstance("西北工业大学");
            System.out.println(stu1.school);

            // 通过反射机制可以强制调用私有构造方法
            Constructor c1 = clazz.getDeclaredConstructor(String.class, int.class);
            // 通过解除私有的封装，下面就可以对私有的构造方法强制调用
            c1.setAccessible(true);
            Student stu2 = (Student) c1.newInstance("trump", 88);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
