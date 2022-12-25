import java.io.FileWriter;
import java.io.IOException;

public class FileCharOutput {
    public static void main(String[] args) throws IOException {
        // 创建文字字符输入流
        FileWriter fw = new FileWriter("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/FileCharOutput.txt");
        // 将数据写入内存中
        fw.write("FileCharOutput1111");
        // 将内存的数据刷到硬盘
        fw.flush();
        // 关闭流
        fw.close();
    }
}
