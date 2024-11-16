import java.util.function.Function;

public class FunctionInterfaceAndThen {
    // 将字符串转换为数字，第二个操作将数字称5
    public static void main(String[] args) {
        getNumber(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        }, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer i) {
                return i * 10;
            }
        });
    }

    public static void getNumber(Function<String, Integer> fn1, Function<Integer, Integer> fn2) {
        // Integer num1 = fn1.apply("6");
        // Integer num2 = fn2.apply(num1);
        Integer num2 = fn1.andThen(fn2).apply("6");
        System.out.println("num2 = " + num2);
    }
}
