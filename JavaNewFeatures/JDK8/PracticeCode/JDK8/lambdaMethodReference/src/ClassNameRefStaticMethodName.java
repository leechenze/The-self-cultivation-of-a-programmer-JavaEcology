import java.util.function.Supplier;

public class ClassNameRefStaticMethodName {
    public static void main(String[] args) {
        // Supplier<Long> supplier = () -> {
        //     return System.currentTimeMillis();
        // };

        Supplier<Long> supplier = System::currentTimeMillis;

        Long time = supplier.get();
        System.out.println("time = " + time);

    }
}
