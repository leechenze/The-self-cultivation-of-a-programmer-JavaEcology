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
            
        整合配置（springmvc_ssm）
            
        
        
        
        
        
        
        
        
        
        

叁.
肆.
伍.
陆.
柒.
捌.
玖.
拾.

