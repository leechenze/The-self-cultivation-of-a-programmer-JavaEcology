import java.util.function.Consumer;

public class ConsumerInterface {
    public static void main(String[] args) {
        printHello(new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str.toUpperCase());
            }
        });
    }

    public static void printHello(Consumer<String> consumer) {
        consumer.accept("Hello world");
    }
}
