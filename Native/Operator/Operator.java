public class Operator {
    public static void main (String[] args) {

        int i = 1;
        short s = 2;
        i = s; // 自动类型转换
        s = (short)i; // 强制类型转换

        System.out.println(i);
        System.out.println(s);

        int i0 = 0;
        int i1 = 0;
        int i2 = 0;

        i0 = i1 = i2 = 1;

        System.out.println(i0 + "," + i1 + "," + i2);


        short s0 = 1;
        s0 = (short)(s0 + 1); // 和扩展运算符的结果是一样的
        s0 += 1; // 扩展运算符的变量参与运算时, 过程会强制类型转换, 不需要以上手动类型转换的步骤

        System.out.println(s0);

        int i3 = 1;
        i3 *= 0.1; // i3 = i3 * .1, 但由于0.1是小数, 强制类型转换为整数后是0;
        i3 = (int) (i3 * .1);

        System.out.println(i3);










    }
}