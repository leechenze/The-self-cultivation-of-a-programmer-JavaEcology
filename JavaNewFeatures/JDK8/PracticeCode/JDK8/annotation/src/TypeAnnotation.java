import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


public class TypeAnnotation<@TypeParam T> {

    private @TypeUse int a = 10;


    public <@TypeParam E> void test() {
    }

    public <@TypeParam E extends Integer> void test1() {
    }

    public void test2(@TypeUse String str, @TypeUse Integer a) {
        @TypeUse double d = 1.0;
    }

    public static void main(String[] args) {

    }
}


// 该注解指定的 TYPE_PARAMETER 表示范型上的注解
@Target(ElementType.TYPE_PARAMETER)
@interface TypeParam {
}

// 该注解指定的 TYPE_USE 表示变量前的注解
@Target(ElementType.TYPE_USE)
@interface TypeUse {
}