import java.io.*;

public class Practice {
    public static void main(String[] args) throws IOException {

        /** =============文件字节流拷贝文件============== */
//        // 读取源文件
//        FileInputStream in = new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/Practice.txt");
//        // 要复制的目录
//        FileOutputStream out = new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src1/Practice.txt");
//        byte[] b = new byte[100];
//        int leg = 0;
//        while ((leg = in.read(b)) != -1) {
//            // 写入内存
//            out.write(b, 0, leg);
//        }
//        // 把写到内存的数据刷到硬盘
//        out.flush();
//        out.close();
//        in.close();






        /** =============文件字符流拷贝文件============== */
//        // 创建文件字符读取和写入流
//        FileReader fr = new FileReader("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/Practice1.txt");
//        FileWriter fw = new FileWriter("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src1/Practice1.txt");
//
//        char[] c = new char[100];
//        int leg1 = 0;
//        while ((leg1 = fr.read(c)) != -1) {
//            fw.write(c, 0, leg1);
//        }
//        fw.flush();
//        fw.close();
//        fr.close();






        /** =============缓冲字节流拷贝文件============== */
        // 缓冲输入流
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/Practice2.txt"));
        // 缓冲输出流
        BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src1/Practice2.txt"));

        byte[] b = new byte[1024];
        int leg = 0;
        while ((leg = bin.read(b)) != -1) {
            bout.write(b, 0, leg);
        }
        bout.flush();
        bout.close();
        bin.close();



    }
}
