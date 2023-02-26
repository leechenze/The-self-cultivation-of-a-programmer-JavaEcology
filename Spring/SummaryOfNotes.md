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

    
    Spring整合mybatis
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
        






贰.
叁.
肆.
伍.
陆.
柒.
捌.
玖.
拾.

