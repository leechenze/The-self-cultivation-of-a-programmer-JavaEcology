import org.junit.Test;

import java.util.Date;
import java.util.function.Supplier;

public class InstanceNameRefMethodName {
    public static void main(String[] args) {
        Date now = new Date();
        // 1.使用传统的lambda表达式
        // Supplier<Long> supplier = () -> {
        //     return now.getTime();
        // };
        // 2.使用实例名引用方法名
        Supplier<Long> supplier = now::getTime;

        Long aLong = supplier.get();
        System.out.println("aLong = " + aLong);
    }
}
