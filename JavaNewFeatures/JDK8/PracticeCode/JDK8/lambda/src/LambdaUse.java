public class LambdaUse {
    public static void main(String[] args) {

        goSwimming(new Swimmable() {
            @Override
            public void swimming() {
                System.out.println("匿名内部类执行");
            }
        });
        goSwimming(() -> {
            System.out.println("Lambda表达式执行");
        });


        goSmoking(new Smokeable() {
            @Override
            public int smoking(String name) {
                int count = 10;
                System.out.println("匿名内部类抽了" + count + "根" + name + "香烟");
                return count;
            }
        });
        goSmoking((String name) -> {
            int count = 10;
            System.out.println("lambda表达式抽了" + count + "根" + name + "香烟");
            return count;
        });

    }

    // 无参数无返回值的Lambda
    public static void goSwimming(Swimmable s) {
        s.swimming();
    }

    // 有参数有返回值的lambda
    public static void goSmoking(Smokeable s) {
        int count = s.smoking("紫云");
        System.out.println("返回了" + count + "根");
    }

}
