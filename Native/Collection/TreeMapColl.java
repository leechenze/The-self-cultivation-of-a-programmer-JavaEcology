import java.util.Map;
import java.util.TreeMap;

public class TreeMapColl {
    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<Integer, String>();
        map.put(4, "a");
        map.put(2, "a");
        map.put(3, "a");
        map.put(1, "a");
        System.out.println(map);
        Map<String, String> map1 = new TreeMap<String,String>();
        map1.put("b", "b");
        map1.put("c", "b");
        map1.put("d", "b");
        map1.put("a", "b");
        // 当Map的Key为String时，那么自然排序方式按着字典排序进行
        System.out.println(map1);
    }
}
