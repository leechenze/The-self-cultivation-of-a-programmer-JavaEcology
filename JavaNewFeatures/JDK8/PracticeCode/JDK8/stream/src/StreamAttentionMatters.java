import java.util.stream.Stream;

public class StreamAttentionMatters {
    public static void main(String[] args) {
        Stream<String> strStream = Stream.of("aa", "bb", "cc");
        /** 1.Stream流的方法只能操作一次 */
        // long count = strStream.count();
        // long count1 = strStream.count();
        /** 2.Stream流的非终结方法返回的是新的流 */
        // Stream<String> limitStream = strStream.limit(1);
        // System.out.println("strStream = " + strStream);
        // System.out.println("limitStream = " + limitStream);
        /** 3.Stream流不调用终结方法,中间的操作不会执行 */
        strStream.filter((item) -> {
            System.out.println("item = " + item);
            return true;
        }).count();
    }
}
