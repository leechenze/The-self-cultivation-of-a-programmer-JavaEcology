import org.junit.Test;

import java.util.Optional;

public class OptionalIntro {
    public static void main(String[] args) {
        // 1.创建Optional对象
        // of：          只能传入具体值
        // ofNullable：  既可以传入具体值，也可以传入空
        // empty：       存入的是null
        Optional<String> op1 = Optional.of("douglas");
        Optional<Object> op2 = Optional.ofNullable(null);
        Optional<Object> op3 = Optional.empty();

        // 2.判断Optional中是否有具体值
        boolean present1 = op1.isPresent();
        boolean present2 = op2.isPresent();
        boolean present3 = op3.isPresent();
        System.out.println("present1 = " + present1);
        System.out.println("present2 = " + present2);
        System.out.println("present3 = " + present3);

        // 3.获取Optional中的具体值
        String getOp1 = op1.get();
        System.out.println("getOp1 = " + getOp1);
        

    }
}
