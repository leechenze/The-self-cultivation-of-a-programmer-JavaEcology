public class ProcedureFlowControl {
    public static void main (String[] args) {

        // for 循环 (初始化表达式; 测试表达式; 更改表达式)
        for (int i = 0; i < 5; i++){
//            System.out.println("hello world");
        }

        for (int i = 0; i < 150; i++) {
            String str = "";
            str += i;
            if(i % 3 == 0) {
                str += " foo";
            }
            if(i % 5 == 0) {
                str += " biz";
            }
            if(i % 7 == 0) {
                str += " baz";
            }
//            System.out.println(str);
        }

        // while 循环
        int i = 1;
        while (i <= 100) {
//            System.out.println(i);
            i++;
        }

        // do while 循环
        int d = 1;
        do {
//            System.out.println(d);
            d++;
        }while (d <= 100);


        // 1-100之间所有偶数的和;
        int res = 0, a = 1;
        while(a <= 100) {
            if(a % 2 == 0) {
                res += a;
            }
            a++;
        }
//        System.out.println(res); // 2550;

//        无限循环
//        for(;;){
//            System.out.println("infinite For");
//        }
//        无限循环
//        while(true) {
//            System.out.println("infinite While");
//        }

//        九九乘法表
        for (int f = 1; f <= 9; f++) {
            for(int s = 1; s <= f; s++) {
//                System.out.print(f + " * " + s + " = " + (f * s) + " | ");
            }
//            System.out.println(); // 用于换行
        }

//        输出 1-100之间所有的质数;
        for(int p1 = 1; p1 <= 100; p1++) {
            int c = 0;
            for(int p2 = 1; p2 <= p1; p2++) {
                if(p1 % p2 == 0) {
                    c++;
                }
            }
            if(c == 2) {
//                System.out.println(p1);
            }
        }


//        终止循环 break
//        for(int t = 0; t < 9; t++) {
//            if(t > 6) {
//                break;
//            }
//            System.out.println(t);
//        }



    }
}