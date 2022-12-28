import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomStream {
    public static void main(String[] args) throws IOException {
        RandomStream.randomFileRead();
        RandomStream.randomFileWrite();
    }

    /**
     * 随机读文件
     */
    public static void randomFileRead() throws IOException {
        // RandomAccessFile的构造有两个参数，参数1是文件路径，参数2是访问模式
        RandomAccessFile raf = new RandomAccessFile("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/RandomStream.txt", "r");
        // 设置读取文件内容的起始点
        // raf.seek(0);
        // 通过设置文件内容的起始点，来达到从文件的任意位置读取
        raf.seek(6);
        byte[] b = new byte[1024];
        int leg = 0;
        while ((leg = raf.read(b)) != -1) {
            System.out.println(new String(b, 0, leg));
        }
        raf.close();
    }
    /**
     * 随机写文件
     */
    public static void randomFileWrite() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/RandomStream.txt", "rw");
        // 设置写入文件的起始点，在零时表示从头开始写入，raf.length时表示从文件结尾写入；
        // 注意如果seek从中间写入时，将替换等长的原内容，也就是说，从中间写入时是替换操作而不是追加操作；
        // raf.seek(0);
        raf.seek(raf.length());
        raf.write("你好,世界!".getBytes());
        // 注意随机存取流不需要flush这一步操作;
        raf.close();
    }
}
