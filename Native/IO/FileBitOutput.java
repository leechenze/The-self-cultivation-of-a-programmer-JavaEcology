import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileBitOutput {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            // 指定向FileBitOutput.txt输出数据
            FileOutputStream out = new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/FileBitOutput.txt");
            String str = "asdfkljgfadjfskjwieonmdcn";
            // 把数据写到内存（必须将写入内容转换为bytes格式）
            out.write(str.getBytes());
            // 把内存中的数据刷写到硬盘上
            out.flush();
            // 关闭流
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}