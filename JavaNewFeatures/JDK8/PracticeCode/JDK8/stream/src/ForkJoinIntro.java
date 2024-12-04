import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinIntro {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        /** ================================================================ */
        ForkJoinPool pool = new ForkJoinPool();
        SumRecursiveTask sumTask = new SumRecursiveTask(1, 9999999999L);
        Long result = pool.invoke(sumTask);
        System.out.println("result = " + result);
        /** ================================================================ */
        long end = System.currentTimeMillis();
        System.out.println("消耗时间 = " + (end - start));
    }
}

/**
 * ForkJoin分治法
 * 创建一个求和的任务
 * RecursiveTask:继承抽象类任务
 */
class SumRecursiveTask extends RecursiveTask<Long> {

    // 是否要拆分的临界值
    private static final Long THRESHOLD = 3000L;

    // 起始值
    private final long start;
    // 结束值
    private final long end;

    SumRecursiveTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length < THRESHOLD) {
            // 计算
            long sum = 0;
            for (long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 拆分
            long middle = (start + end) / 2;
            SumRecursiveTask left = new SumRecursiveTask(start, middle);
            left.fork();
            SumRecursiveTask right = new SumRecursiveTask(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
