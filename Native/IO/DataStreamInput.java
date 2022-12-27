import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataStreamInput{
    public static void main(String[] args) throws Exception {
        // 数据输出流
        DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/DataStreamInput.txt"));
        dataOut.writeBoolean(true);
//        dataOut.writeDouble(1.35d);
//        dataOut.writeInt(100);
        // 可以看到DataStreamInput.txt中是乱码的
        // 用数据输出流写到文件按中的基本数据类型的数据是乱码的，不能直接辨认出来，需要数据的输入流进行读取
        dataOut.flush();
        dataOut.close();
        // 数据输入流
        DataInputStream datain = new DataInputStream(new FileInputStream("/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Native/IO/src/DataStreamInput.txt"));

//        System.out.println(datain.readInt());
//        System.out.println(datain.readDouble());
        System.out.println(datain.readBoolean());
        
    }
}
