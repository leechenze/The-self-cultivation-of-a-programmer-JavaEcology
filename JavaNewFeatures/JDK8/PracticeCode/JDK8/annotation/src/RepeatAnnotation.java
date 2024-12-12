import org.junit.jupiter.api.Test;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 3.使用重复注解
@MyTest("ta")
@MyTest("tb")
@MyTest("tc")
public class RepeatAnnotation {

    @Test
    @MyTest("ma")
    @MyTest("mb")
    @MyTest("mc")
    public void test() {
    }

    public static void main(String[] args) throws NoSuchMethodException {
        // 4.解析重复注解
        // getAnnotationsByType是新增的API,用于获取重复的注解
        // 4.1 获取类上的重复注解
        MyTest[] annotations = RepeatAnnotation.class.getAnnotationsByType(MyTest.class);
        for (MyTest annotation : annotations) {
            System.out.println("annotation = " + annotation);
        }
        System.out.println("--------------------------------------------------");
        // 4.2 获取方法上的重复注解
        MyTest[] tests = RepeatAnnotation.class.getMethod("test").getAnnotationsByType(MyTest.class);
        for (MyTest testAnnotation : tests) {
            System.out.println("testAnnotation = " + testAnnotation);
        }


    }
}


// 1.定义重复注解的容器
@Retention(RetentionPolicy.RUNTIME)
@interface MyTests {
    MyTest[] value();
}


// 2.定义重复注解
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MyTests.class)
@interface MyTest {
    String value();
}




