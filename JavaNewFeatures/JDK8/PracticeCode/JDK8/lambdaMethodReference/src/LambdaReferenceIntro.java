import java.util.function.Consumer;

public class LambdaReferenceIntro {
    // 求一个数组的和
    public static void getSum(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        // 1.匿名内部类的方式
        // printSum(new Consumer<int[]>() {
        //     @Override
        //     public void accept(int[] ints) {
        //         getSum(ints);
        //     }
        // });
        // 2.lambda表达式的方式
        // printSum((int[] ints) -> {
        //     getSum(ints);
        // });
        // 3.lambda表达式更精简的方式,等价于(printSum(ints -> LambdaReferenceIntro.getSum(ints)))
        printSum(LambdaReferenceIntro::getSum);
    }

    public static void printSum(Consumer<int[]> consumer) {
        int[] arr = {11, 22, 33, 44, 55};
        consumer.accept(arr);
    }

}
