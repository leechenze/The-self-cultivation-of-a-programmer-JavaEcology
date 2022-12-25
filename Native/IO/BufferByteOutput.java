import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BufferByteOutput {
    public static void main(String[] args) throws IOException {
        // 创建文件字节输出流
        FileOutputStream fout = new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/BufferByteOutput.txt");
        // 创建缓冲字节输出流对象，把文件字节输出流放到缓冲字节输出流中；
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        String str = "hello world";

        bout.write(str.getBytes());
        bout.flush();
        bout.close();
        fout.close();

    }
}
