package Native.StudyArray;

public class StudyArray {
    public static void main (String[] args) {

        String[] arr = new String[]{"a", "b", "c", "d"};
        int[] arr1 = new int[5];

        System.out.println(arr[0]);
        System.out.println(arr.length);

        System.out.println(arr1[0]);

        arr1[0] = 1;

        System.out.println(arr1[0]);

        // 多维数组
        int[][] arr2 = new int[][]{
                {1, 2}, {3, 4}
        };
        System.out.println(arr2[1][0]);

        // 自定义第一纬的长度,第二纬不定义
        int[][] arr3 = new int[3][];

        // 如下也是一个定义二维数组的方式
        int[] x[];
        x = new int[2][2];
        System.out.println(x[0]);
        System.out.println(x[0][0]);









    }
}
