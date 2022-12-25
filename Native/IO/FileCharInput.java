import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCharInput {
    public static void main(String[] args) throws IOException {
        // 创建文字字符输入流
        FileReader fr = new FileReader("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/FileCharInput.txt");
        // 创建临时存数据的数组
        char[] c = new char[10];
        // 定义输入流读取长度
        int leg = 0;
        while ((leg = fr.read(c)) != -1) {
            System.out.println(new String(c, 0, leg));
        }
        fr.close();
    }
}
