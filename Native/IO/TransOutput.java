import java.io.*;

public class TransOutput {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/TransOutput.txt");
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
        out.write("你好 世界");
        out.flush();
        out.close();
        fos.close();
    }
}
