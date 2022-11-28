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
        
        P62 00:00
 