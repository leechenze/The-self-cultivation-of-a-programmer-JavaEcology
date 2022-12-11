博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;



零.概述
    常用DOS命令
        dir：列出当前目录下的文件夹以及文件
        md：创建目录
        rd：删除目录
        cd：进入指定目录
        cd\：回到根目录
        del：删除文件
        exit：退出dos命令行
    语言概述
        第一代语言：
            打孔机--纯机器语言
        第二代语言：
            汇编
        第三代语言：
            C面向过程
            C++面向过程/面向对象
            Java面向对象
    
    运行机制
        .java文件 ==> 编译 ==> .class文件 ==> 运行 ==> 结果
        通过javac命令对java进行编译, 通过java命令对生成的class文件进行运行

贰.数据类型(DataType)
        基本类型:
            整数类型：byte, short, int, long
            浮点数类型： float，double
            字符类型：char(单引号表示) String(双引号表示)
            布尔类型：boolean
            
            char字符和数字相加会变为ASCLL码进行数学运算
            char,byte,short运算会变为int类型的值的一个转换过程
            char == byte == short => int => long => float => double
            
        引用类型：
            凡是引用类型都可以用null作为值，也就是说可以在初始化的时候赋值为null;
            String 是一个引用类型，是可以用null作为值的，同时String是不可变的;

        强制类型转换
            隐式类型转换(自动转换)
                byte b = 9;
                int i = b;
            强制类型转换(手动转换)
                int i = 9;
                byte b = (byte)i;
            字符串不能直接转换为基本类型，但通过基本类型对应的包装类可以实现把字符串转换为基本类型
            
            Boolean 类型不能强制转换为其他类型



叁.运算符(Operator)
    算术运算符是一种特殊的符号，用以表示数据的运算，赋值和比较等；
        
        1.算术运算符
            + - * / % ++ --
        2.赋值运算符
            = += -= *= /= %=
        3.比较运算符(关系运算符)
            == != < > <= >=
        4.逻辑运算符
            &  逻辑与 左边无论真假, 右边都进行运算
            && 短路与 左边为真时, 右边才参与运算, 左边为假时, 右边不参与运算 (短路意为不再执行后面运算)
            |  逻辑或 左边无论真假, 右边都进行运算
            || 短路或 左边为真时, 右边不参与运算, 左边为假时, 右边才参与运算 (短路意为不再执行后面运算)
            !  逻辑非 
            ^  逻辑异 a^b(a和b不能同时为true或false时成立)
        5.位运算符 (直接对二进制进行运算)
            << 左移
            >> 右移
            >>> 无符号右移
            & 与
            | 或
            ^ 异
            ~ 反码
        6.三元运算符

肆.程序流程控制(ProcedureFlowControl)

    顺序结构
        
    分支结构
        if else
        switch case
            switch只支持 byte, short, int, char, 枚举和string 这几种类型
    循环结构
        for循环
        while循环
        do/while循环

    终止循环
        break   用于switch和循环语句
        continue    只用于循环语句




伍.数组(StudyArray)

    数组声明
        第一种 int[] i;
        第二种 int i[];
    
    初始化
        动态初始化
            int[] i = new int[4]
                表示声明一个长度为4的int类型数组
                此时i[0] 默认是0;
                int类型默认值为0, 对象类型默认值为null;
        静态初始化(声明时就赋值就是静态初始化)
            int[] i = new int[]{1,2,3,4}
                表示声明一个内容为1,2,3,4的数组
        
    数组常见算法
        求数组最大值, 最小值, 总和, 平均数
        数组复制, 反转
        数组元素冒泡排序

    数组操作常见报错
        ArrayIndexOutOfBoundsException(数组下标越界异常);
            int[] arr = new int[3]
            System.out.println(arr[4])
        NullPointerException(空指针);
            int[] arr = null;
            System.out.println(arr[0])




陆.面向对象(ObjectOriented)
    
    面向对象与面向过程（OOP与POP）
        面向对象的三大特性：继承，多态，封装
    Java语言的基本元素：类和对象
    类成员一：属性
        语法格式：
            修饰符 类型 属性名 = 初始值
        修饰符：
            private：修饰该属性只能由该类的方法访问
            public：修饰该属性可以被类以外的方法访问
    类成员二：方法
        语法格式：
            修饰符 方法名（参数列表）{方法提语句}
        修饰符：
            public
            private
            protected：
    对象的创建和使用
        使用 new + 构造器创建一个新对象
        使用 "对象名.对象成员"的方式访问对象成员（包括属性和方法）；
    再谈方法
    面向对象特征之一：封装和隐藏
    类成员三：构造器（构造方法）
    关键字： super，this， package，import
    
    对象的产生
        当一个对象被创建时，会对其中各种类型的成员变量自动进行初始化赋值。
        byte    初始化  0
        short   初始化  0
        int     初始化  0
        long    初始化  0L
        float   初始化  0.0F
        double  初始化  0.0D
        char    初始化  '\u0000'（表示为空）
        boolean 初始化  false
        应用类型  初始化  null

    类的访问机制
        实例方法可以访问static属性
        但static方法只能访问static属性

    方法重载（Overload）
        在同一个类中，允许存在一个以上的同名方法，只要他们的参数类，参数个数或者参数参数顺序不同即可
    
    方法形参可变（ChangeableParam）
        Java特有的...的方式传递可变个数的形参
        在使用时与数组的使用方式相同，并且如果没有参数可以不填
        注意可变参数放置到形参的最后一位;
            public void printInfo1(String s, String... args) {
    
    方法的参数传递
        引用数据类型和基础数据类型
        Java中方法的传递只有一种：值传递；
        即将实际参数值的副本（拷贝品）传入方法内，而参数本身不受影响
        JVM内存模型：
            栈（Stack）：基础数据类型（对象的引用，对象的地址）
            堆（Heap）：所有对象（包括自定义对象和字符串对象）
            方法区（Method）：所有的class和static变量
            
        基础数据类型的值是保存在栈中的；
        引用对象的值保存在堆中，栈中存的是对象在堆中的地址或者说是引用

    软件包（StudyPackage.Animal）
        关键字 package
        1.package 语句作为Java源文件的第一条语句, 知名该文件中定义的类所在的包,若缺省改语句,则指定为无名包
        2.包对应文件系统目录，package语句中用（.）来指定包的层次
        3.包通常用小写单词，类名通常首字母大写。
        
        关键字 import
        1.可以在类型声明中使用路径全名进行声明，如下：
            StudyPackage.Animal.Dog dog = new StudyPackage.Animal.Dog();
        2.可以使用import lee.* 语句，表示引入lee包下的所有类，但lee包下的sub子包内的类不会被引入
            import StudyPackage.Animal.*;
        
        JDK中主要的包介绍：
            所在位置：(JRE System Library ｜ External Libraries)；
                java.lang       Java语言的核心类，如：String，Math，Integer，System和Thread等，提供常用功能；
                java.net        包含执行与网络相关的操作的类和接口
                java.io         包含能提供多种输入/输出功能的类
                java.util       包含一些实用工具类，如：定义系统特性，接口的集合框架类，日期日历相关函数
                java.text       包含了一些java格式化相关的类
                java.sql        包含了java进行JDBC数据库编程的相关类和接口
                java.awt        包含了构成抽象窗口工具集(abstract window toolkits)的多个类,这些类被用来构建和管理应用程序的图形用户界面(GUI)
                java.applet     包含applet运行所需要的一些类
        
    封装和隐藏（StudyPrivate）

    四种访问权限修饰符总结
        
        private     类内部
        default     类内部 + 同一个包 (缺省)
        protected   类内部 + 同一个包 + 子类
        public      类内部 + 同一个包 + 子类 + 任何地方
        
        同个Java文件中写多个class时，只能有一个public，其他class只能是default；
    
    构造器（Constructor）
        
        特性：
            具有与类名相同的名称
            不声明返回值类型。（与声明为void不同）
            不能被static final synchronized abstract, native修饰，不能有返回值
        分类：
            隐式默认无参构造器
            显式定义一个或多个参数构造器
        
        构造器修饰是根据类的修饰符的
        new对象实际上就是调用类的构造方法
        
    构造器重载（ConstructorOverload）
        构造器也叫构造方法，既然是方法就可以重载

    关键字this
        this表示的是当前对象，可以调用类的属性，方法，和构造器
        当在方法内部需要用到调用该方法的对象时就需要用到this；
        指向：
            1.在方法内部使用时，指向这个方法所属对象的引用；
            2.在构造器中使用时，指向该构造器正在初始化的对象

        疑问：构造方法中的age和name会编译报错
            public class ConstructorInstance {
                public ConstructorInstance (int age, String name) {
                    name = name;
                    age = age;
                }
                int age;
                String name;
            }
        答案：此时引用this处理age和name错乱的问题
            public class ConstructorInstance {
                public ConstructorInstance (int age, String name) {
                    this.name = name;
                    this.age = age;
                }
                int age;
                String name;
            }

        this()可以对构造方法进行调用；
            public class Test {
                public Test () {}
                public Test (String name) {
                    this();
                }
                public Test (int age) {
                    this("李四");
                }
                public Test (int age, String name) {
                    this(20);
                }
            }
        this()的注意事项：
            1。this()必须放到方法的首行使用；
            2。两个构造器中不能相互用this()调用;
            3。当前构造器中不能自己调用自己;
        
    JavaBean
        JavaBean是一种Java语言写成的可重用组件
        所谓JavaBean是指符合如下标准的Java类
            类是公共的
            有一个无参的公共构造器
            有属性，且有对应的get，set方法
        
        JavaBean 示例：
            public class BeanTest {
                int age;
                public void setAge (int age) {
                    this.age = age;
                }
                public int getAge (int age) {
                    return age;
                }
            }
        

    继承（Inherit）
        多个子类中存在相同的属性和行为时，将这些内容抽取到同一个类中，那么多个子类通过继承该类，就可以访问这些属性和方法
        公用多个属性和方法的父类被成为（基类或超类）
        子类不是父类的子集，而是对父类的扩展
        类继承语法规则：
            class SubClass extends SuperClass {}
        
        作用：
            提高代码复用性
            使类和类之间产生联系，提供多态的前提
            不要仅为了获取其他类中的某一个功能而继承（要考虑逻辑关系，不能随意继承）
            
        注意：
            子类不能直接访问父类中私有的成员变量和方法；
            Java只支持单继承，不允许多继承。
                一个子类只能有一个父类
                一个父类可以派生出多个子类
                子类还可以被孙类再继承（多层继承）
        
        类继承的方法的重写 ：
            子类重写父类的方法，只是重新编写方法体的代码
            如果父类的方法是public时，子类重写的时候就不能使用缺省以下的修饰符
            重写和被重写的方法需同时为static，或同时为非static。
            
        四种权限修饰符补充：
            如果父类和子类在同一个包下，子类可以访问父类的 default，protected，public 修饰的属性
            如果子类和父类不在同一个包下时，子类可以访问父类的 public和protected 修饰的属性
        
        
        Super（FatherSuper & SonSuper & SunSuper）

            在Java类中使用super进行调用父类中的属性，方法和构造器
            
            如果当父子类中出现同名成员时，可以使用super进行区分
            super的追溯不仅限于直接父类（对于多层继承来讲）
                super可以调用所有的父类层级属性和方法；
            super和this的用法相似，this代表本类的对象引用，super代表父类的内存空间的标识。
            
            调用父类的构造器：
                子类中所有的构造器默认都会执行父类中空参数的构造器（默认构造器）
                当父类中没有空参数构造器时，子类构造器必须通过this（参数列表）或super（参数列表）语句指定调用本类或父类中相应的构造器，且必须放在第一行
        
        
        this & super 区别
            
            访问属性：super访问父类的属性，this访问本类属性如果没有则从父类中查找访问
            调用方法：super访问父级中的方法，this访问本类中的方法
            调用构造器：super调用父类构造器且必须放在子类构造器首行，this调用本类构造器且必须放在构造器首行
            特殊：this表示当前对象，super无特殊概念。
        
        类对象的实力化过程
            JVM内存构造：
                1。栈内存
                2。堆内存
                3。方法区
            父类：Person p = new Person();
            1。在方法区加载person.class
            2。在栈内存中申请空间，声明变量p
            3。在堆内存中开辟空间，为p对象分配一个地址
            4。在堆内存中对对象中的属性进行默认初始化。也就是赋为默认值，类成员变量显式初始值
            5。栈内存调用构造方法；
            6。栈内存中初始化完毕后，将堆内存中的地址值赋给引用变量，构造方法出栈
            
        子类对象的实例化
            子类：Student s = new Student();
            1。先加载Person.class，再加载Student.class;
            2。在栈内存中申请空间，声明变量s
            3。在堆内存中开辟空间，为p对象分配一个地址
            4。在堆内存中对对象中的属性进行默认初始化。包括父类的属性进行初始化；
            5。栈内存调用子类构造方法；
            6。有super()时，堆内存显示初始化父类的属性
            7。栈内存父类构造方法进栈，（调用父类构造器）执行完毕出战
            8。堆内存显示初始化子类的属性
            9。初始化完成后，将堆内存中的地址赋值给引用变量s，子类构造方法出栈；
        
    多态
        多态性是面向对象中最重要的概念，在Java中主要有两种体现
            1。方法的重载和重写
            2。对象的多态性，可以直接应用在抽象类和接口上
        Java引用变量有两个类型：
            编译时类型
                由声明该类型时使用的类型决定；
            运行时类型
                由实际赋给该变量的引用对象决定；
        
        Student s = new Student();
            Student s 这部分就是编译期类型
            new Student() 这部分是运行期类型
        如果编译期类型和运行期类型不一致，就会出现多态（polymorphism）
        
        对象的多态：
            子类的对象可以替代父类的对象来使用
            一个变量只能有一种确定的数据类型
            一个引用类型变量可能指向多种不同类型的对象。如下所示：
                Person p = new Person();
                Person s = new Student();
            子类可以看作是一个特殊的父类，所以父类类型的引用可以指向子类的对象，这种指向被称作向上转型（upcasting）

            一个引用类型变量如果声明为父类的类型,但实际引用的是子类对象,那么该变量就不能再访问子类中添加的属性和方法
        
        虚拟方法调用： Virtual Method Invocation
            如果子类和父类中有同名的方法，变量将调用实际赋值的方法实例对象的类方法；
        多态小结：
            前提：
                需要存在继承或者实现关系
                要有覆盖操作
            成员方法：
                编译时：要查看引用变量所属的类中是否有所调用的方法
                运行时：调用实例对象所属的类中的重写方法
            成员变量：
                不具备多态性，只看引用变量所属的类；
    
    instanceof操作符
        x instanceof A
        检验x是否是类A实例出来的对象，返回值为boolean型；
        要求x所属的类与类A必须是父类和子类的关系，否则编译错误；
        
    Object类（StudyObject）
        Object类是所有Java类的父类
        当一个方法中的形参不确定是一个什么类型的类时，可以使用Object类来指定形参
            public Object()                     构造方法
            public boolean equals(Object obj)   对象比较
            public int hashCode()               获取Hash码
            public String toString()            对象打印时调用
        
        对象类型转换
            基础数据类型转换：
                自动类型转换：小的数据类型可以转换为大的数据类型
                    long g = 20
                    double d = 12.0f
                强制类型转换：可以把大的数据类型转换为小的数据类型
                    float f = (float)12.0;
                    int a = (int)1200l;
            Java对象的强制类型转换称之为造型
                从子类到父类的类型转换可以自动进行
                从父类到子类的类型转换必须通过造型实现（强制类型转换）；
                无继承关系的引用类型间的转换是非法的；
        
        == 和 equals()
            ==：
                基本数据类型比较值：只要两个变量的值相等即为true
                引用数据类型比较引用：只有指向同一个对象时，==才返回true；
                    Person p = new Person();
                    Person p1 = new Person();
                    p == p1;        false;
    
                    Person p = new Person();
                    Person p1 = new Person();
                    p = p1;
                    p == p1;        true;
                用==比较引用数据类型时，符号两边的数据类型必须兼容，否则编译出错
            
            equals()：
                是Object上的方法，只能比较引用类型，作用和==符相同；
                equals()对类File，Stirng，Date及包装类（Wrapper Class）来说，是比较类型及内容，而不考虑引用的是否是同一个对象；
                如下用String举例：
                    String s1 = new String("abc");
                    String s2 = new String("abc");
                    s1 == s2            false
                    s1.equals(s2)       true
                
        String对象的创建方式：
            字面量创建：
                String s1 = "abc";
                String s2 = "abc";
                s1 == s2            true
                字面量形式的String对象都会存储在堆内存中的常量池中，不会像内存地址那样重新开辟一个内存空间放置常量值
            nwe创建String对象：
                String s3 = new String("abc");
                String s4 = new String("abc");
                s3 == s4            false
                
            
            String小结：
                字面量的方式创建对象String比new构造的方式创建更省内存；但是两种方式的判断结果是不一样的；
                

    

    包装类（WrapperClass）
        
        包装类是针对八种基本数据类型的封装，又叫做封装类，并且有了类的特点，就可以调用类中的方法；
        
        基本数据类型：
            boolean
            byte
            short
            int
            long
            char
            float
            double
        包装类：
            Boolean
            Byte
            Short
            Integer
            Long
            Character
            Float
            Double
        
        基本数据类型包装成包装类的实例（装箱）
            通过包装类的构造器实现：
                int i = 500;
                Integer i = new Integer(i);
            还可以通过字符串参数构造成包装类对象：
                Float f = new Float("4.56");
                Long l = new Long("asdf");      Number Format Exception
        
        获取包装类对象中包装的基本数据类型的变量（拆箱）
            调用包装类的 xxxValue() 方法；
            Integer i = new Integer(112);
            int i1 = i.intValue();              112
            
            boolean b = new Boolean("false").booleanValue();

        在JDK1.5之后，支持自动拆箱，自动装箱，但类型必须匹配；
            
            所谓自动装箱即是：
            Integer i = new Integet(112);
            Integer i = 112;
            
            所谓自动拆箱即是：（省略xxxValue的过程）
            Integer i = new Integer(112);
            int i1 = i;
            
        字符串转成基本数据类型
            通过parseXxx方式，如下示例：
            int i = Integer.parseInt("123");
            float f = Float.parseFloat("0.45");
            boolean b = Boolean.parseBoolean("true");
        基本类型转为字符串
            String istr = String.valueOf(i);
            String fstr = String.valueOf(f);
            String bstr = String.valueOf(true);
        
        包装类作用：
            基本数据类型和字符串的直接转化；
        
        toString()
            实例化后会默认执行toString的方法，包括重写的toString方法；
            也就是当打印实例对象时返回的是toString中的值；
            toString是Object的方法（顶层方法）
            如果不重写toString方法时，toString方法输出当前对象的内存地址
            
        static
            声明静态属性和方法（类变量，非实例化变量）
            如果想让一个类的所有实例共享数据，那么就用类变量（静态变量）
            
            类属性和类方法的设计思想：
            
            类属性作为该类各个对象之间共享的变量，在设计类时，如果希望有些属性不因实例对象不同而改变时，就用类属性；
            如果方法和调用者无关，则这样的方法通常声明为类方法，由于不需要创建对象就可以调用类方法；也简化了方法的调用；
            
            适用范围：
                在Java类中，可用static修饰属性，方法，代码块，内部类；
                被修饰后的的成员具备以下特点：
                    随着类的加载而加载
                    优先于对象的存在
                    修饰的成员，被所有对象所共享
                    访问权限允许时，可不创建对象，直接被类调用
            因为不需要实例就可以访问static方法，因此static方法内部不能有this 和 super

        单例设计模式：
            设计模式是在大量的实践中总结和理论化之后优选的代码结构,编程风格,以及解决问题的思考方式.
            单例模式：只有一个实例化对象（只会new一次）；
                
            单例模式一：饿汉式（HungryMan）
                
            单例模式一：懒汉式（Sluggard）
                最开始对象是null，知道有第一次调用时，才new一个对象；
            
            懒汉和饿汉的区别就在于对象是在什么时候创建的；（创建时机不同）；
            
            暂时懒汉式是存在线程安全的问题，到多线程章节会处理如何修复
            
            java.lang.Runtime是Java的一个内置方法，它是一个典型的单例模式的应用；
        
        再谈main方法
            由于Java虚拟机需要调用类的main方法，所以该方法的访问权限必须是public。
            又因为Java虚拟机在执行main方法时不必创建对象，所以mian方法必须是static的。
            该方法接受一个String类型的数组参数，该数组中保存执行Java命令时传递给所有类的参数；
            
            public class TestMain {
                public static void main (String[] args) {
                    for(int i = 0; i < args.length; i++) {
                        System.out.println(args[i]);
                    }
                }
            }
            保存为TestMain.java，然后在命令行执行：
            >_ javac TestMain
            >_ java TestMain abc 123 fff
            >_ abc
            >_ 123
            >_ fff
        
    初始化块：(CodeBlock) 别称代码块
        能够对Java对象进行初始化；
        程序执行顺序：
            1。声明成员变量的默认值；
            2。显示初始化，多个初始化块依次被执行；
            3。在执行构造方法，实例对象进行赋值操作；
            简言之：代码块 > 构造方法
            
        静态代码块:
            静态代码块只能操作静态属性或方法;
        
        代码块和静态代码块区别：
            代码块每次new对象都要重新执行
            静态代码块无论实例(new)多少次只会执行一次
            静态代码块的执行要先于非静态代码块
            实际开发场景中，静态代码块用的会多一些，多用在初始化类的属性（静态属性）；
            代码块典型应用场景就是在匿名内部类中代替构造方法使用，因为是匿名的内部类，没有确切名称，所以构造方法名称无法确定只能用代码块代替；
    
    final：（studyFinal）
        final修饰的类不能被继承了，提高安全性，提高程序的可读性
        final修饰的方法不能被子类重写，但是可以被继承
        final修饰的变量即为常量，不能被改变，一般被final修饰的变量大写
    
    抽象类：（abstractClass)
        用abstract修饰一个类时，这个类就是一个抽象类
        用abstract修饰一个方法时，该方法就是一个抽象方法
            修饰方法没有方法具体实现（没有方法体），只声明名称，返回值和参数；
        含有抽象方法的类必须声明为抽象类
        抽象类不能被实例化。
            抽象类主要是作为父类用来被继承的；作为父类被继承。
            抽象类的子类必须要实现（重写）父类的抽象方法，
        不能用abstract修饰属性，私有方法，构造器，静态方法，final等方法
        抽象方法没有方法体所以不用写 {};
        final和abstract正好是冲突的；

    模版方法设计模式：（TemplateClass）
        抽象类体现的就是一种模版模式的设计,抽象类作为多个子类的通用模版,子类在抽象类的基础上进行扩展,改造,但子类总体上会保留抽象类的行为方式
        
        解决的问题：
            当功能内部一部分实现是不确定的，就把这部分不确定的暴漏出去，让子类去实现
            编写一个抽象父类，父类提供了多个子类的通用方法, 并把方法留给子类实现, 就是一种模版模式
            

    接口：（interface）
        有时必须从几个类中派生出一个子类,继承它们所有的属性和方法.但是,Java不支持多重继承. 有了接口, 就可以得到多重继承的效果
        接口（interface）是一个特殊的抽象类，含有常量和方法的定义，没有变量和方法的实现
        实现接口类：（implements）
            class SubClass implements Interface{}
        一个类可以实现多个接口，接口也可以继承其他接口；
        
        接口的特点：
            用interface定义
            接口中所有成员变量默认是由public static final修饰的（全局常量）；
            接口中所有成员方法默认是由public abstract修饰的
            接口没有构造器
            接口采用多层继承机制，接口可以继承接口
        
        实现接口中的方法必须实现接口中的所有方法的具体实现内容，方可实例化，否则类必须声明为抽象类（abstract）；
        接口主要用途就是指定规范，然后被实现类实现；
        和继承关系类似，接口与实现类之间存在多样性
        语法格式：如果类有继承关系同时还有接口实现关系时，要先写extends，在写implement；（先继承，再实现）
        
        疑问：接口和抽象类很相似，好像接口能做的事情，用抽象类都能做，那干嘛还用接口呢？
            答疑：抽象类在新增抽象方法时，子类继承时，方法必须要实现父类中所有的抽象方法，而接口则不然，这就是接口的作用和必然性
        
        用Java描述：会唱歌的厨师是个好老师；
        Person，Singer，Cook，Teacher；（四个抽象类）
        如果定义是个类的话老师继承厨师继承歌唱家再继承人类，那么歌唱家和厨师的类无法避免的会被污染
        正确做法应该是：
        Teacher类继承Person这个抽象类，实现Singer和Cook这两个接口，也就是把Singer和Cook定义为接口而不是抽象类；
        
        接口其他问题：
            如果实现接口的类中没有实现接口的全部方法时,必须将此类定义为抽象类。


    工厂模式：（FactoryClass）
        Factory工厂模式是设计模式中应用最广泛的设计模式,它通过创建面向对象的手法, 将所要创建的具体对象的创建工作延迟到子类,
        从而提供了一种扩展的策略, 较好的解决了这种紧耦合的关系
        代码详见：Factory
    
    
    内部类：（inernalClass）
        允许一个类定义与另一个类的内部，前者成为内部类，后者成为外部类，
        
        内部类作为类成员
            可以声明为final
            和外部类不同，内部类可声明为 private 和 protected
            内部类可以声明为static，但此时就只能使用外部类static的成员变量
            内部类可以声明为abstract类，因此可以被其他内部类继承
            
        内部类作用（MultipleInherit)
            解决Java不能多重继承的问题






柒.异常(exception)
    
    异常用于处理非预期的情况，如网络错误，非法参数，路径错误等。。。
    
    Java程序运行过程中所发生的异常事件可分为两类
        Error：JVM内部错误，资源耗尽等严重错误
            VirtulMachineError
                StackOverFlowError(栈溢出)
                OutOfMemoryError(内存溢出)
            AWTError
                ...
        Exception：其他因编程错误或偶然的外在因素导致的一般性问题
            IOException(文件读写异常)
            RuntimeException(运行时异常)
        

    捕获异常
        异常处理机制处理的事抓抛模型
            Java程序的执行过程中如出现异常,会自动生成一个异常类对象该异常对象将被提交给Java运行时系统,这个过程称为抛出(throw)异常。
            如果一个方法内抛出异常,该异常会被抛到调用方法中。如果异常没有在调用方法中处理,它继续被抛给这个调用方法的调用者这个过程将一直继续下去,直到异常被处理。这一过程称为捕获(catch)异常。
        通常开发者处理Exception，对Error虚拟机异常无法处理
        
        详见exception.Implement





捌.集合(Collection)
    
    集合类存放在java.util包中，是一个用来存放对象的容器
        1。集合只能存放对象
            如果存入一个基本类型，会自动转换成包装类后存入，Java中每一种基本类型都有对应的引用类型（包装类）；
        2。集合中存放的是多个对象的引用，对象本身还是存放在堆中
        3。集合可以不同的类型，不限数量的数据类型
    
    Java集合可以分为Set，List和Map三大种类
        Set：无序不可重复的集合
        List：有序可重复的集合
        Map：有映射关系的集合
    在JDK5之后，增加了泛型，Java集合可以记住容器中对象的数据类型
        加入泛型后，集合就不能存其他类型了
    

    Set
        HashSet（HashSetColl）
            继承关系：
                HashSet ==> Set ==> Collection
            HashSet是对Set接口的典型实现，大多数时候使用Set集合时都使用这个实现类。我们大多数时候说的Set集合指的都是HashSet
            HashSet按Hash算法存储集合中元素，因此具有很好的存取和查好性能
            HashSet具有一下特点：
                不能保证元素的排列顺序
                不可重复
                    指的是hashCode不相同，而不是通过equals判断是否相同存入的
                    一般equals结果为true时，hashCode值也会为true
                HashSet不是线程安全的
                集合元素可以存null
            HashSet集合判断两个元素相等的标准：两个对象通过equals方法比较相等，并且两个对象的hashCode方法的返回值也相等。

        TreeSet（TreeSetColl）
            继承关系：
                TreeSet ==> NavigableSet ==> SortedSet ==> Set ==> Collection
            TreeSet是SortedSet接口的实现类，可以确保集合元素处于排序状态
            TreeSet支持两种排序方法：
                自然排序（默认排序）
                    必须放入同样类型的对象，否则可能会发生类型转换异常的异常，使用泛型进行限制
                定制排序
                    定制排序需要在创建TreeSet集合对象时，提供一个Comparator接口的实现类对象
                    由Comparator对象负责集合元素的排列逻辑
                
            
            










            
        
            
        
            


