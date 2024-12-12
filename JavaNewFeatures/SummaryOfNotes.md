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
    

    Interface
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
    


    Lambda method reference
        Lambda方法引用介绍(LambdaReferenceIntro)
            :: 称为方法引用运算符
                printSum(ints -> LambdaReferenceIntro.getSum(ints))
                等价于
                printSum(LambdaReferenceIntro::getSum)
            常见的引用方式
                InstanceName::methodName    (实例名::实例方法)
                ClassName::staticMethodName (类名::静态方法)
                ClassName::methodName       (类名::实例方法)
                ClassName::new              (类名::new)(new调用构造器)
                TypeName[]::new             (String[]::new)
        对象名引用成员方法(InstanceNameRefMethodName)
        类名引用静态方法(ClassNameRefStaticMethodName)
        类名引用普通方法(ClassNameRefMethodName)
        类名引用普通方法(ClassNameRefMethodName)
        类名引用New构造器(ClassNameRefNewConstruct)
        数组引用New构造器(ArrayTypeNameRefNewConstruct)



    Stream
        Stream介绍(StreamIntro)
        获取Stream流主要的两种方式(GetStreamWays)
            方式一: 根据Collection接口获取流
                default Stream<E> stream() {
            方式二: Stream中的静态方法of获取流
                public static<T> Stream<T> of(T... values)
        Stream流的注意事项(StreamAttentionMatters)
            注意事项:
                1.Stream流的方法只能操作一次(否则错误信息为: stream has already been operated upon or closed)
                2.Stream流的非终结方法返回的是新的流
                3.Stream流不调用终结方法,中间的操作不会执行
        Stream流的常用方法(StreamFrequentlyUsedMethod）
        ForkJoin(ForkJoinIntro)
            Fork/Join(分治算法)
            Fork/Join(工作窃取算法)


    Optional
        Optional介绍(OptionalIntro)
        Optional高级(OptionalAdvanced)
        

    DateTimeAPI
        日期时间API介绍(DateTimeAPIIntro)
        日期时间常用方法(DateTimeAPIFrequentlyUsedMethod)

    AnnotationUsed
        重复注解(RepeatAnnotation)
        类型注解(TypeAnnotation)
        



