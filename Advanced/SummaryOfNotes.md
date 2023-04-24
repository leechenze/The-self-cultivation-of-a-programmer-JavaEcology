博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;



零.认识微服务

    认识微服务
    分布式服务架构案例 
    Eureka注册中心
    Ribbon负载均衡原理
    Nacos注册中心
    
    服务架构演变：
        单体架构：
            将业务的所有功能集中在一个项目中开发，打成一个包部署。
            优势：
                架构简单
                部署成本低
            缺点：
                耦合度高
        分布式架构：
            根据业务功能对系统进行拆分，每个业务模块拆分成一个独立项目开发，称为一个服务
            优点：
                降低服务耦合
                有利于服务升级拓展
            缺点：
                需要考虑的问题比较多：
                    服务拆分粒度如何？
                    服务集群地址如何维护？
                    服务之间如何实现远程调用？
                    服务健康状态如何感知？
        微服务：
            微服务就是一种经过良好架构设计的分布式架构方案，微服务架构特征：
                单一职责：
                    微服务拆分粒度更小，每一个服务都对应唯一的业务能力，做到单一职责，避免重复业务开发
                面向服务：
                    微服务对外包暴露业务接口
                自治，独立：
                    团队独立，技术独立，数据独立（数据库独立），部署独立
                隔离性强：
                    服务调用做好隔离，容错，降级，避免出现级联问题
        
    技术对比：
        微服务这种方案需要技术框架来落地，全球的互联网公司都在积极尝试自己的微服务落地技术，在国内知名的就是SrpingCloud和阿里巴巴的Dubbo
            Dubbo：
                注册中心：           zookeeper, Redis
                服务远程调用：         Dubbo协议
                配置中心：           无
                服务网关：           无
                服务监控和保护：        duboo-admin，功能弱
            SpringCloud：
                注册中心：           Eureka, Consul
                服务远程调用：         Feign（http协议，Restful）风格
                配置中心：           SpringCloudConfig
                服务网关：           SpringCloudGateway, Zuul
                服务监控和保护：        Hystrix
            SpringCloudAlibaba：（相当于 Dubbo 和 SpringCloud 两者的整合）
                注册中心：           Nacos, Eureka
                服务远程调用：         Dubbo, Feign
                配置中心：           SpringCloudConfig, Nacos
                服务网关：           SpringCloudGateway, Zuul
                服务监控和保护：        Sentinel

    SpringCloud（cloud-demo）
        SpringCloud是目前国内使用最广泛的微服务框架
            官网地址：https://spring.io/projects/spring-cloud
        SpringCloud集成了各种微服务功能组件，并基于SpringBoot实现了这些组件的自动装配，从而提供了良好的开箱即用体验
        Cloud和Boot的版本兼容关系如下：
            Release Train               Boot Version
                2020.0.x aka Ilford         2.4.x
                Hoxton SR5+                 2.3.x
                Hoxton SR5                  2.2.x
                Greenwich                   2.1.x
                Finchley                    2.0.x
                Edgware                     1.5.x
                Dalston                     1.5.x
            详细版本请查：https://spring.io/projects/spring-cloud#overview
    
        服务拆分及远程调用：
            拆分：
                不同微服务，不要重复开发相同业务
                微服务数据独立，不要访问其他微服务的数据库
                微服务可以将自己的业务暴露为接口，供其他微服务调用（解决：数据库独立，但是需要其他模块数据库的数据的一种解决方案）
                
                cloud-demo项目结构：（order-service，user-service）
                    两个项目分别使用单独的数据库：cloud_order 和 cloud_user
                    启动成功后访问：
                        http://127.0.0.1:8081/user/1
                        http://localhost:8080/order/101
            远程调用：
                需求：根据订单ID查询订单的同时，把订单所属的用户信息一起返回
                而且用户信息是单独的用户表，订单是单独的订单表，订单不能访问用户的数据库，所以就需要订单模块对用户模块的调用，就是远程调用
                远程调用方式分析：（即在java中发去http请求，去请求别的模块）
                    注册RestTemplate：（Srping提供的用以发http请求的工具）
                        说明：在order模块的启动类中注册RestTemplate，启动类也是配置类，所以就暂写启动类中了
                        代码片段：
                            @MapperScan("cn.lee.order.mapper")
                            @SpringBootApplication
                            public class OrderApplication {
                                public static void main(String[] args) {
                                    SpringApplication.run(OrderApplication.class, args);
                                }
                                @Bean
                                public RestTemplate restTemplate() {
                                    return new RestTemplate();
                                }
                            }
                    服务远程调用RestTemplate
                        说明：修改过orderService中的queryOrderById方法
                        代码片段：
                            @Service
                            public class OrderService {
                                @Autowired
                                private OrderMapper orderMapper;
                                @Autowired
                                private RestTemplate restTemplate;
                                public Order queryOrderById(Long orderId) {
                                    // 1.查询订单
                                    Order order = orderMapper.findById(orderId);
                                    // 2.使用RestTemplate发起Http请求，查询用户
                                    String url = "http://localhost:8081/user/" + order.getUserId();
                                    // 2.1 发送http请求，实现远程调用
                                    User user = restTemplate.getForObject(url, User.class);
                                    // 3.封装User到Order
                                    order.setUser(user);
                                    // 4.返回
                                    return order;
                                }
                            }
                提供者与消费者概念：
                    概念：
                        服务提供者：一次业务中，被其他微服务调用的服务。（提供接口给其他服务，就是案例中User模块）
                        服务消费者：一次业务中，调用其他微服务的服务。（调用其他微服务提供的接口，就是案例中Order模块）
                    问题：
                        服务A调用了服务B，服务B调用了服务C，那么服务B的角色是？
                            即使提供者又是消费者，所以抛开业务谈这个服务是服务还是消费那就是刷流氓，因为是相对哪个服务而言，才能决定该服务是什么角色
                            总结：一个服务既可以是提供者又可以是消费者














壹.Eureka注册中心：

    远程调用的问题    
    eureka原理
    搭建EurekaServer
    服务注册
    服务发现
    
    服务调用的问题：
        String url = "http://localhost:8081/user/" + order.getUserId();
        如以上就存在硬编码的问题，因为url地址是有开发，测试，生产等多种环境的，所以url应该是动态的，Eureka就是解决这个问题的
        
    Eureka作用：
        
        1。每个服务提供者在启动时都向eureka注册自己的信息，由注册中心保存这些信息，消费者根据服务名称向eureka注册中心拉取提供者信息
        
        2。提供者会向eureka注册中心每隔30秒发送心跳请求，报告健康状态，注册中心会更新记录服务列表信息，心态不正常的服务会被剔除

        在Eureka架构中，微服务角色有两类：
            eureka-server注册中心（eureka的服务端）
                记录服务信息
                心跳监控（服务健康状态监控）
            eureka-service消费者和提供者（eureka的客户端）
                provider：
                    每次启动时都想注册中心注册自己的信息
                    每隔30秒向注册中心发送心跳请求，证明健康状态
                consumer：
                    根据服务名称从注册中心卡去服务列表
                    基于服务列表做负载均衡，选中一个微服务后发起远程调用

    搭建eureka服务：
        搭建EurekaServer
        将user和order模块注册到eureka
        在order模块中完成服务拉取，然后通过负载均衡挑选一个服务，实现远程调用
        
        搭建EurekaServer服务步骤如下：
            创建eureka-server项目模块，引入eureka服务端的坐标依赖：
                <!--eureka 服务端-->
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
                </dependency>
            编写eureka-server模块的EurekaApplication启动类，添加@EnableEurekaServer注解以自动装配开关
                // 自动装配的开关
                @EnableEurekaServer
                @SpringBootApplication
                public class EurekaApplication {
                    public static void main(String[] args) {
                        SpringApplication.run(EurekaApplication.class, args);
                    }
                }
            填写application.yml文件，编写一下配置：
                server:
                 port: 10086 # 服务端口
                spring:
                 application:
                  name: eurekaServer # eureka的服务名称
                
                eureka:
                 client:
                  service-url: # eureka的地址信息
                    defaultZone: http://localhost:10086/eureka
                  register-with-eureka: false # 关闭控制台错误信息
                  fetch-registry: false # 关闭控制台错误信息
        服务注册：
            注册user-service
                在user-service模块引入eureka客户端的依赖
                    <!--eureka 客户端依赖-->
                    <dependency>
                        <groupId>org.springframework.cloud</groupId>
                        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                    </dependency>
                在application.yml文件中，编写配置：（声明客户端服务名称，声明服务端服务地址）
                    spring:
                     application:
                      name: userService # user的服务名称
                    eureka:
                     client:
                      service-url: # eureka的地址信息
                       defaultZone: http://localhost:10086/eureka
            注册order-service
                步骤同上，这里跳过了。
            另外，将user-service多次启动，模拟多实例部署，但为了避免端口冲突，需要修改端口配置：
                右键UserApplication ==> Copy Configuration ==> VM Options -Dserver.port=8082
                此时Services列表中将出现UserApplication(2)这个服务，启动它。
                那么这时eureka页面的（http://127.0.0.1:10086/）
                    Instances currently registered with Eureka
                    USERSERVICE 的 status 就出现了两个实例
                无论是消费者还是提供者，引入eureka-client依赖，知道eureka地址后，都可以完成服务注册
        服务发现：（服务拉取）
            在order-service完成服务拉取
                服务拉取是基于服务名称获取服务列表，然后在对服务列表做负载均衡
                步骤：
                    修改OrderService代码，修改访问url路径，用服务名代替ip和端口
                        String url = "http://userService/user/" + order.getUserId();
                    在order-service的配置类（启动类）中添加负载均衡注解：
                        @Bean
                        @LoadBalanced
                        public RestTemplate restTemplate() {
                            return new RestTemplate();
                        }
                        
    Ribbon负载均衡：
        负载均衡原理
        负载均衡策略
        懒加载
        
        负载均衡解释：
            请看服务发现章节：在order模块每次对user模块发起请求时，由于user模块的服务有两个，
            每次收到请求时，要么是user执行，要么是user1执行，这样就减少了user的请求负载压力，也就做到了负载均衡

        负载均衡原理：
            实际上 http://userService/user/1 并不是真实的请求地址，那么中间就是由于Ribbon这个组件
            通过userService 关键字找到eureka的注册中心（eureka-server）进行对userService服务的匹配，
            然后匹配到后发现有有两个user服务（user，user1），那么Ribbon就会对两个服务做轮询处理，以做到轮询负载
        
        负载均衡策略：
            通过定义IRule 实现可以修改负载均衡的规则，有两种方式：
                代码方式：在order-service的启动类中，定义一个新的 IRule（属于全局的策略配置）
                    @Bean
                    public IRule randomRule() {
                        return new RoundRobinRule();
                    }
                配置文件方式：在order-service的application.yml文件中，添加新的配置进行负载均衡规则修改（针对某个服务的策略配置）
                    userService: # 服务名称
                     ribbon:
                      NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule # 负载均衡策略配置
            负载均衡常用策略：
                RoundRobinRule：轮询策略（默认）
                RandomRule：随机策略
    
    Ribbon饥饿加载：
        Ribbon默认是采用懒加载，即第一次访问时才会去创建LoadBalanceClient,请求时间很长,而饥饿加载则会在项目启动时创建,降低第一次访问的耗时,通过下面配置开启饥饿加载
            ribbon:
             eager-load:
              enabled: true # 开启饥饿加载
               clients: userService # 指定饥饿加载的服务名称（值是一个集合）















贰.Nacos注册中心：

    认识和安装Nacos
    Nacos快速入门
    Nacos服务分级存储模型
    Nacos环境隔离
    Nacos配置管理
    Feign远程调用
    Gateway服务网关



    Nacos简介：
        nacos是阿里巴巴的产品，现在是springcloud的一个组件，相比Eureka功能更丰富，在国内受欢迎程度较高
        官网：https://nacos.io/en-us/
        
    快速开始：
        详见：https://nacos.io/zh-cn/docs/quick-start.html
        这里安装的版本是 1.4.5 版本，之后cd到在bin目录下（后面又安装了2.2.0版本，因为1.x.x版本不在维护了）
            启动nacos：（参数 standalone 表示单机模式，没有此参数时表示集群模式）
                sh startup.sh -m standalone
            关闭nacos：
                sh shutdown.sh
        启动后访问：
            http://localhost:8888/nacos/#/login
            默认账号密码：nacos/nacos
            默认端口号：8848
            
        如要修改端口号在conf目录中进行配置
            conf/application.properties
            坑点：1.4.5版本修改端口号不生效，所以只能用8848了，2.x.x版本可以修改版本（2.2.0端口好改为了8888）
        配置：
            在cloud-demo父工程中添加spring-cloud-alibaba的管理依赖：
                <!-- springCloud alibaba （nacos的管理依赖） -->
                <dependency>
                    <groupId>com.alibaba.cloud</groupId>
                    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                    <version>2021.0.5.0</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            在user-service中添加nacos客户端依赖：
                <!--nacos 客户端依赖（发现依赖）-->
                <dependency>
                    <groupId>com.alibaba.cloud</groupId>
                    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
                    <version>3.1.6</version>
                </dependency>
            修改user-service&order-service中的application.yml文件, 添加nacos地址:
                spring:
                 cloud:
                  nacos:
                   server-addr: localhost:8888 # nacos 服务地址
            启动并测试
                http://localhost:8888/nacos/ ==> 服务管理 ==> 服务列表
                即可查看到 userService 和 orderService
            
    Nacos分级存储模型:
        一级是服务：例如userService
        二级是集群：例如西安和上海（西安:XN 包含userService:8081和userService:8082, 上海:SH 包含userService:8083）
        三级是实例：userService:8081,userService:8082,userService:8083
        设置实例的集群属性：
            user-service中修改application.yml文件：
                spring:
                 cloud:
                  nacos:
                   discovery:
                    cluster-name: XA
            在启动 userService:8081 和 userService:8082 时 cluster-name 为 XA，就会分配到 XA（西安）集群
            在启动 userService:8083 时 cluster-name 为 SH，就会分配到 SH（上海）集群
            在启动时如果没有 discovery.cluster-name 这项配置时，将会分配到（DEFAULT）默认集群
    
    NacosRule负载均衡：
        优先选择同集群的服务实例列表
        本地集群找不到提供者，才回去其他集群找对应服务，并且会抛出警告（cross-cluster）
        确定了可用实例列表后，再采用随机负载的策略挑选实例
        
        修改order-service 中的 application.yml 配置文件：
            userService:
             ribbon:
              # NIWSServerListClassName: com.netflix.loadbalancer.ConfigurationBasedServerList
              # NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule # 负载均衡策略配置
              NF LoadBalancerRuleClassName: com.alibaba.cloud.nacos.ribbon.NacosRule # nacos 负载均衡策略配置

    NacosRule服务实例权重设置：
        时机开发场景中，我们通常是希望性能好的机器承担更多的用户请求，性能较差的机器承担少一点请求。                    
        这种场景就需要使用权重进行负载策略处理了
        Nacos提供了权重配置来控制访问频率，权重越大，访问的频率就越高。
        权重值配置：
            http://localhost:8888/nacos
            服务管理 ==> 服务列表：详情 ==> 服务详情：编辑 ==> 弹出框中：编辑 ==> 权重属性（值在 1和0 之间，如果为0时将不会接收到请求，相当于备用机）
            如果编辑报错就将 nacos/data 和 nacos/logs 两个目录删除，然后再次重启即可
    
    Nacos环境隔离：
        环境隔离（namespace）：
        nacos首先是一个注册中心，其次它还是一个数据中心。
        在nacos中为了做数据和服务的管理，有一个环境隔离的概念。
        
        namespace > group > service > cluster > instance
        命名空间 > 分组 > 服务 > 集群 > 实例
        
        步骤：
            在 http://localhost:8888/nacos 中选择左侧栏目 命名空间新建一个dev空间
            id会自动生成，必填项是空间名称和空间描述；
                命名空间 ==> 新建命名空间
            在 order-service 的 application.yml 文件中配置 命名空间的ID
                spring:
                 cloud:
                  nacos:
                   discovery:
                    cluster-name: XA
                    namespace: f324b4ac-7a64-426d-8515-7fc9ce35669d # dev环境的命名空间ID
            此时 order服务在dev空间，user服务在public的默认空间，所以order就无法对user服务进行调用。
            报错信息为： No instances available for userService
        总结：
            每个namespace都有一个唯一的ID，用于在application.yml中配置。
            不同的namespace下的服务不可见，也不可访问。
            namespace用于做环境隔离
    
    Nacos的临时实例和非临时实例：
        在order-service的 application.yml 中配置： 
            spring:
             cloud:
              nacos:
               discovery:
                ephemeral: false # true为临时实例（默认值），false为非临时实例
        临时实例和非临时实例的区别：
            临时实例在停止服务时将会被删除，非临时实例只是 健康状态 这一栏中 ture 和 false 的变化，而不会删除服务
                这里遇见错误： failed to req API:/nacos/v1/ns/instance after all servers([localhost:8888]) tried: caused: errCode: 400
                原因是nacos缓存问题，nacos/data 这个目录需要删除再次重启 nacos 即可（注意真正开发时，data数据不能删除，删除后nacos很多配置信息也都没有了）
        Nacos集群默认采用AP方式，当集群中存在非临时实例时，会采用CP模式，Eureka采用AP模式但不支持切换CP
            CP模式主要强调数据的可靠性和一致性，（非临时服务的数据可靠性很重要）
            AP模式主要强调服务的可用性
    
    
    Nacos配置管理：
        http://localhost:8888/nacos
            配置管理 ==> 配置列表 ==> 创建配置 ==> 填写相关配置然后发布
            这里创建了一个 userservice-dev.yaml 的配置
            配置内容：主要是一些核心的将来会有变化的配置，而不是所有配置都写里面。
            dataID：[userService]-[dev].[suffixName]
        统一配置管理：
            在user-service中引入Nacos的 config 依赖
                <!--nacos配置管理依赖-->
                <dependency>
                    <groupId>com.alibaba.cloud</groupId>
                    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-bootstrap</artifactId>
                </dependency>
                
            在user-service中的resource目录添加一个bootstrap.yml文件，这个文件是引导文件，程序读取的优先级要高于 application.yml
                spring:
                 application:
                  name: userService
                 profiles:
                  active: dev # 环境配置
                 cloud:
                  nacos:
                   server-addr: localhost:8888
                   config:
                    file-extension: yaml # 文件后缀名
                    namespace: f218710e-36db-4ec6-be40-28caaf23ec2d # dev环境的命名空间ID
                   discovery:
                    namespace: f218710e-36db-4ec6-be40-28caaf23ec2d # dev环境的命名空间ID
            在UserController中读取nacos中userService.yaml的dateformat的配置
                @Slf4j
                @RestController
                @RequestMapping("/user")
                @RefreshScope
                public class UserController {
                    @Value("${pattern.dateformat}")
                    private String dateformat;
                    /**
                     * 路径： /user/time
                     *
                     * @return 时间
                     */
                    @GetMapping("/time")
                    public String time() {
                        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat)));
                        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
                    }
                }
            然后通过 http://localhost:8081/user/time 成功访问到 一段时间 则表示成功读取到 dateformat的配置了
    
    Nacos热更新
        热更新即：配置自动刷新
        方式一：通过在 public class UserController 类上加上 @RefreshScope注解即可
            @RefreshScope
            public class UserController { ... }
        方式二：使用 @ConfigurationProperties 注解（推荐）
            新建config.PatternProperties.java，源吗如下
                @Data
                @Component
                @ConfigurationProperties(prefix = "pattern")
                    public class PatternProperties {
                    private String dateformat;
                }
            在 UserController 中自动注入 patternProperties：（推荐用法）
                @Autowired
                private PatternProperties patternProperties;
                /**
                 * 路径： /user/time
                 *
                 * @return 时间
                 */
                @GetMapping("/time")
                public String time() {
                    System.out.println("nacos 热更新配置方式二");
                    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat())));
                    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDateformat()));
                }
    
    多环境配置共享：
        微服务启动时会从nacos读取多个配置文件：
            1.[spring.application.name]-[spring.profiles.active].[suffixName] 例如：userService-dev.yaml
            2.[spring.application.name].[suffixName] 例如：userService.yaml
        无论profile.active如何变化，userService.yaml这个文件一定会加载，因此多环境共享可以写入这个文件
        
        步骤：
            在 nacos 的 dev 的命名空间中添加 userService.yaml 的配置
                配置内容：
                    pattern:
                        envSharedValue: 环境共享属性值
            在 PatternProperties 实体类中添加 envSharedValue 属性
            在 UserController 中：
                @Autowired
                private PatternProperties patternProperties;

                /**
                 * 路径： /user/prop
                 *
                 * @return PatternProperties 实体类(就是配置中的所有属性)
                 */
                @GetMapping("prop")
                public PatternProperties prop() {
                    return patternProperties;
                }
            访问 /user/prop 可以读取 envSharedValue 的值，但是如果将profile.active的值改掉为test时，
            那么 userService-dev.yaml 文件中的值就读不到了，只能读到 userService.yaml文件中的值了
        多种配置的优先级：
            userService-dev.yaml  >  userService.yaml  >  user-service/*/resources/application.yml（本地配置）
            总结：nacos 远端的两个配置文件优先级更高，本地配置的优先级最低，
                然后 nacos 远端的配置 当前环境（dev） 又大于 共享环境的配置
                
    Nacos集群搭建：
        Nacos生产环境下一定要部署为集群状态，部署方式参考：CSDN（Nacos生产环境集群部署方案）
        新建nacos数据库：
            执行sql：lib/day2/nacos-all.sql
        nacos配置：
            因为目前没有三台机器，所以这里就使用一个nacos目录配完了后拷贝三分作为三个nacos节点充当三台机器。
            nacos目录：lib/day2/nacos-cluster/nacos1, lib/nacos-cluster/nacos2, lib/nacos-cluster/nacos3
            
            在 nacos/conf/cluster.conf.example 改为 cluster.conf.example 进行配置：（都是本机ip的不同端口号）
                #example
                127.0.0.1:8045
                127.0.0.1:8046
                127.0.0.1:8047
            在 nacos/conf/application.properties 修改数据库配置为以下配置：
                #*************** Config Module Related Configurations ***************#
                ### If use MySQL as datasource:
                spring.datasource.platform=mysql（声明使用的是mysql的数据源）
                
                ### Count of DB:（集群中有几台mysql）
                db.num=1
                
                ### Connect URL of DB:（数据库配置）
                db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
                db.user.0=root
                db.password.0=lcz19930316
                
                ### Connection pool configuration: hikariCP
                db.pool.config.connectionTimeout=30000
                db.pool.config.validationTimeout=10000
                db.pool.config.maximumPoolSize=20
                db.pool.config.minimumIdle=2

            然后如上所述，将配置好的 nacos 目录复制三份，然后分别把每个nacos的端口配置改下：
                nacos1 改为 8045
                nacos2 改为 8046
                nacos3 改为 8047
            配置完成之后再次运行时 就不需要 -m standalone 参数了，直接 sh startup.sh 即可，默认就是集群启动
            
            下载nginx：
                brew install nginx
                brew info nginx（查看nginx信息，安装和配置目录等）
                nginx （启动 nginx）
                nginx -s stop（停止 nginx）
                
            nginx方向代理：下载nginx，在nginx.conf 配置文件中添加如下片段
                upstream nacos-cluster {
                    server 127.0.0.1:8045;
                    server 127.0.0.1:8046;
                    server 127.0.0.1:8047;
                }
                
                server {
                    listen       99;
                    server_name  localhost;
                
                    location /nacos {
                        proxy_pass http://nacos-cluster;
                    }
                }
            然后启动nginx：
                访问：localhost:99
                表面看上去访问的是一个，实际上nginx代理的 99 端口 会在 8045，8046，8047三个nacos之间做负载均衡
            代码改动：
                user-service 模块下的bootstrap.yml地址直接配置 nginx 的 99 即可。
            那么此时配置列表中新建一个配置，在数据库中会config_info 这张表中可以看到新增的配置
            
        nacos 集群启动必须要配置数据库信息，单机启动不需要
        nacos 2.2.0 版本启动报错，改用2.1.0















叁.Http客户端Feign：

    Feign代替RestTemplate        
    自定义配置        
    Feign使用优化        
    最佳实践        

    定义和使用Feign客户端
        在order-service中引入依赖：
            <!--feign客户端依赖包-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
            
        在order-service的启动类中添加注解开启Feign的功能：
            // 开启Feign的功能
            @EnableFeignClients
            public class OrderApplication {...}

        编写客户端，声明远程Feign的远程调用：clients.IUserClient（接口）
            
            // 声明客户端，参数为服务名称
            @FeignClient("userService")
            public interface IUserClient {
                @GetMapping("/user/{id}")
                User findById(@PathVariable("id") Long id);
            }
        用Feign客户端代替 RestTemplate
            /**
             * 使用 Feign 进行请求调用
             */
            @Autowired
            private IUserClient userClient;
            public Order queryOrderById(Long orderId) {
                // 1.查询订单
                Order order = orderMapper.findById(orderId);
                // 2.使用 Feign 发起Http请求，查询用户
                User user = userClient.findById(order.getUserId());
                // 3.封装User到Order
                order.setUser(user);
                // 4.返回
                return order;
            }
            
        总结：
            主要是基于SrpingMvc的注解来声明远程调用信息，如下对比：
                服务名称：userservice
                请求方式：GET
                请求路径：/user/{id}
                请求参数：Long id
                返回值类型：User
            代码对比：
                RestTemplate:
                    String url = "http://userService/user/" + order.getUserId();
                    User user = restTemplate.getForObject(url, User.class);
                Feign:
                    User user = userClient.findById(order.getUserId());

    自定义配置：
        Feign实现了自动装配，Feign也允许自定义配置覆盖默认配置的。可以修改的配置如下：
            feign.Logger.Level
                修改日志级别：
                    NONE：默认值，无输出日志信息
                    BASIC：请求发送和结束时间耗时多久等一些基本日志信息
                    HEADERS：包含BASIC信息和请求头响应头信息
                    FULL：包含BASIC信息和HEADERS和请求体和响应体信息
            feign.codec.Decoder
                响应结果解析器：
                    http远程调用的结果做解析，例如解析json字符串为java对象
            feign.codec.Encoder
                请求参数编码：
                    将请求参数编码，便于通过http请求发送
            feign.Contract
                支持的注解格式：
                    默认是SpringMVC的注解，用来规定Feign中支持哪些注解
            feign.Retryer
                失败重试机制：
                    请求失败的重试机制，默认没有重试机制，但是feign是依赖ribbon的，所以会使用ribbon的重试机制
        方式一：(order-service/.../application.yml)
            配置文件的方式：
                全局生效：
                    feign:
                        client:
                            config:
                                default: # 这里用default就是全局配置，如果指定服务名称，则是针对某个微服务的配置
                                    loggerLevel: FULL # 日志级别
                局部生效：
                    feign:
                        client:
                            config:
                                userService: # 这里用default就是全局配置，如果指定服务名称，则是针对某个微服务的配置
                                    loggerLevel: FULL # 日志级别
        方式二：
            java代码方式需要首先声明一个Bean：
                public class FeignClientCOnfiguration {
                    @Bean
                    public Logger.Level feignLogLevel() {
                        return Logger.Lever.FULL
                    }
                }
            而后如果是全局配置，则把它放到@EnableFeignClients这个注解中（在启动类中添加）
                @EnableFeignClients(defaultConfiguration = FeignClientConfiguration.class)
            如果是局部配置，则把它放到@FeignClient这个注解中（在clients.IUserClient类中添加）
                @FeignClient(value = "userService", configuration = FeignClientConfinguration.class)
    Feign性能调优：
        Feign底层的客户端实现：
            URLConnection：默认实现，不支持连接池
            Apache HttpClient：支持连接池
            OKHttp：支持连接池
        因此优化Feign的性能主要包括：
            使用连接池代替默认的URLConnection这个不支持连接池的客户端实现
            日志级别：最好用basic和none（不开日志对性能提升的影响很大）
        具体步骤：（order-service）
            Feign添加对HttpClient支持的依赖（这个依赖已经被spring管理起来的，不需要声明版本，只要引入即可）
                <!--引入HttpClient依赖，以修改Feign客户端默认的URLConnection的依赖-->
                <dependency>
                    <groupId>io.github.openfeign</groupId>
                    <artifactId>feign-httpclient</artifactId>
                </dependency>
            配置连接池：
                feign:
                    client:
                        config:
                            default:
                                loggerLevel: FULL
                    httpclient:
                        enabled: true # 支持HttpClient的开关
                        max-connections: 200 # 最大连接数（开发中要根据压测进行对这个属性的最优值进行配置）
                        max-connections-per-route: 50 # 单个请求路径的最大连接数（开发中要根据压测进行对这个属性的最优值进行配置）
    Feign的最佳实践
        方式一（继承思想）：给消费的FeignClient和提供者的controller定义统一的父接口作为标准
            
        方式二（抽取思想）：将FeignClient抽取为独立模块，并且把接口有关的POJO，默认的Feign配置都放到这个模块中，提供给所有消费者使用：
            步骤：
                首先创建一个单独模块，命名为feign-api，然后引入feign的starter依赖
                将 order-service中的 IUserclient、User、 DefaultfeignConfiguration 都复制到 feign-api 项目中
                在 order-service中删除 IUserclient、User、 DefaultfeignConfiguration 相关配置的POJO，并引入 feign-api 的依赖
                修改 order-service 中的所有与上述三个组件有关的 import部分, 改成导入fegn-api中的包
                重启测试
            错误描述：
                Field userClient in cn.lee.order.service.OrderService required a bean of type 'cn.lee.feign.clients.IUserClient' that could not be found.
            原因分析：
                将feign相关配置抽离单独模块feign-api后，order-service启动类扫描包无法扫描到 feign-api模块，所以报错找不到IUserClient这个实现接口
            解决方案：
                但定义的FeignClient不在SpringBootApplication的扫描包范围时，这些FeignClient无法使用。有两种解决方案：
                    方式一：在Order启动类中指定FeignClient所在包：
                        @EnableFeignClients(basePackages = "cn.lee.feign.clients")
                    方式二：在Order启动类中指定FeignClient字节码：（推荐）
                        @EnableFeignClients(clients = {UserClient.class})

















肆.统一网关Gateway：

    为什么需要网关：
    gateway快速入门：
    断言工厂：
    过滤器工厂：
    全局过滤器：
    跨域问题：

    为什么需要网关：
        有些敏感业务是不允许通过外部直接访问的，只能公司内部访问，这时就要对微服务的访问做限制，所以一切请求一定要先到网关再到微服务；
        网关功能：
            身份认证和权限校验：
            服务路由和负载均衡：
                根据请求判断是userSerivce的请求还是OrderService的请求，这就是服务路由，然后对这些微服务的实例再做负载均衡处理
            请求限流：
                微服务能够允许的请求量就是500，如果有两千个请求的话，后面1500的请求会被限制，是对微服务的一种保护措施
            在SpringCloud中网关的实现包括两种，gateway和zuul。
                zuul是基于Servlet的实现，属于阻塞式编程。
                Gateway是基于Spring5中提供的WebFlux，属于响应式编程的实现，具备更好的性能。
    网关搭建：
        网关是一个独立的服务，因此搭建网关就需要创建一个全新的模块。
        操作步骤：
            引入依赖：
                网关依赖
                    <!--Gateway网关依赖-->
                    <dependency>
                        <groupId>org.springframework.cloud</groupId>
                        <artifactId>spring-cloud-starter-gateway</artifactId>
                    </dependency>
                nacos服务发现依赖：因为网关本身也是一个微服务，也需要把自己注册到nacos，或者从nacos拉取服务，所以必须需要nacos的发现依赖。
                    <!--nacos 客户端依赖（发现依赖）-->
                    <dependency>
                        <groupId>com.alibaba.cloud</groupId>
                        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
                    </dependency>
            编写路由配置以及nacos地址：
                server:
                    port: 10010
                spring:
                    application:
                        name: gateway
                    cloud:
                        nacos:
                            server-addr: localhost:8888
                        gateway:
                            routes:
                                - id: userService # 路由标识，必须唯一
                                uri: lb://userService # 路由的目标地址，lb（LoadBalance的首写）
                                predicates: # 路由断言，判断请求是否符合规则
                                    - Path=/user/** # 路径断言，判断路径是否是以/user/开头，如果是则符合规则，那么就会代理到userService中
            此时访问 http://localhost:10010/user/1 即可转发到 http://localhost:8081/user/1 这个userSerivce的服务上
    路由断言工厂：
        网关路由可以配置的内容包括：
            路由id：路由唯一表示
            uri：路由目的地，支持http和lb两种方式
            predicates：路由断言，用以判断请求是否符合要求，符合则转发到路由目的地
            filers：路由过滤器，用以处理请求和响应
        这段主要是对predicates断言的了解：
            Spring提供了十多种基本的predicate工厂：
            我们这里只是简单配置了 Path的规则：还有很多请看spring官网
              predicates:
                - Path=/order/**
            spring官网： https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-after-route-predicate-factory

    路由过滤器：
        GatewayFilter是网关中提供的一种过滤器，可以对进入网关的请求和微服务返回的响应做处理：        
        在Spring中提供了将近40种不同的路由过滤器工厂。        
            spring官网：https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-addrequestheader-gatewayfilter-factory
        作用：
            在过滤器中添加请求头等操作。
            default-filters:
                和 gateway.routes 同级，是针对所有路由的过滤器。
            修改：UserController.java
                第二个参数：@RequestHeader(value = "Truth", required = false) String truth 用来接收过滤器中的请求头参数信息：Truth。
                @GetMapping("/{id}")
                public User queryById(@PathVariable("id") Long id, @RequestHeader(value = "Truth", required = false) String truth) {
                    System.out.println("Truth: " + truth);
                    return userService.queryById(id);
                }

    全局过滤器GlobalFilter：
        和default-filters优点类似，但是有区别：
            GatewayFilter是通过配置定义，处理逻辑是固定的
            GlobalFilter的逻辑是需要自己写代码实现的
        操纵步骤：
            定义实现接口GlobalFilter的接口
                // @Order(-1)
                @Component
                public class AuthorizeFilter implements GlobalFilter, Ordered {
                
                    @Override
                    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                
                        // 获取请求参数
                        ServerHttpRequest request = exchange.getRequest();
                        MultiValueMap<String, String> params = request.getQueryParams();
                        // 获取参数中的 authorization 参数
                        String authorize = params.getFirst("authorization");
                        // 判断参数子是否等于 admin，是则放行
                        if ("admin".equals(authorize)) {
                            return chain.filter(exchange);
                        }
                        // 否则设置为认证的状态吗，然后拦截
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                
                    @Override
                    public int getOrder() {
                        // Order也可以通过过滤器进行指定，用以定义过滤器执行的优先级。返回值越小优先级越高（包括负数）
                        return -1;
                    }
                }
            访问：
                此时访问就需要加上 authorization 参数： http://localhost:10010/user/1?authorization=admin
                否则无法正确访问
            细节：
                GlobalFilter返回的都是WebFlux的Mono接口。
                此外@Order注解用来指定过滤器的执行顺序，代码实现方式就需要继承 Ordered 接口
                过滤器一定要有Order这个顺序
    过滤器链执行顺序：
        请求进入到网关会碰到三类过滤器：当前路由过滤器，DefaultFilter，GlobalFilter。
        请求路由后，会将当前路由器和DefaultFilter和GlobalFilter合并到一个路由器链（集合）中，排序后依次执行每个过滤器
        每一个过滤器都必须指定一个int类型的order值，order值越小，优先级越高，执行顺序越靠前。
            GlobalFilter通过实现Ordered接口，或者@Order注解进行指定
            路由过滤器和DefaultFilter的Order是由Spring指定，默认是按照声明顺序从1递增。
              default-filters：
                - AddRequestHeader=Truth, Douglas Lee is freaking awesome!!! ==> order => 1
                - AddXxxHeader=Truth, Douglas Lee is freaking awesome!!! ==> order => 2
                - AddXxxHeader=Truth, Douglas Lee is freaking awesome!!! ==> order => 3
        当过滤器的Order值都是一样的时，会按着Default > 路由过滤器 > GlobalFilter的顺序执行排序
        总结：
            过滤器排序先看order值，值越小优先级越高，当order值一样时，顺序是：Default > 路由过滤器 > GlobalFilter
    跨域问题处理：
        网关处理跨域采用的同样是CORS方案，并且只需要简单配置即可（globalcors 和 routes 同级）
           globalcors:
            add-to-simple-url-handler-mapping: true
            cors-configurations:
              "[/**]":
                allowedOrigins: # 允许哪些网站的跨域请求
                  - "http://localhost:8090"
                  - "http://www.leechenze.com"
                allowedMethods: # 允许的请求方式
                  - "GET"
                  - "POST"
                  - "DELETE"
                  - "PUT"
                  - "OPTIONS"
                allowedHeaders: "*" # 允许在请求头中携带的头信息
                allowCredentials: true # 是否允许携带cookie
                maxAge: 360000 # 这次跨域检查的有效期
        CORS跨域要配置的参数主要包含以下配置：
            允许哪些域名跨域
            允许哪些请求头
            允许哪些请求方式
            是否允许使用cookie
            有效期时间















伍.Docker

    初识Docker
    Docker的基本操作
    Dockerfile的自定义镜像
    Docker-Compose
    Docker镜像服务
    
    初识Docker
        什么是Docker：
            Docker用来解决Java服务的部署问题。
            java项目在部署时，因为依赖关系复杂，很容易出现兼容性问题，一般服务器都是linux系统。
            那我们的java项目依赖在linux下就要分别对各种各样的依赖进行版本配置。这是一件很复杂的事情。
            因此在没有Docker时，开发部署的效率非常非常底下。
            Docker所做的就是将应用的 Libs（函数库）和 Deps（依赖库）和配置 以及应用一起打包
            再将每个应用放到一个隔离容器中运行，避免互相干扰。比如（Node容器，Rides容器，MQ容器，MySql容器等...）
            那么有一个问题，打包是基于某中操作系统进行打包的，也就是说在ubuntu系统下的应用的依赖和函数库在CentOS上是无法运行的。
            但是Doker是如何跨系统的呢？了解Docker如何跨系统运行前，需要了解下操作系统的结构：（以Ubuntu为例）
                所有的Linux系统都分为以下三层：
                    上层：系统应用（如：ubuntu，centOS）
                    中层：内核（Linux）
                    底层：计算机硬件
                所有的上层系统应用都是基于Linux内核封装的一些方法，进行对内核指令的一个调用。
                内核是不会改变的，但是变化的是系统应用，所以Ubuntu系统的一些方法可能 centOS中根本就不存在。
            那么Docker是如何处理不同系统应用的问题的呢？
                Docker实际是将用户程序和所需要调用的系统（比如Ubuntu）函数库一起打包
                所以Docker就不需要关注系统是什么，因为他自己就有，他依赖的只是Linux内核，所以只需要关注是Linux内核就行。
        总结：
            Docker就是一个快速交付应用，运行应用的技术：
                可以将程序及其依赖，运行环境一起打包为一个镜像，可以迁移到任意Linux操作系统 


        Docker和虚拟机的区别：
            虚拟机（Virtual Machine）是在操作系统中模拟硬件设备，然后运行另一个操作系统。
            Docker应用执行时是直接调用操作系统内核的
            VirtualMachine是使用Hypervisor的技术来实现和内核交互的，所以性能会有折扣 
            因此企业在做服务部署时，都会选择使用Docker，而不会选择用虚拟机了。
            总结：
                性能：
                    Docker接近原生，虚拟机性能较差
                硬盘占用：
                    Docker一般为MB级别，虚拟机一般都在GB级别
                启动：
                    Docker在秒级别，虚拟机在分钟级别
                    因为Docker是系统的一个进程，而虚拟机属于在操作系统中的又一个操作系统。

        Docker架构：
            镜像和容器：
                Docker架构中有几个比较重要的概念，首先就是镜像和容器
                镜像（Image）：Docker将应用程序及其所需要的依赖，函数库，环境，配置文件等打包在一起，称为镜像
                容器（Container）：镜像中的应用程序运行后形成的进程就是容器，只是Docker会给容器做隔离，对外不可见。
                    最大的特点，容器是只可读的，不可写入的，想要写入需要拷贝一份文件到自己的文件系统中重写，别对原容器有污染，这就是容器隔离的特性
                DockerHub：
                    DockerHub是一个Docker镜像托管平台，这样的平台成为Docker Registry（即镜像服务器）
                    这个镜像服务器国内有很多比如网易云，阿里云，同样公司内部都可以搭建自己的私有云
                    都可以从镜像服务器中拉取镜像和运行容器。
            架构：
                Docker是一个CS的架构程序，由两部分组成：
                    服务端（Server）：Docker守护进程，负责处理Docker指令，管理镜像、容器等。
                    客户端（Client）：通过命令或RestAPI向Docker服务端发送指令，可以在本地或远程向服务端发送指令

        安装Docker：
            企业部署一般都是采用Linux操作系统，而其中又数CentOS发行版占比最多，所以在CentOS下安装Docker配置请看 lib/day2/Centos7安装Docker.md
            docker镜像加速：https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors
            dockerhub
            后面教程使用本机mac进行学习。
        
        Docker基本操作：
            镜像操作：
            容器操作：
            数据卷（容器数据管理）：
            
            镜像相关命令：
                镜像名称一般分为两部分组成：[repository]:[tag]
                比如：mysql:5.7，mysql就是repository，5.7就是版本（tag）
                如果指定tag时，默认即使latest，代表最新版本镜像。
            
            镜像操作命令：
                获取镜像：
                    本地获取：通过一个DockerFile的文件，通过 docker build 的命令构建为一个镜像。
                    远程获取：大多数情况下我们会通过 Docker registry （DockerHub）镜像服务器中通过 docker pull 远程拉取镜像
                常用命令：
                    docker search： 通过镜像名搜索镜像
                    docker build： 构建镜像
                    docker images： 查看镜像
                        -a： 查看所有镜像
                    docker rmi： 删除镜像
                    docker push：推送镜像到服务器
                    docker pull：拉取镜像到服务器
                    docker save：保存镜像为一个压缩包
                        -o： 镜像输出的保存名称
                    docker load：加载压缩包为一个镜像
                        -i： 输入的镜像保存时的名称
                    docker --help： docker命令帮助文档
                    docker images --help：docker的images命令帮助文档
                镜像命令实操：（docker本地实操都在 lib/day2/local-docker 这个目录下）
                    首先去docker hub 搜索镜像，这里就在 DockerHub 搜索了。
                    根据查到的镜像名称，拉取自己需要的镜像：
                        docker pull nginx
                    查看拉取到的镜像：
                        docker iamges
                    导出nginx镜像为压缩包
                        docker save -o nginx.tar nginx:latest
                        这时本地目就会出现一个 nginx.tar 压缩包
                    删除本地镜像
                        docker rmi nginx:latest
                    查看images确定已经删除掉了 nginx:latest
                        docker images
                    确保成功删掉 nginx:latest 之后，加载本地的 nginx.tar
                        docker load -i nginx.tar
                    再次查看images确定已经成功加载了 nginx.tar 为 nginx:latest
                        docker images
                    
            容器命令操作：
                常用命令：
                    docker run： 创建容器及运行容器
                        --name： 指定容器名称
                        -p： (重要)将宿主机端口与容器端口映射，冒号左侧是宿主机端口，右侧是容器端口。如果不暴露宿主机端口则无法访问容器。
                        -d： 后台运行的容器
                        -a： 查看所有状态的容器（默认只能查看运行中的容器）
                        -v [html:/root/html]：指定把html数据卷挂载到容器内的/root/html这个目录中  
                        [image name]： 指定宿主机中的容器
                    docker pause：暂停容器
                    docker unpause： 取消暂停容器
                    docker stop： 停止容器
                        [container name]： 指定容器名称
                    docker start： 开始容器
                        [container name]： 指定容器名称
                    docker rm： 删除容器
                        -f： 强制删除运行中的容器（运行状态的容器不允许删除）
                    docker ps： 查看运行的容器及其状态
                    docker logs： 查看容器运行日志
                        -f： 持续更新日志信息
                        [container name]： 指定查看日志的容器名称
                    docker exec： 进入容器内部执行命令
                        -it： 指定当前进入的容器创建一个输入、输出终端，允许与容器的交互
                        [container name]：要进入的容器名称
                        [command]：进入容器后执行的命令，如：bash（一个linux的终端交互命令）
                容器命令实操一：（创建运行一个nginx容器）
                    docker run --name mynginx -p 80:80 -d nginx
                    docker logs -f mynginx
                容器命令实操二：（进入mynginx容器，修改html文件内容，添加"Hello Douglas Z. Lee"
                    进入mynginx容器内部
                        docker exec -it mynginx bash
                    pwd查看当前目录，并ls 确定已经到了linux的根目录
                        pwd
                        ls
                        此时就需要知道nginx在这个容器中的哪个目录，这就需要去官网去查看了，官网给出的目录地址是：/usr/share/nginx/html
                    进入到nginx的html目录，并查看index.html
                        cd /usr/share/nginx/html
                        ls
                        cat index.html
                    修改替换index.html中的内容（这里只是演示，不要在容器内做文件修改，不仅麻烦而且无法记录修改内容和日志）
                        sed -i 's#Welcome to nginx#Hello Douglas Z. Lee#g' index.html
                        sed -i 's#<head>#<head><meta charset="utf-8">#g' index.html
                    再次刷新localhost:80 查看 nginx的欢迎页Title 已经变为 Hello Douglas Z. Lee。
                    退出容器：
                        exit
                    停止容器：
                        docker stop mynginx
                    启动容器：
                        docker start mynginx
                    查看所有容器：               
                        docker ps -a
                    删除容器：
                        docker rm nginx
                    查看所有容器确定下 mynginx 容器删除成功：     
                        docker ps -a
                容器命令实操三：（创建并运行一个redis容器，并且支持数据持久化）
                    创建并运行myredis容器：
                        docker run --name myredis -p 6379:6379 -d redis redis-server --appendonly yes
                    进入myredis容器内部：
                        docker exec -it myredis bash
                        当然也可以直接进入 redis-cli
                        docker exec -it myredis redis-cli
                    连接redis：
                        redis-cli
                    存储数据：
                        set num 666
            
            数据卷命令操作：
                解读：数据卷（volume）是一个虚拟目录，指向宿主机文件系统中的某个真实目录。
                $PWD：此命令表示当前路径
                docker volume 命令是数据卷操作，后面跟随的command来确定下一步的操作：
                    create： 创建一个数据卷
                    inspect： 显示一个或多个volume的信息
                    ls： 列出所有的volume
                    prune： 删除未使用的volme
                    rm： 删除一个或多个指定的volume
                数据卷命令实操一：
                    创建一个名为html的数据卷：
                        docker volume create html
                    查看所有的数据卷，以查看html是否成功创建：
                        docker volume ls
                    查看html的详细信息：
                        docker volume inspect html
                    删除未使用的数据卷：
                        docker volume prune
                        会有提示，大致意思：将删除所有的本地的未使用的卷，是否确定？
                    删除html卷：
                        docker volume rm html
                数据卷命令实操二：（注意：这里使用CentOS进行操作了）
                    案例：这里演示的还是之前的 nginx 首页标头更改案例。
                    问题处理：
                        CentOS中（一切失败的命令使用 --nogpgcheck 进行安装）
                        CentOS中操作步骤：ifconfig 中找到 ens160 的 inet 的值：172.16.168.130
                        然后docker 中 nginx的开放端口为 80，所以：在本地浏览器访问：(172.16.168.130:80  ==>  172.16.168.130)
                        就可以看到在虚拟机中启动的 nginx 的首页。
                    
                    创建并且运行mynginx容器：
                        docker run --name mynginx -p 80:80 -v html:/usr/share/nginx/html -d nginx
                    查看html数据卷的详细信息：
                        docker volume inspect html
                        信息中找到 MountPoint 字段，就是在挂载的虚拟目录（/var/lib/docker/volumes/html/_data）
                    进入这个虚拟目录：
                        cd /var/lib/docker/volumes/html/_data
                    查看目录下的文件：
                        ls
                        此时会输出 50x.html 和 index.html 说明此时已经挂载到了宿主机的真实目录下了。
                        那么此时已经映射到宿主机的目录下了，无论是 vi，nano还是vim都可以对其进行修改。
                        再次把 Welcome Nginx 换成 Hello Docker Hub！ 这个标头改一下。
                        然后进行访问 centos中ifconfig中的本机地址 + nginx 的端口 即可看到修改后的内容。
                数据卷命令实操三：（创建并运行一个MySql容器，将宿主机目录直接挂载到容器）
                    提示：这里是宿主机目录挂载，而不是数据卷名称挂载了，仍是在CentOS下进行操作
                        目录挂载和数据卷挂载的语法是类似的：
                            -v [宿主机目录]:[容器内目录]
                            -v [宿主机文件]:[容器内文件]
                    操作步骤：
                        从DockerHub下载mysql镜像
                            docker pull mysql
                        创建目录：/tmp/mysql/data
                            mkdir /tmp/mysql/data
                        创建目录：/tmp/mysql/conf，将lib/day2/hmy.cnf 文件放到 /tmp/mysql/conf
                            mkdir /tmp/mysql/conf
                        创建及运行mysql容器：
                            docker run --name mysql -e MYSQL_ROOT_PASSWORD=lcz19930316 -p 3333:3306 -v "$PWD/tmp/mysql/data/":/var/lib/mysql/ -v "$PWD/tmp/mysql/conf/hmy.cnf":/etc/mysql/conf.d/hmy.cnf -d mysql:latest
                            docker run --name mysql -e MYSQL_ROOT_PASSWORD=lcz19930316 -p 3333:3306 -v /tmp/mysql/data:/var/lib/mysql -v /tmp/mysql/conf/hmy.cnf:/etc/mysql/conf.d/hmy.cnf -d mysql:latest
                        在DockerHub查阅资料，创建并运行MySql容器，要求：
                            1。挂载/tmp/mysql/data 到 mysql容器内数据存储目录（/var/lib/mysql）
                            2。挂载/temp/mysql/conf/hmy.cnf 到 mysql容器的配置文件（/etc/mysql/conf.d/hmy.cnf）
                            3。设置MySql密码（MYSQL_ROOT_PASSWORD）
                            
                    踩坑记录：一定要注意docker的参数顺序！！！
            
            自定义镜像：
                镜像结构：
                    再次回顾镜像概念：镜像是将应用程序及其需要的系统函数库、环境、配置、依赖打包而成。
                    镜像是分层结构，每一层成为一个Layer。在任何镜像结构当中一般都会有两层（BaseImage，EntryPoint）：
                        BaseImage：基础镜像层，包含基础的系统函数库，环境变量，文件系统。
                        EntryPoint：入口，是镜像中应用启动的命令。
                        其他：在BaseImage基础上添加依赖，安装程序，完成整个应用的安装和配置。
                自定义镜像：
                    DockerFile是一个文本文件，其中包含了一个个的指令，用指令来说明要执行什么操作来构建镜像，每一个指令都会形成一个Layer。
                    常用指令：
                        FROM：       指定基础镜像，如：（FROM centos:6）
                        ENV：        设置环境变量，可在后面指令使用，如：（ENV key value）
                        COPY：       拷贝本地文件到镜像到指定目录，如：（COPY ./mysql-5.7.rpm /tmp）
                        RUN：        执行Liunx的shell命令，一般是安装过程命令，如：（RUN yun install gcc）
                        EXPOSE：     指定容器运行时监听的端口，是暴露给镜像使用者看的，如：（EXPOSE 8080）
                        ENTRYPOINT： 镜像中应用的启动命令，容器运行时调用，如（ENTRYPOINT java -jar xx.jar）
                    更多更详细的语法说明，请参考官网文档：https://docs.docker.com/engine/reference/builder    
                构建一个Java项目镜像：（基于Ubuntu镜像构建一个新镜像，运行一个java项目）
                    新建一个空文件夹 custom-docker
                    拷贝day2/docker-demo.jar 到 custom-docker
                    拷贝day2/jdk8.tar.gz 到 custom-docker
                    拷贝day2/Dockerfile 到 custom-docker
                    进入custom-docker
                        docker build -t javaweb:1.0 .
                            解读：-t 是tag的简写，最后还有一个 . 表示Dockerfile所在的目录
                        docker run --name myjavaweb -p 8090:8090 -d javaweb:1.0
                            此时访问：http://localhost:8090/hello/count
                基于java:8-alpine镜像，将一个java项目构建为镜像。
                    新建一个空文件夹 custom-alpine
                    拷贝day2/docker-demo.jar 到 custom-alpine
                    拷贝day2/jdk8.tar.gz 到 custom-alpine
                    拷贝day2/Dockerfile-alpine 到 custom-alpine 并且更名为 Dockerfile
                        修改Dockerfile：
                            1。基于java:8-alpine作为基础镜像
                            2。将app.jar拷贝到镜像中
                            3。暴露端口
                            4。编写入口ENTRYPOINT
                    进入custom-alpine
                        docker build -t javawebalpine:1.0 .
                            解读：-t 是tag的简写，最后还有一个 . 表示Dockerfile所在的目录
                        docker run --name myjavawebalpine -p 8070:8070 -d javawebalpine:1.0
                            此时访问：http://localhost:8070/hello/count
            
            DockerCompose：
                初识DockerCompose：
                    Docker Compose可以基于compose文件帮我们快速部署分布式应用，而无需手动一个个创建和运行容器。
                    compose是一个文本文件，通过指令定义集群中每个容器如何运行。
                    实际上通过compose文件对比就能发现：docker compose文件中就是把 docker run 命令行的各种命令行参数变成指令定义了。
                    docker compose 官网：https://docs.docker.com/compose/compose-file/
                    docker compose安装：详细请看：Advanced/lib/day2/Centos7安装Docker.md
                    总结：compose就是用以实现集群微服务的快速的构建和部署
                部署微服务集群：
                    1。day2/cloud-demo中，编写docker-compose.yml文件 和 每个微服务中的Dockerfile文件
                    2。修改自己的cloud-demo项目，将数据库、nacos地址都命名为 docker-compose.yml 中的服务名
                    3。将每个微服务都打个包，名为app.jar，因为Dockerfile中配置的名字叫做app.jar
                        user-service, order-service, gateway三个微服务的 pom.xml 中新增如下插件： 
                            <build>
                                <finalName>app</finalName>
                                <plugins>
                                    <plugin>
                                        <groupId>org.springframework.boot</groupId>
                                        <artifactId>spring-boot-maven-plugin</artifactId>
                                    </plugin>
                                </plugins>
                            </build>
                        注意打包前最好clean下，然后package后 target 就会出现app.jar包
                    4。将打包好的app.jar拷贝到每个微服务对应的子目录中。
                    5。cd cloud-demo中，使用 docker-compose up -d 进行部署。
                    emmmm...反正就是这些步骤，docker-compose对于macm1是不受支持的, so... 这里没有完成部署。
                windows和x86芯片的MacOS和其他Linux内核的系统都可以根据以上步骤进行。
                
            Docker镜像仓库：
                搭建私有镜像仓库：
                    镜像仓库（Docker Registry）有共有和私有两种形式：
                        公共仓库：例如Docker官方的DockerHub，国内也有一些云服务商提供类似于DockerHub的公开服务，比如网易云镜像服务，DaoCloud镜像服务，阿里云镜像服务等。
                        私有仓库：除了使用公开仓库外，用户还可以在本地搭建私有Docker Registry。企业自己的镜像服务考虑安全性和私密性一般都是私有的Docker Registry。
                    搭建私有镜像仓库可以基于Docker官方提供的Docker Registry来搭建：
                        官网地址：https://hub.docker.com/_/registry
                    方式一：简化板镜像仓库
                        Docker的官方DockerRegistry是一个基础版的Docker镜像仓库，具备仓库管理的完整功能，但没有图形化界面，命令如下：
                            docker run -d \
                                --restart=always \
                                --name registry	\
                                -p 5000:5000 \
                                -v registry-data:/var/lib/registry \
                                registry
                    方式二：带有图形化界面的版本
                        使用DockerCompose部署带有图形界面的DockerRegistry，命令如下：
                            version: '3.0'
                            services:
                                registry:
                                    image: registry
                                    volumes:
                                        - ./registry-data:/var/lib/registry
                                ui:
                                    image: joxit/docker-registry-ui:static
                                    ports:
                                        - 8080:80
                                    environment:
                                        - REGISTRY_TITLE=这是标题可以随便写
                                        - REGISTRY_URL=http://registry:5000
                                    depends_on:
                                        - registry
                        注：registry默认端口是5000
                        私服采用的是http协议，默认不被Docker信任，所以需要配置Docker信任地址：
                            # 打开要修改的文件
                            vi /etc/docker/daemon.json
                            # 添加内容：
                            "insecure-registries":["http://192.168.150.101:8080"]
                            # 重加载
                            systemctl daemon-reload
                            # 重启docker
                            systemctl restart docker
                        cloud-demo 同级目录新建：registry-ui
                            mkdir registry-ui
                            cd registry-ui
                            touch docker-compose.yml
                                将DockerRegistry的命令粘贴进去
                            docker-compose up -d
                        此时访问 172.16.168.130:8080 就会看到 DockerRegistry 的图形化界面，172.16.168.130 是centos中的ip地址。

                向镜像仓库推送和拉取镜像：
                    重新tag本地镜像，名称前缀为私有仓库地址：172.16.168.130:8080/
                        docker tag nginx:latest 172.16.168.130:8080/nginx:1.0
                        命令解读：tag重命名：nginx:latest是原tag，修改为了nginx:1.0，就是将latest的tag修改为1.0的tag。
                    推送镜像：
                        docker push 172.16.168.130:8080/nginx:1.0
                        此时访问 172.16.168.130:8080 就可以看到推送的这个镜像。
                    拉取镜像：
                        docker pull 172.16.168.130:8080/nginx:1.0




















陆.RabbitMQ（mq-demo）
    
    初识MQ：
    RabbitMQ：
    SpringAMQP：



    初识MQ：
        MQ（MessageQueue），翻译为消息队列，字面来看就是存放消息的队列，实际上就是事件队列。也就是事件驱动架构中的Broker。
        常见的MQ框架有：RabbitMQ，ActiveMQ，RocketMQ，Kafka
        以上四种都各有优劣，优势各有不同，一般根据应用场景选择使用哪个。比较常用的还是RabbitMQ和Kafka和RocketMQ。

        同步通讯：
            微服务间基于Feign的调用就是同步方式的通讯。
            优点：
                实效性较强，可以立即得到结果。
            缺点：
                耦合度高：每次加入新的需求，都要修改原代码
                性能下降：调用者需要等待服务提供者的响应，如果调用链过长，则响应时间等于每次调用的时间之和。
                资源浪费：调用链中的每个服务在等待响应过程中，不能释放请求占用的资源，高并发场景下极度浪费系统资源。
                级联失败：如果服务提供者出现问题，所有调用方都会跟着出问题，如同多米诺骨牌一样，导致整个微服务群故障。

        异步通讯：
            异步调用常见实现就是事件驱动模式。
            优点：
                耦合度低
                吞吐量提升
                故障隔离
                流量削峰：高并发来了之后，通过broker做一个缓存，微服务基于自己的能力从broker中获取事件和处理事件，这样能做到对微服务的保护作用
            缺点：
                依赖于Broker的可靠性，安全性和吞吐能力，且有较高的要求。
                架构复杂了，业务没有明显的流程线路，不好追踪管理。
            总结：大多数情况下都是用的是同步通讯，因为大多数情况下对并发没有很高的要求，反而对实效性要求较高。

        RibbitMQ：
            安装：docker pull rabbitmq:3.12-rc-management
            运行：
                docker run \
                    -e RABBITMQ_DEFAULT_USER=leechenze \
                    -e RABBITMQ_DEFAULT_PASS=930316 \
                    --name rabbitmq \
                    --hostname mq1 \
                    -p 15672:15672 \
                    -p 5672:5672 \
                    -d \
                    rabbitmq:3.12-rc-management
                陌生的命令解读：
                    -e RABBITMQ_DEFAULT_USER 和 RABBITMQ_DEFAULT_PASS是配置用户名和密码的环境变量，后续访问mq和登陆管理平台都需要账号和密码。
                    --hostname 是配置主机名，单机可以不用配置，但是如果后续集群部署就必要配置主机名了。                    
                    -p 15672是RabbitMQ提供的一个UI界面，管理平台的端口。
                    -p 5672是后续做消息通讯的一个端口，发消息和收消息都是通过5672
                访问：127.0.0.1:15672然后登录
            核心概念：
                channel：        操作MQ的工具
                exchange：       路由消息到队列中
                queue：          缓存消息
                virtual host：   虚拟主机，是对queue，exchange等资源的逻辑分组
            常见消息模型：
                MQ的官网中给出了几种MQ的Demo示例，对应了几种不同的用法：
                    基本消息队列：（BasicQueue）
                        只包含三个角色：
                            publisher：发布者
                                负责发送消息到队列
                            queue：缓存消息队列
                                接收消息并且缓存消息
                            consumer：消费者
                                从缓存中获取消息和处理消息
                        基本消息队列的消息发送流程：（PublisherTest.java）
                            1。建立connection
                            2。创建channel
                            3。利用channel声明队列
                            4。利用channel向队列发送消息
                        基本消息队列的消息接收流程：（Consumer.java）
                            1。建立connection
                            2。创建channel
                            3。利用channel声明队列
                            4。定义consumer的消费行为handleDelivery
                            5。利用channel将消费者与队列绑定以处理消息
                    工作消息队列：（WorkQueue）
                    发布订阅消息队列：（Publish，Subscribe），又根据交换机类型不同分为三种：
                        Fanout Exchange：广播（Publish，Subscribe）
                        Direct Exchange：路由（Routing）
                        Topic Exchange：主题（Topics）


        SpringAMQP：
            初识SpringAMQP：
            BasicQueue：简单队列模型
            WorkQueue：工作队列模型
            发布、订阅模型-Fanout：广播模型
            发布、订阅模型-Direct：路由模型
            发布、订阅模型-Topic：主题模型
            消息转换器：


            初识SpringAMQP：
                AMQP：Advanced Message Queuing Protocol（高级消息队列协议）
                    是用于在应用程序或之间传递业务消息的开放标准。改协议和语言与平台无关，更符合微服务中独立性的要求。
                SpringAMQP：
                    是基于AMQP协议定义的一套API规范，提供了模版发送和接收消息，包含两部分，其中spring-amqp是基础抽象，spring-rabbit是底层的默认实现。
            BasicQueue模型：
                利用springamqp实现helloworld中的基础消息队列功能
                    在父工程中引入spring-amqp的依赖。
                    在publisher服务中利用RabbitTemplate发送消息到simple.queue这个队列。
                    在consumer服务中编写消费逻辑，绑定simple.queue这个队列。
                    具体步骤：
                        1。父工程中引入amqp依赖：因为publisher和consumer服务都需要amqp依赖。因此这里把依赖直接放到父工程mq-demo中。
                            <dependency>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter-amqp</artifactId>
                            </dependency>
                        2。在publisher服务中编写application.yml，添加mq连接信息：
                            spring:
                                rabbitmq:
                                    host: 127.0.0.1
                                    port: 5672
                                    username: leechenze
                                    password: 930316
                                    virtual-host: / # leechenze这个用户的虚拟主机（/）
                        3。在publisher服务中新建一个测试类，编写测试方法：（PublisherSpringAMQPTest.java）
                            注意：这里要在rabbit控制台手动创建 （simple.queue）这个队列，SpringAMQP默认不会主动创建队列。
                            @RunWith(SpringRunner.class)
                            @SpringBootTest
                            public class PublisherSpringAMQPTest {
                                @Autowired
                                private RabbitTemplate rabbitTemplate;
                                @Test
                                public void testSendMessage2SimpleQueue() {
                                    String queueName = "simple.queue";
                                    String message = "hello SpringAMQP";
                                    rabbitTemplate.convertAndSend(queueName, message);
                                }
                            }
                        4。在consumer中编写application.yml，添加mq连接信息：
                            spring:
                                rabbitmq:
                                    host: 127.0.0.1
                                    port: 5672
                                    username: leechenze
                                    password: 930316
                                    virtual-host: / # leechenze这个用户的虚拟主机（/）
                        5。在consumer中编写消费逻辑，监听 simple.queue：（listener.SpringRabbitListener）
                            @Component
                            public class SpringRabbitListener {
                                @RabbitListener(queues = "simple.queue")
                                public void listenSimpleQueue(String msg) {
                                    System.out.println("消费者接收到simple.queue的消息：【" + msg + "】");
                                }
                            }
                            注意这里要运行的是ConsumerApplication这个启动类。
                            运行成功后可以成功打印接收到的simple.queue中的消息
                            消息一旦接收到后，就会从队列中删除，RabbitMQ没有消息回溯功能（阅后即焚）
            WorkQueue模型：       
                作用：提高消息处理速度，避免队列消息堆积：
                相比基础队列模型（BasicQueue）workQueue拥有多个消费者
                基本思路如下：
                    1。在publisher服务中定义测试方法，每秒产生50条消息，发送到work.queue中。（PublisherSpringAMQPTest）
                        /**
                         * WorkQueue 工作队列模型
                         */
                        @Test
                        public void testSendMessage2WorkQueue() throws InterruptedException {
                            String queueName = "work.queue";
                            String message = "Hello SpringAMQP WorkQueue --> ";
                            for(int i = 0; i <= 50; i++) {
                                rabbitTemplate.convertAndSend(queueName, message + i);
                                Thread.sleep(20);
                            }
                        }
                    2。在consumer服务中定义两个消息监听者，都监听 work.queue 队列（listener.SpringRabbitListener）
                        /**
                         * WorkQueue 工作队列模型
                         * @param msg
                         */
                        @RabbitListener(queues = "work.queue")
                        public void listenWorkQueue1(String msg) throws InterruptedException {
                            // 为了和工作队列2区别，这里使用 out 打印普通日志，LocalTime.now()当前时间
                            System.out.println("工作队列模型消费者1 收到work.queue的消息：【" + msg + "】" + LocalTime.now());
                            Thread.sleep(20);
                        }
                        @RabbitListener(queues = "work.queue")
                        public void listenWorkQueue2(String msg) throws InterruptedException {
                            // 为了和工作队列1区别，这里使用 err 打印红色错误日志，LocalTime.now()当前时间
                            System.err.println("工作队列模型消费者2 收到work.queue的消息：【" + msg + "】" + LocalTime.now());
                            Thread.sleep(200);
                        }
                    3。消费者1每秒处理50条消息，消费者2每秒处理10条消息。
                    4。消息预取限制配置：consumer下的application.yml中配置：
                        listener:
                          simple:
                            prefetch: 1 # rabbitmq消息预取配置，声明每次只能获取一条消息，处理完成之后才能获取下一条消息
            发布（publisher）、订阅（subscribe）模型：
                发布订阅模式与之前案例的区别就是允许将同一消息发送给多个消费者。实现方式就是加入了exchange（交换机）
                常见的exchange类型有：
                    Fanout：广播类型
                    Direct：路由类型
                    Topic：话题类型
                注意：exchange负责消息路由和消息转发，并不负责消息存储，只有队列Queue负责消息存储，所以如果路由失败则消息丢失
                
                Fanout广播模型：
                    Fanout Exchange会将接收到的消息路由到每一个跟其绑定的queue。
                    利用SpringAMQP演示FanoutExchange的使用：
                        实现思路如下：
                            1。在consumer服务中，利用代码声明队列，交换机，并将两者绑定。
                            2。在consumer服务中，编写两个消费者的方法，分别监听 fanout.queue1 和 fanout.queue2
                            3。在publisher中编写测试方法，向leechenze.fanout发送消息。
                    操作步骤：
                        1。在consumer服务创建配置类 config.FanoutConfig.java：
                            @Configuration
                            public class FanoutConfig {
                                /**
                                * FanoutExchange 发布订阅模型：广播队列
                                */
                                // leechenze.fanout
                                @Bean
                                public FanoutExchange fanoutExchange(){
                                    return new FanoutExchange("leechenze.fanout");
                                }
                            
                                // fanout.queue1
                                @Bean
                                public Queue fanoutQueue1(){
                                    return new Queue("fanout.queue1");
                                }
                            
                                // 绑定队列1到交换机
                                @Bean
                                public Binding fanoutBinding1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
                                    return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
                                }
                            
                                // fanout.queue2
                                @Bean
                                public Queue fanoutQueue2(){
                                    return new Queue("fanout.queue2");
                                }
                            
                            
                                // 绑定队列2到交换机
                                @Bean
                                public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
                                    return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
                                }
                            }
                        2。在consumer服务中添加两个方法，分别监听fanout.queue1 和 fanout.queue2：（SpringRabbitListener.java）
                            /**
                             * FanoutExchange 发布订阅模型：广播队列
                             * @param msg
                             */
                            @RabbitListener(queues = "fanout.queue1")
                            public void listenFanoutQueue1(String msg) {
                                System.out.println("消费者接收到fanout.queue1的消息：【" + msg + "】");
                            }
                            @RabbitListener(queues = "fanout.queue2")
                            public void listenFanoutQueue2(String msg) {
                                System.out.println("消费者接收到fanout.queue2的消息：【" + msg + "】");
                            }
                        3。在publisher服务发送消息到FanoutExchange，注意发送到交换机而不是直接发给消息队列了：（PublisherSpringAMQPTest.java）
                            /**
                             * FanoutExchange 发布订阅模型：广播队列
                             * @description 不同之处在于之前都是发消息到队列，这里是发消息到交换机，代码也略有不同
                             */
                            @Test
                            public void testSendFanoutExchange() {
                                // 交换机名称
                                String exchangeName = "leechenze.fanout";
                                // 消息定义
                                String message = "hello FanoutExchange every one";
                                // 消息发送
                                rabbitTemplate.convertAndSend(exchangeName, "", message);
                            }
                    总结： 
                        队列的Bean是用：Queue 
                        交换机的Bean是用：FanoutExchange 
                        绑定关系的Bean是用：Binding

                Direct路由模型：
                    Direct Exchange会将接收到的消息根据规则路由到指定的queue，因此成为路由模式。
                        每一个Queue都与Exchange设置一个Bindingkey
                        发布者发送消息时，指定消息的bindingkey。
                        Exchange将消息路由到Bindingkey与消息的Bindingkey一致的队列。
                    利用SpringAMQP演示DirectExchange的使用：
                        实现思路如下：
                            1。利用@RabbitListener声明Exchange，Queue，RoutingKey，不用@Bean是因为需要声明的方法太多，用Bean声明太复杂了，这里使用Rabbit官方提供的注解。
                            2。在consumer服务中编写两个消费者方法，分别监听direct.queue1和direct.queue2
                            3。在publisher中编写测试方法，向leechenze.direct发送消息。
                    操作步骤：
                        1。在consumer服务中编写两个消费者的 direct 的监听方法：（SpringRabbitListener.java）
                            /**
                             * DirectExchange 发布订阅模型：路由队列
                             *
                             * @param msg
                             */
                            @RabbitListener(bindings = @QueueBinding(
                                    value = @Queue(name = "direct.queue1"),
                                    exchange = @Exchange(name = "leechenze.direct", type = ExchangeTypes.DIRECT),
                                    key = {"red", "blue"}
                            ))
                            public void listenDirectQueue1(String msg) {
                                System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
                            }
                            @RabbitListener(bindings = @QueueBinding(
                                    value = @Queue(name = "direct.queue2"),
                                    exchange = @Exchange(name = "leechenze.direct", type = ExchangeTypes.DIRECT),
                                    key = {"red", "yellow"}
                            ))
                            public void listenDirectQueue2(String msg) {
                                System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
                            }
                        2。在publisher服务中发送消息到 direct.queue1 和 direct.queue2：（PublisherSpringAMQPTest.java）
                            /**
                             * FanoutExchange 发布订阅模型：广播队列
                             * @description 不同之处在于之前都是发消息到队列，这里是发消息到交换机，代码也略有不同
                             */
                            @Test
                            public void testSendDirectExchange() {
                                // 交换机名称
                                String exchangeName = "leechenze.direct";
                                // 消息定义
                                String message = "hello red";
                                // 消息发送（这里routingkey要分别尝试改为：red（direct.queue1 && queue2），blue（direct.queue1），yellow（direct.queue2））
                                rabbitTemplate.convertAndSend(exchangeName, "red", message);
                            }

                Topic话题模型：
                    Topic Exchange 和 Direct Exchange类似，区别在于routingkey必须是多个单词的列表，并且以 . 分割
                        Queue和Exchange在指定BindingKey时可以使用通配符：
                            #：代指零个或多个单词
                            *：代指一个单词
                    利用SpringAMQP演示TopicExchange的使用：
                        实现思路如下：和 Direct Exchange实现思路相同。
                    操作步骤：
                        1。在consumer服务中编写两个消费者的 topic 的监听方法：（SpringRabbitListener.java）
                            /**
                             * TopicExchange 发布订阅模型：话题队列
                             * china.news
                             * china.weather
                             * japan.news
                             * jspan.weather
                             *
                             * @param msg
                             */
                            @RabbitListener(bindings = @QueueBinding(
                                    value = @Queue(name = "topic.queue1"),
                                    exchange = @Exchange(name = "leechenze.topic", type = ExchangeTypes.TOPIC),
                                    key = "china.#"
                            ))
                            public void listenTopicQueue1(String msg) {
                                System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
                            }
                            @RabbitListener(bindings = @QueueBinding(
                                    value = @Queue(name = "topic.queue2"),
                                    exchange = @Exchange(name = "leechenze.topic", type = ExchangeTypes.TOPIC),
                                    key = "#.news"
                            ))
                            public void listenTopicQueue2(String msg) {
                                System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
                            }
                        2。在publisher服务中发送消息到 topic.queue1 和 topic.queue2：（PublisherSpringAMQPTest.java）
                            /**
                             * TopicExchange 发布订阅模型：广播队列
                             */
                            @Test
                            public void testSendTopicExchange() {
                                // 交换机名称
                                String exchangeName = "leechenze.topic";
                                // 消息定义
                                String message = "中国新闻！！！轰20首飞了，十二万吨核动力航母下水啦！！！";
                                // 消息发送（这里routingkey要分别尝试：china.news, china.weather, japan.news, japan.weather）
                                rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
                            }
                    总结：
                        Topic 和 Direct 代码差别很小，差别在于Topic交换机（BindingKey）支持通配符匹配，RoutingKey必须多个单词以 . 分割。
                        
                消息转换器：
                    总结：
                        SpringAMQP的序列号和反序列化是通过MessageConverter实现的，默认是JDK的序列号。
                        推荐使用json自定义的MessageConverter覆盖默认的MessageConverter。
                        另外注意发送方和接收方必须使用的是相同的MessageConverter。
                        不能一边是JDK序列化的一边是json的，那肯定是无法沟通的。
                    
                    操作步骤：
                        1。在FanoutConfig中编写声明一个队列：（FanoutConfig）
                            /**
                             * 消息转换器演示：因为在listener.SpringRabbitListener中基于注解声明消息直接被消费了，所以在这里声明@Bean的方式。
                             * @return
                             */
                            @Bean
                            public Queue objectQueue() {
                                return new Queue("object.queue");
                            }
                        2。在Publisher服务编写消息发送方，发送一个Map类型的消息：（PublisherSpringAMQPTest）
                            /**
                             * 消息转换器演示
                             */
                            @Test
                            public void testSendObjectQueue() {
                                Map<String, Object> msg = new HashMap<>();
                                msg.put("name", "trump");
                                msg.put("age", 60);
                                rabbitTemplate.convertAndSend("object.queue", msg);
                            }
                            此时会发现Rabbitmq控制台中接收到的消息是一段 content_type 为 application/x-java-serialized-object的序列化串
                            那么我们就需要通过json序列化为一段可读的对象。
                        3。在父工程（mq-demo）中引入jackson依赖，用于json序列化，因为consumer和publisher都要用到：
                            <!--jackson依赖-->
                            <dependency>
                                <groupId>com.fasterxml.jackson.core</groupId>
                                <artifactId>jackson-databind</artifactId>
                            </dependency>
                        4。在Publisher服务的启动类中声明MessageConverter的Bean覆盖默认的消息转换器：（PublisherApplication）
                            /**
                             * 用Jackson2JsonMessageConverter 覆盖 MessageConverter 这个默认的消息转换器，用以json格式的序列化。
                             * @return
                             */
                            @Bean
                            public MessageConverter messageConverter() {
                                return new Jackson2JsonMessageConverter();
                            }
                            注意MessageConverter接口有很多，应该是 amqp.support.converter.MessageConverter 这个接口，别搞错了
                            此时再次在Rabbitmq控制台中查看接收到的消息就变成了json序列化的 content_type 为 application/json 的可读对象
                            然后注意在重新发送前在控制台在 purge 一下原来的消息。
                        5。在Consumer服务的启动类和Publisher启动类中做同样的操作。直接代码粘贴一份到Consumer的启动类中即可：
                        6。在Consumer的监听类中监听object.queue队列的消息：
                            /**
                             * 消息转换器演示：监听object.queue
                             *
                             * @param msg
                             */
                            @RabbitListener(queues = "object.queue")
                            public void listenObjectQueue(Map<String, Object> msg) {
                                System.out.println("消费者接收到 object.queue 的消息：【" + msg + "】");
                            }
                        












柒.elasticsearch
    
    初识elasticsearch
    索引库操作
    文档操作
    RestAPI
    
    初识elasticsearch
        了解ES
            elasticsearch是一款非常强大的开源搜索引擎，可以帮助我们从海量的数据中快速找到需要的内容。
            elasticsearch结合Kibana，Logstansh，Beats，也就是elastic stack（ELK）。被广泛应用在日志数据分析，实时监控等领域。
                Kibana是数据可视化的组件，官方提供的可以换性的组件。
                ElasticSearch是存储，计算，搜索数据的组件，不可替代的核心。
                Logstash，Beats是数据抓取的组件，官方提供的可以换性的组件。
            elasticsearch底层实现是基于lucene，lucene是一个java语言实现的搜索引擎类库，是Apache公司的顶级项目，由 DougCutting 与1999年研发。
                官网地址：https://lucene.apache.org/
                Lucene优势：
                    易扩展
                    高性能（基于倒排索引）
                Lucene劣势：
                    只限于Java语言开发
                    学习曲线陡峭
                    不支持水平扩展
            2004年 Shay Banon 基于Lucene开发了Compass
            2010年 Shay Banon 重写了Compass，取名为 ElasticSearch
                官网地址：https://www.elastic.co/cn/
            相比与lucene，elasticsearch具备以下优势：
                支持分布式，可水平扩展
                提供Restful接口，可被任何语言调用
        倒排索引
            正向索引和倒排索引：
                传统数据库（如MySql）采用正向索引：
                    基于文档ID创建索引，查询词条时必须先找到文档，而后判断是否包含词条。
                elasticsearch采用倒排索引：
                    文档（document）：每条数据就是一个文档。
                    词条（term）：文档按照语义分成的词语。
                    对文档内容分词，对词条创建索引，并记录词条所在文档的信息，查询时先根据词条查询到文档ID，而后获取到文档。
                区别：
                    正向索引是先找文档，然后判断每条文档是否符合我们的要求，查找时根据文档找词条。
                    而倒排索引是反过来的，是居于词条创建索引，然后关联这个文档，查找时根据词条找到这个文档。
        ElasticSearch的一些概念
            elasticsearch是面向文档存储的，可以是数据库中的一条商品信息，一个订单信息。
            文档数据会被序列化为json格式后存储在elasticsearch中。
            索引（index）：相同类型的文档的集合
            映射（mapping）：索引中文档的字段约束信息，类似于表的结构约束
            概念对比：
                MySQL       ==>         ElasticSearch                   说明
                Table       ==>         Index                           文档集合，类似于数据库的表
                Row         ==>         Document                        对应数据库一条条的数据，类似于数据库中的行，文档都是JSON格式
                Column      ==>         Field                           JSON文档中的字段，类似于数据库中的列
                Schema      ==>         Mapping                         Mapping是索引中文档的约束，例如字段类型约束。类似数据库的表结构（Schema）
                SQL         ==>         DSL                             DSL是elasticsearch提供的JSON风格的请求语句，用来操作elasticsearch，实现CRUD。                         
            架构：
                MySql：擅长事物类型操作，以确保数据的安全和一致性
                ElasticSearch：擅长海量数据的搜索，分析，计算。
                两者并不是替代关系，而是互补的关系。
        安装es，kibana
            ElasticSearch：
                创建网络：
                    因为我们还需要部署Kibana容器，因此需要让es和kibana容器互联。这里先创建一个网络：
                    docker network create es-net
                安装ES：（镜像非常大一个多G）
                    docker pull elasticsearch:7.17.6
                部署单点ES：
                    docker run -d \
                        --name myes \
                        -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
                        -e "discovery.type=single-node" \
                        -e "xpack.security.enabled=false" \
                        -v $PWD/es-data:/usr/share/elasticsearch/data \
                        -v $PWD/es-plugins:/usr/share/elasticsearch/plugins \
                        --privileged \
                        --network es-net \
                        -p 9200:9200 \
                        -p 9300:9300 \
                        elasticsearch:7.17.6
                    命令解读：
                        第一个 -e 表示配置JVM的堆内存大小为512，这里配置的就是ES将来运行时的内存大小，默认值为1G（如果虚拟机内存比较大就可以使用这个1G的默认值），512也不能再小了，否则会出现内存不足的问题
                        第二个 -e 表示配置运行模式为单点运行，不是集群模型。
                        第一个 -v 表示挂载的数据保存的目录
                        第二个 -v 表示挂载的插件目录，以备将来ES做拓展用。
                        --network es-net 表示让容器加入到这个网络当中
                        第一个 -p 9200是http协议的端口，供用户访问界面用的
                        第二个 -p 9300是es容器各个节点之间互联的接口
                访问：127.0.0.1:9200 返回一段json信息则表示成功访问。
            Kibana：
                Kibana 用于提供一个elasticsearch的可视化界面，便于我们操作。
                安装Kibana：
                    docker pull kibana:7.17.6
                部署：注意要和 elasticsearch 在同一个网络中（es-net）
                    docker run -d \
                        --name mykibana \
                        -e ELASTICSEARCH_HOSTS=http://myes:9200 \
                        --network=es-net \
                        -p 5601:5601  \
                        kibana:7.17.6
                    命令解读：
                        -e ELASTICSEARCH_HOSTS=http://myes:9200 表示配置 elasticsearch 的主机地址为 elasticsearch 对应的启动名称 name:端口
                        --network=es-net 注意要和 elasticsearch 在同一个网络
                        -p 5601:5601 kibana默认端口 5601
                访问：http://127.0.0.1:5601
                    进入后先选择 explore on my own
                    左侧菜单选择 dev tools
            中文分词器 IK：
                官网：https://github.com/medcl/elasticsearch-analysis-ik
                es在创建倒排索引时需要对文档分词，但是所有的分词规则对中文处理分词非常不友好，会把中文每一个字都分成一个词。
                那么此时就需要 IK 分词器来此中文分词做支持。
                安装：
                    从官网下载 ik 安装包 解压到 es-plugin 挂载目录，重启docker即可。
                    注意 elasticsearch，kibana，ik的版本号都是对应的。
                重启 es：
                    docker restart myes
                查看日志信息：
                    docker logs -f myes
                    搜索 loaded plugin 关键字 确认加载了 analysis-ik 则插件加载成功。
                此时回到kibana控制台，对中文分词进行测试：
                    // 测试分词器
                    GET /_analyze
                    {
                        "analyzer": "ik_max_word",
                        "text": "李晨泽是白嫖的大神，奥力给！！！"
                    }
                    IK分词器包含两种模式：
                        ik_smart：最少切分
                        ik_max_word：最细切分
                    两种分词器的选择根据内存占用，执行效率以及搜索概率这些应用场景下自行恒定。
                    ik_max_word分词更多，粒度更细，搜索概率更高，同时内存占用也更高。
                    ik_smart分词较少，内存占用少，搜索概率较低。
            IK分词器拓展和停用词典：
                IK分词器分词的原理就是依赖底层的一些词库进行匹配。它不会匹配到一些比如"白嫖"，"奥力给"这些新兴的网络用语。
                所以有些词是需要拓展的，修改IK分词器config目录中的 IKAnalyzer.cfg.xml文件：
                    <!--用户可以在这里配置自己的扩展字典 -->
                    <entry key="ext_dict">ext.dic</entry>
                     <!--用户可以在这里配置自己的扩展停止词字典-->
                    <entry key="ext_stopwords">stopword.dic</entry>
                ext.dic 和 stopword.dic 是两个文件，拓展词和停止词都是从这两个文件中读取。
                这里添加两个白嫖和奥力给的拓展词，并且将"的"这个字加入到停止词中。
                重启es
                    docker restart myes
                再次测试
                    "text": "李晨泽是白嫖的大神，奥力给！！！" 这段文本，就已经将白嫖和奥力给成功匹配为一个词，并且没有匹配到"的"这个停止词
                更多用法探索请移步官网查询。
        搜索库操作：
            mapping：映射属性
                mapping映射是对索引库中文档的约束，常见的mapping属性包括：
                    type：字段数据类型，常见的简单类型有：
                        字符串：text（可分词的文本），keyword（精确值，例如：品牌，国家，ip地址）
                        数值：long，integer，short，byte，double，float
                        布尔：boolean
                        日期：date
                        对象：object
                        注意：es中没有数组类型，但可以有多个值，比如：score:[99,98,12]，它实际上是具有多个值的数值类型
                    index：是否创建索引，默认为true，如果为false就不会创建倒排索引。
                    analyzer：指定使用哪种分词器，（一般只有字符串类型的 text 需要分词，其他类型都无需分词）
                    properties：表示一个字段的子字段，即object的子属性
                文档查阅地址：https://www.elastic.co/guide/en/elasticsearch/reference/current/elasticsearch-intro.html
            创建索引库：
                解释：ES中通过Restful请求操作索引库，文档。请求内容用DSL语句来表示。
                语法：
                    PUT /索引库名
                    { DSL... }
                示例：
                    # 创建索引库
                    PUT /panda
                    {
                        "mappings": {
                            "properties": {
                                "info": {
                                    "type": "text",
                                    "analyzer": "ik_smart"
                                },
                                "email":{
                                    "type": "keyword",
                                    "index": false,
                                },
                                "name":{
                                    "type":"object",
                                    "properties": {
                                        "firstName":{
                                            "type": "keyword",
                                        },
                                        "lastName":{
                                            "type": "keyword",
                                        },
                                    },
                                }
                            }
                        }
                    }
            删除索引库：
                语法
                    DELETE /索引库名
                示例：
                    DELETE /panda
            查看索引库：
                语法：
                    GET /索引库名
                示例：
                    GET /panda
            修改索引库：
                解释：
                    在ES中是禁止修改索引库的，因为创建时ES基于mapping创建倒排索引，如果修改一个字段后，就会导致原有的倒排索引失效。
                    但是ES是允许添加新的字段。
                语法：
                    PUT /索引库名/_mapping
                    { DSL... }
                示例：
                    PUT /panda/_mapping
                    {
                        "properties": {
                            "age": {
                                "type": "integer"
                            }
                        }
                    }
                注意：修改绝对不能和之前的字段重复，如果重复的话，就会意外在修改之前的字段，就会报错，因为ES是禁止修改原索引库字段的。
                总结：索引库修改操作只能添加字段，而不能修改之前的字段。
            
        文档操作：
            新增文档：
                语法：
                    POST /索引库名/_doc/文档ID
                    { DSL... }
                示例：
                    POST /panda/_doc/1
                    {
                        "info": "李晨泽是白嫖的大神，奥力给！！！",
                        "email": "leeczyc@gmail.com",
                        "name": {
                            "firstName": "云",
                            "lastName": "赵"
                        }
                    }
            查看文档：
                语法：GET /索引库名/_doc/文档ID
                示例：GET /panda/_doc/1
            删除文档：
                语法：DELETE /索引库名/_doc/文档ID
                示例：DELETE /panda/_doc/1
            修改文档：
                全量修改：
                    解释：删除旧文档，添加新文档。
                    语法：
                        PUT /索引库名/_doc/文档ID
                        { DSL... }
                    示例：
                        PUT /panda/_doc/1
                        {
                            "info": "特朗普是白嫖的大神，奥力给！！！",
                            "email": "leeczyc@gmail.com",
                            "name": {
                                "firstName": "云",
                                "lastName": "赵"
                            }
                        }
                    注意：这种方式逻辑：先把原来的文档干掉，再添加全新的文档进去，所以它既能做修改，也能做新增。
                增量修改：
                    解释：修改指定字段。
                    语法：
                        POST /索引库名/_update/文档ID
                        { DSL... }
                    示例：
                        POST /panda/_update/1
                        {
                            "doc": {
                                "info": "奥巴马是白嫖的大神，奥力给！！！",
                            }
                        }
        RestClient操作索引库：（hotel-es-demo）
            RestClient就是ES官方提供的各种语言的客户端，用来操作ES，这些客户端的本质就是组装DSL语句。                
            文档地址：https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/introduction.html
            案例：根据 /day5/tb_hotel.sql 中的酒店数据创建索引库，索引库名为hotel，mapping属性根据数据库结构定义
            操作步骤：
                导入Demo
                分析数据结构，定义mapping属性
                初始化JavaRestClient
                使用JavaRestClient创建，删除和判断索引库。
            
            分析数据结构：
                mapping要考虑的问题：
                    字段名，数据类型，是否参与搜索，是否分词，如果分词，分词器是什么？
                ES中支持两种地理坐标数据类型：
                    geo_point 类型：由精度和纬度确定的一个点，例如："121.5748384, 37.3458234"
                    geo_shape 类型：有多个geo_point组成的几何图层，例如："LINESTRING(121.5748384 37.3458234, 121.1100233 37.8834349)"
                如果查询时需要根据多个字段进行查询时，且都必须是参与搜索的（index为true），可以使用copy_to 属性将当前字段拷贝到指定字段，例如：
                    "all": {
                        "type": "text",
                        "analyzer": "ik_max_word"
                    },
                    "brand": {
                        "type": "keyword",
                        "copy_to": "all"
                    }
                    这样就可以在一个字段中搜索到多个字段的内容了。
            初始化JavaRestClient：
                引入ES的RestHighLevelClient依赖：
                    <dependency>
                        <groupId>org.elasticsearch.client</groupId>
                        <artifactId>elasticsearch-rest-high-level-client</artifactId>
                    </dependency>
                因为SpringBoot默认的ES版本就是7.17.9，所以我们需覆盖默认的ES版本为7.17.6，ES版本和Rest的依赖需要保持一致：
                    <properties>
                        <java.version>1.8</java.version>
                        <elasticsearch.version>7.17.6</elasticsearch.version>
                    </properties>
                初始化RestHighLevelClient：（HotelIndexTest）
                    void setUp() {
                        client = new RestHighLevelClient(RestClient.builder(
                            HttpHost.create("http://127.0.0.1:9200")
                        ));
                    }
            创建索引库：(HotelIndexTest)
                // 1.准备Request      PUT /hotel
                CreateIndexRequest request = new CreateIndexRequest("hotel");
                // 2.准备请求参数
                request.source(MAPPING_TEMPLATE, XContentType.JSON);
                // 3.发送请求
                client.indices().create(request, RequestOptions.DEFAULT);
                
                执行成功后在 5601 端口的控制台上查询 hotel 库：
                    GET /hotel
                    查到结果，成功创建
            删除索引库：(HotelIndexTest)
                // 1.准备Request
                DeleteIndexRequest request = new DeleteIndexRequest("hotel");
                // 2.发送请求
                client.indices().delete(request, RequestOptions.DEFAULT);
            判断索引库是否存在：(HotelIndexTest)
                // 1.准备Request
                GetIndexRequest request = new GetIndexRequest("hotel");
                // 2.发送请求
                boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);
                // 3.输出
                System.out.println(isExists ? "hotel索引库已存在" : "hotel索引库不存在");
            
        RestClient操作文档：
            利用JavaRestClient实现文档的CRUD
            去数据库查询酒店数据，导入到hotel索引库，实现酒店数据的CRUD。
            中间需要从数据库获取数据，转换成索引库所需要的格式，然后写到索引库。

            新增酒店数据：(HotelDocumentTest)
                // 根据ID查询酒店数据
                Hotel hotel = hotelService.getById(36934L);
                // 转换为文档类型
                HotelDoc hotelDoc = new HotelDoc(hotel);
                // 准备Request对象
                IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
                // 准备Json DSL文档
                request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
                // 发送请求
                client.index(request, RequestOptions.DEFAULT);
            根据ID查询酒店数据：(HotelDocumentTest)
                注意：根据ID查询的文档数据是JSON，需要反序列化为java对象。
                // 准备Request对象
                GetRequest request = new GetRequest("hotel", "36934");
                // 发送请求，获取响应
                GetResponse response = client.get(request, RequestOptions.DEFAULT);
                // 解析响应结果
                String json = response.getSourceAsString();
                // 反序列化JSON对象
                HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
                // 输出
                System.out.println(hotelDoc);
            修改酒店数据：(HotelDocumentTest)
                全量更新：再次写入ID一样的文档，就会删除旧文档，添加新文档。
                局部更新：只更新部分字段。
                    // 准备Request对象
                    UpdateRequest request = new UpdateRequest("hotel", "36934");
                    // 准备请求参数
                    request.doc("price", "633", "starName", "三钻");
                    // 发送请求
                    client.update(request, RequestOptions.DEFAULT);
            删除酒店数据：(HotelDocumentTest)
                // 准备Request对象
                DeleteRequest request = new DeleteRequest("hotel", "36934");
                // 发送请求
                client.delete(request, RequestOptions.DEFAULT);
            批量导入文档：(HotelDocumentTest.testBulkRequest)
                需求：批量查询酒店数据，批量导入索引库中。
                思路：
                    1。利用mybatis-plus查询酒店数据
                    2。将查询到的酒店数据（Hotel）转换为文档类型数据（HotelDoc）
                    3。利用JavaRestClient中的Bulk批处理，实现批量新增文档。
                实例代码：
                    在ES控制台通过 GET /hotel/_search 命令查看

        DSL操作：
            DSL查询文档：
                查询分类：
                    官网地址：https://www.elastic.co/guide/en/elasticsearch/reference/7.17/query-filter-context.html
                    ElasticSearch提供了基础的JSON的DSL（Domain Specific Language）来定义查询。常见的查询类型包括：
                        查询所有：查询所有数据，一般测试用，但是会有分页的现在。例如：
                            match_all
                        全文检索查询（full text）：利用分词器对用户输入内容分词，然后去倒排索引库中匹配。例如：
                            match_query：                 
                            multi_match_query：              
                        精确查询：根据精确词条值查找数据，一半是查找keyword，数值，日期，booelan等类型字段。例如：
                            ids
                            range
                            term
                        地理查询（geo）：根据经纬度查询，例如：
                            geo_distance
                            geo_bounding_box
                        复合查询（compound）：将上述各种查询条件组合在一起，合并查询条件，例如：
                            bool    
                            functino_score    
                    查询基本语法：
                        GET /indexName/_search
                        {
                            "query": {
                                "查询类型": {
                                    "查询条件": "条件值"
                                }
                            }
                        }
                        示例：
                            GET /hotel/_search
                            {
                                "query": {
                                    "match_all": { ... 条件 ... }
                                }
                            }
                全文检索查询：
                    match：全文检索查询的一种，会对用户输入内容分词，然后去倒排索引库检索，语法：
                        GET /hotel/_search
                        {
                            "query":{
                                "match": {
                                    "all": "外滩如家"
                                }
                            }
                        }
                    multi_match：与match查询类似，只不过允许同时查询多个字段，语法：
                        GET /hotel/_search
                        {
                            "query": {
                                "multi_match": {
                                    "query": "外滩如家",
                                    "fields": [
                                        "name",
                                        "brand",
                                        "business"
                                    ]
                                }
                            }
                        }
                    以上例子的结果是一样的，因为 all 就是拷贝的 name，brand，business字段，这种情况推荐使用all查询，因为只查询一个all字段效率高，或者说查询的字段越多查询性能越差。
                精确查询：
                    解释：精确查询一般是查找keyword，数值，日期，boolean等类型字段，并不会对搜索条件进行分词，常见的有：
                    term：根据词条精确值查询，语法：
                        GET /hotel/_search
                        {
                            "query": {
                                "term": {
                                    "city": {
                                        "value": "上海"
                                    }
                                }
                            }
                        }
                    range：根据值范围查询，语法：
                        GET /hotel/_search
                        {
                            "query": {
                                "range": {
                                    "price": {
                                        "gte": 3000,
                                        "lte": 5000
                                    }
                                }
                            }
                        }
                地理坐标查询：
                    geo_bounding_box：查询bounds矩形范围内的所有geo_point的文档，语法：
                        GET /hotel/_search
                        {
                            "query": {
                                "geo_bounding_box": {
                                    "location": {
                                        "top_left": {
                                            "lat": 31.1,
                                            "lon": 121.5
                                        },
                                        "bottom_right": {
                                            "lat": 30.9,
                                            "lon": 121.7
                                        }
                                    }
                                }
                            }
                        }
                    geo_distance：查询到指定中心点小于某个距离值的所有文档，语法：
                        GET /hotel/_search
                        {
                            "query": {
                                "geo_distance": {
                                    "distance": "5km",
                                    "location": "31.21,121.5"
                                }
                            }
                        }
                组合查询：
                    解释：组合查询亦称复合查询，可以将其他简单查询组合起来，实现更复杂的搜索逻辑。
                    相关性算分算法：
                        TF-IDF：在ES5.0版本之前使用的算法，会随着词频增加而越来越大。
                        BM25：在ES5.0版本之后使用的算法，会随着词频的增加而增大，但增长曲线会趋于水平。
                    Function Score Query：算分函数查询，可以控制文档相关性算分，控制文档排名。例如百度竞价：
                        解释：可以修改文档的相关性算分（query score），根据新得到的算分排序：
                        # 原始查询条件，搜索文档并根据相关性打分（query score）
                        # 过滤条件，符合条件的文档才会被重新算分
                        # 算分函数，算分函数的结果称为 function score，后续会与query score运算，得到新算分，常见的算分函数有：
                            weight：给一个常量值，作为函数结果（function score）
                            field_value_factor：用文档中的某个字段值作为函数结果。
                            random_score：随机生成一个值，作为函数结果。
                            script_score：自定义计算公司，公式结果作为函数结果。
                        # 加权模式，定义function score与query score的运算方式，包括：
                            multiply：两者想乘，默认值。
                            replace：用function score 替换 query score
                            sum：相加
                            avg：平均值
                            max：最大值
                            min：最小值
                        
                        需求：将如家这个品牌的酒店排名考前一些：
                            哪些文档需要计算分权？
                                品牌为如家的酒店。
                            算分函数是什么？
                                weight即可。
                            加权模式是什么？
                                求和。
                        DSL语句：
                            GET /hotel/_search
                            {
                            "query": {
                                "function_score": {
                                    # 原始查询条件
                                    "query": {
                                        "match": {
                                            "all": "外滩"
                                        }
                                    },
                                    "functions": [
                                        {
                                            # 过滤条件
                                            "filter": {
                                                "term": {
                                                    "brand": "如家"
                                                }
                                            },
                                            # 算分函数
                                            "weight": 10
                                        }
                                    ],
                                    # 加权模式
                                    "boost_mode": "sum"
                                    }
                                }
                            }
                    Boolean Query：布尔查询是一个或多个查询子句的组合。
                        子查询的组合方式有：
                            must：必须匹配每个子查询，类似"与"
                            should：选择性匹配子查询，类似"或"
                            must_not：必须不匹配，不参与算分，类似"非"
                            filter：必须匹配，不参与算分
                        以上参与算分的must和should一般是用来匹配关键字的，
                        其余不参与算分的都应该放在must_not和filter中，尽可能减少算分，提高查询效率
                        需求：
                            搜索名字包含"如家"，价格不高于400，在坐标 31.21,121.5 周围 10km 范围内的酒店
                        DSL：
                            GET /hotel/_search
                            {
                                "query": {
                                    "bool": {
                                        "must": [
                                            {
                                                "match": {
                                                    "name": "如家"
                                                }
                                            }
                                        ],
                                        "must_not": [
                                            {
                                                "range": {
                                                    "price": {
                                                        "gt": 400
                                                    }
                                                }
                                            }
                                        ],
                                        "filter": [
                                            {
                                                "geo_distance": {
                                                    "distance": "10km",
                                                    "location": {
                                                        "lat": 31.21,
                                                        "lon": 121.5
                                                    }
                                                }
                                            }
                                        ]
                                    }
                                }
                            }
            搜索结果处理：
                排序：
                    ElasticSearch支持对搜索结果排序，默认是根据相关度算分（_score）来排序。可以排序字段类型有：keyword类型，数值类型，地理坐标类型，日期类型等。
                    如果一旦自定义了排序方式，那么ES就不会再根据相关度算分进行排序了，所以_score的值会为null。
                    需求：
                        对搜索结果进行排序，规则为：按评分降序，按价格升序。
                    DSL：
                        降序：desc
                        升序：asc
                        GET /hotel/_search
                        {
                            "query": {
                                "match_all": {}
                            },
                            "sort": [
                                {
                                    "score": "desc"
                                },
                                {
                                    "price": "asc"
                                }
                            ]
                        }
                    需求：
                        搜索坐标为121.517064,31.241675的附近酒店进行升序排序
                    DSL：
                        GET /hotel/_search
                        {
                            "query": {
                                "match_all": {}
                            },
                            "sort": [
                                {
                                    "_geo_distance": {
                                        "location": {
                                            "lat": 31.241675,
                                            "lon": 121.517064
                                        },
                                        "order": "asc",
                                        "unit": "km"
                                    }
                                }
                            ]
                        }
                分页：
                    ElasticSearch默认情况下只返回top10的数据，而如果要查询更多数据就要修改分页参数了。
                    ElasticSearch通过from，size参数来控制要返回的分页结果：
                    分页方式：
                        from+size：
                            优点：支持随机分页
                            缺点：深度分页问题，默认查询分页上线（from+size）上限是10000条，超出就会报错。
                            场景：百度，京东，谷歌，淘宝这样的随机翻页搜索
                        after search：
                            优点：没有查询上限（单次查询的size不超过10000）
                            缺点：只能向后逐页查询，不支持随机翻页
                            场景：没有随机翻页需求的搜索，例如手机向下滚动翻页
                        scroll：
                            优点：没有查询上限（单次查询的size不超过10000）
                            缺点：会有额外的内存消耗，并且搜索结果都是非实时的
                            场景：海量数据的获取和迁移，从ES7.1开始不推荐，建议用after search方案。
                    from+size方式查询的 DSL：
                        GET /hotel/_search
                        {
                            "query": {
                                "match_all": {}
                            },
                            "sort": [
                                {
                                    "price": {
                                        "order": "asc"
                                    }
                                }
                            ],
                            "from": 9999,
                            "size": 20
                        }
                高亮：
                    就是把搜索结果的关键字突出显示出来
                    原理：
                        将搜索结果中的关键字用标签标记出来。
                        在页面中给标签添加css样式。
                    DSL：
                        GET /hotel/_search
                        {
                            "query": {
                                "match": {
                                    "all": "如家"
                                }
                            },
                            "highlight": {
                                "fields": {
                                    "name": {
                                        "require_field_match": "false"
                                    }
                                }
                            }
                        }
                    解释：
                        最终的返回结果字段会多一个highlight其中就是高亮显示的结果值，将原字段即可替换。
        RestClient查询文档：        
            快速入门：  
                
            match查询：
            精确查询：        
            复合查询：        
            排序：
            分页：
            高亮：
            黑马旅游案例：
            
            














捌.
















玖.
















拾.















