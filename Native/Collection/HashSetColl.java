import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetColl {
    public static void main(String[] args) {
        Set set = new HashSet();
        // 添加元素
        set.add(1);
        set.add("a");
        System.out.println(set);
        // 删除元素
        set.remove(1);
        System.out.println(set);
        // 判断元素是否存在
        System.out.println(set.contains(1));
        // 清空元素
        set.clear();
        System.out.println(set);

        // 使用迭代器遍历集合
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        System.out.println(set);
        Iterator it = set.iterator();
        while(it.hasNext()){
            System.out.print(it.next());
        }
        System.out.println();
        // forEach迭代集合
        // Object obj: set 意思是将set每一个值取出来赋给obj，直到循环完set的所有值
        for(Object obj: set){
            System.out.print(obj);
        }
        System.out.println();
        System.out.println(set.size());
        // 证明set可以存null值，同时不保证排列顺序（null的位置不是固定在某一位的）；
        set.add(null);
        System.out.println(set);
        
    }
}
