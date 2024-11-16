import java.util.function.Predicate;

public class PredicateInterfaceAndOrNegate {
    public static void main(String[] args) {
        // 需求:
        // 判断字符串中既包含W,又包含H(judgmentAnd)
        // 判断字符串包含W或包含H(judgmentOr)
        // 判断字符串不包含W(judgmentNegate)
        judgment(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("W");
            }
        }, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("H");
            }
        });


        judgmentAnd(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("W");
            }
        }, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("H");
            }
        });

        judgmentOr(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("W");
            }
        }, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("H");
            }
        });

        judgmentNegate(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("H");
            }
        });
    }

    public static void judgment(Predicate<String> p1, Predicate<String> p2) {
        String str = "Hello World";
        boolean b1 = p1.test(str);
        boolean b2 = p2.test(str);
        if (b1 && b2) {
            System.out.println("judgment: 既包含W,也包含H");
        }
    }

    public static void judgmentAnd(Predicate<String> p1, Predicate<String> p2) {
        String str = "Hello World";
        boolean res = p1.and(p2).test(str);
        if (res) {
            System.out.println("jugementAnd: 既包含W,也包含H");
        }
    }

    public static void judgmentOr(Predicate<String> p1, Predicate<String> p2) {
        String str = "Hello World";
        boolean res = p1.or(p2).test(str);
        if (res) {
            System.out.println("jugementOr: 包含W,或者H");
        }
    }

    public static void judgmentNegate(Predicate<String> predicate) {
        String str = "ello World";
        boolean res = predicate.negate().test(str);
        if (res) {
            System.out.println("jugementNegate: 不包含H");
        }
    }

}
