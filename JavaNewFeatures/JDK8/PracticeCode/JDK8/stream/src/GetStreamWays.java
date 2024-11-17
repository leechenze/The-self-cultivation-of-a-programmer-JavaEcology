import java.sql.Array;
import java.util.*;
import java.util.stream.Stream;

public class GetStreamWays {
    public static void main(String[] args) {
        // 方式一: 根据Collection接口获取流
        ArrayList<String> list = new ArrayList<>();
        Stream<String> listStream = list.stream();

        HashSet<String> set = new HashSet<>();
        Stream<String> setStream = set.stream();

        HashMap<String, String> map = new HashMap<>();
        Stream<Map.Entry<String, String>> mapEntrySetStream = map.entrySet().stream();
        Stream<String> mapKeySetStream = map.keySet().stream();
        Stream<String> mapValListStream = map.values().stream();

        // 方式二: Stream中的静态方法of获取流
        Stream<String> stream = Stream.of("aa", "bb", "cc");

        String[] strArr = {"aa", "bb", "cc"};
        Stream<String> strStream = Stream.of(strArr);
        // Stream对基础数据类型的数组会当作整个数组进行操作,请看如下演示(int是基础数据类型,Integer是包装类)
        // 基础数据类型演示 int
        int[] intArr = {1, 2, 3};
        Stream<int[]> intStream = Stream.of(intArr);
        intStream.forEach(item -> {
            System.out.println("int item = " + item);
        });
        // 包装类演示 Integer
        Integer[] integerArr = {1, 2, 3};
        Stream<Integer> integerStream = Stream.of(integerArr);
        integerStream.forEach(item -> {
            System.out.println("Integer item = " + item);
        });
    }
}
