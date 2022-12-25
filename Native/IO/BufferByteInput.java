import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferByteInput {
    public static void main(String[] args) throws IOException {
        // 创建文件字节输入流对象
        FileInputStream fin = new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/BufferByteInput.txt");
        // 创建缓冲字节输入流对象，把文件字节输入流放到缓冲字节输入流中；
        BufferedInputStream bin = new BufferedInputStream(fin);
        byte[] b = new byte[10];
        int leg = 0;
        while ((leg = bin.read(b)) != -1) {
            System.out.println(new String(b, 0, leg));
        }
        // 关闭流时顺序为：最后开的最早关闭，依次关闭
        bin.close();
        fin.close();
    }
}
