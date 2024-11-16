import java.util.function.Function;

public class FunctionInterface {
    // 将字符串转化为整数
    public static void main(String[] args) {
        getNumber(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        });
    }

    public static void getNumber(Function<String, Integer> fn) {
        Integer num = fn.apply("10");
        System.out.println("num = " + 10);

    }
}
