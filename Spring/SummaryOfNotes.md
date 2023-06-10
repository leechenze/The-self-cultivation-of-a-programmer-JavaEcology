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
                在bean中定义简单类型属性并提供可访问的setterÏ方法
                    private int connectionNumber;
                    public void setConnectionNumber(int connectNumber) {
                        this.connectionNumber = connectNumber;
                    }
                配置中使用property标签和value属性注入简单类型数据
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl">
                        <property name="connectionNumber" value="10"></property>
                    </bean>
            引用类型：
                在bean中提供定义引用类型属性并提供可访问的setter方法；
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
                在bean中定义简单类型属性并提供可访问的setter方法；
                    private int connectionNumber;
                    public void setConnetionNumber(int connectionNumber) {
                        this.connectionNumber = connectionNumber;
                    }
                配置中使用 constructor-arg 标签和value属性注入简单类型对象
                    <bean id="bookService" class="com.lee.dao.impl.BookDaoImpl">
                        <constructor-arg name="connectionNumber" value="10"></constructor-arg>
                        OR（通过参数类型指定，以解决形参名称高耦合的问题）
                        <constructor-arg type="int" value="10"></constructor-arg>
                        OR（通过参数索引指定，以解决多个同类型参数的问题）
                        <constructor-arg index="0" value="10"></constructor-arg>
                    </bean>
            引用类型：
                在bean中定义引用类型属性并提供可访问的构造方法；
                    private BookDao bookDao;
                    public void BookServiceImpl(BookDao bookDao) {
                        this.bookDao = bookDao;
                    }
                配置中使用 constructor-arg 标签和ref属性注入引用类型对象
                    <bean id="bookService" class="com.lee.service.impl.BookServiceImpl">
                        <constructor-arg name="bookDao" ref="bookDao"></constructor-arg>
                    </bean>
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl"></bean>

        依赖注入方式选择：
            自己开发的模块使用setter注入，别使用构造器，别给自己找麻烦；
        依赖自动装配：
            IoC容器根据bean所依赖的资源在容器中查找并注入到bean中的过程为自动装配
            自动装配方式
                按类型（byType）（常用）
                按名称（byName）
                按构造方法（constructor）（不常用）
                默认（default）
                不自动装配（no）
            配置：(自动装配省去 property和constructor的标签的定义)
                之前：
                    <bean id="bookService" class="com.lee.service.impl.BookServiceImpl">
                        <constructor-arg name="bookDao" ref="bookDao"></constructor-arg>
                    </bean>
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl"></bean>
                现在：
                    <bean id="bookService" class="com.lee.service.impl.BookServiceImpl" autoWrite="byType"></bean>
                    <bean id="bookDao" class="com.lee.dao.impl.BookDaoImpl"></bean>
            依赖自动装配的特征：
                自动装配用于引用类型的依赖注入，不能对简单类型进行操作
                使用按类型装配时（byType）必须保障容器中相同类型的bean唯一，推荐使用
                使用按名称装配时（byName）必须保障容器中具有指定名称的bean（及setter方法名去掉set前缀后的首字母小写），因变量名与配置耦合，不推荐使用
                自动装配优先级低于setter注入与构造器注入，同时出现时自动装配配置将失效
                
        集合注入：
            Array，List，Set，Map，Properties

            BookDaoImpl：
                public class BookDaoImpl implements BookDao{
                    private int[] array;
                    private List<String> list;
                    private Set<String> set;
                    private Map<String, String> map;
                    private Properties properties;
    
                    public void setArray (int[] array) { this.array = array; }
                    public void setList (List<String> list) { this.list = list; }
                    public void setSet (Set<String> set) { this.set = set; }
                    public void setMap (Map<String,String> map) { this.map = map; }
                    public void setProperties (Properties properties) { this.properties = properties; }
                    
                    public void save() {
                        System.out.println("Array" + Array.toString(array));
                        System.out.println("List" + list);
                        System.out.println("Set" + set);
                        System.out.println("Map" + map);
                        System.out.println("Properties" + properties);
                    }
                }

            配置：
                <bean name="bookDao" class="com.lee.dao.impl.BookDaoImpl">
                    注入Array对象：
                        <property name="array">
                            <array>
                                <value>100</value>
                                <value>200</value>
                                <value>300</value>
                            </array>
                        </property>
                    注入List对象：
                        <property name="list">
                            <list>
                                <value>100</value>
                                <value>200</value>
                                <value>300</value>
                            </list>
                        </property>
                    注入Set对象：
                        <property name="set">
                            <set>
                                <value>trump</value>
                                <value>obama</value>
                                <value>lincoln</value>
                            </set>
                        </property>
                    注入Map对象：
                        <property name="map">
                            <map>
                                <entry key="country" value="china" />
                                <entry key="province" value="shanxi" />
                                <entry key="city" value="xian" />
                            </map>
                        </property>
                    注入Properties对象：
                        <property name="map">
                            <props>
                                <prop key="country">china</prop>
                                <prop key="province">shanxi</prop>
                                <prop key="city">xian</prop>
                            </props>
                        </property>
                </bean>
             
        数据源对象管理：（datasource）
            查找maven坐标：mvnrepository.com
            第三方资源配置管理：这里以druid 和 一个比较陌生的c3p0为例。
            详见 datasource模块，这个章节 管理第三方bean 就是为了使你拿到第三方模块应该会去思考模块，和查阅该Bean的使用资料

        加载properties文件：
            1。开启context的命名空间
                原来配置：
                    <beans xmlns="http://www.springframework.org/schema/beans"
                           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="
                                http://www.springframework.org/schema/beans
                                http://www.springframework.org/schema/beans/spring-beans.xsd
                    ">...</bean>
                修改后的配置：
                    <beans xmlns="http://www.springframework.org/schema/beans"
                           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xmlns:context="http://www.springframework.org/schema/context"    新增行✅✅✅
                           xsi:schemaLocation="
                                http://www.springframework.org/schema/beans
                                http://www.springframework.org/schema/beans/spring-beans.xsd
                                http://www.springframework.org/schema/context    新增行✅✅✅
                                http://www.springframework.org/schema/context/spring-context.xsd    新增行✅✅✅
                    ">...</bean>
            2。使用context空间加载proerties文件
                location会读取resources目录下的文件和java源码下的文件
                不加载系统环境变量的属性
                    <context:property-placeholder location="jdbc.properties" system-properties-mode="NEVER" />
                加载多个properties文件
                    <context:property-placeholder location="jdbc.properties,msg.properties" />
                加载所有properties文件
                    <context:property-placeholder location="*.properties" />
                加载properties文件标准格式
                    <context:property-placeholder location="classpath:*.properties" />
                从类路径和jar包中搜索并加载properties文件
                    <context:property-placeholder location="classpath*:*.properties" />
                
            3。使用属性占位符${}读取properties文件中的属性
                <bean id="userDao" class="com.lee.dao.impl.UserDaoImpl">
                    <property name="name" value="${jdbc.driver}"/>
                </bean>
                
    IoC容器补充（对之前内容的补充）
        创建容器
            方式一：类路径加载配置文件
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            方式二：文件路径加载文件
                ApplicationContext applicationContext = new FileSystemXmlApplicationContext("绝对路径/applicationContext.xml");
            加载多个配置文件
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml","bean.xml");
        获取bean
            方式一：
                UserDao userDao = (UserDao) applicationContext.getBean("userDao");
            方式二：
                UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
            方式三：（直接使用bean类型获取,问题：UserDao类型的bean只能有一个，多个将会报错）
                UserDao userDao = applicationContext.getBean(UserDao.class);
                
        容器类的层次结构:
            BeanFactory 是所有容器类的顶层接口，ApplicationContext 所集成的父类就是继承自它，当然中间有很多层继承关系
            早期spring是用 BeanFactory 来加载容器的，初始化BeanFactory对象时，加载的bean是有延迟的
            现在ApplicationContext 接口是Spring容器的核心接口，初始化时bean是立即加载的，可以通过配置进行更改延迟加载（lazy-init="true"）
            
    注解开发定义Bean（annotation_bean）
        spring2.0开始逐步提供了各种个样的注解，到2.5时注解就比较完善了，到3.0时可以完全纯注解开发
        配置：
            使用@Component定义bean
                @Component
                public class BookServiceImpl implements BookService {}
                @Component("bookDao")
                public class BookDaoImpl implements BookDao {}
            在核心配置文件通过组件扫描加载bean
                <context:component-scan base-package="com.lee"/>
        Spring提供了三个@Component注解的衍生注解：
            @Controller：用于表现层bean定义
            @Service：用于业务层bean定义
            @Repository：用于数据层bean定义
            以上三个和Component使用完全一样，区别只是在不同层面是 更加具有语义化帮助理解

    纯注解开发零配置
        在Spring3.0开启的纯注解零配置的开发模式，使用Java类代替Spring核心配置文件
            @Configuration注解用于设定当前类为配置类
            @ComponentScan注解用于设定扫描路径，此注解只能添加一次，多个数据格式请用数组格式
                @ComponentScan({"com.lee.dao","com.lee.service"})
                @ComponentScan("com.lee")
        读取Java配置类初始化容器对象
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        @Scope定义Bean作用范围
            @Scope("singleton")
            public class BookDaoImpl implements BookDao{}
        @PostConstruct, @PreDestroy定义bean生命周期
            @Scope("singleton")
            public class BookDaoImpl implements BookDao{
                public BookDaoImpl() { System.out.println("book dao constructor") }
                
                @PostConstruct
                public void init() { System.out.println("book init ...") }
                
                @PreDestroy
                public void destroy() { System.out.println("book destroy ...") }
            }
        注解开发依赖注入：（annotation_di)
            @AutoWrited注解开启自动装配（默认按类型）
                @Service
                public class BookServiceImpl implements BookService {
                    @Autowired
                    private BookDao bookDao;
                    
                    public void save() {
                        System.out.println("book service save");
                        bookDao.save();
                    }
                }
                注意：自动装配基于反射设计创建对象并暴力反射对应属性为私有属性初始化数据，因此无需提供setter方法
            @Qualifier注解开启指定名称装配bean
                即如果有多个相同类型的实现类，那么就需要使用@Qualifier指定名称，以告诉程序该加载哪个（如：BookDaoImpl和BookDaoImpl1）
                @Service
                public class BookServiceImpl implements BookService {
                    @Autowired
                    // @Qualifier("bookDao1")
                    private BookDao bookDao;
                
                    public void save() {
                        System.out.println("book service save");
                        bookDao.save();
                    }
                }
                注意：@Qualifier无法单独使用，必须配合@AutoWrited注解一块使用
            @Value用于对简单类型进行注入
            @PropertySource注解加载properties文件
                @PropertySource("jdbc.properties")
                @PropertySource({"jdbc.properties", "jdbc1.properties"})
                @PropertySource("classpath:jdbc.properties")
                注意：路径仅支持单一文件配置，多文件使用数组的格式，不允许使用通配符*
            
        注解开发管理第三方
            @Bean表示声明当前方法的返回值是一个Bean
            @Import手动加入配置类到核心配置，此注解只能添加一次，多个数据用数组格式{}进行添加
                主要是在核心的SpringConfig配置类中导入其他一些独立的配置类所用的
            在第三方Bean中注入简单类型：
                @Value("${jdbc.driver}")
                private String driver;
                @Value("${jdbc.url}")
                private String url;
                @Value("${jdbc.username}")
                private String username;
                @Value("${jdbc.password}")
                private String password;
            
                @Bean
                public DataSource dataSource(BookDao bookDao) {
                    DruidDataSource dds = new DruidDataSource();
                    dds.setDriverClassName(driver);
                    dds.setUrl(url);
                    dds.setUsername(username);
                    dds.setPassword(password);
                    return dds;
                }
            在第三方Bean中注入引用类型：
                @Bean
                public DataSource dataSource(BookDao bookDao) {
                    // 假如DataSource这个第三方Bean需要依赖 BookDao
                    System.out.println(bookDao);
                    
                    DruidDataSource dds = new DruidDataSource();
                    dds.setDriverClassName(driver);
                    dds.setUrl(url);
                    dds.setUsername(username);
                    dds.setPassword(password);
                    return dds;
                }
    注解开发总结：
        XML配置与注解配置比较：
            执行在网上搜索差异进行比较，此处不在罗列

    
    Spring整合mybatis（spring_mybatis）
        原SqlMapConfig.xml：
            <configuration>
                <properties resource="jdbc.properties"></properties>
                <typeAliases>
                    <package name="com.lee.domain"/>
                </typeAliases>
                <environments default="mysql">
                    <environment id="mysql">
                        <transactionManager type="JDBC"></transactionManager>
                        <dataSource type="POOLED">
                            <property name="driver" value="${jdbc.driver}"></property>
                            <property name="url" value="${jdbc.url}"></property>
                            <property name="username" value="${jdbc.username}"></property>
                            <property name="password" value="${jdbc.password}"></property>
                        </dataSource>
                    </environment>
                </environments>
                <mappers>
                    <package name="com.lee.dao"></package>
                </mappers>
            </configuration>
            <typeAliases>
                <package name="com.lee.domain"/>
            </typeAliases>
        整合后：
            MyBatisConfig.java
                @Bean
                public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
                    SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
                    // 原typeAliases标签配置
                    ssfb.setTypeAliasesPackage("com.lee.domain");
                    // 原environments标签配置：
                    ssfb.setDataSource(dataSource);
                    return ssfb;
                }
                // 原mappers标签的相关配置（数据源扫描相关）
                @Bean
                public MapperScannerConfigurer mapperScannerConfigurer() {
                    MapperScannerConfigurer msc = new MapperScannerConfigurer();
                    msc.setBasePackage("com.lee.dao");
                    return msc;
                }
        mybatis整合配置总结
            SqlSessionFactoryBean
            MapperScannerConfigurer
        
    Srping整合Junit
        @Runwith(SpringJUnit4ClassRunner.class)
            设定类运行器（类加载器）
        @ContextConfiguration(classes = SpringConfig.class)
            设定Spring的配置类
        @Autowired
            然后要测哪个模块，就把哪个模块自动装配（Autowired）一下就行了
        


    AOP
        简介：Aspect Oriented Programming 面向切面编程，一种编程范式，知道开发者如何组织程序结构
        作用：在不惊动原始设计的基础上为其进行功能增强，这是Spring倡导的一个理念：无侵入式编程/无入侵式编程
        AOP核心概念：
            连接点（JoinPoint）：程序执行过程中的任意位置，粒度为执行方法，抛出异常，设置变量等
                在SpringAOP中，理解为方法的执行
            切入点（PointCut）：匹配连接点的式子
                在SpringAOP中，一个切入点可以描述一个具体方法，也可以匹配多个方法
            通知（Advice）：在切入点处执行的操作，也就是共性功能（通知类）
                在SpringAOP中，功能最终以方法的形式呈现
            切面（Aspect）：描述通知与切入点的对应关系
        
        AOP入门案例：(AOP)
            AOP坐标导入：
                aop的坐标默认是被依赖的
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjweaver</artifactId>
                    <version>1.9.4</version>
                </dependency>
            制作连接点方法：
                BookDaoImple.java
            定义通知类：
                com.lee.aop.MyAdvice
            制作共性功能方法：
                com.lee.aop.MyAdvice
                    public void method() {
                        System.out.println(System.currentTimeMillis());
                    }
            定义切入点：
                com.lee.aop.MyAdvice
                    依托一个不具有实际意义的方法进行, 即无参数, 无返回值, 方法体无实际逻辑
                    @Pointcut("execution(void com.lee.dao.BookDao.update())")
                    private void pt() {}
            绑定切入点与通知关系（切面）：
                com.lee.aop.MyAdvice
                    @Component // 这里指定为Spring的bean，受到Spring容器的管理
                    @Aspect // 指定这是个AOP类，才会读取@Pointcut 和 @Before两个注解
                    public class MyAdvice {
                        // 切入点
                        @Pointcut("execution(void com.lee.dao.BookDao.update())")
                        private void pt() {}
    
                        // 共性功能方法
                        @Before("pt()")
                        public void method() {
                            System.out.println(System.currentTimeMillis());
                        }
                    }
            开启Spring对AOP注解式驱动支持
                ...
                @EnableAspectJAutoProxy
                public class SpringConfig {}

        AOP工作流程：
            1。Spring容器启动
            2。读取所有切面配置了的切入点
            3。初始化Bean，判定bean对应的类中的方法是否匹配到已配置了的切入点
            4。匹配成功后，创建原始对象的代理对象
            5。获取的bean是代理对象时，根据代理对象的运行模式运行原始方法与增强内容，完成操作。
            结论：SpringAOP本质就是代理模式（目标对象，代理对象）
        AOP切入点表达式：
            语法格式：
                切入点表达式描述的标准格式：
                    execution(public User com.lee.service.UserService.findByInt(int))

                    1。访问修饰符（public，private等，可以省略）
                    2。返回值（User）
                    3。包（com.lee.service）
                    4。类或者接口（UserService）
                    5。方法名（findById）
                    6。参数（int）
                    7。异常名（方法定义中抛出指定异常，可以省略）
            通配符：
                可以使用通配符描述切入点，快速描述
                    *：单个独立的任意符号，可以独立出现，也可以作为前缀或后缀的匹配符出现
                        execution(public * com.lee.*.UserService.find*(*))
                    ..：多个连续的任意符号，可以独立出现，常用语简化包名与参数的书写
                        execution(public User com..UserService.findById(..))
                    +：专用于匹配子类类型（写在类或接口的后面）
                        execution(* *..*Service+.*(..))
                通配所有的：（这是最宽容的写法，但是开发中不会有这么写的）
                    (* *..*(..)) 
            注意事项：
                描述切入点通常描述接口，而不描述实现类，因为描述道实现类就不符合（Spring提倡的降低耦合的规范）
                包名书写尽量不要使用..效率太低，并且容易被同事打死
                通常不使用异常作为匹配规则
                .. 表示可以没有或者有多个，*表示至少有一个或多个

        AOP通知类型：
            AOP通知共分为5中类型
                前置通知
                    // @Before("pt()")
                    public void before() {
                        System.out.println("before advice ...");
                    }
                后置通知
                    // @After("pt()")
                    public void after() {
                        System.out.println("after advice ...");
                    }
                环绕通知（重点）
                    // @Around("pt()")
                    public Object around(ProceedingJoinPoint pjp) throws Throwable {
                        System.out.println("around before advice ...");
                        Object res = pjp.proceed();
                        System.out.println("around after advice ...");
                        return res;
                    }
                    注意事项：
                        ProceedingJoinPoint是一个必须依赖的形参，表示对原始方法的调用
                        对原始方法的调用可以不接收返回值，通知方法设置成void即可，如果接收返回值，必须设定为Object类型
                        因为原始方法的调用无法预期是否有异常，所以要先强制抛出异常
                返回后通知（了解）
                    // @AfterReturning("pt()")
                    public void afterReturning() {
                        System.out.println("afterReturning advice ...");
                    }
                    细节：和After的区别在于 AfterReturning 只有在没有异常并顺利走完的情况下才会执行
                抛出异常后通知（了解）
                    @AfterThrowing("pt()")
                    public void afterThrowing() {
                        System.out.println("afterThrowing advice ...");
                    }
                    只有在有异常的情况下才会执行
        业务层接口执行效率：(AOP_runspeed)
            测试业务层接口万次执行效率
                获取signature对象
                Signature signature = pjp.getSignature();
                获取类名
                String className = signature.getDeclaringTypeName();
                获取方法名
                String methodName = signature.getName();
                拼接打印
                System.out.println("业务层接口执行万次效率: " + className + "." + methodName + " ==> " + (end - start) + "ms");
        AOP通知获取数据(AOP_advice_data)
            获取参数
                Around：
                    ProceedJoinPoint pjp
                    Object[] args = pjp.getArgs();
                After：
                Before：
                AfterReturning：
                AfterThrowing：
                    JoinPoint jp
                    Object[] args = jp.getArgs();
            获取返回值
                Around：
                    ProceedJoinPoint pjp
                    Object res = pjp.proceed(args);
                After：
                Before：
                AfterReturning：
                    @AfterReturning(value = "pc()", returning = "ret")
                    public void afterReturning(Object ret) {
                        System.out.println("afterReturning advice ..." + ret);
                    }
                AfterThrowing：
            获取异常（了解）
                Around：
                    try {
                        res = pjp.proceed(args);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }
                After：
                Before：
                AfterReturning：
                AfterThrowing：
                    @AfterThrowing(value = "pc()",throwing = "t")
                    public void afterThrowing(Throwable t) {
                        System.out.println("afterThrowing advice ..." + t);
                    }
    
    事务
        事务作用：在数据层保障一系列的数据库操作同成功同失败
        Spring事务作用：在数据层或业务层保障一系列的数据库操作同成功同失败
        案例：账户转账（case_transfer）
            1。在业务层接口上开启Spring事务管理（AccountService.java）
                @Transactional
                public void transfer(String out, String in, Double money);
            2。设置平台事务管理器（JdbcConfig.java）
                @Bean
                public PlatformTransactionManager transactionManager(DataSource dataSource){
                    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
                    transactionManager.setDataSource(dataSource);
                    return transactionManager;
                }
            3。开启注解式事务驱动（SpringConfig.java）
                @EnableTransactionManagement
                public class SpringConfig {
                }
        事务角色：
            事务管理员：
                发起事务方：在Spring中通常指代业务层开启事务的方法
                    @Transactional
                    public void transfer(String out, String in, Double money);
            事务协调员：
                加入事务方：在Spring中通常指代数据层方法，也可以是业务层方法，业务层也可以在业务层相互调用
                    public interface AccountDao {
                        @Update("update transfer_account set money = money + #{money} where name = #{name}")
                        void inMoney(@Param("name") String name, @Param("money") Double money);
                        @Update("update transfer_account set money = money - #{money} where name = #{name}")
                        void outMoney(@Param("name") String name, @Param("money") Double money);
                    }
            注意：
                MybatisConfig下的sqlSessionFactory 和 JdbcConfig下的transactionManager 两个方法的参数都是dataSource
                必须这俩参数是同一个，才能完成统一管理
        事务属性：
            事务相关配置：
            案例：转账业务追加日志（事务传播行为）
                能够处理回滚的异常：
                    Error异常，不多见，比如内存溢出
                    运行时异常 
                除了运行时异常之外的其他异常都不会进行回滚，比如IOException这种异常是不会回滚的
                而处理这种异常就需要在注解@Transactional中指定参数：@Transactional(rollbackFor = IOException.class)，即遇到IOException就进行回滚
            事务传播行为：
                核心：@Transactional(propagation = Propagation.REQUIRES_NEW)
                现在要求追加日志的功能要求无论是否执行成功都要执行日志的业务层调用
                但是由于日志业务和账户业务使用的是同一个事务，所以事务的同成功同失败原则就导致了账户操作失败时，日志也同时失败了
                处理方法就是为日志的业务层事务设置事务传播行为：Propagation.REQUIRES_NEW
                    public interface LogService {
                        @Transactional(propagation = Propagation.REQUIRES_NEW)
                        void log(String out, String in, Double money);
                    }
                
                


贰.SpringMVC
    
    SpringMVC概述：
        SpringMVC技术与Servlet技术功能等同，均属于web层开发技术，但是SpringMVC开发中更简便
        章节内容：
            请求与响应
            REST风格
            SSM整合
            拦截器
        章节目标：
            掌握基于SpringMVC获取请求参数与响应json数据操作
            熟练应用基于REST风格的请求路径设置与参数传递
            能够根据实际业务建立前后端开发通信协议并进行实现
            基于SSM整合技术开发任意业务模块功能
        简介：
            SpringMVC是一种基于Java实现MVC模型的轻量级Web框架，用于进行表现层功能开发的表现层框架技术
    入门案例：（springmvc_quickstart）
        四个步骤：
            1。导入SpringMVC坐标与Servlet坐标
                <!--servlet坐标-->
                <dependency>
                  <groupId>javax.servlet</groupId>
                  <artifactId>javax.servlet-api</artifactId>
                  <version>3.1.0</version>
                  <scope>provided</scope>
                </dependency>
                <!--springmvc坐标-->
                <dependency>
                  <groupId>org.springframework</groupId>
                  <artifactId>spring-webmvc</artifactId>
                  <version>5.2.10.RELEASE</version>
                </dependency>
            2。创建SpringMVC控制器类（等同于Servlet功能）
                @Controller
                    因为是Spring技术，所以必须要定义为一个bean（@Component）
                    在表现层中的bean使用的是 @Controller 注解
                @RequestMapping("url")    
                    定义接收请求的名称
                @ResponseBody
                    设置当前控制器方法响应内容为当前响应体反馈给浏览器

            3。初始化SpringMVC环境（同Spring环境），设定SpringMVC加载对应的Bean
                @Configuration
                @ComponentScan("com.lee.controller")
                public class SpringMvcConfig {}

            4。初始化Servlet容器，加载SpringMVC环境，并设置SpringMVC技术处理的请求
                // 定义servlet容器启动的配置类，继承的类是固定的，来进行加载spring的配置
                public class ServletContainerInitConfig extends AbstractDispatcherServletInitializer {
                    // 加载SpringMVC容器配置
                    @Override
                    protected WebApplicationContext createServletApplicationContext() {
                        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
                        ctx.register(SpringMvcConfig.class);
                        return ctx;
                    }
                    
                    // 设置哪些请求归属SpringMVC处理
                    @Override
                    protected String[] getServletMappings() {
                        // String[]{"/"} 表示所有请求归SpringMVC处理
                        return new String[]{"/"};
                    }
                    
                    // 加载Spring容器配置，因为现在只有SpringMVC容器，没有Spring容器，所以直接Return null即可；
                    @Override
                    protected WebApplicationContext createRootApplicationContext() {
                        return null;
                    }
                }
        四个步骤总结：（1+N）
            一次性工作：
                创建工程，设置服务器，加载工程
                导入坐标
                创建web容器启动类，加载SpringMVC配置，并设置SpringMVC请求拦截路径
                SpringMVC核心配置类（设置配置类，扫描controller包，加载Controller控制器bean）
            多次工作
                定义处理请求的控制器类
                定义处理请求的控制器方法，并配置映射路径（@RequestMapping）与返回json数据（@ResponseBody）
        工作流程分析：
            启动服务器初始化过程：
                服务器启动，执行ServletContainerInitConfig类，初始化web容器
                执行createServletApplicationContext方法，创建了WebApplicationContext对象
                加载SpringMvcConfig
                执行@ComponentScan加载对应的Bean
                加载UserController，每个@RequestMapping的名称对应一个具体方法
                执行getServletMapping方法，定义所有请求都通过SpringMVC处理
        加载Bean控制
            Controller加载控制与Bean加载控制
                SpringMVC加载的Bean
                    表现层Bean（Controller）
                Spring加载的Bean
                    业务Bean（Service）
                    功能Bean（DataSource）等
            问题：Spirng不能加载SpringMVC表现层的Bean
            解决：加载Srping控制的bean的时候排除掉SpringMVC控制的Bean
                方式一：Spring加载的Bean设定扫描范围为com.lee,排除掉Controller包内的bean
                    @ComponentScan(value = "com.lee",
                        excludeFilters = @ComponentScan.Filter(
                            type = FilterType.ANNOTATION,
                            classes = Controller.class
                        )
                    )
                    注意这种加载方式，需要将SpringMvcConfig中的 @Configuration 给去掉，否则过滤掉之后，再次读取到SpringMvcConfig
                    中的@Configuration 又给com.lee.controller扫描进来了，所以需要注掉。
                方式二：Spring加载的Bean设定扫描范围为精准范围，例如：service包，dao包等
                    @ComponentScan({"com.lee.service", "com.lee.dao"})
                方式三：不区分Spring与SpringMVC的环境，加载到同一个环境中

                    // 方式一：
                    // 定义servlet容器启动的配置类，继承的类是固定的，来进行加载spring的配置
                    public class ServletContainerInitConfig extends AbstractDispatcherServletInitializer {
                    // 加载SpringMVC容器配置
                    @Override
                    protected WebApplicationContext createServletApplicationContext() {
                        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
                            ctx.register(SpringMvcConfig.class);
                            return ctx;
                        }
                    
                        // 加载Spring容器配置
                        @Override
                        protected WebApplicationContext createRootApplicationContext() {
                            AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
                            ctx.register(SpringConfig.class);
                            return ctx;
                        }
                    
                        // 设置哪些请求归属SpringMVC处理
                        @Override
                        protected String[] getServletMappings() {
                            // String[]{"/"} 表示所有请求归SpringMVC处理
                            return new String[]{"/"};
                        }
                    }
                    
                    // 方式二：简化开发，方式一的封装版
                    public class ServletContainerInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
                    
                        @Override
                        protected Class<?>[] getRootConfigClasses() {
                            return new Class[]{SpringConfig.class};
                        }
                    
                        @Override
                        protected Class<?>[] getServletConfigClasses() {
                            return new Class[]{SpringMvcConfig.class};
                        }
                    
                        @Override
                        protected String[] getServletMappings() {
                            return new String[]{"/"};
                        }
                    }
                    
    Web请求与响应：       
        请求映射路径    
            @RequestMapping    
                这个注解可以加到方法上，也可以加载到类上    
                在类上表示：整个模块的访问前缀    
                在方法上表示：具体名称    
                代码片段：
                    // 定义为Bean
                    @Controller
                    @RequestMapping("/user")
                    public class UserController {
                        // 设置当前操作的访问路径
                        @RequestMapping("/save")
                        // 设置当前操作的返回值类型
                        @ResponseBody
                        public String save() {
                            System.out.println("user save ...");
                            return "{'module':'springmvc'}";
                        }
                    }
        请求参数（BookController）
            代码片段
                public String save(String name, int age) {
                    System.out.println(name);
                    System.out.println(age);
                    return "{'module':'book save'}";
                }
            Post请求中文参数乱码处理：
                @Override
                protected Filter[] getServletFilters() {
                    CharacterEncodingFilter filter = new CharacterEncodingFilter();
                    filter.setEncoding("UTF-8");
                    return new Filter[]{filter};
                }
            参数类型（详见：BookController）
                普通参数
                    @RequestMapping("/save")
                    @ResponseBody
                    public String save(String name, int age) {
                        System.out.println(name);
                        System.out.println(age);
                        return "{'module':'book save'}";
                    }
                POJO类型参数
                    // POJO参数
                    @RequestMapping("/pojo")
                    @ResponseBody
                    public String pojo(Book book) {
                        System.out.println("POJO参数" + book);
                        return "POJO参数";
                    }
                嵌套POJO类型参数
                    同上
                数组类型参数
                    // 数组类型参数
                    @RequestMapping("/arrayParam")
                    @ResponseBody
                    public String arrayParam(String[] likes) {
                        System.out.println("数组类型参数" + Arrays.toString(likes));
                        return "数组类型参数";
                    }
                集合类型参数
                    // 集合类型参数
                    @RequestMapping("/listParam")
                    @ResponseBody
                    public String listParam(@RequestParam List<String> likes) {
                        System.out.println("集合类型参数" + likes);
                        return "集合类型参数";
                    }
                JSON数据传递参数（详见：JackSonController）
                    导入jackson坐标
                        <!-- jackson坐标 -->
                        <dependency>
                          <groupId>com.fasterxml.jackson.core</groupId>
                          <artifactId>jackson-databind</artifactId>
                          <version>2.9.0</version>
                        </dependency>
                    开启JSON对象自动转换的支持
                        SpringMvcConfig中：
                        @EnableWebMvc // 开启JSON数据转换对象的功能
                    注意接收参数时需要注解声明 @RequestBody 注意不是 @RequestParam
                        // 集合参数的JSON格式
                        @RequestMapping("/jsonlist")
                        @ResponseBody
                        public String jsonlist(@RequestBody List<String> likes) {
                            System.out.println("集合参数的JSON格式" + likes);
                            return "集合参数的JSON格式";
                        }
                日期类型传递参数（详见：JackSonController）
                    日期类型的格式参数可以自动转换，如果需要设置格式，通过：@DateTimeFormat 这个注解
                    // 日期参数
                    @RequestMapping("/dateParam")
                    @ResponseBody
                    public String dateParam(@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date date) {
                        System.out.println("日期参数" + date);
                        return "日期参数";
                    }
        响应参数：（ResponseController）
            响应页面/页面跳转
            响应文本
            响应Json数据
            响应Json集合数据
            
            重新解读@ResponseBody：你的控制器方法返回值是什么 ResponseBody就指定返回的响应体是什么类型

    RESTful
        rest风格/简介
            REST（Representational State Transfer）表现形式状态转换，白话：访问网络资源的格式
            传统风格资源描述形式
                http://localhost/users/getById?id=1
                http://localhost/users/saveUser
            REST风格描述形式
                http://localhost/users/1
                http://localhost/users/
            优点：
                书写简化
                隐藏资源的访问行为，无法通过地址得知对资源是何种操作
            按照REST风格访问资源时使用行为动作区分对资源进行了何种操作
                http://localhost/users                  查询全部用户信息（GET）
                http://localhost/users/1                查询指定用户信息（GET）
                http://localhost/users                  添加用户信息（POST）
                http://localhost/users                  修改用户信息（PUT）
                http://localhost/users/1                删除用户信息（DELETE）
            根据REST风格对资源进行访问称为RESTful

        入门案例
            设定http请求动作
                添加：
                    @RequestMapping(value = "/users",method = RequestMethod.POST)
                    @ResponseBody
                    public String save(){
                        System.out.println("user save...");
                        return "{'module':'user save'}";
                    }
                更新：
                    @RequestMapping(value = "/users",method = RequestMethod.PUT)
                    @ResponseBody
                    public String update(@RequestBody User user){
                        System.out.println("user update..."+user);
                        return "{'module':'user update'}";
                    }
            设定请求参数：（路径变量）
                @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
                @ResponseBody
                public String delete(@PathVariable Integer id){
                    System.out.println("user delete..." + id);
                    return "{'module':'user delete'}";
                }
            @RequestBody：接收请求体参数的
            @RequestParam：接收路径参数和表单参数
            @pathVariable：接收路径变量
        快速开发/简化开发  
            @RestController
                等同于 @Controller 与 @ResponseBody 的组合功能
            @GetMapping
            @PostMapping
            @PutMapping
            @DeleteMapping
                用于替代 @RequestMapping 注解
        案例：（springmvc_restful_case）
            注意点：设置对静态资源的访问放行（config.SpringMvcSupport）
                @Configuration
                public class SpringMvcSupport extends WebMvcConfigurationSupport {
                    //设置静态资源访问过滤，当前类需要设置为配置类，并被扫描加载
                    @Override
                    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
                        //当访问/pages/????时候，从/pages目录下查找内容
                        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
                        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
                        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
                        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
                    }
                }
        


    SSM整合
        Spring
            SpringConfig
        Mybatis
            MybatisConfig
            JdbcConfig
            jdbc.properties
        SpringMVC
            ServletConfig
            SpringMvcConfig
        功能模块
            表与实体类
            Dao（接口+自动代理）
            service（接口+实现类）
                业务层接口测试（整合Junit）
            controller
                表现层接口测试（PostMan）

        表现层与前端数据传输协议规定：(springmvc_result)
            表现层数据封装：
                设置统一数据返回结果类
                    public class Result {
                        private Object data;
                        private Integer code;
                        private String message;
                    }
                设置统一数据返回结果编码
                    public class Code {
                        /**
                        * 成功状态码定义
                        * 200 + 操作码(1,2,3,4) + 成功与否(0,1)
                        */
                        public static final Integer SAVE_OK = 20011;
                        public static final Integer DELETE_OK = 20021;
                        public static final Integer UPDATE_OK = 20031;
                        public static final Integer GET_OK = 20041;

                        /**
                         * 失败状态码定义
                         * 200 + 操作码(1,2,3,4) + 成功与否(0,1)
                         */
                        public static final Integer SAVE_ERR = 20010;
                        public static final Integer DELETE_ERR = 20020;
                        public static final Integer UPDATE_ERR = 20030;
                        public static final Integer GET_ERR = 20040;
                    }
                根据实际情况设定合理的result
                    @GetMapping
                    public Result getAll(){
                        List<Book> books = bookService.getAll();
                        Integer code = books != null ? Code.GET_OK : Code.GET_ERR;
                        String message = books != null ? "" : "数据查询失败，请重试";
                        return new Result(code, books, message);
                    }
            异常处理器
                在程序开发者不可避免会遇到异常现象
                出现异常现象的常见位置与原因：
                    框架内部抛异常：因使用不合规导致（如：mybatis配置文件写错）
                    数据层抛异常：因外部服务器故障导致（如：sql语句写错）
                    业务层抛异常：因业务逻辑书写错误导致，最常遇见的异常，通常是代码异常
                    表现层抛异常：因收集数据，校验数据等规则导致，也最常遇见的异常，通常是代码异常
                    工具类抛出异常：因为工具类书写不严谨不够健壮导致，（如：必要释放的连接，长期未能释放）
                各个层级均出现异常，异常处理代码书写在哪一层？
                    1。数据层异常抛到业务层，业务层异常抛到表现层，然后统一在表现层处理所以层级抛出的异常
                    2。异常还需要分类处理，不同的层级抛出的异常类别不同
                    3。异常要使用AOP的思想进行处理
                Spring异常类
                    Spring提供的异常类就涵盖了以上的三点特性
                        /**
                        * 表现层的异常处理类
                        */
                        @RestControllerAdvice
                        public class ProjectExceptionAdvice {
                            @ExceptionHandler(Exception.class)
                            public Result doException(Exception exception) {
                                System.out.println(exception);
                                String message = exception.toString();
                                return new Result(500, null, message);
                            }
                        }
                    @RestControllerAdvice / @ControllerAdvice：
                        声明异常处理器，表示该类是做异常处理的，因为目前是Rest风格开发，所以使用 @RestControllerAdvice
                    @ExceptionHandler(Exception.class)
                        用以声明在方法上，表示此方法是接收异常用的，表示用来处理哪种异常。并且异常类型是Exception.class
                业务层和数据层的异常如何交到声明在表现层的这个异常处理器中呢？
                    业务异常：(BusinessException)
                        规范的用户行为产生的异常
                        不规范的用户行为操纵产生的异常
                        处理方式：发送对应消息给客户端，提醒用户规范操作
                    系统异常：(SystemException)
                        项目运行过程中可预计但无法避免的异常
                        处理方式：
                            发送固定消息传递给用户，安抚用户
                            发送特定消息给运维人员，提醒维护，同时记录日志
                    其他异常：(Exception)
                        开发人员未预期的异常
                        处理方式：
                            发送固定消息传递给用户，安抚用户
                            发送特定消息给发开人员，进行排错，且记录日志
                代码片段：
                    自定义系统级异常：exception.SystemException
                    自定义业务级异常：exception.BusinessException
                    自定义异常编码：controller.Code
                    触发自定义异常：BookServiceImpl
                        @Override
                        public Book getById(Integer id) {
                            // 业务异常模拟
                            if (id < 0) {
                                throw new BusinessException(Code.BUSINESS_ERR, "id异常，请重试");
                            }
                            // 系统异常模拟
                            // 将可能出现的异常进行包装，转换成自定义异常（try catch）
                            try {
                                int i = 1 / 0;
                            } catch (Exception exception) {
                                throw new SystemException(Code.SYSTEM_ERR, "服务器异常，请重试");
                            }
                            return bookDao.getById(id);
                        }
                    拦截并处理异常：controller.ProjectExceptionAdvice
                        /**
                        * 表现层的异常处理类
                        */
                        @RestControllerAdvice
                        public class ProjectExceptionAdvice {
                            /**
                            * 处理系统异常
                            */
                            @ExceptionHandler(SystemException.class)
                            public Result doSystemException(SystemException systemException) {
                                // 记录日志
                                // 发送消息给运维
                                // 发送邮件给开发
                                return new Result(systemException.getCode(), null, systemException.getMessage());
                            }
                        
                            /**
                            * 处理业务异常
                            */
                            @ExceptionHandler(BusinessException.class)
                            public Result doBusinessException(BusinessException businessException) {
                                return new Result(businessException.getCode(), null, businessException.getMessage());
                            }
                        
                            /**
                            * 处理其他异常（其实走到这个异常中，作为开发者是理亏的）
                            * @param exception
                            * @return
                            */
                            @ExceptionHandler(Exception.class)
                            public Result doException(Exception exception) {
                                // 记录日志
                                // 发送消息给运维
                                // 发送邮件给开发
                                System.out.println(exception);
                                String message = exception.toString();
                                return new Result(Code.OTHER_ERR, null, "系统繁忙，请重试！");
                            }
                        } 
        前后端协议联调：(springmvc_result_page)
    拦截器：
        拦截器概念：
            拦截器（interceptor）是一种动态拦截方法调用的机制，在SpringMVC中动态拦截控制器的方法
        作用：
            在指定的方法调用前后执行预先设定的代码
            阻止原始方法的执行
        拦截器与过滤器的区别：
            归属不同：Filter属于servlet的技术，Interceptor属于SpringMVC的技术
            拦截内容不同：Filter对所有访问增强，Interceptor仅针对SpringMVC的访问进行增强
        入门案例：（springmvc_interceptor）
            声明拦截器的bean，并实现HandlerInterceptor接口（注意：扫描加载bean）
                controller.interceptor.ProjectInterceptor
                    @Component
                    public class ProjectInterceptor implements HandlerInterceptor {
                        @Override
                        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                            System.out.println("preHandle......");
                            // 如果返回值为false时，将拦截掉原始操作，同时后续postHandle和afterCompletion操作都将被拦截停止执行
                            return true;
                        }
                    
                        @Override
                        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                            System.out.println("postHandle......");
                        }
                    
                        @Override
                        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                            System.out.println("afterCompletion......");
                        }
                    }
            定义配置类，继承WebMvcConfigurationSupport，实现addInterceptors方法（注意扫描加载配置）
                config.SpringMVCSupport
                    @Configuration
                    public class SpringMvcSupport extends WebMvcConfigurationSupport {
                        @Autowired
                        private ProjectInterceptor projectInterceptor;
                        
                        @Override
                        protected void addInterceptors(InterceptorRegistry registry) {
                            //配置拦截器
                            registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
                        }
                    }
            添加拦截器并设定拦截的访问路径,路径可以通过可变参数设置多个
                registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
            此外，还可以实现WebMvcConfigurer接口可以简化开发，但具有一定的侵入性
            侵入性指的是SpringMvcConfig这个类通过实现Spring的方法（WebMvcConfigurer），导致这个类和Spring强绑定了，大白话就是这个类和Spring的API关联在一起了，不太好
                config.SpringMvcConfig
                public class SpringMvcConfig implements WebMvcConfigurer {
                @Autowired
                private ProjectInterceptor projectInterceptor;
                
                    @Override
                    public void addInterceptors(InterceptorRegistry registry) {
                        registry.addInterceptor(projectInterceptor).addPathPatterns("/books", "/books/*");
                    }
                }
        拦截器参数：
            前置处理：
                @Override
                public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                    System.out.println("preHandle......");
            
                    // request和response两个参数不解释了，handler参数指代的就是原始执行的对象
                    HandlerMethod hm = (HandlerMethod) handler;
                    // 获取原始执行的对象
                    hm.getMethod();
            
                    // 如果返回值为false时，将拦截掉原始操作，同时后续postHandle和afterCompletion操作都将被拦截停止执行
                    return true;
                }
                参数：
                    request：请求对象
                    response：响应对象
                    handler：被调用的处理器对象，本质上是一个方法对象，对反射技术中的Method对象进行了再包装
                返回值：
                    boolean， 如果返回值为false，那被拦截的处理器将不在执行
            后置处理：
                @Override
                public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                    // modelAndView 封装了一些页面间跳转的相关数据 ModelAndView 指的就是MVC中的M和V
                    System.out.println("postHandle......");
                }
                参数：
                    modelAndView：如果处理器执行完成具有返回结果，可以读取到对应数据与页面信息，并进行调整
            完成后处理：
                @Override
                public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                    // Exception 对象可以捕获原始程序执行过程中出现的异常，但是程序中有统一异常处理机制，所以对exception参数也就没有特别大的需求了
                    System.out.println("afterCompletion......");
                }
                参数：
                    exception：如果处理器执行过程中出现异常现象，可以针对异常情况进行单独处理
        拦截器链配置：
            当配置多个拦截器时，形成拦截器链
            拦截器的运行顺序以拦截器添加时的顺序为标准
            prehandler运行顺序是正常的，但是posthandler和afterCompletion运行顺序是正好反着的。（这里标准是穿脱原则）
                顺序如下：
                    1。prehandler1执行
                    2。prehandler2执行
                    3。posthandler2执行
                    4。posthandler1执行
            如果都prehandler1 返回false时，那么prehandler2也将不在执行了
            拦截器链的具体运行顺序可以CSDN，这里不在详述，一般情况下一个拦截器就够用了，所以拦截器链了解即可
            拦截器的底层就是AOP
            








叁.Maven进阶
    
    分模块开发与设计
    依赖管理
    聚合与继承
    属性管理
    多环境配置与应用
    私服
    
    分模块开发的意义：
        将原始模块按照功能拆分成若干个子模块，方便模块间的相互调用，接口共享
        比如：我们可以将com.lee.domain拆成独立的模块，那么domain的模型就可以被公用，
        或者也可以将com.lee.service拆成独立模块，那么别人使用我的业务层接口就可以直接调用
        所以controller, dao, domain, service这些模块都可以进行拆分
    
        入门案例：（maven_01_all, maven_01_pojo, maven_01_dao）
            这里拆分掉了原有模块domain和dao来进行演示
            创建自己的独立模块：
                创建maven_01_pojo和maven_01_dao模块，把all中的pojo（domain）和dao模块删掉
            书写对应的模块代码
                这里是将pojo，和dao模块拆出来了，实际开发中是domain功能单独写，dao功能单独写，
                当然这是横向拆分（功能拆分），还有根据模块拆分（订单和商品模块）的等，然后给定坐标引入相对应模块即可
            通过maven指令安装模块到本地仓库中（maven install指令）
                因为maven是通过本地的maven仓库中读取的，而不是通过idea读取的
        
    依赖管理：
        依赖传递：
            依赖具有传递性：
                传递性表示：项目中可以直接使用间接依赖的资源
                直接依赖：在当前项目中通过依赖配置建立的依赖关系
                间接依赖：被直接依赖的资源所依赖的第三资源称为间接依赖
        依赖冲突：
            声明优先：当同级配置了相同资源的不同版本时，后配置的将覆盖先配置的
            路径优先：当依赖中出现相同资源时，层级越深，优先级越低，层级越浅，优先级越高
            注意：如果相同层级的资源出现冲突时，那么将遵循声明优先的原则
            工具：maven工具栏中show dependencies，可以查看资源依赖关系
        可选依赖与排除依赖：
            可选依赖：是隐藏当前工程所依赖的资源，隐藏的资源将不具有传递性（<optional>false</optional>）
            排除依赖：指隐藏直接依赖资源中的依赖资源。不用指定版本，因为整个一套资源都不依赖了。
                <dependency>
                  <groupId>com.lee</groupId>
                  <artifactId>maven_01_dao</artifactId>
                  <version>1.0-SNAPSHOT</version>
                      <exclusions>
                        <exclusion>
                          <groupId>com.lee</groupId>
                          <artifactId>maven_01_pojo</artifactId>
                        </exclusion>
                      </exclusions>
                </dependency>
            两者区别：
                可选依赖是在子级依赖中操作，使得父级无法依赖子级的依赖。
                排除依赖是在父级依赖中操作，使得父级无法依赖子级的依赖。
        继承与聚合：
            聚合：就是将多个模块组织成一个整体，同时进行项目构建的过程叫做聚合
                聚合工程：通常是一个不具有业务功能的空工程，而且仅有一个pom文件（maven_01_all_polymerization）
                作用：使用聚合功能可以将多个工程编组，通过对聚合工程进行构建，实现对所包含的模块进行同步构建
                    当工程中某个模块发生更新（变更）时，必须保障工程中与已更新模块关联的模块同步更新，此时可以使用聚合工程解决批量模块同步构建的问题
                    
                步骤：
                    创建全新的空模块，设置打包类型为pom：
                        打包方式既不是war也不是jar 而是pom：<packaging>pom</packaging>，代表该模块用来管理其他模块的工程
                        此时 compile 编译 maven_01_all_polymerization 工程时，将同时编译该工程下的管理的所有工程，其他操作同理（安装，测试，打包）。。。
                    设置当前模块所包含或所管理的子模块名称：
                        <!--设置管理的模块名称-->
                        <modules>
                            <module>../maven_01_all</module>
                            <module>../maven_01_dao</module>
                            <module>../maven_01_pojo</module>
                        </modules>
            继承：描述两个工程的关系，子工程中可以继承父工程中的配置信息，常用于依赖关系的继承
                作用：简化配置，减少资源版本冲突
                步骤：
                    创建全新的空模块，设置打包类型为pom：
                        打包方式既不是war也不是jar 而是pom：<packaging>pom</packaging>，代表该模块用来管理其他模块的工程
                        此时 compile 编译 maven_01_all_polymerization 工程时，将同时编译该工程下的管理的所有工程，其他操作同理（安装，测试，打包）。。。
                    在父工程中配置子工程要用的依赖：
                        <dependencies>
                            <dependency>
                                。。。
                            </dependency>
                            。。。
                        </dependencies>
                    在父工程中配置子工程可选的依赖：
                        <dependencyManagement>
                            <dependencies>
                                <dependency>
                                    <groupId>junit</groupId>
                                    <artifactId>junit</artifactId>
                                    <version>4.12</version>
                                    <scope>test</scope>
                                </dependency>
                            </dependencies>
                        </dependencyManagement>
                    在子工程中定义继承自哪个父工程：
                      <parent>
                        <groupId>com.lee</groupId>
                        <artifactId>maven_01_all_polymerization</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <!--填写父工程的pom文件路径，省略也可以用-->
                        <relativePath>../maven_01_all_polymerization/pom.xml</relativePath>
                      </parent>
                    在子工程中可使用父工程的所有依赖，同时可以选择父工程中配置的可选依赖
                        注：父工程中的可选依赖不能配版本号：
                            <!--没有版本就表示继承自父级的依赖管理-->
                            <dependency>
                              <groupId>junit</groupId>
                              <artifactId>junit</artifactId>
                              <scope>test</scope>
                            </dependency>
            聚合和继承的区别：
                聚合是在当前模块配置关系，聚合可以感知到参与聚合的模块有哪些
                继承是在子模块中配置关系，父模块无法感知到哪些子模块继承了自己
                聚合用于快速构建项目
                继承用于快速配置
                相同点：打包方式（packaging）都是 pom，可以将两种关系制作在同一个pom文件中，并且，两者均属于设计型模块，并无实际的模块内容
        属性：
            定义属性：
                <properties>
                    <!--自定义标签 spring.version -->
                    <spring.version>5.2.10.RELEASE</spring.version>
                    <mybatis.version>3.5.6</mybatis.version>
                    <junit.version>4.12</junit.version>
                </properties>
            引用属性：
                <dependency>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-webmvc</artifactId>
                    <version>${spring.version}</version>
                </dependency>
            配置文件加载属性：
                resources资源目录下的资源文件使用maven中配置的属性：
                    定义属性（不解释了，看上一章节）
                        <properties>
                            <!--jdbc.properties 相关配置-->
                            <jdbc.url>jdbc:mysql:///springmvc_ssm</jdbc.url>
                        </properties>
                    配置资源文件中引用属性：（jdbc.properties）
                        jdbc.url=${jdbc.url}
                    在pom.xml插件中开启资源文件目录加载的相关配置
                        <build>
                            <resources>
                                <resource>
                                    <directory>${project.basedir}/src/main/resources</directory>
                                    <filtering>true</filtering>
                                </resource>
                            </resources>
                        </build>
                        这一步作用就是指定路径为${project.basedir}/src/main/resources的目录可以使用pom.xml中配置的属性
                        filtering作用是为了能够在jdbc.properties中解析${jdbc.url}这个符号
                属性读取：
                    ${project.basedir}：表示读取maven中内置的系统属性
                    其他属性：    
                        在终端运行指令：mvn help:system
                        可以看到：
                            自定义属性
                            内置属性
                            Setting属性
                            Java系统属性
                            环境变量属性
                        根据 ${} 括号内写入  mvn help:system 指令读取到的 key（=之前的值）即可读取具体值
            版本管理：        
                工程版本：
                    SNAPSHOT：（快照版本）
                        快照版本会随着开发的进展不断更新（在开发版本）
                    RELEASE：（发布版本）
                        已经上线的版本
        多环境配置和应用：
            多环境开发：（maven_01_all_polymerization）
                maven提供配置多种环境的设定，帮助开发者在使用过程中快速切换环境
                    比如：本地开发环境，生产环境，测试环境，每个环境的数据库是不同的，jdbc.url对应相应的配置
                定义多环境
                    <!--配置多环境-->
                    <profiles>
                        <profile>
                            <!--定义开发环境-->
                            <id>env_dep</id>
                            <properties>
                                <jdbc.url>jdbc:mysql:///springmvc_ssm</jdbc.url>
                            </properties>
                            <!--设定是否为默认启动的环境-->
                            <activation>
                                <activeByDefault>true</activeByDefault>
                            </activation>
                        </profile>
                        <profile>
                            <!--定义生产环境-->
                            <id>env_pro</id>
                            <properties>
                                <jdbc.url>jdbc:mysql://10.10.167.47/springmvc_ssm</jdbc.url>
                            </properties>
                        </profile>
                        <profile>
                            <!--定义测试环境-->
                            <id>env_test</id>
                            <properties>
                                <jdbc.url>jdbc:mysql://10.10.167.21/springmvc_ssm</jdbc.url>
                            </properties>
                        </profile>
                    </profiles>
                使用多环境：
                    操作按钮：
                        maven工具栏中点击 Execute Maven Goal
                    mvn 指令 -P 环境定义ID
                        mvn install -P env_pro
            跳过测试：
                应用场景：
                    1。功能更新中并没有开发完毕
                    2。快速打包
                1.指令：
                    mvn 指令 -D skipTests
                    mvn package -D skipTests
                2.操作按钮：
                    maven工具栏中点击 Toggle Skip Tests Mode
                    此时 maven生命周期中的test就会置灰
                3.细粒度控制跳过测试（maven_01_all_polymerization）
                    <!--跳过测试-->
                    <plugins>
                        <plugin>
                            <artifactId>maven-surefire-plugin</artifactId>
                            <version>2.12.4</version>
                            <configuration>
                                <!--true：跳过测试，等同于点击 Toggle Skip Tests Mode -->
                                <!--<skipTests>true</skipTests>-->

                                <!--false：不跳过测试-->
                                <skipTests>false</skipTests>
                                <!--排除掉不参与测试的内容-->
                                <excludes>
                                    <exclude>**/BookServiceTest.java</exclude>
                                </excludes>
                            </configuration>
                        </plugin>
                    </plugins>
        私服：
            简介：
                私服就是应用于公司内部或开发团队内部的一个"中央仓库"
                私服就是一台服务器，用于解决团队内部资源共享和资源同步的问题
                私服产品：
                    Nexus：
                        Sonatype公司出的一款maven私服产品
                        下载地址：https://help.sonatype.com/repomanager3/product-information/download

                    自行CSDN搜索安装使用：
                        https://blog.csdn.net/weixin_44190513/article/details/122127042?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522167897525516800213032248%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=167897525516800213032248&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-122127042-null-null.142^v74^control_1,201^v4^add_ask,239^v2^insert_chatgpt&utm_term=mac%20%E5%AE%89%E8%A3%85nexus&spm=1018.2226.3001.4187
                    启动：
                        进入到/Users/lee/Library/nexus-3.49.0-02-mac/nexus-3.49.0-02/bin 下：
                            ./nexus start
                            ./nexus stop
                            运行后稍等一下可以进入页面：localhost:8081/
                        页面运行信息：可以在nexus/etc/nexus-default.properties进行配置
                        服务器运行信息：可以在nexus/bin/nexus.vmoptions进行配置

            私服仓库分类：
                宿主仓库：（hosted）
                    功能：保存自主研发+第三方资源
                    关联操作：上传
                代理仓库：（proxy）
                    功能：代理连接中央仓库（一般不允许上传，一般只是下载资源用）
                    关联操作：下载
                仓库组：（group）
                    功能：给仓库编组简化下载操作，相当于一个仓库集合，里面有含有多个仓库（纯下载用）
                    关联操作：下载
            资源上传与下载：
                本地仓库访问私服权限设置：
                    配置位置：（/apache-maven-3.6.3/conf/settings.xml）
                        <!-- 为了使本地仓库能访问私服 -->
                        <!-- 访问私服权限的相关配置 -->
                        <server>
                          <id>lee-release</id>
                          <username>admin</username>
                          <password>lcz19930316</password>
                        </server>
                        <server>
                          <id>lee-snapshot</id>
                          <username>admin</username>
                          <password>lcz19930316</password>
                        </server>
                本地仓库访问私服地址配置：
                    配置位置：（/apache-maven-3.6.3/conf/settings.xml）
                         <!-- 私服访问路径的映射(镜像)配置 -->
                         <mirror>
                          <!-- 仓库组的ID -->
                          <id>maven-public</id>
                          <!-- 表示 具体什么资源来自于 maven-public, 这里就用*,表示所有的东西,这样不管进行什么操作,都能对私服访问了  -->
                          <mirrorOf>*</mirrorOf>
                          <!-- 地址映射的配置 -->
                          <url>http://127.0.0.1:8099/repository/maven-public/</url>
                         </mirror>
                工程上传到私服服务器配置：
                    配置位置：（工程pom文件中）
                        <!--配置当前工程保存到私服的具体位置-->
                        <distributionManagement>
                            <!--发布到正式版的仓库中（release）-->
                            <repository>
                                <id>lee-release</id>
                                <url>http://127.0.0.1:8099/repository/lee-release/</url>
                            </repository>
                            <!--发布到快照版的仓库中（snapshot）-->
                            <snapshotRepository>
                                <id>lee-snapshot</id>
                                <url>http://127.0.0.1:8099/repository/lee-snapshot/</url>
                            </snapshotRepository>
                        </distributionManagement>
                    发布命令：mvn deploy
                        <version>1.0-SNAPSHOT</version>
                            SNAPSHOT关键字的将会发布到 Version policy 为 snapshot的仓库中
                        <version>1.0-RELEASE</version>
                            RELEASE关键字的将会发布到 Version policy 为 release的仓库中
                私服访问中央服务器设置：
                    配置位置：（nexus服务页面设置）
                        Server按钮点击：Repository ==> Repositories ==> type为proxy的仓库 ==> remote storage中进行配置

            





肆.SpringBoot

    简介：
        springboot 是由pivotal团队提供的全新框架，其设计目的是用来简化Srping应用的初始搭建和开发过程
        
        核心文件：
            pom.xml文件
                <parent>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-parent</artifactId>
                    <version>2.7.9</version>
                    <relativePath/> <!-- lookup parent from repository -->
                </parent>
                <groupId>com.lee</groupId>
                <artifactId>springboot_01_quickstart</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <properties>
                    <java.version>1.8</java.version>
                </properties>
                <!-- 初始化Boot项目时，勾选Spring web 的结果 -->
                <dependencies>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-web</artifactId>
                    </dependency>
                </dependencies>
            application类
                @SpringBootApplication
                public class Application {
                    public static void main(String[] args) {
                        SpringApplication.run(Application.class, args);
                    }
                }
    Spring程序和SpringBoot程序对比
        spring
            pom文件中的坐标：手工添加
            web3.0配置类：手工制作
            Spring/SpringMVC配置类：手工制作
            控制器：手工制作
        springBoot
            pom文件中的坐标：勾选添加
            web3.0配置类：无
            Spring/SpringMVC配置类：无
            控制器：手工制作
        Spring官网：https://spring.io/
            可以通过：https://start.spring.io/ 在官网中进行构建项目
    SpringBoot快速启动
        对于SpringBoot项目打包：
            执行Maven构建指令package
        执行启动指令：
            java -jar springboot.jar
            这个jar包能够直接执行就是因为依赖了 pom.xml中的（spring-boot-maven-plugin）这个插件
            spring-boot-maven-plugin：这个插件就是用来打一个可直接运行的jar包的
    起步依赖：
        Spring缺点：
            配置繁琐
            依赖设置繁琐
        SpringBoot优点：
            自动配置
            起步依赖（简化依赖配置）
                starter
                    只要是配置中有starter关键字的，一定是起步依赖的东西
                    比如：
                        <parent>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-starter-parent</artifactId>
                            <version>2.7.9</version>
                        </parent>
                        <dependencies>
                            <dependency>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter-web</artifactId>
                            </dependency>
                    
                            <dependency>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter-test</artifactId>
                                <scope>test</scope>
                            </dependency>
                        </dependencies>
                parent
                    pom.xml中可见，所有SpringBoot项目要继承的项目，定义了若干个坐标版本号。
                    之后在项目开发中，如果要使用Spring5.2.10的版本，那么就要查5.2.10对应的Boot的版本是多少
                    然后用对应的Boot版本，而不是一律都使用最新的springBoot版本
            辅助功能（内置服务器Tomcat...）
                Jetty和tomcat一样，相比Tomcat更加轻量，可扩展性更强，谷歌应用引擎（GAE）已经全面替换为Jetty了
                <dependencies>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-web</artifactId>
                        <!--在web起步依赖环境中，排除tomcat起步依赖-->
                        <exclusions>
                            <exclusion>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter-tomcat</artifactId>
                            </exclusion>
                        </exclusions>
                    </dependency>
                    <!--添加jetty起步依赖（以替换tomcat服务器），版本由springboot的starter控制-->
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-jetty</artifactId>
                    </dependency>
            
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-test</artifactId>
                        <scope>test</scope>
                    </dependency>
                </dependencies>
    基础配置：
        修改服务器端口：
            三种配置文件格式：
                application.properties:
                    server.port=81
                application.yml:
                    server:
                        port: 82
                    # 配置哪些打印哪些日志，默认值为info。可选值：warn，debug。
                    logging:
                        level:
                            root: warn
                application.yaml:
                    server:
                    port: 83
            优先级：properties > yml > yaml
                 以后中配置主要是用yml这种文件
        ymal：一种数据序列化格式
            优点：
                容易阅读
                容易与脚本语言交互
                以数据为核心，重数据轻格式
            yaml文件扩展名：
                .yml（主流）
                .yaml
            yaml语法规则：
                大小写敏感
                属性层级关系使用多行描述，每行结尾使用冒号结束
                使用缩进表示层级关系，同层级左侧对齐，只允许使用空格（不允许使用tab键）
                属性值前面添加空格（属性值与属性名之间使用冒号+空格作为分隔符）
                # 表示注释
            yaml数组数据
                enterprise:
                    name: leechenze
                    age: 16
                    subject:
                        - java
                        - golang
                        - python
            yaml数据读取方式：
                BookController：
                    public class BookController {
                        // 读取方式一
                        @Value("${lesson}")
                        private String lesson;
                        @Value("${server.port}")
                        private String port;
                        @Value("${enterprise.subject[0]}")
                        private String subject0;
                    
                        // 读取方式二
                        @Autowired
                        private Environment environment;
                    
                        // 读取方式三
                        @Autowired
                        private Enterprise enterprise;
                    
                        @GetMapping("/{id}")
                        public String getById(@PathVariable Integer id) {
                            System.out.println("id ==> " + id);
                            System.out.println("===============================");
                            System.out.println("lesson ==> " + lesson);
                            System.out.println("port ==> " + port);
                            System.out.println("subject0 ==> " + subject0);
                            System.out.println("===============================");
                            System.out.println("lesson ==> " + environment.getProperty("lesson"));
                            System.out.println("port ==> " + environment.getProperty("server.port"));
                            System.out.println("subject0 ==> " + environment.getProperty("enterprise.subject[0]"));
                            System.out.println("===============================");
                            System.out.println("enterprise.toString() ==> " + enterprise.toString());
                            return "hello spring boot";
                        }
                    }
                Enterprise：
                    @Component
                    @ConfigurationProperties(prefix = "enterprise")
                    public class Enterprise {
                        private String name;
                        private Integer age;
                        private String[] subject;
                    
                        @Override
                        public String toString() {
                            return "Enterprise{" +
                                    "name='" + name + '\'' +
                                    ", age=" + age +
                                    ", subject=" + Arrays.toString(subject) +
                                    '}';
                        }
                    
                        public String getName() {
                            return name;
                        }
                    
                        public void setName(String name) {
                            this.name = name;
                        }
                    
                        public Integer getAge() {
                            return age;
                        }
                    
                        public void setAge(Integer age) {
                            this.age = age;
                        }
                    
                        public String[] getSubject() {
                            return subject;
                        }
                    
                        public void setSubject(String[] subject) {
                            this.subject = subject;
                        }
                    }
                Enterprise实体类中的警告问题解决：
                    pom.xml:
                        <!--解决yaml文件读取的实体类中的警告-->
                        <dependency>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-configuration-processor</artifactId>
                            <optional>true</optional>
                        </dependency>
            多环境开发配置：
                yaml格式：
                    logging:
                     level:
                      root: info
                    
                    ---
                    
                    # 设置启用环境
                    spring:
                     profiles:
                      active: pro
                    ---
                    
                    # 开发
                    server:
                     port: 81
                    # 不过时的写法
                    spring:
                     config:
                      activate:
                       on-profile: dev
                    ---
                    
                    # 生产
                    server:
                     port: 82
                    # 已过时的写法
                    spring:
                     profiles: pro
                    
                    
                    ---
                    
                    # 测试
                    server:
                     port: 83
                    # 已过时的写法
                    spring:
                     profiles: test
                properties格式：（早期时的方式）
                    主启动配置文件：application.properties：
                        #设置启用的环境
                        spring.profiles.active=pro
                    生产环境分类配置文件：application-dev.properties：
                        server.port=8081
                    生产环境分类配置文件：application-pro.properties：
                        server.port=8080
        多环境命令行启动参数设置：
            启动jar包时，通过命令行配置参数即可指定启动哪个环境，并不需要每次打包之前都在yaml配置中手动指定环境了
            建议在执行package之前，首先执行下clean
            yaml配置中难免会有中文注释，这样在打包时会报错，需要修改配置：
                preferences ==> search(file encoding) ==> project Encoding ==> UTF-8
            
            带参数启动springboot：
                格式：java -jar springboot.jar --spring.profiles.active=pro
                    比如：java -jar springboot_02_multiple_environments-0.0.1-SNAPSHOT.jar --spring.profiles.active=pro
                格式：java -jar springboot.jar --spring.profiles.active=pro --server.port=88
                    比如：java -jar springboot_02_multiple_environments-0.0.1-SNAPSHOT.jar --spring.profiles.active=pro --server.port=88
        多环境开发控制：
            当Maven和SpringBoot同时都配置了多环境时，那么打出来的jar包是受maven控制的，因为是maven执行的打包。
            Maven中设置多环境属性
                <!--Maven多环境配置-->
                <profiles>
                    <profile>
                        <id>dev_env</id>
                        <properties>
                            <profile.active>dev</profile.active>
                        </properties>
                    </profile>
                    <profile>
                        <id>pro_env</id>
                        <properties>
                            <profile.active>pro</profile.active>
                        </properties>
                    </profile>
                    <profile>
                        <id>test_env</id>
                        <properties>
                            <profile.active>test</profile.active>
                        </properties>
                        <activation>
                            <activeByDefault>true</activeByDefault>
                        </activation>
                    </profile>
                </profiles>
            对资源文件开启对默认占位符的解析
                这一步操作是为了能够让 application.yaml 读取并解析到 pom.xml 配置中 profiles的相关配置（在pom.xml中配置一个插件）
                <!--对资源文件进行解析的插件-->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-resources-plugin</artifactId>
                    <configuration>
                        <encoding>UTF-8</encoding>
                        <useDefaultDelimiters>true</useDefaultDelimiters>
                    </configuration>
                </plugin>
            SpringBoot中引用Maven属性（application.yml）
                # 设置启用环境
                spring:
                profiles:
                active: ${profile.active}
                注意：${profile.active} 读取的是 <profile.active>pro</profile.active>
            
        配置文件分类
            SpringBoot中的四级文件配置
                1级：target/springboot.jar/BOOT-INF/classes/config/application.yml
                2级：target/springboot.jar/BOOT-INF/classes/application.yml
                3级：resource/config/application.yml
                4级：resource/application.yml
            作用：
                1级和2级用于系统打包后的配置
                3级和4级用于开发阶段的配置
                所以一般我们把application.yml文件复制一份到jar包的根目录，既可以省去掉 java -jar springboot.jar --xxx.xxx=xxx等一系列属性
                后面跟的这些命令行中的参数都可以从jar包根目录中的application.yml配置文件中进行读取，如果要更高的层级那就在application.yml外层加一层config目录即可
            springboot2.5版本的Bug：
                springboot 2.5版本打包后会有一个报错。大概信息为：
                    no subdirectories found for mandatory directory location "file:./config/*/".
                    解决方案就是在config目录下随便再创建一个任意名称的目录，把application.yml放到该目录下即可
                了解即可，后面版本已经将这个bug修复了。
    SpringBoot整合第三方技术：
        整合JUnit：（springboot_03_junit）
            // @SpringBootTest(classes = Springboot03JunitApplication.class)
            @SpringBootTest
            class Springboot03JunitApplicationTests {
                @Autowired
                private BookService bookService;
                @Test
                public void save() {
                    bookService.save();
                }
            } 
            @SpringBootTest：springboot的测试类注解
                作用：设置Junit加载的SpringBoot启动类
                相关属性：classes 设置SpringBoot启动类
                注意：如果测试类在SpringBoot的包或子包中，可以省略启动类的设置，也就是可以省略掉classes的设定
        基于SpringBoot实现SSM整合：（springboot_04_mybatis）
            在创建boot工程时选择需要的技术集（MyBatis Framework，MySql Drive）
            在application.yml中配置数据源
                spring:
                 datasource:
                  driver-class-name: com.mysql.cj.jdbc.Driver
                  url: jdbc:mysql:///springboot_ssm
                  username: root
                  password: lcz19930316
                  type: com.alibaba.druid.pool.DruidDataSource
                注意：
                    boot版本低于2.4.3时，mysql版本大于8.0时，需要在url连接串中配置时区
                        url: jdbc:mysql:///springboot_ssm?serverTimezone=TUC
            定义数据层接口与映射配置
                @Mapper
                public interface BookDao {
                    @Select("select * from tb_book where id = #{id}")
                    public Book getById(Integer id);
                }
                解释：
                    @Mapper注解之前章节讲过，在这里用来代替原自动代理的相关代码，如下：
                        @Bean
                        public MapperScannerConfigurer mapperScannerConfigurer() {
                            MapperScannerConfigurer msc = new MapperScannerConfigurer();
                            msc.setBasePackage("com.lee.dao");
                            return msc;
                        }
                    以上代码是原Spring整合mybatis时的相关配置，但是现在boot配置是还原了之前@Mapper的注解。以声明为代理类（BookDao）
            测试类中注入Dao接口，测试功能
                @SpringBootTest
                class Springboot04MybatisApplicationTests {
                    @Autowired
                    private BookDao bookDao;
                    @Test
                    public void getById() {
                        Book book = bookDao.getById(3);
                        System.out.println(book);
                    }
                }
    基于SpringBoot的SSM案例（springboot_05_page）
        大概步骤：
            pom.xml
                配置起步依赖，导入必要的资源坐标
                    这里导入的都是一些boot工程初始化时没继承的一些资源，比如druid，boot已有资源在创建项目是勾选配置即可
            application.yml
                设置数据源，端口等
            配置类
                全部删除，boot工程中无需配这些繁琐的配置类了
            dao
                数据层一定要通过 @Mapper 将dao声明为代理类
            测试类
            页面
                boot工程中的页面在resources下的static目录下（原来mvc目录是和resources同级的webapp目录下）

        







伍.MyBatisPlus
    
    简介
    标准数据层开发
    DQL控制
    DML控制
    快速开发
    

    MyBatisPlus概述：
        MP是基于MyBatis框架基础上开发的增强型工具，旨在简化开发，提高效率
        官网：
            https://mp.baomidou.com/        （官方的）
            https://mybatis.plus/           （热心开发者捐赠的域名）
    入门案例：（mybatisplus_01_quickstart）
        创建工程勾选 MySQL Driver 配置（注意这里不要 Mybatis framework了）
        手动添加mp起步依赖
            因为初始化boot工程时没有 关于 mybatisplus的相关依赖，所以要手动导入mybatisplus的依赖坐标
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>3.4.2</version>
            </dependency>
                
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.2.9</version>
            </dependency>
        设置JDBC参数（application.yml）
            server:
            port: 80
            
            # TODO 数据源相关配置
            spring:
            datasource:
            type: com.alibaba.druid.pool.DruidDataSource
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: jdbc:mysql:///mybatisplus_db
            username: root
            password: lcz19930316
        实体类和表
        定义数据接口，继承BaseMapper<User>
            @Mapper
            public interface UserDao extends BaseMapper<User> {
            
            }
        在测试类中注入dao接口，测试功能
            @SpringBootTest
            class Mybatisplus01QuickstartApplicationTests {
                @Autowired
                private UserDao userDao;
                @Test
                void testGetAll() {
                    List<User> userList = userDao.selectList(null);
                    System.out.println(userList);
                }
            }
        
    标准CURD制作：（mybatisplus_01_quickstart）
        详见：Mybatisplus01QuickstartApplicationTests
        关于使用MP增删改查的一些操作
        工具介绍：
            lombok:
                一个Java类库，提供了一组简化POJO实体类开发的注解
            坐标：
                <dependency>
                    <groupId>org.projectlombok</groupId>
                    <artifactId>lombok</artifactId>
                    <scope>provided</scope>
                </dependency>
            详见：User实体类（domain.User）
    分页功能：
        设置分页拦截器作为Spring管理的bean：（config.MyBatisPlusConfig.java）
            @Configuration
            public class MyBatisPlusConfig {
                @Bean
                public MybatisPlusInterceptor mybatisPlusInterceptor() {
                    // 1.创建mybatisplus的拦截器。
                    MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
                    // 2.给示例添加具体的拦截器
                    mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
                    return mybatisPlusInterceptor;
                }
            }
        执行分页查询：（Mybatisplus01QuickstartApplicationTests.getByPage()）
            @Test
            void getByPage(){
                IPage page = new Page(1, 2);
                userDao.selectPage(page, null);
                System.out.println("当前页码值：" + page.getCurrent());
                System.out.println("每页显示的条数：" + page.getSize());
                System.out.println("总共多少页：" + page.getPages());
                System.out.println("总共多少条：" + page.getTotal());
                System.out.println("数据：" + page.getRecords());
            }
        开启日志，这一步不是必须的，做个了解后续会用到：（application.yml）
            # 开启mybatisplus的日志输出（输出到控制台的日志）（调试时打开，开发时就关掉了，信息太多了）
            #mybatis-plus:
            #  configuration:
            #    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
        resources/logback.xml（清空控制台多余信息）
            <?xml version="1.0" encoding="UTF-8" ?>
            <!--此配置用于清空控制台中的多余打印信息-->
            <configuration></configuration>


    条件查询的三种格式：
        DQL编程控制（mybatisplus_02_dpl）
            条件查询方式（Mybatisplus02DqlApplicationTests.java）
                mybatisplus将书写复杂的sql查询条件进行了封装，使用编程的形式完成查询条件的组合
                Wrapper这个接口参数就是用来封装查询条件的
                    // 方式一：按条件查询对应的操作
                    // QueryWrapper queryWrapper = new QueryWrapper();
                    // queryWrapper.lt("age", 9);
                    // List<User> userList = userDao.selectList(queryWrapper);
                    // System.out.println(userList);
            
                    // 方式二：lambda表达式按条件查询
                    // QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
                    // queryWrapper.lambda().lt(User::getAge, 9);
                    // List<User> userList = userDao.selectList(queryWrapper);
                    // System.out.println(userList);
            
                    // 方式三：lambda表达式按条件查询
                    LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
                    // 支持链式调用（年龄小于10 或者 年龄大于6的，也就是全部数据）
                    // 如果没有.or()的调用，那么就是并且的关系
                    lambdaQueryWrapper.lt(User::getAge, 10).or().gt(User::getAge, 6);
                    List<User> userList = userDao.selectList(lambdaQueryWrapper);
                    System.out.println(userList);
                条件查询null值的处理：（Mybatisplus02DqlApplicationTests1.java）
                    Mybatisplus02DqlApplicationTests1.java
                        @Test
                        void getAll() {
                            // 模拟前端传递来的查询参数的
                            UserQuery userQuery = new UserQuery();
                            userQuery.setAge(1);
                            userQuery.setAge2(10);
                            // null判定（如果写成if，else判断很不友好，此时可以使用queryWrapper提供的方法，其中的参数）
                            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
                            lambdaQueryWrapper
                                    .lt(null != userQuery.getAge2(), User::getAge, userQuery.getAge2())
                                    .gt(null != userQuery.getAge(), User::getAge, userQuery.getAge());
                            List<User> userList = userDao.selectList(lambdaQueryWrapper);
                            System.out.println(userList);
                        }
                    UserQuery.java
                        @Data
                        public class UserQuery extends User {
                            // 一般数值型的一般会有上下限，比如价格，还有日期型也会有上下限，字符串类型的是不会有上下限的
                            // 继承自User那么User所有的属性都会有，再声明个具有上限的age2属性
                            private Integer age2;
                        }

            查询投影（Mybatisplus02DqlApplicationTests2.java）
                解释：直白点讲就是设置查询结果具体是什么样子，也就是规定查询哪些字段用的
                    /**
                     * 正常格式
                     */
                    // 查询投影：
                    // QueryWrapper queryWrapper = new QueryWrapper();
                    // queryWrapper.select("name", "gender","password");
                    // List<User> userList = userDao.selectList(queryWrapper);
                    // System.out.println(userList);
                    // 查询分组：（count（*））用来做字段统计，是一个User模型之外的字段，所以定义为了一个Map来接收结果
                    // QueryWrapper queryWrapper = new QueryWrapper();
                    // queryWrapper.select("count(*) as count", "gender").groupBy("gender");
                    // List<Map<String, Object>> resultList = userDao.selectMaps(queryWrapper);
                    // System.out.println(resultList);
                    /**
                     * Lambda格式
                     */
                    // 查询投影：只能select自己User模型内的字段属性，如果要select User模型之外的字段，那就要使用正常格式了
                    // LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
                    // lambdaQueryWrapper.select(User::getName, User::getPassword, User::getGender);
                    // List<User> userList = userDao.selectList(lambdaQueryWrapper);
                    // System.out.println(userList);
                注意：mybatisplus只是做增强，如果感觉不好用，同样可以在UserDao中使用注解的方式进行书写（@Select）
                    因为是继承BaseMapper，所以并不冲突。

            查询条件设定（Mybatisplus02DqlApplicationTests3.java）
                地址：https://baomidou.com/pages/10c804/
                在官网查找关键字（条件构造器）
                
                代码示例详见：Mybatisplus02DqlApplicationTests3.java
                详细使用查询文档即可，按着官网给出的例子搬下来就行了，各位高级CV工程师们。
            
                
            字段映射与表名映射（Mybatisplus02DqlApplicationTests4.java）
                详见：domain.User.java
                @TableName("tb_user")
                    用于处理类名和表名同步
                @TableField(value = "pwd", select = false)
                    用于处理类属性名和表字段名同步
                    value  表示映射的字段名称
                    select 表示某个字段不参与查询
                    exist  表示声明类中的字段是表中不存在的
            
        DML编程控制（mybatisplus_03_dml）
            Insert
            Delete
            Update

            id生成策略控制
                不同的表应用不同的id生成策略：
                    日志：id自增（1，2，3，4，5。。。）某类id信息并不重要的可以自增
                    购物订单：特殊规则（FQ23948AK3843）
                    外卖单：关联地区日期等信息（10 04 20200314 34 91）
                    关系表：可省略id
                    。。。
                
                @TableId(type = IdType.AUTO)
                    ID自增，数据库设计表，勾选Auto Increment，请在Options中设置Auto Increment的值
                @TableId(type = IdType.INPUT)
                    需要在数据库中取消勾选Auto Increment，取消自增策略
                    此时这个ID需要自行指定。
                        User user = new User();
                        user.setId(666L);
                        user.setName("heima");
                        user.setPassword("heima");
                        user.setAge(11);
                        user.setTel("102220000");
                        userDao.insert(user);
                @TableId(type = IdType.ASSIGN_ID)
                    ASSIGN_ID会自动生成ID，（1638172143701307393）生成这种长串ID，同时也是默认值，这种生成策略叫做雪花算法
                    ASSIGN_ID允许不设值，进行自动生成。当然设值后，ID将为我们设的那个值
                    
            ID生成策略总结：
                AUTO（0）：使用数据库ID自增策略控制id生成
                NONE（1）：不设置id生成策略
                INPUT（2）：用户手工输入ID
                ASSIGN_ID（3）：雪花算法生成ID（可兼容数值型和字符串型，是默认策略）
                ASSIGN_UUID（4）：以UUID生成算法作为id生成策略
            
            通过配置进行声明ID生成策略：application.yml
                mybatis-plus:
                 # 开启mybatisplus的日志输出（输出到控制台的日志）（调试时打开，开发时就关掉了，信息太多了）
                 configuration:
                  log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
                 # 关闭mybatisplus在控制台中的banner
                 global-config:
                  banner: false
                   db-config:
                   id-type: assign_id
                设置id-type后等同于在实体类（User.java）中@TableId注解的设定
                    @TableId(type = IdType.ASSIGN_ID)
            通过配置代替 @TableName 注解
                mybatis-plus:
                 # 开启mybatisplus的日志输出（输出到控制台的日志）（调试时打开，开发时就关掉了，信息太多了）
                 configuration:
                  log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
                 # 关闭mybatisplus在控制台中的banner
                 global-config:
                  banner: false
                   db-config:
                   id-type: assign_id
                   table-prefix: tb_
                设置table-prefix后等同于在实体类（User.java）中@TableName注解的设定，其实就是声明加个前缀
                    @TableName("tb_user")
            
            多数据操作：
                List<Long> userIdList = new ArrayList<Long>();
                userIdList.add(1638172143701307393L);
                userIdList.add(1638174811878334466L);
                userIdList.add(666L);
                userDao.deleteBatchIds(userIdList);
            逻辑删除：
                解释：（垃圾数据不进行物理删除，而是使用逻辑删除的字段标注是否删除）
                步骤：
                    数据库表中添加逻辑删除的字段（deleted），类型为int，设默认值为0
                    实体类中添加对应的字段，然后挂上@TableLogic注解表示该字段是逻辑删除
                        @TableLogic(value = "0", delval = "1")
                        private Integer deleted;
                    全局配置逻辑删除字段值application.yml中进行配置
                      logic-delete-field: deleted   逻辑删除的字段名称
                      logic-delete-value: 1         是逻辑删除的值
                      logic-not-delete-value: 0     不是逻辑删除的值
            乐观锁：
                用于处理业务并发带来的问题
                步骤：
                    数据库表中添加逻辑删除的字段（version），类型为int，设默认值为1。
                    在实体类中添加对应字段，然后挂上@Version注解表示标记一个当前的值，
                        每次这条数据操作后，这个字段会自动加一，但并不是表设计时自增的原因，该字段也没有勾选自增属性，
                        而是mybatisplus 自动自增的结果。
                            @Version
                            private Integer version;
                    配置乐观锁拦截器实现锁机制对应的动态SQL语句拼装（config.MyBatisPlusConfig.java）
                        @Configuration
                        public class MyBatisPlusConfig {
                            @Bean
                            public MybatisPlusInterceptor mybatisPlusInterceptor() {
                                // 添加乐观锁的拦截器
                                MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
                                // 添加具体拦截
                                mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
                                return mybatisPlusInterceptor;
                            }
                        }
                    使用乐观锁机制在修改前必须保障对应的数据中有version字段，方可正常进行
                        Mybatisplus03DmlApplicationTests.java
                            // 模拟A，B用户操作，对乐观锁进行验证
                            User userA = userDao.selectById(3L);    // version 3
                            User userB = userDao.selectById(3L);    // version 3
                    
                            // 会成功
                            userA.setName("trump aaa");
                            userDao.updateById(userA);    // version 4
                    
                            // 不会成功
                            userB.setName("trump bbb");
                            // 此时 userB 还是3，但是userA执行后 version 已经变成4了，所以条件已经不成立了
                            // 因为已经配置了乐观锁拦截器的作用，就不会执行了
                            userDao.updateById(userB);    // version 3
                    
            代码生成器：（mybatisplus_04_generator）
                模版：MyBatisPlus提供        
                数据库相关配置：读取数据库信息获取        
                开发者自定义配置：手工配置        
                
                配置步骤：
                    导入坐标：
                        <!--代码生成器-->
                        <dependency>
                            <groupId>com.baomidou</groupId>
                            <artifactId>mybatis-plus-generator</artifactId>
                            <version>3.4.1</version>
                        </dependency>
                
                        <!--velocity模板引擎-->
                        <dependency>
                            <groupId>org.apache.velocity</groupId>
                            <artifactId>velocity-engine-core</artifactId>
                            <version>2.3</version>
                        </dependency>
                    com.lee.Generator.java
                        public class Generator {
                            public static void main(String[] args) {
                                if(true) {
                                    //1.获取代码生成器的对象
                                    AutoGenerator autoGenerator = new AutoGenerator();
                        
                                    //设置数据库相关配置
                                    DataSourceConfig dataSourceConfig = new DataSourceConfig();
                                    dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
                                    dataSourceConfig.setUrl("jdbc:mysql:///mybatisplus_db");
                                    dataSourceConfig.setUsername("root");
                                    dataSourceConfig.setPassword("lcz19930316");
                                    autoGenerator.setDataSource(dataSourceConfig);
                        
                                    //设置全局配置
                                    GlobalConfig globalConfig = new GlobalConfig();
                                    globalConfig.setOutputDir(System.getProperty("user.dir")+"/mybatisplus_04_generator/src/main/java");    //设置代码生成的位置
                                    globalConfig.setOpen(false);    //设置生成完毕后是否打开生成代码所在的目录
                                    globalConfig.setAuthor("leechenze");    //设置作者
                                    globalConfig.setFileOverride(true);     //设置是否覆盖原始生成的文件
                                    globalConfig.setMapperName("%sDao");    //设置数据层接口名，%s为占位符，指代模块名称
                                    globalConfig.setIdType(IdType.ASSIGN_ID);   //设置Id生成策略
                                    autoGenerator.setGlobalConfig(globalConfig);
                        
                                    //设置包名相关配置
                                    PackageConfig packageInfo = new PackageConfig();
                                    packageInfo.setParent("com.aaa");   //设置生成的包名，与代码所在位置不冲突，二者叠加组成完整路径 (默认包是 com.baomidou)
                                    packageInfo.setEntity("domain");    //设置实体类包名，（默认生成的包是 entity）
                                    packageInfo.setMapper("dao");   //设置数据层包名，（默认生成的包是 mapper）
                                    autoGenerator.setPackageInfo(packageInfo);
                        
                                    //策略设置
                                    StrategyConfig strategyConfig = new StrategyConfig();
                                    strategyConfig.setInclude("tb_user", "tb_book");  //设置当前参与生成的表名，参数为可变参数，（由于数据库里没有 tb_book这张表，所以不会生成）
                                    strategyConfig.setTablePrefix("tb_");  //设置数据库表的前缀名称，模块名 = 数据库表名 - 前缀名  例如： User = tbl_user - tbl_
                                    strategyConfig.setRestControllerStyle(true);    //设置是否启用Rest风格
                                    strategyConfig.setVersionFieldName("version");  //设置乐观锁字段名
                                    strategyConfig.setLogicDeleteFieldName("deleted");  //设置逻辑删除字段名
                                    strategyConfig.setEntityLombokModel(true);  //设置是否启用lombok
                                    autoGenerator.setStrategy(strategyConfig);
                                    //2.执行生成操作
                                    autoGenerator.execute();
                                }
                            }
                        }













陆.Spring Security

    SpringSecurity是一个专注于为Java应用程序提供身份认证和授权的框架。
    关联的项目（spring_security）
    
    集成Security
        向pom.xml中引入依赖：
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-security</artifactId>
            </dependency>
        继承WebSecurityConfigurerAdapter类, 添加@EnableWebSecurity, 并重写以下方法:
            @Override
            protected void configure(HttpSecurity http) throws Exception {}
        启动：
            可以看到控制台输出一段信息为：
                Using generated security password: 0f1a303a-a6c2-48b4-b623-605dfec4b6f0
            当没有添加任何账号时，security会自动生成一个账号名为 user，密码就是：0f1a303a-a6c2-48b4-b623-605dfec4b6f0
            访问：http://localhost:8080/login 可以看到一个登陆页，项目没有任何页面，这个登陆页是security提供的。
            
    登录和认证：
        注释说明都在代码中，详见：common/SecurityConfig
    URL访问控制：
        注释说明都在代码中，详见：common/SecurityConfig
        ant表达式规则：
            ？：匹配一个字符
            *：匹配零个或多个字符
            **：匹配零个或多个目录
    角色与授权的关系：
        注释说明都在代码中，详见：common/SecurityConfig
        总结：角色和授权的关系：
            通过查看Roles方法的源码可以看到 roles 实际上底层是对 authorities 的调用
            只不过在名称之前加了一个 ROLE_ 的前缀作为标记，所以roles和authorities是相同的，是通用的。
            在authorities声明的名称加个ROLE_它就是roles。
    连接数据库认证登录：
        需要实现 UserDetailsService 接口，并实现 loadUserByUsername 方法：（service/UserService）
            @Service
            public class UserService implements UserDetailsService {
            
                @Autowired
                private UserInfoMapper userInfoMapper;
            
                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    UserInfo userInfo = this.userInfoMapper.selectByUsername(username);
                    return User.withUsername(userInfo.getUsername()).password(userInfo.getPassword()).roles(userInfo.getRole().split(",")).build();
                }
            }
        在configure方法中设置 UserService：（SecurityConfig）
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
            }
        注意service层的其他配置这里不在叙述了。
    自定义登录页面：
        
        
        
        
        
        
        
        
        