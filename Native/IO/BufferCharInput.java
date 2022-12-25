import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferCharInput {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/BufferCharInput.txt");
        BufferedReader br = new BufferedReader(fr);
        char[] c = new char[100];
        int leg = 0;
        while ((leg = br.read(c)) != -1) {
            System.out.println(new String(c, 0, leg));
        }
        br.close();
        fr.close();
    }
}
