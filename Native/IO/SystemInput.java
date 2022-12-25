import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInput {
    public static void main(String[] args) throws IOException {
        // 创建一个接收键盘输入数据的输入流
        InputStreamReader isr = new InputStreamReader(System.in);
        // 把输入流放到缓冲里
        BufferedReader br = new BufferedReader(isr);
        // 定义临时接收数据的字符串
        String str = "";
        // in.readLine 方法有个返回值，返回值是读取的数据的长度，如果读取到最后一个数据时，还会向后读一个，然后返回null。
        // 也就意味着当in.read的返回值是null时那整个文件就读完了；
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
        br.close();
        isr.close();
    }
}
