import java.util.function.BiFunction;
import java.util.function.Function;

public class ClassNameRefMethodName {
    public static void main(String[] args) {
        // Function<String, Integer> fn = (String str) -> {
        //     return str.length();
        // };
        Function<String, Integer> fn = String::length;
        Integer length = fn.apply("Hello World");
        System.out.println("length = " + length);


        BiFunction<String, Integer, String> bfn = String::substring;
        String resStr = bfn.apply("Hello World", 6);
        System.out.println("resStr = " + resStr);
    }
}
