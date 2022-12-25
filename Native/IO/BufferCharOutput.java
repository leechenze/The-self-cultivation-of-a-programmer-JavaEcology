import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferCharOutput {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/BufferCharOutput.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        String str = "hello world";
        int leg = 0;
        bw.write(str);
        bw.flush();
        bw.close();
        fw.close();
    }
}
