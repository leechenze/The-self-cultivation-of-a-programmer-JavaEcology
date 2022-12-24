import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileBitInput {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            FileInputStream in = new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/FileBitInput.txt");
            // 设置一个byte数组接收读取的文件内容；
            byte[] b = new byte[10];
            // 设置一个读取数据的长度
            int leg = 0;

            // in.read方法有个返回值，返回值是读取的数据的长度，如果读取到最后一个数据时，还会向后读一个，然后返回-1。
            // 也就意味着当in.read的返回值是-1时那整个文件就读完了；
            // in.read(b);
            while((leg = in.read(b)) != -1){
                // new String（缓冲数据的数组，从数组的那个位置开始转化字符串，总共转换几个字节）
                System.out.println(new String(b, 0, leg));
            }

            // 注意流使用完毕后一定要关闭
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
