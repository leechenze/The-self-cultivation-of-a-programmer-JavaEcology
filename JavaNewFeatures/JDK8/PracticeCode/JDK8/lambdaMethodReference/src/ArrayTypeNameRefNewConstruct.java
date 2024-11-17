import java.util.Arrays;
import java.util.function.Function;

public class ArrayTypeNameRefNewConstruct {
    public static void main(String[] args) {
        // Function<Integer, int[]> fn = (Integer length) -> {
        //     return new int[length];
        // };
        Function<Integer, int[]> fn = int[]::new;
        int[] arr = fn.apply(10);
        System.out.println("arr = " + Arrays.toString(arr));
    }
}
