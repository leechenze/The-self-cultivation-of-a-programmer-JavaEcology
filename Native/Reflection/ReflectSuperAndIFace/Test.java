package ReflectSuperAndIFace;

public class Test {
    public static void main(String[] args)  {
        try {
            Class clazz = Class.forName("ReflectSuperAndIFace.Student");
            // 获取当前类的父类
            Class superClazz = clazz.getSuperclass();
            System.out.println(superClazz);
            // 获取当前类的所有实现的接口
            Class[] interfaces = clazz.getInterfaces();
            for (Class iface : interfaces) {
                System.out.println(iface);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
