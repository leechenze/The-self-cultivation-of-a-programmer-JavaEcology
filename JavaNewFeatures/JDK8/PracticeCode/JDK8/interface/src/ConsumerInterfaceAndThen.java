import java.util.function.Consumer;

public class ConsumerInterfaceAndThen {
    public static void main(String[] args) {
        printHello(new Consumer<String>() {
            public void accept(String str) {
                System.out.println(str.toUpperCase());
            }
        }, new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str.toLowerCase());
            }
        });
    }

    public static void printHello(Consumer<String> consumer1, Consumer<String> consumer2) {
        String str = "Hello World";
        // consumer1.accept(str);
        // consumer2.accept(str);
        consumer1.andThen(consumer2).accept(str);
    }
}
