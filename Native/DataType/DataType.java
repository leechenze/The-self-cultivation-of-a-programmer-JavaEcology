public class DataType {
    public static void main (String[] args) {
        int i = 1;
        short s = 2;
        byte b = 3;
        // byte和short 可以赋值给Int, int不可以赋值给byte或short， 不同类型数字相加，byte和short都会有先转换为int的操作
        int res = i + s + b;
        System.out.println(res);
        // char 类型的字符串在和数字进行数学运算时，会转换成ASCLL码进行运算;
        char c = 'a';
        byte b0 = 2;
        int res1 = c + b0;
        System.out.println(res1);

        String str = "abc";
        int i1 = 1;
        System.out.println(str + i1);

        String str0 = "abc";
        String str1 = str0 + 1 + 2 + 3;
        System.out.println(str1);


    }
}