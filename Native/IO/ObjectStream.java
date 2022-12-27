import java.io.*;

public class ObjectStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String name;
        int age;
        // 对象序列化
        // 定义对象的输出流，把对象的序列化之后的流放到指定的文件中
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/ObjectStream.txt"));
        // 随便定义一个对象
        Teacher t = new Teacher();
        t.name = "trump";
        t.age = 80;

        out.writeObject(t);
        out.flush();
        out.close();

        // =================================
        // 对象的反序列化
        // 创建输入流对象，从指定的文件中把对象序列化后的流读出来
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/ObjectStream.txt"));
        Object obj = in.readObject();
        Teacher t1 = (Teacher) obj;

        // 正常输出表示反序列化回来了
        System.out.println(t1.name);
        System.out.println(t1.age);



    }
}

/**
 * 可以序列化和反序列化对象必须实现Serializable接口
 * 凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态变量,用来表明类的不同版本间的兼容性（serialVersionUID）
 */
class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public int age;
}