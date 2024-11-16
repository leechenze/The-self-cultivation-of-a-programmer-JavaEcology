public class FunctionalInterface {
    public static void main(String[] args) {

        // test(new Operation() {
        //     @Override
        //     public void getSum(int a, int b) {
        //         System.out.println(a + b);
        //     }
        // });

        test((int a, int b) -> {
            System.out.println(a + b);
        });
    }

    public static void test(Operation operation) {
        operation.getSum(1, 2);
    }
}

interface Operation {
    public abstract void getSum(int a, int b);
}