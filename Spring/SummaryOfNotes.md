博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;



壹.Spring

    Spring简介:
        Spring是JavaEE开发必备技能，企业开发技术选型命中率>90%
    专业角度：
        简化开发，降低企业级开发的复杂性
            IoC
            AOP
                事务处理
        整合框架，高效整合其他技术，提高企业级应用开发与运行效率
            MyBatis
            MyBatis-plus
            Struts
            Struts2
            Hibernate
            。。。
    初始Spring：
        了解Spring家族：
            官网：spring.io（Spring全家桶）
            Spring发展到今天已经形成了一套开发的生态圈，Spring提供了若干个项目，每个项目用于完成特定的功能
        Spring Framework
            framework是一个底层框架，所有东西都能放在它上面运行，是Spring生态圈中最基础的项目，是其他项目的根基，其他项目都在它的基础上运行使用。
        Spring Boot
            Spring可以简化开发，在Boot基础上进行加速开发，能是原来的spring开发更简单，书写更少的代码
        Spring cloud
            cloud应用于分布式开发，这三个框架注意满足企业级开发的所有主流开发工作
        版本介绍：（Spring）发展史
            Spring1。0（2004年）
                使用纯配置的形式开发
            Spring2。0（2006年）
                引入了注解的功能，提升开发效率
            Spring3。0（2009年）
                演化为可以不写配置的开发模式，大幅度提升开发效率
            Spring4。0（2013年）
                紧跟JDK的版本升级，对个别API进行调整，和3。0版本有些许的变化
            Spring5。0（2017年）
                Spring框架全面支持JDK8，也就是说，从第五版本之后，如果想用JDK开发，那么JDK版本必须是在8版本以上
            
    Spring系统架构：
        Core Container 核心容器：
            Spring框架中最核心的部分，以下的所有的模块都是依赖它运行的。
        
        AOP 面向切面编程 & Aspects AOP 思想实现：
            
        Data Access 数据访问 & Data Integration 数据集成：
            JDBC，ORM，OXM，JMS，Transactions
            
        Web Web开发：

        Test 单元测试与集成测试：
            
        优先级：
            第一部分：核心容器
                核心概念：（IoC/DI）
                容器基本操作
            第二部分：整合
                整合数据层技术MyBatis
            第三部分：AOP
                AOP基础开发
                AOP实用开发
            第四部分：事务
                事务实用开发
            第五部分：家族
                SpringMVC
                SpringBoot
                SpringCloud
    
    核心概念：
        IoC/DI/Bean
            IoC（Inversion of control）控制反转
                对象的控制权由程序转移到外部，这种思想成为控制反转，即（IoC思想），这一思想的目的就是为了让代码具备低耦合的特性，大白话就是不要自己new对象，对象由外部提供；
                Spring对IoC技术进行了实现，Spring提供了一个容器成为IoC容器（也称为Spring容器），用来充当IoC思想当中的"外部"
                也就是开发过程中，由主动new对象转换为IoC容器提供对象。Service对象还是dao对象都可以在IoC容器进行管理，Service（业务层），Dao（数据层）。
            Bean
                IoC控制对象的创建，初始化等一系列工作，被创建或被管理的对象在IoC容器中统称为 Bean。
            DI（Dependency Injection）依赖注入
                在容器中建立Bean与Bean之间的依赖关系的整个过程，称为依赖注入。
                比如业务层Service需要依赖数据层Dao才能运行，那么Service和Dao这两个Bean就是依赖关系，那么这种依赖关系在IoC容器中绑定好。这个过程就是依赖注入。
            目的：充分解耦
                使用IoC容器管理Bean（IoC）
                在IoC容器内将有依赖关系的Bean进行关系绑定（DI）
            最终效果：
                使用对象时不仅可以直接从IoC容器中获取，并且获取到的Bean已经自动绑定了所有的依赖关系。
    
    IoC入门案例：(IoC)
        1。导入坐标
            <dependencies>
                <dependency>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-context</artifactId>
                    <version>5.2.10.RELEASE</version>
                </dependency>
                <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>4.12</version>
                    <scope>test</scope>
                </dependency>
            </dependencies>
            导完以上的坐标更新Maven后，在resources下创建applicationContext.xml文件进行对IoC容器的配置
        2。配置Bean（applicationContext.xml）
            <!--标签：bean-->
            <!--    属性id：标示Bean名称-->
            <!--    属性class：表示给Bean定义类型-->
            <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl"></bean>
            <bean id="bookService" class="com.lee.service.impl.BookServiceImpl"></bean>
        3。获取IoC容器（App2.java)
            // 获取IoC容器
            ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        4。获取Bean（App2.java)
            BookDao bookDao = (BookDao) ctx.getBean("bookDao");
            // 执行方法...
            bookDao.save();
    DI入门案例：(IoC)
        1。取消业务层中使用new的方式创建dao对象（BookServiceImpl）
            private BookDao bookDao = new BookDaoImpl();
            改为：
            private BookDao bookDao;
        2。提供依赖对象对应的setter方法进行对bookDao的赋值（setter方式是由容器执行的）
            public void setBookDao(BookDao bookDao) {
                this.bookDao = bookDao;
            }
        3。配置service与dao的关系（applicationContext.xml)
            <bean id="bookService" class="com.lee.service.impl.BookServiceImpl">
                <!-- property标签表示配置当前bean的属性 -->
                <!--name属性表示配置具体哪一个属性（private BookDao bookDao）-->
                <!--ref属性表示参照哪一个bean（property标签上 name="bookDao"）-->
                <property name="bookDao" ref="bookDao"></property>
            </bean>
            
    bean基础配置
        别名配置
            范例：<bean id="bookService" name="service,service1,service2" class="com.lee.service.impl.BookServiceImpl"></bean>
            通过name指定别名，多个别名用 , ; 空格 都可以进行分割
        作用范围配置
            指创建Bean时的作用数量，作用与一个还是作用于多个
            可选范围如下：
                singleton：单例（默认）
                prototype：非单例
            范例：<bean id="bookService" scope="prototype" class="com.lee.service.impl.BookServiceImpl"></bean>
            大白话：（App2）
                BookDao bookDao = (BookDao) ctx.getBean("bookDao");
                BookDao bookDao1 = (BookDao) ctx.getBean("bookDao");
                singleton时：bookDao == bookDao1
                prototype时：bookDao != bookDao1
            问题：
                为什么bean默认为单例？
                    如果一个bean不是单例的，那么这个bean的数量将会非常多，用一次都要造一个，所以IoC管理对象的思想就是造可以复用的对象，这样效率才会更高一些
                    适合交给容器进行管理的Bean（适合单例的Bean，造一次就OK了，可以反复用）：
                        表现层对象（servlet）
                        业务层对象（service）
                        数据层对象（dao）
                        工具类对象
                    不适合交给容器管理的Bean
                        封装实体的域对象
    bean实例化
        构造方法实例化Bean：
            Bean本质上就是对象，spring创建bean也是使用构造方法完成 
            Spring创建Bean时用的是无参的构造方法，无参构造方法如果不存在，将抛出异常（BeanCreationException）
        静态工厂方法实例化Bean：(做个了解，这是早些年的一个常用的方式，为了造对象不要自己new，用工厂的方式作一定程度的解耦)
            提供一个静态工厂（OrderDaoFactory）
                public class OrderDaoFactory{
                    public static OrderDao getOrderDao () {
                        return new OrderDaoImpl();
                    }
                }
            配置
                <bean id="orderDao" class="com.lee.factory.OrderDaoFactory" factory-method="getOrderDao"></bean>
                使用factory-method指定工厂类中生成示例的方法，否则造出来的就是工厂对象
        实例工厂方法实例化Bean：(这种方式很多余)
            实例工厂
                public class UserDaoFactory{
                    public UserDao getUserDao () {
                        return new UserDaoImpl();
                    }
                }
            配置
                <bean id="userDaoFactory" class="com.lee.factory.userDaoFactory"></bean>
                <bean id="userDao" factory-method="getUserDao" factory-bean="userDaoFactory"></bean>
        实例工厂方法加FactoryBean：（实例工厂的升级版，很重要，务必掌握）
            实例工厂
                public class UserDaoFactoryBean implements FactoryBean<UserDao>{
                    public UserDao getObject() throws Exception{
                        return new UserDaoImpl();
                    }
                    public Class<?> getObjectType() {
                        return UserDao.class;
                    }
                    public boolean isSingleton() {
                        return false;
                    }
                }
            配置
                <bean id="userDao" class="com.lee.factory.UserDaoFactoryBean"></bean>
    Bean的生命周期
        生命周期控制之配置方式：
            提供生命周期控制方法：
                public class BookDaoImpl implements BookDao{
                    public void save() {
                        System.out.println("book dao save");
                    }
                    public void init () {
                        System.out.println("book dao init");
                    }
                    public void destroy() {
                        System.out.println("book dao destroy");
                    }
                }
            配置生命周期控制方法：
                <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl" init-method="init" destroy-method="destroy"></bean>
        生命周期控制之实现接口方式：（实现 InitializingBean,DisposableBean 接口，则无需配置）
            public class BookDaoImpl implements BookDao, InitializingBean, DisposableBean {
                public void save() {
                    System.out.println("book dao save");
                }
                public void afterPropertiesSet() throws Exception {
                    System.out.println("afterPropertiesSet");
                }
                public void destroy() throws Exception{
                    System.out.println("destroy");
                }
            }
        Bean的生命周期中经过的阶段
            初始化容器：
                1。创建对象（内存分配，new做的事情）
                2。执行构造方法
                3。执行属性注入（set操作）
                4。执行bean初始化方法
        Bean销毁时机：
            容器关闭前触发bean的销毁        
            关闭容器的方式：        
                手工关闭容器：（不推荐，属于暴力关闭）    
                    ConfigurableApplicationContext 接口 close() 操作
                注册关闭钩子，在虚拟机退出前先关闭容器再退出虚拟机
                    ConfigurableApplicationContext 接口 registerShutdownHook() 操作
                
                代码片段:
                    public class AppForLifeCycle{
                        public static void main(String[] args) {
                            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
                            ctx.registerShutdownHook();
                        }
                    }
    DI依赖注入            
        Setter注入：
            简单类型：(细节配置中的value，（value="10"）spring会自动识别类型的)
                在bean中定义简单类型属性并提供可访问的set方法
                    private int connectionNumber;
                    public void setConnectionNumber(int connectNumber) {
                        this.connectionNumber = connectNumber;
                    }
                配置中使用property标签和value属性注入简单类型数据
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl">
                        <property name="connectionNumber" value="10"></property>
                    </bean>
            引用类型：
                在bean中提供定义引用类型属性并提供可访问的set方法；
                    private BookDao bookDao;
                    public void setBookDao(BookDao bookDao) {
                        this.bookDao = bookDao;
                    }
                配置中使用property标签和ref属性注入引用类型对象
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl">
                        <property name="bookDao" ref="bookDao"></property>
                    </bean>
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl"></bean>
        构造器（构造方法）注入：
            简单类型：
            引用类型：
        




