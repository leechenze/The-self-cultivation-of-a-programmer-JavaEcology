import java.util.ArrayList;
import java.util.List;

public class ArrayListColl {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("d");
        list.add("c");
        System.out.println(list);
        // 查看元素
        System.out.println(list.get(2));
        list.add("a");
        System.out.println(list);
        // 指定索引下标的位置插入数据
        list.add(1, "f");
        System.out.println(list);
        List<String> list1 = new ArrayList<String>();
        list1.add("123");
        list1.add("456");
        // 插入集合中插入集合(集合合并);
        list.addAll(0, list1);
        System.out.println(list);
        // 获取元素下标
        System.out.println(list.indexOf("a"));
        // 删除元素
        list.remove(0);
        System.out.println(list);
        // 修改元素
        list.set(0, "123");
        System.out.println(list);
        // 截取list集合
        List<String> sublist = list.subList(2,4);
        System.out.println(sublist);
        // 元素长度
        System.out.println(list.size());
    }
}
