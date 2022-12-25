import java.io.*;

public class TransInput {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/TransInput.txt");
        // 把字节流转换成字符流
        InputStreamReader in = new InputStreamReader(fis, "UTF-8");
        char[] c = new char[100];
        int leg = 0;
        while ((leg = in.read(c)) != -1) {
            System.out.println(new String(c, 0, leg));
        }
        in.close();
        fis.close();
    }
}
