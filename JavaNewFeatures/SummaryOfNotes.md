博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;

官网地址(openjdk.org)
视频地址(https://www.bilibili.com/video/BV1zJ411R7uQ/)

零.JDK8

    Lambda
        Lambda表达式介绍(LambdaIntro)
        Lambda标准格式(LambdaUse)(LambdaParam)
        Lambda省略格式(LambdaOmit)
            和JavaScript的箭头函数省略规则一致
        Lambda的前提条件()
            方法的参数或局部变量类型必须为接口才能使用Lambda
            接口中有且仅有一个抽象方法
            只有一个抽象方法的接口称为函数式接口,这种接口可以使用Lambda表达式
    
    interface
        接口默认方法的使用(InterfaceDefaultFunctionUse)
        接口的静态方法(InterfaceStaticFunctionUse)
            接口默认方法可以被继承和重写
            接口静态方法不可以被继承和重写
        常用内置函数式接口(FunctionalInterface)
            Supplier接口(SupplierInterface)
                无参数有返回值(供给型接口)
                public interface Supplier<T> {
                    T get();
                }
            Consumer接口(ConsumerInterface)(ConsumerInterfaceAndThen)
                有参数无返回值(消费型接口)
                public interface Consumer<T> {
                    void accept(T t);
                }
            Function接口(FunctionInterface)(FunctionInterfaceAndThen)
                有参数有返回值(类型转化接口)
                public interface Function<T, R> {
                    R apply(T t);
                }
            Predicate接口(PredicateInterface)(PredicateInterfaceAndOrNegate)
                有参数返回值布尔值
                public interface Predicate<T> {
                    boolean test(T t);
                }
    
    Lambda方法引用
        




