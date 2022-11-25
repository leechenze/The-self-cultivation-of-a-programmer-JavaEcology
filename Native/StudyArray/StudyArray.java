
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
//        System.out.println(arr2[1][0]);

        // 自定义第一纬的长度,第二纬不定义
        int[][] arr3 = new int[3][];

        // 如下也是一个定义二维数组的方式
        int[] x[];
        x = new int[2][2];
//        System.out.println(x[0]);
//        System.out.println(x[0][0]);

//        数组常见算法
        int[] arr4 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        // 最大值 最小值
        int maxVal = 0, minVal = 0;
        for(int i = 0; i < arr4.length; i++) {
            if (maxVal < arr4[i]) {
                maxVal = arr4[i];
            }
            if (minVal > arr4[i]) {
                minVal = arr4[i];
            }
        }
        System.out.println(maxVal);
        System.out.println(minVal);

        // 总数 平均数
        int average = 0, total = 0;
        for(int i = 0; i < arr4.length; i++) {
            total += arr4[i];
        }
        average = total / arr4.length;
        System.out.println(total);
        System.out.println(average);

        // 数组复制
        int[] copyArr4 = new int[arr4.length];
        copyArr4 = arr4;
        System.out.println(copyArr4);
        int i1 = 0;
        while(i1 < copyArr4.length) {
            System.out.print(i1 + 1 + " ");
            i1++;
        }
        System.out.println();






        // 数组反转
        int[] reverseArr4 = new int[arr4.length];
        int reverseInd = 0;
        for(int i = arr4.length - 1; i >= 0; i--) {
            reverseArr4[reverseInd] = arr4[i];
            reverseInd++;
        }
        for(int i = 0; i < reverseArr4.length; i++) {
//            System.out.println(reverseArr4[i]);
        }

        // 冒泡排序
        int[] bubbleArr = new int[]{2, 9, 4, 6};
        int tempVal = 0;
        for(int i = 0; i < bubbleArr.length - 1; i++) {
            for(int j = 0; j < bubbleArr.length - 1 - i; j++) {
                if(bubbleArr[j] > bubbleArr[j + 1]){
                    tempVal = bubbleArr[j];
                    bubbleArr[j] = bubbleArr[j + 1];
                    bubbleArr[j + 1] = tempVal;
                }
            }
        }
        for(int i = 0; i < bubbleArr.length; i++) {
            System.out.println(bubbleArr[i]);
        }



//        数组下标越界
//        int[] indexOutOfBoundsArr = new int[3];
//        System.out.println(indexOutOfBoundsArr[4]);
//        空指针异常例子
//        int[] nullPointerArr = null;
//        System.out.println(nullPointerArr[0]);



    }
}
