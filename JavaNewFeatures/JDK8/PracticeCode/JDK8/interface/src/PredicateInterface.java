import java.util.function.Predicate;

public class PredicateInterface {
    public static void main(String[] args) {
        // 判断名字是否超过三个字
        isLongName(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 3;
            }
        });
    }

    public static void isLongName(Predicate<String> predicate) {
        boolean isLong = predicate.test("迪丽热巴");
        System.out.println("是否是过长的名字" + isLong);
    }
}
