import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamFrequentlyUsedMethod {
    public static void main(String[] args) {

    }

    @Test
    public void forEach() {
        List<String> strList = new ArrayList<>();
        strList.addAll(Arrays.asList("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子"));

        // 循环打印strList中的每一项
        // strList.stream().forEach((String str) -> {
        //     System.out.println(str);
        // });
        // 1.省略写法一
        // strList.stream().forEach(str -> System.out.println(str));
        // 2.省略写法二
        strList.stream().forEach(System.out::println);
    }

    @Test
    public void count() {
        List<String> strList = new ArrayList<>();
        strList.addAll(Arrays.asList("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子"));

        // 统计strList的数量
        long count = strList.stream().count();
        System.out.println("count = " + count);
    }

    @Test
    public void filter() {
        List<String> strList = new ArrayList<>();
        strList.addAll(Arrays.asList("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子"));

        // 得到名字长度为三个字的人
        // strList.stream().filter(s -> {
        //     return s.length() == 3;
        // }).forEach((item) -> {
        //     System.out.println(item);
        // });
        // 简化写法
        strList.stream().filter(str -> str.length() == 3).forEach(System.out::println);
    }

    @Test
    public void limit() {
        List<String> strList = new ArrayList<>();
        strList.addAll(Arrays.asList("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子"));

        // 获取前三个数据
        strList.stream().limit(3).forEach(System.out::println);
    }

    @Test
    public void skip() {
        List<String> strList = new ArrayList<>();
        strList.addAll(Arrays.asList("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子"));

        // 跳过前两个数据
        strList.stream().skip(2).forEach(System.out::println);
    }

    @Test
    public void map() {
        Stream<String> strStream = Stream.of("11", "22", "33");

        // 将strStream中的元素转换为整数型
        // strStream.map((String str) -> {
        //     return Integer.parseInt(str);
        // }).forEach(System.out::println);
        // 简化写法
        strStream.map(Integer::parseInt).forEach(System.out::println);
    }

    @Test
    public void sorted() {
        Stream<Integer> integerStream = Stream.of(11, 22, 55, 44, 33);

        // 对integerStream中的元素进行默认排序(升序)
        // integerStream.sorted().forEach(System.out::println);

        // 对integerStream中的元素进行执行Comparator降序排序
        integerStream.sorted((Integer o1, Integer o2) -> {
            return o2 - o1;
        }).forEach(System.out::println);
        // 简化写法
        integerStream.sorted(((o1, o2) -> o2 - o1)).forEach(System.out::println);
    }

    @Test
    public void distinct() {
        Stream<Integer> integerStream = Stream.of(11, 22, 22, 66, 33, 33, 22, 11);
        Stream<String> strStream = Stream.of("aa", "bb", "aa");
        Stream<Person> personStream = Stream.of(new Person("貂蝉", 22), new Person("西施", 23), new Person("西施", 23), new Person("杨玉环", 22), new Person("王昭君", 28), new Person("王昭君", 28));

        // 对integerStream进行去重
        integerStream.distinct().forEach(System.out::println);
        strStream.distinct().forEach(System.out::println);
        // 对自定义的stream对象进行去重
        personStream.distinct().forEach(System.out::println);
    }

    @Test
    public void match() {
        Stream<Integer> integerStream = Stream.of(5, 3, 8, 1);

        /** allMatch: 匹配所有元素都满足条件(5, 3, 8, 1都大于0所以结果为true,否则结果都是false) */
        // boolean allMatchRes = integerStream.allMatch(i -> {
        //     return i > 0;
        // });
        // System.out.println("allMatchRes = " + allMatchRes);

        /** anyMatch: 匹配有任何一个元素满足条件 */
        // boolean anyMatchRes = integerStream.anyMatch(i -> {
        //     return i > 5;
        // });
        // System.out.println("anyMatchRes = " + anyMatchRes);

        /** noneMatch: 匹配所有元素都不满足条件 */
        boolean noneMatchRes = integerStream.noneMatch(i -> {
            return i > 10;
        });
        System.out.println("noneMatchRes = " + noneMatchRes);
    }

    @Test
    public void find() {
        Stream<Integer> integerStream = Stream.of(33, 11, 2, 5);

        // 查找第一个元素
        // Optional<Integer> first = integerStream.findFirst();
        // System.out.println(first.get());

        Optional<Integer> any = integerStream.findAny();
        System.out.println(any.get());
    }

    @Test
    public void maxMin() {
        Stream<Integer> integerStream = Stream.of(1, 3, 6, 5, 3, 4, 8, 0);

        /** 获得最大值 */
        // Optional<Integer> max = integerStream.max((o1, o2) -> o1 - o2);
        // System.out.println("max.get() = " + max.get());
        /** 获得最小值 */
        Optional<Integer> min = integerStream.min((o1, o2) -> o1 - o2);
        System.out.println("min.get() = " + min.get());
    }

    @Test
    public void reduce() {
        // 统计流中的数字之和
        Integer reduce = Stream.of(4, 5, 3, 9).reduce(0, (x, y) -> {
            return x + y;
        });
        System.out.println("reduce = " + reduce);
        // 用reduce获取最大值
        Integer max = Stream.of(4, 5, 3, 9).reduce(0, (x, y) -> {
            return x > y ? x : y;
        });
        System.out.println("max = " + max);
    }

    @Test
    public void mapUniteReduce() {
        Stream<Person> allPerson = Stream.of(new Person("刘德华", 58), new Person("张学友", 56), new Person("郭富城", 54), new Person("黎明", 52));
        Stream<String> strStream = Stream.of("a", "b", "c", "a", "a");

        /** 求出所有人的年龄总和 */
        // Integer totalAge = allPerson.map(p -> p.getAge()).reduce(0, (a, b) -> a + b);
        /** 简化写法 */
        // Integer totalAge = allPerson.map(p -> p.getAge()).reduce(0, Integer::sum);
        // System.out.println("totalAge = " + totalAge);

        /** 找出最大年龄 */
        // Integer maxAge = allPerson.map(p -> p.getAge()).reduce(0, (x, y) -> {
        //     return x > y ? x : y;
        // });
        /** 简化写法 */
        // Integer maxAge = allPerson.map(p -> p.getAge()).reduce(0, Integer::max);
        // System.out.println("maxAge = " + maxAge);

        /** 统计a出现的次数 */
        Integer sumOfA = strStream.map(i -> {
            if (i == "a") {
                return 1;
            } else {
                return 0;
            }
        }).reduce(0, Integer::sum);
        System.out.println("sumOfA = " + sumOfA);
    }

    @Test
    public void mapToInt() {
        /**
         * mapToInt的作用就是将 Integer包装类转换为举出数据类型int
         * Stream流因为传递的是范型 所以只能用包装类Integer
         * 然而Integer相比int基础类型的内存占用是肯定多的
         * 而Stream流的操作会有很多Integer和int的装箱和拆箱操作 非常影响性能
         * 所以mapToInt就是将Integer转换为int 最终得到一个IntStream的操作基础数据类型的流
         * 节省内存 减少装箱和拆箱 其他方法同理(mapToDouble, mapToLong...)
         */
        // IntStream intStream = Stream.of(1, 2, 3, 4, 5).mapToInt((Integer n) -> {
        //     return n.intValue();
        // });
        /** 简化写法 */
        IntStream intStream = Stream.of(1, 2, 3, 4, 5).mapToInt(Integer::intValue);
        intStream.filter(x -> x > 3).forEach(System.out::println);
    }

    @Test
    public void concat() {
        Stream<String> stream1 = Stream.of("张三");
        Stream<String> stream2 = Stream.of("李四");

        // 合并称一个Stream流
        Stream<String> newStream = Stream.concat(stream1, stream2);
        newStream.forEach(System.out::println);
    }

    /**
     * 综合案例
     */
    @Test
    public void comprehensiveCase() {
        // 第一个队伍
        List<String> one = List.of("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子", "洪七公");
        // 第二个队伍
        List<String> two = List.of("古力娜扎", "张无忌", "张三丰", "赵丽颖", "张二狗", "张天爱", "张三");

        // 1.第一个队伍只要名字为3个字的成员姓名;
        // 2.第一个队伍筛选之后只要前3个人;
        Stream<String> streamA = one.stream().filter(e -> e.length() == 3).limit(3);
        // 3.第二个队伍只要姓张的成员姓名;
        // 4.第二个队伍筛选之后不要前2个人;
        Stream<String> streamB = two.stream().filter(e -> e.startsWith("张")).skip(2);
        // 5.将两个队伍合并为一个队伍;
        Stream<String> streamAB = Stream.concat(streamA, streamB);
        // 6.根据姓名创建`Person`对象;
        // 7.打印整个队伍的Person对象信息。
        streamAB.map(e -> new Person(e, 1)).forEach(System.out::println);
    }

    /**
     * 收集Stream流中的结果到集合中
     */
    @Test
    public void collect() {
        Stream<String> stream = Stream.of("aa", "bb", "cc");
        // collect收集流中的数据到集合中
        // List<String> list = stream.collect(Collectors.toList());
        // System.out.println("list = " + list);

        // collect收集流中的数据到set中
        // Set<String> set = stream.collect(Collectors.toSet());
        // System.out.println("set = " + set);

        // collect收集流中的数据到指定的集合中(ArrayList）
        // ArrayList<String> arrayList = stream.collect(Collectors.toCollection(() -> new ArrayList<>()));
        ArrayList<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));
        System.out.println("arrayList = " + arrayList);

    }

    /**
     * 收集Stream流中的结果到数组中
     */
    @Test
    public void toArray() {
        Stream<String> stream = Stream.of("aa", "bb", "cc");

        // Object[] objects = stream.toArray();
        // for (Object object : objects) {
        //     System.out.println("object = " + object);
        // }

        // String[] strings = stream.toArray((size) -> new String[size]);
        String[] strings = stream.toArray(String[]::new);
        System.out.println(Arrays.toString(strings));
    }

    @Test
    public void ssss10() {
        
    }

    @Test
    public void ssss11() {

    }

}
