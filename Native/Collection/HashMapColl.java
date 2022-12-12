import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapColl {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        // 添加数据
        map.put("b",1);
        map.put("c",2);
        map.put("e",2);
        System.out.println(map);
        // 根据key获取数据
        System.out.println(map.get("b"));
        // 根据key移除数据
        map.remove("c");
        System.out.println(map);
        // 获取map长度
        System.out.println(map.size());
        // 判断是否具有指定的key
        System.out.println(map.containsKey("a"));
        // 判断是否具有指定的value
        System.out.println(map.containsValue(1));
        // 获取map集合的所有key的值
        System.out.println(map.keySet());
        // 获取map集合的所有value值
        System.out.println(map.values());
        // 遍历map集合一
        Set<String> keys = map.keySet();
        for(String key : keys) {
            System.out.println("key:" + key + ",value:" + map.get(key));
        }
        // 遍历map集合二
        Set<Map.Entry<String,Integer>> entrys = map.entrySet();
        for(Map.Entry<String, Integer> en : entrys){
            System.out.println("key:" + en.getKey() + ",value:" + en.getValue());
        }
    }
}
