import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

public class Annotation {
    public static void main(String[] args) {

        @TestAnn(id = 100, desc = "姓名")
        String name;

        @SuppressWarnings("rawtypes")
        List list = new ArrayList();
    }
}

/**
 * 自定义注解
 */
//@Target(ElementType.FIELD)// 表示给类的属性做注解
//@Retention(RententionPolicy.RUNTIME)// 定义注解的生命周期，RUNTIME表示整个程序运行周期
@Documented // 表示注解放到Doc文档中
@interface TestAnn {
    public int id() default 0;
    public String desc() default "";
}