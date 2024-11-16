import java.util.Arrays;
import java.util.function.Supplier;

public class SupplierInterface {
    public static void main(String[] args) {
        // 使用Lambda表达式返回数组元素最大值
        // printMax(new Supplier<Integer>() {
        //     @Override
        //     public Integer get() {
        //         int[] arr = {1, 2, 5, 4, 3};
        //         Arrays.sort(arr);
        //         return arr[arr.length - 1];
        //     }
        // });

        printMax(() -> {
            int[] arr = {1, 2, 5, 4, 3, 6, 0};
            Arrays.sort(arr);
            return arr[arr.length - 1];
        });
    }

    public static void printMax(Supplier<Integer> supplier) {
        Integer maxNum = supplier.get();
        System.out.println("maxNum = " + maxNum);
    }
}
