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
            快速入门：（HotelSearchTest.testMatchAll）       
                通过match_all来演示下基本的API：
                    @Test
                    void testMatchAll() throws IOException {
                        // 1.准备Request
                        SearchRequest request = new SearchRequest("hotel");
                        // 2.准备DSL
                        request.source().query(QueryBuilders.matchAllQuery());
                        // 3.发送请求
                        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                        System.out.println(response);
                        // 4.解析响应
                        SearchHits searchHits = response.getHits();
                        // 5.获取总条数
                        long total = searchHits.getTotalHits().value;
                        System.out.println("共搜索到" + total + "条数据");
                        // 6.获取文档数组
                        SearchHit[] hits = searchHits.getHits();
                        for (SearchHit hit : hits) {
                            // 获取文档
                            String json = hit.getSourceAsString();
                            // 反序列化
                            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
                            System.out.println("hotelDoc = " + hotelDoc);
                        }
                    }
                RestAPI中构建DSL是通过HighLevelRestClient中的source()类来实现的，其中包含了查询，排序，分页高亮等功能。
                RestAPI中构建查询条件的核心部分是由一个名为QueryBuilders的工具类提供的，其中包含了检索查询，精确查询，地理坐标查询，组合查询等多种查询方法。
                
            全文检索查询：（HotelSearchTest.testMatch）      
                全文检索的match和multi_match查询与match_all的API基本一致，差别是查询条件，也就是query部分。
                match查询和multi_match查询：
                    @Test
                    void testMatch() throws IOException {
                        // 1.准备Request
                        SearchRequest request = new SearchRequest("hotel");
                        // 2.准备DSL
                        // 2.1.但字段查询
                        // request.source().query(QueryBuilders.matchQuery("all", "如家"));
                        // 2.2.多字段查询
                        request.source().query(QueryBuilders.multiMatchQuery("如家", "name", "business"));
                        // 3.发送请求
                        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                        System.out.println(response);
                        // 4，5，6（解析相关操作，抽取为了公共方法）
                        extractedResolution(response);
                    }
            精确查询：
                精确查询常见的有term（词条查询）和range（范围查询），同样利用QueryBuilders实现：
                词条查询：
                    QueryBuilders.termQuery("city","杭州");
                范围查询：
                    QueryBuilders.rangeQuery("price").gte(100).lte(300);
            复合查询：（HotelSearchTest.testBool）       
                布尔查询：
                    // 创建布尔查询
                    BoolQueryBuilders boolQuery = QueryBuilders.boolQuery();
                    // 添加must条件
                    boolQuery.must(QueryBuilders.termQuery("city","杭州"));
                    // 添加filter条件
                    boolQuery.filter(QueryBuilders.rangeQuery("price").lte(250));
                总结：要构建查询条件，只要牢记一个类：QueryBuilders。
            排序：（HotelSearchTest.testPageAndSort）      
                request.source().from(0).size(5)
            分页：（HotelSearchTest.testPageAndSort）      
                request.source().sort("price", SortOrder.ASC)
            高亮：
                高亮API包括请求DSL构建和结果解析两部分：
                    DSL构建：HotelSearchTest.testHighLight）
                        // 2.准备DSL
                        // 2.1.查询（因为是关键字高亮，所以不能使用matchAll，这里就用match查询，指定具体的要高亮的关键字）
                        request.source().query(QueryBuilders.matchQuery("all", "如家"));
                        // 2.1.高亮
                        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false).preTags("<em>").postTags("</em>"));
                    DSL解析：（HotelSearchTest.extractedResolution）
                        // 获取高亮结果
                        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                        if(!CollectionUtils.isEmpty(highlightFields)){
                            // 根据字段名获取高亮结果
                            HighlightField highlightField = highlightFields.get("name");
                            if(highlightField != null) {
                                // 获取高亮值（一个string的数组）
                                String name = highlightField.getFragments()[0].string();
                                // 覆盖非高亮结果
                                hotelDoc.setName(name);
                            }
                        }
                总结：request.source() 就相当于 DSL 中最外层的花括号。
                
            黑马旅游案例：
                基本搜索和分页：
                    定义接收前端请求参数的类：（pojo.RequestParams）
                        @Data
                        public class RequestParams {
                            private String key;
                            private Integer page;
                            private Integer size;
                            private String sortBy;
                        }
                    定义返回前端数据的类：（pojo.PageResult）
                        @Data
                        public class PageResult {
                            private Long total;
                            private List<Hotel> hotels;
                        }
                    定义controller接口，接收前端请求：（controller.HotelController）
                        @RestController
                        @RequestMapping("/hotel")
                        public class HotelController {
                            @Autowired
                            private IHotelService hotelService;
                            
                            @PostMapping("/list")
                            public PageResult search(@RequestBody RequestParams params) {
                                System.out.println(hotelService.search(params));
                                return hotelService.search(params);
                            }
                        }
                    在启动类（配置类）中将 RestHighLevelClient 声明为Bean：（HotelEsDemoApplication）
                    定义service层：（service.impl.HotelService）
                        详情请看：service.impl.HotelService
                        代码量有点多这里，就不再这里展示了。
                    此时访问 http://localhost:8089/# 即可成功操作搜索和分页
                    
                添加品牌，城市，星级，价格等过滤功能：
                    修改RequestParams类，添加brand，city，starName，minPrice，maxPrice等参数
                        @Data
                        public class RequestParams {
                            private String key;
                            private Integer page;
                            private Integer size;
                            private String sortBy;
                            private String city;
                            private String brand;
                            private String starName;
                            private Integer minPrice;
                            private Integer maxPrice;
                        }
                    修改search方法的实现，在关键字搜索时，如果brand等参数存在，对其做过滤（HotelService）
                        @Override
                        public PageResult search(RequestParams params) {
                            try {
                                // 1.准备Request
                                SearchRequest request = new SearchRequest("hotel");
                                // 2.准备DSL
                                // 基础查询条件方法
                                buildBasicQuery(params, request);
                                // 分页
                                int page = params.getPage();
                                int size = params.getSize();
                                request.source().from((page - 1) * size).size(size);
                                // 3.发送请求
                                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                                System.out.println(response);
                                // 4，5，6（解析相关操作，抽取为了公共方法）
                                return extractedResolution(response);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                实现我附近的酒店：    
                    修改RequestParams类，添加location字段：
                        @Data
                        public class RequestParams {
                            private String key;
                            private Integer page;
                            private Integer size;
                            private String sortBy;
                            private String city;
                            private String brand;
                            private String starName;
                            private Integer minPrice;
                            private Integer maxPrice;
                            private Integer location;
                        }
                    修改search方法的业务逻辑，如果location有值，添加根据geo_distance排序的功能：（HotelService）
                        @Override
                        public PageResult search(RequestParams params) {
                            try {
                                // 1.准备Request
                                SearchRequest request = new SearchRequest("hotel");
                                // 2.准备DSL
                                // 基础查询条件方法
                                buildBasicQuery(params, request);
                                // 分页
                                int page = params.getPage();
                                int size = params.getSize();
                                request.source().from((page - 1) * size).size(size);
                                // 排序
                                String location = params.getLocation();
                                if (location != null && !location.equals("")) {
                                    request.source().sort(
                                        SortBuilders
                                        .geoDistanceSort("location", new GeoPoint(location))
                                        .order(SortOrder.ASC)
                                        .unit(DistanceUnit.KILOMETERS)
                                    );
                                }
                                // 3.发送请求
                                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                                System.out.println(response);
                                // 4，5，6（解析相关操作，抽取为了公共方法）
                                return extractedResolution(response);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    给HotelDoc返回值的类添加 distance
                        private Object distance;
                广告指定功能实现：
                    实现：给需要置顶的酒店文档添加一个标记，然后利用function score给带有标记的文档增加权重。
                    给HotelDoc类添加isAD字段，Boolean类型。
                        private Boolean isAD;
                    挑选几个喜欢的酒店，给他的文档数据添加isAD字段，值为true。
                        在ElasticSearch控制台中给几条数据添加isAD字段。
                        # 添加isDA字段,方便置顶
                        POST /hotel/_update/56201
                        {
                            "doc": {
                                "isAD": true
                            }
                        }
                        POST /hotel/_update/56392
                        {
                            "doc": {
                                "isAD": true
                            }
                        }
                        POST /hotel/_update/60214
                        {
                            "doc": {
                                "isAD": true
                            }
                        }
                        POST /hotel/_update/60223
                        {
                            "doc": {
                                "isAD": true
                            }
                        }
                    修改search方法，添加function score功能，给isAD值为true的酒店增加权重。
                        private void buildBasicQuery(RequestParams params, SearchRequest request) {
                            // 算分控制：function score给isAD（广告字段）添加权重。
                            FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(
                                    // 原始查询，做相关性算分的查询。
                                    boolQuery,
                                    // function score的数组
                                    new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                                            // 其中的一个Function Score元素
                                            new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                                    // 过滤条件
                                                    QueryBuilders.termQuery("isAD", true),
                                                    // 算分函数
                                                    ScoreFunctionBuilders.weightFactorFunction(10)
                                            )
                                    }
                            );
                            // 执行查询
                            request.source().query(functionScoreQuery);
                        }

        数据聚合：         
            聚合的分类：
                官网文档：https://www.elastic.co/guide/en/elasticsearch/reference/7.11/search-aggregations-bucket.html
                聚合（aggregations）可以实现对文档数据的统计，分析，运算，聚合常见的有三类：
                    桶（Bucket）聚合：用来对文档做分组：
                        TermAggregation：按照文档字段值分组
                        Date Histogram：按照日期阶梯分组，例如一周为一组，或者一月为一组
                    度量（Metric）聚合：用以计算一些值，比如：最大值，最小值，平均值等。
                        Avg：平均值
                        Max：最大值
                        Min：最小值
                        Stats：同时求max，min，avg，sum等。
                    管道（pipeline）聚合：其他聚合的结果为基础做聚合
                参与聚合的字段类型必须是：
                    keyword，数值，日期，布尔
            实现Bucket聚合：
                需求：统计数据中的酒店品牌有几种，此时可以根据酒店品牌名称做聚合：
                DSL：
                    GET /hotel/_search
                    {
                        "size": 0, # 设置size为0，结果中不包含文档，只包含聚合结果，即hits数据应为空。
                        "aggs": { # 定义聚合
                            "brandAgg": { # 定义聚合的名称
                                "terms": { # 聚合的类型，按照品牌值聚合，所以用term
                                    "field": "brand", # 参与聚合的字段
                                    "size": 20 # 希望获取的聚合结果数量
                                    "order": {
                                      "_count": "asc" # 按着_count升序排序
                                    }
                                }
                            }
                        }
                    }
                详见控制台的相关演示：桶聚合,基础
            实现Metrics聚合：
                需求：例如，我们要求获取每个品牌的用户评分为min，max，avg等值：
                DSL：
                    GET /hotel/_search
                    {
                        "size": 0,
                        "aggs": {
                            "brandAgg": {
                                "terms": {
                                    "field": "brand",
                                    "size": 10,
                                    "order": {
                                        "scoreAgg.avg": "desc"
                                    }
                                },
                                "aggs": { # brands聚合的子聚合，也就是分组后对每组分别计算
                                    "scoreAgg": { # 聚合名称
                                        "stats": { # 聚合类型，这里stats可以计算min，max，avg等
                                            "field": "score" # 聚合字段，这里对score字段做聚合
                                        }
                                    }
                                }
                            }
                        }
                    }
                详见控制台的相关演示：度量聚合,利用stats聚合
            RestClient实现聚合：（HotelSearchTest.testAggregation）
                @Test
                void testAggregation() throws IOException {
                    // 准备Request
                    SearchRequest request = new SearchRequest("hotel");
                    // 准备DSL
                    // 设置size，表示不要文档结果，只要聚合结果
                    request.source().size(0);
                    // 聚合
                    request.source().aggregation(
                            AggregationBuilders
                                    .terms("brandAgg")
                                    .field("brand")
                                    .size(10)
                    );
                    // 发出请求
                    SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                    // 解析结果
                    Aggregations aggregations = response.getAggregations();
                    // 根据聚合名称获取聚合结果
                    Terms aggregation = aggregations.get("brandAgg");
                    // 获取buckets
                    List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
                    // 遍历
                    for (Terms.Bucket bucket : buckets) {
                        // 获取key
                        String key = bucket.getKeyAsString();
                        System.out.println(key);
                    }
                }
            多条件聚合：
                案例：在IUserServices中定义方法，实现对品牌，城市，星级的聚合：
                需求：搜索页面中，品牌，城市等信息不应该是在页面写死的，而是通过聚合索引库中的酒店数据得来的：
                实现步骤：
                    在IHotelService中定义一个filters接口：
                    在HotelService中实现filters接口：
                        @Override
                        public Map<String, List<String>> filters() {
                            try {
                                // 准备Request
                                SearchRequest request = new SearchRequest("hotel");
                                // 准备DSL
                                // 设置size，表示不要文档结果，只要聚合结果
                                request.source().size(0);
                                // 聚合
                                request.source().aggregation(
                                        AggregationBuilders
                                                .terms("brandAgg")
                                                .field("brand")
                                                .size(10)
                                );
                                request.source().aggregation(
                                        AggregationBuilders
                                                .terms("cityAgg")
                                                .field("city")
                                                .size(10)
                                );
                                request.source().aggregation(
                                        AggregationBuilders
                                                .terms("starNameAgg")
                                                .field("starName")
                                                .size(10)
                                );
                                // 发出请求
                                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                                // 定义Map
                                Map<String, List<String>> result = new HashMap<>();
                                // 解析结果
                                Aggregations aggregations = response.getAggregations();
                                // 根据名称获取品牌的结果
                                List<String> brandList = getAggByName(aggregations, "brandAgg");
                                // 将聚合结果put到Map中
                                result.put("品牌", brandList);
                                // 城市和星级亦同（复制粘贴）
                                List<String> cityList = getAggByName(aggregations, "cityAgg");
                                result.put("城市", cityList);
                                List<String> starNameList = getAggByName(aggregations, "starNameAgg");
                                result.put("星级", starNameList);
                                // 结果返回
                                return result;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    在HotelEsDemoApplicationTests中对service层的filters方法进行测试：
                        @SpringBootTest
                        public class HotelEsDemoApplicationTests {
                            @Autowired
                            private IHotelService hotelService;
                            @Test
                            void contextLoads(){
                                Map<String, List<String>> filters = hotelService.filters();
                                System.out.println(filters);
                            }
                        }
            带过滤条件的聚合：
                需求：实现多条件聚合的接口：
                实现步骤：
                    编写controller接口，接收该请求：（HotelController.getFilters）
                        @PostMapping("/filters")
                        public Map<String, List<String>> getFilters(@RequestBody RequestParams params) {
                            return hotelService.filters(params);
                        }
                    修改IUserService#getFilters()方法，添加RequestParam参数：
                        Map<String, List<String>> filters(RequestParams params);
                    修改getFilters()方法的业务，聚合时添加query条件：(HotelService.filters)
                        给filter定义一个接收参数，执行一下之前封装的基础查询条件方法即可，用来限制聚合的范围。
                        buildBasicQuery(params, request);

        自动补全：（所有ES控制台的DSL语句都在 lib/day5/es_console_dsl.jsonc 文件中）
            安装拼音分词器：
                要实现根据字母做补全，就必须对文档按照拼音分词，在GitHub上又一个对ES的拼音分词器插件
                地址：https://github.com/medcl/elasticsearch-analysis-pinyin
                安装步骤：和IK分词器安装步骤相同
                    解压
                    上传到elasticsearch的es-plugins目录
                    重启elasticsearch
                    在ES控制台测试
                        DSL：
                            POST /_analyze
                            {
                                "text": ["如家酒店还不错"],
                                "analyzer": "pinyin"
                            }
            自定义分词器：
                官网参考：https://github.com/medcl/elasticsearch-analysis-pinyin
                elasticsearch中分词器（analyzer）的组成包括三部分：
                    character filters：在tokenizer之前对文本进行处理。例如删除字符，替换字符
                    tokenizer：将文本按照一定的规则切割成词条（term），例如keyword，就是不分词，还有ik_smart
                    tokenizer filter：将tokenizer输出的词条做进一步处理。例如大小写转换，同义词处理，拼音处理等。
                示例：
                    原文本：四级考试通过了:)
                    character filter：四级考试通过了开心
                        :) ==> 开心
                        :( ==> 伤心
                    tokenizer：四级，考试，通过，开心
                        （通过ik_smart分词处理）
                    tokenizer filter：siji，kaoshi，tongguo，kaixin
                        （通过pinyin进行分词处理）
                DSL：
                    详见ES控制台：==========自定义分词器==========
                问题：评分分词器适合在创建倒排索引时使用，但不能在搜索的时候使用。
                    如ES控制台中：如果在搜索时使用，对搜索方式为my_analyzer的搜索进行测试
                    这段文本是无法对狮子和虱子两个字进行辨别。
                解决：在创建时和搜索时用不同的分词器：
                    在指定mapping映射时指定两个，analyzer是在创建索引时用的，search_analyzer是在搜索时用的。
                    DSL：详见==========自定义分词器==========的test索引库创建部分
                        "name": {
                            "type": "text",
                            "analyzer": "my_analyzer",
                            "search_analyzer": "ik_smart"
                        }
                    此时在控制台中执行，可以看到只有狮子这一个关键字匹配到了
                        GET /test/_search
                        {
                            "query": {
                                "match": {
                                    "name": "掉入狮子笼咋办"
                                }
                            }
                        }
            DSL实现自动补全：
                completion suggester查询
                elasticsearch提供了 completion suggester查询来实现自动补全功能。这个查询会匹配以用户输入内容开头的词条并返回。
                为了提高补全查询的效率，对于文档中字段的类型有一些约束：
                    参与补全查询的字段必须是completion类型。
                    字段的内容一般是用来补全的多个词条形成的数组
                查询语法如下：
                    # ==========自动补全的索引库==========
                    PUT test2
                    {
                        "mappings": {
                            "properties": {
                                "title":{
                                    "type": "completion"
                                }
                            }
                        }
                    }
                    # 插入示例数据
                    POST test2/_doc
                    {
                        "title": ["Sony", "WH-1000XM3"]
                    }
                    POST test2/_doc
                    {
                        "title": ["SK-II", "PITERA"]
                    }
                    POST test2/_doc
                    {
                        "title": ["Nintendo", "switch"]
                    }
                    # 自动补全查询，根据 "s" 进行匹配 suggest
                    GET test2/_search
                    {
                        "suggest": {
                            "title_suggest": {
                                "text": "s", // 关键字
                                "completion": {
                                    "field": "title", // 补全查询的字段
                                    "skip_duplicates": true, // 跳过重复的
                                    "size": 10 // 获取前10条结果
                                }
                            }
                        }
                    }
            自动补全实操：
                需求：实现hotel索引库的自动补全，拼音搜索功能
                    实现思路如下：
                        修改hotel索引库结构，设置自定义拼音分词器
                            详见：# ==========自动补全实操==========
                        修改索引库的name和all字段，使用自定义分词器
                            详见：# ==========自动补全实操==========
                        索引库添加一个新字段suggestion，类型为completion类型，使用自定义分词器
                            详见：# ==========自动补全实操==========
                        给HotelDoc类添加suggestion字段，内容包含brand和business
                            详见：HotelDoc.java
                                private List<String> suggestion;
                                this.suggestion = Arrays.asList(this.brand, this.business);
                        重新导入数据到hotel库
                            执行 HotelDocumentTest.textBulkRequest 方法
                            在控制台中查询
                                GET /hotel/_search
                                {
                                    "query": {
                                        "match_all": {}
                                    }
                                }
                            可以看到控制台的每一个查询结果多了个suggestion字段。
                        针对business字段进行切割
                            详见：HotelDoc.java
                            if (this.business.contains("、")) {
                                // business有多个值，需要切割
                                String[] arr = this.business.split("、");
                                // 添加元素
                                this.suggestion = new ArrayList<>();
                                this.suggestion.add(this.brand);
                                // addAll方法会将arr的每一个元素add 到 this.suggestion中。
                                Collections.addAll(this.suggestion, arr);
                            }else{
                                this.suggestion = Arrays.asList(this.brand, this.business);
                            }
                            再次在控制台中查询结果：（大概151行）
                                GET /hotel/_search
                                {
                                    "query": {
                                        "match_all": {}
                                    }
                                }
                                结果：
                                    "suggestion" : [
                                        "7天酒店",
                                        "江湾、五角场商业区",
                                    ]
                                    成功切割为变为
                                    "suggestion" : [
                                        "7天酒店",
                                        "江湾",
                                        "五角场商业区"
                                    ]
                        在ES控制台中suggestion查询：
                            GET /hotel/_search
                            {
                                "suggest": {
                                    "suggestions": {
                                        "text": "h",
                                        "completion": {
                                            "field": "suggestion",
                                            "skip_duplicates": true,
                                            "size": 10
                                        }
                                    }
                                }
                            }
                            结果：h 拼音开头的汉字的所有品牌和商圈都成功匹配。
                    
                RestAPI实现自动补全查询：    
                    代码片段：(HotelSearchTest.testSuggest)
                        @Test
                        void testSuggest() throws IOException {
                            // 准备Request
                            SearchRequest request = new SearchRequest("hotel");
                            // 准备DSL（最好对照控制台 # ==========查询suggest========== 写RestAPI代码）
                            request.source().suggest(new SuggestBuilder().addSuggestion(
                                    "suggestions",
                                    SuggestBuilders.completionSuggestion("suggestion")
                                            .prefix("h")
                                            .skipDuplicates(true)
                                            .size(10)
                            ));
                            // 发起请求
                            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                            // System.out.println(response);
                            // 解析结果
                            Suggest suggest = response.getSuggest();
                            // 根据补全查询名称，获取补全结果
                            CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
                            // 获取options
                            List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
                            // 遍历
                            for (CompletionSuggestion.Entry.Option option : options) {
                                String text = option.getText().toString();
                                System.out.println(text);
                            }
                        }
                
                实现酒店搜索页面输入框的自动补全
                    在服务端编写接口，接收该请求，返回补全结果的集合，类型为List<String>（HotelController）
                        @GetMapping("/suggestion")
                        public List<String> getSuggestions(@RequestParam("key") String prefix) {
                            return hotelService.getSuggestions(prefix);
                        }
                    iHotelService接口：
                        List<String> getSuggestions(String prefix);
                    HotelService实现方法：
                        @Override
                        public List<String> getSuggestions(String prefix) {
                            try {
                                // 准备Request
                                SearchRequest request = new SearchRequest("hotel");
                                // 准备DSL（最好对照控制台 # ==========查询suggest========== 写RestAPI代码）
                                request.source().suggest(new SuggestBuilder().addSuggestion(
                                        "suggestions",
                                        SuggestBuilders.completionSuggestion("suggestion")
                                                .prefix(prefix)
                                                .skipDuplicates(true)
                                                .size(10)
                                ));
                                // 发起请求
                                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                                // System.out.println(response);
                                // 解析结果
                                Suggest suggest = response.getSuggest();
                                // 根据补全查询名称，获取补全结果
                                CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
                                // 获取options
                                List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
                                // 准备List (长度即使options的长度)
                                List<String> list = new ArrayList<>(options.size());
                                // 遍历
                                for (CompletionSuggestion.Entry.Option option : options) {
                                    String text = option.getText().toString();
                                    list.add(text);
                                }
                                return list;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    此时访问页面，在搜索看随便输入字母，以该字母为前缀的汉字的品牌和商圈会出现在列表中表示成功。

        数据同步：
            数据同步思路分析
                elasticsearch中的酒店数据来自于mysql数据库，因此mysql数据发生改变时，elasticsearch也必须跟着改变，这个就是elasticsearch与mysql之间的数据同步
                同步问题不光是es和mysql，凡是数据库双写的情况，都会有数据同步的问题，比如redis和mysql都会存在数据同步问题
                在微服务中，负责酒店管理（操作mysql）的业务与负责酒店搜索（操作elasticsearch）的业务可能在两个不同的微服务上，数据同步该如何实现呢？
                    方案一：同步调用
                        当有人做增删改查的业务时，首先把数据同步到库里，然后调用hotel-es-demo项目暴露的更新索引库的接口，去同步更新es
                        弊端：
                            这样做就导致从原来的一步变成三步了，写入数据库，调用hotel-es-demo更新es的接口，然后hotel-es-demo更新es。
                            并且三个步骤是依次执行的，顺序不可乱，同时导致性能下降，从原来一个接口的响应，变成了两个接口的响应等待。
                            且还会有业务耦合和数据耦合的问题。
                        优点：实现简单，粗暴
                    方案二：异步通知（推荐方式）
                        基于MQ的技术实现的，当有人新增时，先写入数据库，写完后向MQ发布消息，通知MQ有数据新增，至于谁监听这个消息
                        监听完之后做什么和什么时候做都不会和写数据这一步有任何冲突，这样一来业务和数据的耦合就解除了。
                        并且监听方（hotel-es-demo）耗时多少秒也不会对 hoteo-admin 项目有任何影响。
                    方案三：监听binlog
                        优点：完全解除服务见的耦合
                        缺点：开启binlog会增加数据库负担，并且canal中间件的实现复杂的较高。

            实现elasticsearch与数据库数据同步（利用MQ，方式二）
                新建hotel-admin项目作为酒店管理的微服务，当酒店数据发生增删改查时，要求对elasticsearch数据也要完成相同操作
                步骤：
                    创建hotel-admin项目，启动并测试酒店数据的CRUD
                    在hotel-es-demo中导入amqp坐标，并在application中配置 rabbitmq 地址
                        rabbitmq:
                            host: 127.0.0.1
                            port: 5672
                            username: leechenze
                            password: 930316
                            virtual-host: / # leechenze这个用户的虚拟主机（/）
                    声明exchange，queue，RoutingKey的常量
                        constants.MqConstants
                            package com.lee.hotel.constants;
                            public class MqConstants {
                                /**
                                * 交换机
                                */
                                private final static String HOTEL_EXCHANGE = "hotel.topic";
                                /**
                                * 监听新增和修改的队列
                                */
                                private final static String HOTEL_INSERT_QUEUE = "hotel.insert.queue";
                                /**
                                * 监听删除的队列
                                */
                                private final static String HOTEL_DELETE_QUEUE = "hotel.delete.queue";
                                /**
                                * 新增和修改的RoutingKey
                                */
                                private final static String HOTEL_INSERT_KEY = "hotel.insert.key";
                                /**
                                * 新增和修改的RoutingKey
                                */
                                private final static String HOTEL_DELETE_KEY = "hotel.delete.key";
                            }
                        config.MqConfig
                            @Configuration
                            public class MqConfig {
                                /**
                                * 定义交换机
                                * @return
                                */
                                @Bean
                                public TopicExchange topicExchange() {
                                    return new TopicExchange(MqConstants.HOTEL_EXCHANGE, true, false);
                                }
                                
                                /**
                                 * 定义插入的队列
                                 * @return
                                 */
                                @Bean
                                public Queue insertQueue() {
                                    return new Queue(MqConstants.HOTEL_INSERT_QUEUE, true);
                                }
                            
                                /**
                                 * 定义删除的队列
                                 * @return
                                 */
                                @Bean
                                public Queue deleteQueue() {
                                    return new Queue(MqConstants.HOTEL_DELETE_QUEUE, true);
                                }
                        
                                /**
                                 * 定义插入的绑定关系
                                 * @return
                                 */
                                @Bean
                                public Binding insertQueueBinding() {
                                    return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstants.HOTEL_INSERT_KEY);
                                }
                            
                                /**
                                 * 定义删除的绑定关系
                                 * @return
                                 */
                                @Bean
                                public Binding deleteQueueBinding() {
                                    return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstants.HOTEL_DELETE_KEY);
                                }
                            }

                    在hotel-admin中的增删改业务中完成消息发送的逻辑
                        首先copy一份 constants.MqConstants 和 amqp 坐标依赖和 application 配置到 hotel-admin
                        在controller中处理消息发送的业务（HotelController）
                            // 首先注入发送消息需要的API
                            private RabbitTemplate rabbitTemplate
                            
                            @PostMapping
                            public void saveHotel(@RequestBody Hotel hotel){
                                hotelService.save(hotel);
                                // 发送业务新增时的MQ消息，参数为（消息交换机，新增消息的key，酒店的ID）
                                rabbitTemplate.convertAndSend(MqConstants.HOTEL_EXCHANGE, MqConstants.HOTEL_INSERT_KEY, hotel.getId());
                            }
                        
                            @PutMapping()
                            public void updateById(@RequestBody Hotel hotel){
                                if (hotel.getId() == null) {
                                    throw new InvalidParameterException("id不能为空");
                                }
                                hotelService.updateById(hotel);
                                // 发送业务修改时的MQ消息，参数为（消息交换机，修改消息的key，酒店的ID）
                                rabbitTemplate.convertAndSend(MqConstants.HOTEL_EXCHANGE, MqConstants.HOTEL_INSERT_KEY, hotel.getId());
                            }
                        
                            @DeleteMapping("/{id}")
                            public void deleteById(@PathVariable("id") Long id) {
                                hotelService.removeById(id);
                                // 发送业务删除时的MQ消息，参数为（消息交换机，删除消息的key，酒店的ID）
                                rabbitTemplate.convertAndSend(MqConstants.HOTEL_EXCHANGE, MqConstants.HOTEL_DELETE_KEY, id);
                            }
                            
                    在hotel-es-demo中完成消息监听，并更新elasticsearch中的数据
                        首先创建mq的消息监听类：mq.HotelListener
                            @Component
                            public class HotelListener {
                                @Autowired
                                private IHotelService hotelService;
                                /**
                                 * 监听酒店新增或修改的业务
                                 * @param id 酒店ID
                                 */
                                @RabbitListener(queues = MqConstants.HOTEL_INSERT_QUEUE)
                                public void listenHotelInsertOrUpdate(Long id) {
                                    hotelService.insertById(id);
                                }
                                /**
                                 * 监听酒店删除的业务
                                 * @param id 酒店ID
                                 */
                                @RabbitListener(queues = MqConstants.HOTEL_DELETE_QUEUE)
                                public void listenHotelDelete(Long id) {
                                    hotelService.deleteById(id);
                                }
                            }
                        在IHotelService中声明对应接口
                            void insertById(Long id);
                            void deleteById(Long id);
                        在HotelService中编写两个方法的实现
                            @Override
                            public void insertById(Long id) {
                                try {
                                    // 根据ID查询酒店数据
                                    Hotel hotel = getById(id);
                                    // 转换为文档类型
                                    HotelDoc hotelDoc = new HotelDoc(hotel);
                                    // 准备Request对象
                                    IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
                                    // 准备Json DSL文档
                                    request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
                                    // 发送请求
                                    client.index(request, RequestOptions.DEFAULT);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        
                            @Override
                            public void deleteById(Long id) {
                                try {
                                    // 准备Request对象
                                    DeleteRequest request = new DeleteRequest("hotel", String.valueOf(id));
                                    // 发送请求
                                    client.delete(request, RequestOptions.DEFAULT);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        
                    启动并测试数据的同步功能
                        hotel-admin：http://localhost:8099/
                        hotel-es-demo：http://localhost:8089/
                        
                        在酒店管理项目修改一条上海希尔顿酒店，将价格值改变后保存。
                        在http://127.0.0.1:15672/的RabbitMQ管理界面查看监听的队列状态，会有一条接收到的消息，则表示成功
                        在酒店搜索项目选择1500以上价格区间，查看该酒店数据已经改变。
                        
                        然后在酒店管理中将上海希尔顿酒店这条数据删除，删除前在Vue devtools工具中把这条数据copy value，等会新增数据时候还要加回来。
                        在酒店搜索中刷新1500以上价格区间查看原来的13条数据变为了12条数据，此时上海希尔顿酒店数据已经找不到了，至此删除已完成
                        
                        在酒店管理中打开新增酒店Dialog，在Vue Devtools中将刚才拷贝的数据加回来，然后保存。
                        同理再次在酒店搜索项目中刷新1500以上价格区间的数据，发现从12条变为了13条，上海希尔顿酒店可以查到了。至此所有功能都验证通过。
                    
                    
        ES集群：
            集群结构介绍：
                单机的elasticsearch做数据存储，必然面临两个问题：海量数据存储问题，单点故障问题。
                    海量数据存储问题：将索引从逻辑上拆分为N个分片（shard），存储到多个节点
                    单点故障问题： 将分片数据在不同节点备份（replica）
            集群搭建：
                这里利用3个docker容器和docker-compose模拟3个ES的节点。
                    docker-compose.yml的配置文件在 lib/day5/es-cluster/docker-compose.yml
                CentOS：
                    如果在liunx系统运行还需要修改/etc/sysctl.conf文件
                        vi /etc/sysctl.conf
                    添加下面的内容：
                        vm.max_map_count=262144
                    然后执行命令，让配置生效：
                        sysctl -p
                    通过docker-compose启动集群：
                        docker-compose up -d
                cd到/Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Advanced/lib/day5/es-cluster目录下
                    执行 docker-compose up -d 即 docker-compose.yml配置文件所在的目录。 
                es01，es02，es03容器都已常见成功，查看每一个容器的运行日志：
                    docker logs -f es01
                    docker logs -f es02
                    docker logs -f es03
            集群状态监控：
                kibana可以监控es集群，不过新版需要依赖es的x-pack功能，配置比较麻烦
                这里使用 cerebro 来代替Kibana监听es集群状态，官网地址：https://github.com/lmenezes/cerebro
                下载v0.9.4这个版本，解压即可使用，非常方便
                解压好后进入bin目录，双击其中的 cerebro 文件 或 在命令行执行 sh cerebro 即可启动服务。
                启动成功后访问 localhost:9000 即可进入 cerebro 的控制台界面
                在首页的 Node address 输入框中输入 http://127.0.0.1:9200 即可进入es01的管理界面
                    es01地址是 http://127.0.0.1:9200
                    es02地址是 http://127.0.0.1:9201
                    es03地址是 http://127.0.0.1:9202
                创建索引库时指定分片信息的DSL：
                    PUT /panda
                    {
                        "settings": {
                            "number_of_shards": 3,  // 分片数量（表示节点总共拆分主分片的数量）
                            "number_of_replicas": 1 // 副本数量（表示每一个分片中除了主分片外，还能拥有几个副分片）
                        },
                        {
                            "mappings": {
                                "properties": {
                                    // mapping映射定义...
                                }
                            }
                        }
                    }
                在cerebro中创建索引库：
                    选择顶部导航的more按钮，选中 create index，对应输入框的值如下：
                        name：panda
                        number of shards：3  // 分片数量
                        number of replicas：1  // 备份数量（副本数量 或 副分片数量）
                    如果有更多 settings 可以在右侧直接写settings 的 json配置。
                回到控制台首页即可看到变化
                    shards：3*2 表示我们配置的三个节点，每个分片中有两个分片，除一个主分片外还有一个副分片。
                    
            ES集群的节点角色：
                elasticsearch 中集群节点有不同的职责划分：        
                节点类型：
                    master eligible（备选主节点）
                        配置参数：node.master
                        默认值：true
                        节点职责：主节点可以管理和记录集群状态，决定分片在那个节点，处理创建和删除索引库的请求。
                    data（数据节点）
                        配置参数：node.data
                        默认值：true
                        节点职责：存储数据，搜索，聚合，CRUD
                    ingest（数据预处理节点）
                        配置参数：node.ingest
                        默认值：true
                        节点职责：数据存储之前的预处理
                    coordinating（协调节点）
                        协调节点不做业务处理，如果有用户请求到达了协调节点，它会把请求路由到真正处理的数据节点上去，
                        数据节点处理完后会把结果返回，返回以后它再将结果合并然后返回给用户。
                        相当于协调节点就是路由 和 负载均衡然后合并结果的作用。
                        配置参数：以上三个参数都为false时，那么该节点就是协调节点，默认也就是协调节点。
                ES集群的脑裂问题：
                    默认情况下：每个节点都是master eligible节点，因此一旦master节点宕机，其他候选节点会选举一个成为主节点，
                    当主节点与其他备选节点网络故障时，其他备选节点就又会选出一个主节点，
                    因此就会出现两个主节点都会对集群的状态，节点和CRUD的管理导致脑裂。
                    简而言之，es集群的脑裂现象指的就是：一个集群出现了两个主节点。
                    
                    为了避免脑裂问题，需要要求选票超过（备选主节点数量 + 1）/ 2 才能当选为主节点，因此备选主节点的数量最好是奇数
                    对应配置项是 discovery.zen.minimum_master_nodes，在es7.0以后，已经成为默认配置，因此7.0后一般不会发生脑裂问题。
                    
                    总结：
                        master eligible 节点作用：    
                            参与集群选主    
                            一旦成为主节点：
                                管理集群状态，管理分片信息，处理创建和删除索引库的请求。
                        data 节点作用：
                            数据的CRUD
                        coordinator节点作用：
                            路由请求协调到其他节点
                            合并查询到的请求，返回给用户。
                ES集群分布式新增和查询流程：
                    这里不用kibana了，用insomnia这个Restful工具来处理DSL的增删改查
                    官网地址：https://insomnia.rest/download，类似于PostMan，操作很简单
                        新增文档：
                            POST http://127.0.0.1:9200/panda/_doc/1
                            POST http://127.0.0.1:9201/panda/_doc/2
                            POST http://127.0.0.1:9202/panda/_doc/3
                        新增文档：
                            GET http://127.0.0.1:9200/panda/_search
                    DSL：
                        {
                            "explain": true, // 显示在哪个分片上
                            "query": {
                                "match_all": {}
                            }
                        }
                    在新增文档时，应该保存到不同的分片，保证数据均衡，那么coordinating node如何确定数据存储到哪个分片呢？
                        ES会通过hash算法来计算文档应该存储在哪个分片
                            shard = hash(_routing) % number_of_shards
                            shard = hash(文档ID) % 分片数量
                        说明：
                            _routing默认是文档ID
                            算法与分片数量有关，因此索引库一旦建立，分片数量不能修改！
                    ES故障转移：
                        集群的master节点会监控集群中的几点状态,如果发现有节点宕机,会立即将宕机节点的分片数据迁移到其他节点,确保数据安全,这个叫做故障转移
                        操作：
                            首先将es02停掉，因为es02是主节点。
                                docker-compose stop es02
                            此时在cerebro控制台可以看到绿条变为黄条了，表示集群不健康了，因为es02停掉了
                            那么稍等片刻，es02这个主节点上的分片就会分配个重新选举出来的主节点上，
                            同时当es02恢复健康后，它也已不再是主节点了，但是分片会重新分配给他！ 就是主打一个智能！！
                        






捌.sentinel

    雪崩问题及解决方案
        微服务调用链路中的某个服务出现故障，引起整个链路中的所有微服务都不可用，这就是雪崩，非常恐怖。
        解决雪崩问题的常见方式有四种：
            超时处理：设定超时时间，请求超过一定时间没有响应就返回错误信息，不会无休止等待。
            舱壁模式：限定每个业务能使用的线程数，避免耗尽整个tomcat的资源，因此也叫线程隔离
            熔断降级：由断路器统计业务执行的异常比例，如果超出阈值则会熔断该业务，即拦截访问该业务的一切请求。
            流量控制：限制业务访问的QPS（每秒处理的请求的数量），避免服务因瞬间高并发流量而导致的故障。
    服务保护技术对比
        Sentinel：好！！！
        Hystrix：一般。。。
        CSDN一片总结较好：
            https://blog.csdn.net/truelove12358/article/details/107507455?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522168364478816800211587394%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=168364478816800211587394&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-2-107507455-null-null.142^v86^control_2,239^v2^insert_chatgpt&utm_term=sentinel%E5%92%8Chystrix%E7%9A%84%E5%8C%BA%E5%88%AB&spm=1018.2226.3001.4187
    Sentinel介绍和安装
        Sentinel是阿里巴巴开源的一款微服务流量控制组件。
        官网地址：https://sentinelguard.io/zh-cn/
        安装Sentinel
            Sentinel官方提供了UI控制台，方便我们对系统做限流设置。
            Github下载地址：https://github.com/alibaba/Sentinel
            将其拷贝到本地，然后运行命令：
                java -jar sentinel-dashboard-1.8.6.jar
            访问：localhost:8080即可访问控制台页面，默认账号密码都是sentinel
            进入之后是一个空白控制台，因为目前还没有和微服务做整合，他没有监控任何东西
                java -Dserver.port=8888 -jar sentinel-dashboard.jar
                修改默认端口8080指向8888
                更多参数请看文档：https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0
                注意所有参数之前都要加一个 -D 为前缀，如：-Dserver.port=8888
            
    微服务整合Sentinel
        要使用Sentinel肯定要结合微服务，这里我们继续使用cloud-demo工程。
            项目结构如下：
                cloud-demo：
                    gateway：微服务网关
                    user-service：用户服务，包含用户的CURD
                    order-service：订单服务，调用user-service
                    feign-api：用户服务对外暴露的feign客户端，实体类
            启动以上的所有服务前先启动 nacos（2.2.0）
                cd /Users/lee/Library/nacos-server-2.2.0/bin
                sh startup.sh -m standalone
            修改user-service和order-service中的application.yml配置文件
                主要改动是nacos地址配置和mysql连接配置。
            启动Sentinel
                java -Dserver.port=1024 -jar sentinel-dashboard-1.8.6.jar

        在order-service中整合sentinel，并连接sentinel控制台，步骤如下：
            引入Sentinel依赖
                <!--引入Sentinel依赖-->
                <dependency>
                    <groupId>com.alibaba.cloud</groupId>
                    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
                </dependency>
            配置控制台地址
                sentinel:
                  transport:
                    dashboard: localhost:1024 # sentinel 控制台地址
            访问微服务的任意端点，触发sentinel监控
                端点解释：（EndPoint）springmvc任意一个controler的接口都是一个端点
                访问：http://localhost:8080/order/101
                多访问几次，在sentinel控制台的 orderService ==> 即可看到端点访问信息。
    限流规则
        概念了解：
            簇点链路：
                即项目内的调用链路，链路中被监控的每个接口就是一个资源，默认情况下Sentinel会监控srpingmvc的每一个端点
                因此springmvc的每个端点就是调用链路中的一个资源。
                比如：当请求进入MVC的那一刻起，首先会进入controller，controller会调用service
                service又会调用mapper，那么controller，service和mapper就形成了一个调用链路，即所谓的簇点链路
        流控，熔断等都是针对簇点链路中的资源来设置的，因此我们可以点击对应资源后面的按钮来设置规则
            流控按钮：流量控制
            降级按钮：降级熔断
            热点按钮：热点参数限流
            授权按钮：授权规则
        新增流控规则：
            资源名：/order/{orderId}
            针对来源：default
                default表示一切访问的请求都要被限流，一般选择default即可
            阈值类型：一般选择QPS
            单机阈值：表示QPS的上限
                假设单机阈值为1，表示/order/{orderId}每秒钟最多处理一个请求，超出的请求会被拦截并报错。
                所以单机阈值一般就是我们接口最大的并发量值，通过压测测试的最大并发量。
            
            需求：给/order/{orderId}这个资源设置流控规则，QPS不能超过5，然后利用JMeter进行测试
                JMeter工具：
                    /order/{orderId}一秒钟刷五次，一般人达不到这样的手速，所以要依赖一款名为 JMeter 的工具来实现
                    下载地址：https://jmeter.apache.org/download_jmeter.cgi
                    cd jmeter.sh 运行 JMeter
                    点击Open，选择 lib/day6/sentinel-test.jmx
                    右键流控入门，QPS < 5，点击start，进行对 order服务进行测试。
                    可以发现 连着五次的请求都是成功的，其他几次是失败的，并报错：Blocked by Sentinel (flow limiting)
                    这个报错就是表示请求被限制：被Sentinel阻塞（流量限制）
        流控模式：
            在添加限流规则时，点击高级选项，可以选择三种流控模式：
                直接：统计当前资源的请求，触发阈值时对当前资源直接限流，也是默认模式
                    默认模式，就是对QPS单机阈值的默认配置数
                    操作步骤：
                        首先对/order/101地址的 Sentinel配置 QPS单机阈值为 5
                        右键流控入门，QPS < 5，点击start，进行对 order服务进行测试。
                        可以发现 连着五次的请求都是成功的，其他几次是失败的，并报错：Blocked by Sentinel (flow limiting)
                        这个报错就是表示请求被限制：被Sentinel阻塞（流量限制）
                关联：统计与当前资源相关的另一个资源，触发阈值时，对当前资源限流
                    应用场景：
                        两个有竞争关系的资源
                        一个优先级较高，一个优先级较低
                    操作步骤：
                        在OrderController新建两个端点，/order/query 和 /order/update，无需业务实现。
                            // Sentinel 限流高级设置的关联模式的相关案例 /query 和 /update
                            @GetMapping("/query")
                            public String queryOrder() {
                                return "订单查询成功";
                            }
                            @GetMapping("/update")
                            public String updateOrder() {
                                return "订单更新成功";
                            }
                        配置流控规则，当/order/update 资源被访问的QPS超过5时，对/order/query请求限流
                            给谁限流就给谁加流控规则，所以这里是对query进行流控配置，别搞错了。
                            点击 /order/query 的新增流控规则，单机阈值仍然选择5
                            点击打开高级选项，流控模式选择关联，关联资源输入 /order/update
                            一切就绪后，就可以用JMeter进行对 /order/query 和 /order/update 进行测试了。
                        打开JMeter，选择左侧的流控模式-关联
                            Number of Threads（users）：1000
                            Ramp-up period（seconds）：100
                            这两个配置表示 100 秒内 触发 1000次请求。
                            右键流控模式-关联，点击start启动测试。
                            那么此时我们配置的 /order/query 的单机阈值仍然是5，这样就是一秒触发10次请求了，超过了配置的5个
                            但是 /order/update 这个请求本身是没有任何配置的，所以update能全部成功，限流的只是配置的 /order/query这个端点。
                            此时我们再次访问 http://localhost:8080/order/query 就会报错：被Sentinel阻塞（流量限制）。
                            注意访问 /order/query 时一定要在 /order/update的JMeter程序执行中时才能验证。
                            一旦/order/update的JMeter程序走完后，/order/query一定是能正常访问的。
                链路：统计从指定链路访问到本资源的请求，触发阈值时，对指定链路限流
                    例如有两条链路：
                        /test1/common
                        /test2/common
                    如果只希望统计从/test2进入到/common的请求，则可以在Sentinel控制台这样配置：
                        资源名：/common
                        针对来源：default
                        阈值类型：QPS
                        单机阈值：
                        流控模式：链路
                        入口资源：/test2
                    需求：有查询订单和创建订单的业务，两者都需要查询商品，针对从查询订单进入到查询商品的请求统计，并设置限流：
                        实现步骤：
                            在OrderService中添加一个queryGoods方法，不用实现业务：
                                Sentinel默认只标记Controller中的方法为资源，如果要标记service中的方法或其他方法，需要利用@SentinelResource注解，示例：
                                    // Sentinel 限流高级设置的链路模式的相关案例 /queryGoods 和 /saveGoods
                                    @SentinelResource("queryGoods")
                                    public void queryGoods() {
                                        System.out.println("===========================");
                                        System.out.println("查询商品");
                                        System.out.println("===========================");
                                    }
                                Sentinel默认会将Controller方法做context整合，导致链路模式的流控失效，关闭该功能需要修改application.yml，添加配置：
                                    sentinel:
                                      web-context-unify: false # 关闭Sentinel的context整合，从而导致的链路模式的流控失效。
                                重启服务Order服务
                            在OrderController中添加一个/order/queryGoods端点，调用OrderService中的queryGoods方法
                            在OrderController中添加一个/order/saveGoods端点，也调用OrderService中的queryGoods方法
                                // Sentinel 限流高级设置的链路模式的相关案例 /queryGoods 和 /saveGoods
                                @GetMapping("/queryGoods")
                                public String queryGoods() {
                                    // 查询商品
                                    orderService.queryGoods();
                                    // 查询商品
                                    System.out.println("查询商品=====queryGoods");
                                    return "查询商品成功=====queryGoods";
                                }
                                @GetMapping("/saveGoods")
                                public String saveGoods() {
                                    // 查询商品
                                    orderService.queryGoods();
                                    // 查询商品
                                    System.out.println("创建商品=====saveGoods");
                                    return "创建商品成功=====saveGoods";
                                }
                            给queryGoods设置限流规则，从/order/queryGoods 进入queryGoods的方法限制QPS必须小于2
                                打开控制台后可以看到
                                    /order/queryGoods
                                    /order/saveGoods
                                    两个链路成为了两个独立的资源，这就是 web-context-unify:false 配置的结果
                                打开这两个资源中的任意一个queryGoods的子资源，就是在service 中 @SentinelResource("queryGoods")注解对应的这个方法
                                对queryGoods子资源进行流控配置：
                                    资源名：queryGoods
                                    针对来源：default
                                    阈值类型：QPS
                                    单机阈值：2
                                    流控模式：链路
                                    入口资源：/order/queryGoods
                            打开JMeter对 /order/queryGoods进行测试：
                                在JMeter左侧选择 流控模式-链路
                                Number of Threads（users）：1000
                                Ramp-up period（seconds）：100
                                以上配置表示QPS为4
                                但是在Sentinel控制台中对 /order/queryGoods 的QPS配置为2
                                所以/order/queryGoods 会被限流：
                                    注意之前限流报错为 Blocked by Sentinel (flow limiting)：被Sentinel阻塞（流量限制）
                                    而现在则会报500错误：Internal Server Error，服务器内部错误。
                                但是/order/saveGoods 因为没有针对他的配置所以他不会被限流。
        流控效果：                    
            流控效果是指当请求达到流控阈值时应该采取的措施，包括三种：
                快速失败：达到阈值后，新的请求会被立即拒绝并且抛出FlowException异常，是默认的处理方式。
                    诸如：Internal Server Error 和 Blocked by Sentinel (flow limiting)
                    这些错误的抛出都是属于配置快速失败的结果
                warm up：预热模式，对超出阈值的请求同样是拒绝并抛出异常，不同的是这种模式阈值会动态变化，从一个较小的阈值逐渐增加到最大阈值
                    warm up是应对服务冷启动的一种方案，请求阈值初始值是 threshold / coldFactor，持续指定时长后，
                    会逐渐提高到threshold值，而coldFactor的默认值是3，那么根据threshold / coldFactor，也就是说初始阈值是最大阈值的三分之一。
                    所以总结得出：预热模式就是为了避免冷启动那一刻因过高并发而导致服务故障的。
                    冷启动解释：可理解为慢热启动，比如服务器最大QPS能达到10，刚启动时不能上来就把QPS打满，要给服务器时间"预热"。
                    threshold：最大阈值
                    coldFactor：冷启动因子
                    例如：
                        设置QPS的threshold为10，预热时间为5秒，那么初始阈值就是10/3，也就是3，然后在5秒后逐渐增长到10。
                    所以在预热模式中有两个关键的参数：threshold 和 预热时长，coldFactor一般不用管，默认是3就可以。
                    
                    需求：给/order/{orderId}这个资源设置限流，最大QPS为10，利用warm up效果，预热时长为5秒。
                        重新访问 http://localhost:8080/order/101后，打开Sentinel控制台：
                            在簇点链路中选择/order/{orderId}的流控按钮，配置如下：
                                资源名：/order/{orderId}
                                针对来源：default
                                阈值类型：QPS
                                单机阈值：10
                                流控模式：直接
                                流控效果：Warm Up
                                预热时长：5秒
                        在JMeter中选择：流控效果，warm up
                            Number of Threads（users）：200
                            Ramp-up period（seconds）：20
                        然后选择：流控效果，warm up 的查看结果树
                            可以看到开始成功的请求只有三个，随着时间的推移，成功的请求会越来越多，直到最终的10个最大阈值为止。这就是预热模式。
                排队等待：让所有的请求按照先后次序排队执行，两个请求的间隔不能小于指定时长。
                    当请求超过QPS阈值时，快速失败和warm up会拒绝新的请求并抛出异常，而排队等待则是让所有请求进入一个队列中，然后按照阈值允许的时间间隔一次执行，
                    后面的请求必须等前面的执行完才可以执行，如果请求预期的等待时间超出最大时长，则会被拒绝。
                    需求：给/order/{orderId}这个资源设置限流，最大的QPS为10，利用排队的流控效果，超时时长设置为5s。
                        在Sentinel控制台流控规则中的/order/{orderId}资源修改流控效果的 warm up 为 排队等待，超时时间为5000。
                        打开JMeter选择：流控效果，队列
                            Number of Threads（users）：300
                            Ramp-up period（seconds）：20
                        然后选择：流控效果，队列的查看结果树
                            可以看到所有的请求都成功了，我们设置的发起的请求是15个（300/20）， 但是Sentinel控制台的QPS通过都是10，
                            剩下的请求实际都在队列中等待被执行了。并且很少有报错，除非超过5000这个规定的超时时长。所以排队等待就是起到了流量整型的作用。
        热点参数限流：        
            之前的限流都是统计访问某个资源的所有请求，判断是否超过QPS阈值，而热点参数限流是分别统计参数值相同的请求，判断是否超过QPS阈值。
                比如：order/101 请求有四个， order/102 请求有1个，那么Sentinel会分别统计order资源的101和102这两个不同id的参数。
                配置示例：
                    资源名：hot
                    限流模式：QPS模式
                    参数索引：0
                    单机阈值：5
                    统计窗口时长：1秒
                    以上配置的含义表示：对hot这个资源id为0的参数做统计，每1秒相同参数值的请求数不能超过5。
                在热点参数限流的高级选项中，可以对部分参数进行额外配置：
                    参数例外项：
                        参数类型：long
                        第一个参数值：100
                        第一个参数类型：long
                        第一个限流阈值：10
                        第二个参数值：100
                        第二个参数类型：long
                        第二个限流阈值：15
                        结合上个配置，这里的含义是对hot这个资源id为0的参数做统计，每1秒相同参数值的请求数不能超过5，有两个例外：
                            如果参数值为100，则每1秒允许的QPS为10。
                            如果参数值为101，则每1秒允许的QPS为15。
                需求：给/order/{orderId}这个资源添加热点参数限流，规则如下：
                    默认的热点参数规则是每1秒请求量不超过2个。
                    给102这个参数设置例外项，每1秒请求量不超过4。
                    给103这个参数设置例外项，每1秒请求量不超过10。
                注意：热点参数限流对默认的SpringMVC资源无效，只有通过@SentinelResource注解声明的资源才可以配置热点参数限流。
                操作步骤：
                    在OrderController中对@GetMapping("{orderId}")这个资源添加@SentinelResource("hot")注解。
                        // 只有添加这个注解才能对热点参数限流规则起作用。
                        @SentinelResource("hot")
                        @GetMapping("{orderId}")
                        public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
                            // 根据id查询订单并返回
                            return orderService.queryOrderById(orderId);
                        }
                    修改完成后重启Order服务生效后，访问：http://localhost:8080/order/101后，刷新Sentinel控制台查看簇点链路
                        可以发现/order/{orderId}下多了一个hot资源，就是我们在OrderController中SentinelResource注解声明的这个hot资源
                        选择左侧热点规则栏目：
                            新增热点限流规则：
                                资源名：hot
                                限流模式：QPS模式
                                参数索引：0
                                单机阈值：2
                                统计窗口时长：1秒
                                参数例外项：
                                    参数类型：long
                                    第一个参数值：102
                                    第一个参数类型：long
                                    第一个限流阈值：4
                                    第二个参数值：103
                                    第二个参数类型：long
                                    第二个限流阈值：10
                                点击新增按钮即可
                    打开JMeter，选择热点参数限流 QPS1：
                        Number of Threads（users）：500
                        Ramp-up period（seconds）：100
                        第一个是101的，是默认的，Sentinel中配置的最大的QPS是2。
                            查看结果树：
                                结果为：101所有的请求都是每5个请求中只有2个是成功。
                        第二个是102的，Sentinel中配置的热点参数限流最大的QPS是4。
                            查看结果树：
                                结果为：102所有的请求都是每5个请求中只有4个是成功。
                        第三个是103的，Sentinel中配置的热点参数限流最大的QPS是10。
                            查看结果树：
                                结果为：101所有的请求都是每5个请求中都是成功的，没有失败的，因为103在Sentinel配置的QPS为10，所以不会失败。
                    那么现在热点参数限流就实现了，同一个资源不同的参数限流的上限是不同的（101默认的2，102的4，103的10）
    隔离和降级    
        限流是为了避免服务出现故障，如果服务已经出现了故障，就很容易把故障传递给其他想依赖的服务，这样很容易产生雪崩，    
        所以需要线程隔离和熔断降级这些手段去避免级连失败，避免雪崩。    
        
        FeignClient整合Sentinel
            SpringCloud中，微服务之间的调用都是通过Feign来实现的，因此做客户端保护必须整合Feign和Sentinel
                修改Order服务的application.yml文件，开启Feign和Sentinel功能。
                    feign:
                        client:
                            sentinel:
                                enabled: true # 开启feign对Sentinel的支持
                给FeignClient编写失败后的降级逻辑
                    方式一：FallbackClass，无法对远程调用的异常做处理。
                    方式二：FallbackFactory，可以对远程调用的异常做处理，所以我们选择这种。
                在feign-api项目中定义fallback.UserClientFallbackFactory类，实现FallbackFactory：
                    @Slf4j
                    public class UserClientFallbackFactory implements FallbackFactory<IUserClient> {
                        @Override
                        public IUserClient create(Throwable cause) {
                            // IUserClient 是一个接口，要实现它必须写成匿名内部类。
                            return new IUserClient() {
                                @Override
                                public User findById(Long id) {
                                    log.error("查询用户异常", cause);
                                    return new User();
                                }
                            };
                        }
                    }
                在feign-api项目中的DefaultFeignConfiguration类中将UserClientFallbackFactory注册为一个Bean：
                    @Bean
                    public UserClientFallbackFactory userClientFallbackFactory() {
                        return new UserClientFallbackFactory();
                    }
                在feign-api项目中的IUserClient接口中使用UserClientFallbackFactory：
                    // 声明客户端，参数为服务名称
                    @FeignClient(value = "userService", fallbackFactory = UserClientFallbackFactory.class)
                那么重启Order服务后，并访问一次Order服务，再打开Sentinel控制台的簇点链路即可看到 /order/{orderId}下的hot下就出现了通过Feign调用的接口。
                    
        线程隔离（舱壁模式）
            线程池隔离
                优点：
                    支持主动超时，即会主动终止超时的线程
                    支持异步调用
                缺点：
                    线程的额外开销比较大
                适用场景：
                    低扇出，即依赖或相关联比较少的服务，如果依赖的服务越多那么扇出就会越高。而扇出越高，调用的越多，需要开启的线程也越多，消耗也就越大，并不是线程隔离说擅长的场景。
            信号量隔离（Sentinel默认采用方式）
                优点：
                    轻量级，没有额外开销。
                缺点：
                    不支持主动超时
                    不支持异步调用
                使用场景：
                    高频调用/高扇出，比如Gateway网关都是采用这种模式，这也是Sentinel为何默认选择信号量模式的原因。
            在添加限流规则时，可以选择两种阈值类型，即QPS和线程数。
                QPS：就是每秒的请求数。
                线程数：就是该资源能使用的tomcat线程数的最大值。也就是通过限制线程数量，实现舱壁模式。
            需求：给UserClient的查询用户接口设置流控规则，线程数不超过2，并利用JMeter测试。
                首先打开Sentinel控制台的簇点链路，给刚才添加的Feign的调用资源（GET:http://userService/user/{id}）做一个线程隔离，点击流控按钮
                    资源名：GET:http://userService/user/{id}
                    针对来源：default
                    阈值类型：并发线程数
                    单机阈值：2
                打开JMeter，选择最后一个：阈值类型-线程数<2
                    Number of Threads（users）：10
                    Ramp-up period（seconds）：0
                    表示一瞬间线程发十个请求。
                    然后打开查看结果树，可以看到所有的结果都成功了，是因为在代码做了降级策略，即IUserClient加了Fallback。
                    所以当线程隔离后并不会报错，而是会走 UserClientFallbackFactory 这个降级逻辑，然后返回一个空用户。
                    这些在Idea控制台中可以看到 在Fallback中手动抛出的报错（log.error("查询用户异常", cause)）详见：feign-api服务的fallback.UserClientFallbackFactory
                    但是实际上成功请求且正确返回的只有两个，即单机阈值设置为2的结果。其余请求都返回的是一个空用户。

        熔断降级（新版Sentinel是熔断规则，老版Sentinel是降级规则）
            熔断降级是解决雪崩问题的重要手段，其思路是由断路器统计服务调用的异常比例，慢请求比例，如果超出阈值则会熔断该服务。
            即拦截访问该服务的一切请求；而当服务恢复时，断路器会放行访问该服务的请求。
                断路器的三种状态：
                    Closed：断路器不会拦截任何请求。
                    Open：断路器拦截任何请求。
                    Half-open：断路器会放开一次请求，根据此次请求的结果成功与否来再次决定将状态重调为Open或Closed状态。中间多久放开一次请求，是由配置决定的。
            断路器策略有三种：
                慢调用
                    业务响应时长（RT）大于指定时长的请求认定为慢调用请求。在指定时间内，如果请求数量超过设定的最小数量，慢调用比例大于设定的阈值，则触发熔断
                    例如：
                        资源名：/test
                        熔断策略：慢调用比例
                        最大RT：500ms
                        比例阈值：0.5
                        熔断时长：5s
                        最小请求数：10
                        统计时长：10000ms
                    解读：RT超过500ms的调用是慢调用，统计最近10000ms内的10次请求，如果慢调用比例不低于0.5即不低于一半。
                        则会触发熔断，如果熔断时长为5秒。就进入half-open状态，放行一次请求做重试。
                    需求：给UserClient的查询用户接口设置降级规则，慢调用的RT阈值为50ms，统计时间小于1秒，最小请求数量为5，失败阈值比例为0.4，熔断时长为5秒。
                        提示：为了触发慢调用规则，需要修改UserService中的业务，增加业务耗时：（UserController）
                            @GetMapping("/{id}")
                            public User queryById(@PathVariable("id") Long id, @RequestHeader(value = "Truth", required = false) String truth) throws InterruptedException {
                                System.out.println("Truth: " + truth);
                                if (id == 1) {
                                    // 休眠60ms，满足Sentinel中配置的慢调用的50ms，触发熔断
                                    Thread.sleep(60);
                                }
                                return userService.queryById(id);
                            }
                        重启服务，后访问 http://localhost:8080/order/101，101会调用user/1的服务，可以发现最起码在60ms以上的响应。
                        在Sentinel配置新增熔断规则：
                            资源名：GET:http://userService/user/{id}
                            熔断策略：慢调用比例
                            最大RT：50
                            比例阈值：0.4
                            熔断时长：5s
                            最小请求数：5
                            统计时长：1000ms
                        保存以上熔断配置后，不需要JMeter测试了，就在 http://localhost:8080/order/101 这个页面，在5秒内速刷5次页面，即可触发熔断
                        可以看到结果user对象中的值都为null了，表示进入了代码中的降级策略，表示触发成功。
                        而稍等两秒后进入到half-open的状态后，再次刷新一次后，就会正常返回user的值。

                异常比例，异常数
                    异常比例或异常数：统计指定时间内的调用，如果调用次数超过指定请求数，并且出现异常的比例达到设定的比例阈值（或者超过指定异常数），则触发熔断。
                    例如：
                        资源名：/test
                        熔断策略：异常比例 ｜ 熔断策略：异常数
                        比例阈值：0.4 ｜ 异常数：2
                        熔断时长：5s
                        最小请求数：10
                        统计时长：10000ms
                    解读：统计最近10s内的请求，如果请求量超过10次，并且异常比例不低于0.5，则会触发熔断，熔断时长为5秒。然后进入Half-open状态，放行一次请求做重试。
                    解读：统计最近10s内的请求，如果请求量超过10次，并且异常数达到2次，则会触发熔断，熔断时长为5秒。然后进入Half-open状态，放行一次请求做重试。
                    需求：给IUserClient的查询用户接口设置降级规则，统计时间为1秒，最小请求数为5，失败阈值比例为0.4，熔断时长为5s。
                        提示：为了触发异常统计，需要修改UserService中的业务，抛出异常：（UserController）
                            @GetMapping("/{id}")
                            public User queryById(@PathVariable("id") Long id, @RequestHeader(value = "Truth", required = false) String truth) throws InterruptedException {
                                System.out.println("Truth: " + truth);
                        
                                if (id == 1) {
                                    // 休眠60ms，满足Sentinel中配置的慢调用的50ms，触发熔断
                                    Thread.sleep(60);
                                }else if(id == 2) {
                                    throw new RuntimeException("成功抛错，触发熔断，以满足异常比例，异常数的演示。");
                                }
                                return userService.queryById(id);
                            }
                        重启服务，后访问 http://localhost:8080/order/102，102会调用user/2的服务，可以发现user服务报错了，并且user的值都为null。
                        在Sentinel配置新增熔断规则：
                            资源名：GET:http://userService/user/{id}
                            熔断策略：异常比例 ｜ 熔断策略：异常数
                            比例阈值：0.4 ｜ 异常数：2
                            熔断时长：5s
                            最小请求数：10
                            统计时长：10000ms
                        保存以上熔断配置后，不需要JMeter测试了，就在 http://localhost:8080/order/102 这个页面，在5秒内速刷5次页面，即可触发熔断
                        可以看到结果user对象中的值都为null了，并且也成功触发throw new RuntimeException。
                        而此时刷新 http://localhost:8080/order/103 这个页面就发现 user值也为null了。
                        而稍等两秒后进入到half-open的状态后，再次刷新一次103，就会正常返回user的值。
    授权规则
        系统规则：是对当前应用所在的服务器的一种保护，不过这个保护的规则只对Liunx系统有效，现阶段无法演示。
        授权规则：是对请求者的一种身份的判断。
        集群流控：是将限流规则放到集群环境下做判断，而不再针对单个机器，不过这个模式目前还处于一个state阶段，不建议生产环境使用，所以这也不做演示。
        授权规则：
            授权规则可以对调用方的来源做控制，有白名单和黑名单两种方式。        
            白名单：来源（origin）在白名单内的调用者允许访问。        
            黑名单：来源（origin）在黑名单内的调用者不允许访问。        
            问题：SpringCloud Gateway网关就是做身份认证的，看是否有权限访问，那么为何授权规则还要再次做这个验证呢？        
                所有请求经过网关路由到微服务，这个时候网关当然可以对请求做身份认证，但是万一公司内部人员将微服务地址泄漏出去        
                那么就可以绕过网关直接访问微服务，那么网关内做的安全校验在严密也没用。所以解决这个问题就要使用到Sentinel的授权规则。        
                Sentinel可以验证请求从哪里来的，如果是从Gateway网关来的就放行，否则就拦截。
            操作步骤：
                Sentinel是通过RequestOriginParser这个接口的ParseOrigin来获取请求的来源的，
                例如，我们可以从request中获取一个名为origin的请求头，作为origin的值：（）
                    @Component
                    public class HeaderOriginParser implements RequestOriginParser {
                        @Override
                        public String parseOrigin(HttpServletRequest httpServletRequest) {
                            // 获取请求头
                            String origin = httpServletRequest.getHeader("origin");
                            // 非空判断
                            if (StringUtils.isEmpty(origin)) {
                                origin = "blank";
                            }
                            return origin;
                        }
                    }
                但是因为浏览器和网关都没有名为origin的请求头，所以要给它的请求头加一个名为origin的属性，
                在gateway服务中，利用网关的过滤器添加值为gateway的origin头：
                  default-filters:
                    - AddRequestHeader=origin, gateway
                重启服务，然后在Sentinel给 /order/{orderId} 添加授权规则。        
                    Sentinel授权规则配置：
                        资源名：/order/{orderId}
                        流控应用：gateway
                            这填写的就是origin的值。我们在gateway网关中设置了order为gateway，如果origin为空那么就证明请求源不是从gateway网关来的。
                        授权类型：白名单 
                那么此时再访问 http://localhost:8080/order/101 就会报错：Blocked by Sentinel (flow limiting)
                    因为8080是绕过了gateway网关直接访问的微服务的端口。
                此时再通过gateway访问这个服务：http://localhost:10010/order/101?authorization=admin 就可以正常访问！
                    因为10010是gateway网关的端口，通过它访问就会给请求头加一个orign为gateway的值。通过Sentinel的授权校验。
        自定义异常：        
            默认情况下：发生限流，降级，授权拦截时，都会抛出异常到调用方，但是无论是什么异常返回的结果都是 Blocked by Sentinel (flow limiting) 这个限流异常。        
            如果要自定义异常时的返回结果，需要实现一个阻塞异常处理器 BlockExceptionHandle接口：
            BlockException包含很多之类，分别对应不同的场景：
                FlowException：限流异常
                ParamFlowException：热点参数限流的异常
                DegradeException：降级异常
                AuthorityException：授权规则异常
                SystemBlockException：系统规则异常
            操作步骤：
                在order服务中定义类，实现BlockExceptionHandler接口：（sentinel.ExceptionHandler）
                    @Component
                    public class ExceptionHandler implements BlockExceptionHandler {
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
                            String msg = "未知异常";
                            int status = 429;
                        
                            if (e instanceof FlowException) {
                                msg = "请求被限流了";
                            } else if (e instanceof ParamFlowException) {
                                msg = "请求被热点参数限流";
                            } else if (e instanceof DegradeException) {
                                msg = "请求被降级了";
                            } else if (e instanceof AuthorityException) {
                                msg = "没有权限访问";
                                status = 401;
                            }
                    
                            response.setContentType("application/json;charset=utf-8");
                            response.setStatus(status);
                            response.getWriter().println("{\"msg\": " + msg + ", \"status\": " + status + "}");
                        }
                    }
                重启服务后，给 /order/{orderId}这个资源配置一个流控规则，单机阈值配为1，
                那么在一秒钟内速刷两次即可触发 FlowException，页面返回 {"msg":"status": 429}。
    
    规则持久化
        每当服务重启后，我们在Sentinel所配的各种规则就会丢失。这是因为Sentinel默认会把规则保存在内存里，重启后自然就丢失了，解决这个问题就需要对规则做持久化的处理。
        规则管理模式：
            Sentinel的控制台规则管理有三种模式：
                原始模式：Sentinel的默认模式，将规则保存在内存，重启服务会丢失。
                pull模式：
                    控制台将配置的规则推送到Sentinel客户端，而客户端会将配置规则保存在本地文件或数据库中，以后会定时在本地文件或数据库中查询，更新本地规则。
                    但是这个模式缺点就是实效性比较差，比如刚写入一条规则，这个模式不会马上读取，它是定时读取的，所以就会导致服务之间的规则不一致，所以并不推荐。
                push模式：
                    控制台将配置规则推送到远程配置中心，例如Nacos，Sentinel客户端监听Nacos，获取配置变更的推送消息，完成本地配置更新（推荐方案）。

        实现push模式：
            push模式实现较为复杂，依赖于Nacos，并且需要修改Sentinel控制台源码，详细步骤请看 /lib/day6/sentinel规则持久化.md
            操作步骤：
                在order服务中引入Sentinel监听nacos的依赖：(保证和Sentinel的版本一致)
                    <dependency>
                        <groupId>com.alibaba.csp</groupId>
                        <artifactId>sentinel-datasource-nacos</artifactId>
                        <version>1.8.6</version>
                    </dependency>
                在order-service中的application.yml文件配置nacos地址及监听的配置信息：
                    spring:
                        cloud:
                            sentinel:
                                datasource:
                                    flow:
                                        nacos:
                                            server-addr: localhost:8888 # nacos地址
                                            dataId: orderservice-flow-rules
                                            groupId: SENTINEL_GROUP
                                            rule-type: flow # 还可以是：degrade、authority、param-flow
                重启服务。
                修改Sentinel-dashboard源码，详见 /lib/day6/sentinel规则持久化.md
                    这里处理完后打包好的是 /lib/day6/sentinel-dashboard-nacos.jar
                    启动方式跟官方一样：
                        java -jar sentinel-dashboard.jar
                    如果要修改nacos地址，需要添加参数：
                        java -jar -Dnacos.addr=localhost:8888 sentinel-dashboard.jar
                浏览器清楚缓存后刷新即可看到多出一个栏目：流控规则 - Nacos
                    那么在这个栏目下配置的流控规则就会进入Nacos
                    在流控规则 - Nacos栏目下新增流控规则（注nacos需要时集群启动）
                        资源名：/order/{orderId}
                        针对来源：default
                        阈值类型：QPS
                        单机阈值：1
                    对应的nacos的dataId是 orderservice-flow-rules。
                重新启动服务后，可以发现，流控规则 - Nacos 栏目下配置的规则仍然存在，这样就实现持久化了。
                







玖.Seata
    
    事物的ACID的原则：
        A：原子性，事物中的所有操作要么全部成功，要么全部失败
        C：一致性，要保证数据库内部完整性约束，声明性约束
        I：隔离性，对统一资源操作的事物不能同时发生
        D：持久性，对数据库做的一切修改将永久保存，不管是否出现障碍
    分布式服务的事物问题：
        在分布式系统下，一个业务跨越多个服务或数据源，每个服务都是一个分支事物，要保证所有分支事物最终状态一致，这样的事物就是分布式事物。
    理论基础：
        CAP定理：
            1998年，加州大学的计算机科学家Eric Brewer提出，分布式系统的三个指标：
                Consistency（一致性）
                    用户访问分布式系统的任意节点，得到的数据必须是一致的。
                    因此做一个分布式系统，数据备份的时候应该要即使的完成数据同步，这样才能满足一致性。
                Availability（可用性）
                    用户访问集群中任意健康节点，必须能得到响应，而不是超时或拒绝。
                    所以可用性指的就是节点是否能被正常访问。
                Partition tolerance（分区容错性）
                    分区：因为网络故障或其他原因导致分布式系统中的部分节点与其他节点拾取连接，形成独立分区。
                    容错：在集群出现分区时，整个系统也要持续对外提供服务。
            Eric Brewer说，分布式系统无法同时满足这三个指标，这个结论就叫做CAP
            简述CAP定理内容：
                分布式系统节点通过网络连接，一定会出现分区问题。（P）
                当出现分区问题时，系统的一致性（C）和可用性（A）就无法同时满足。
            思考：
                ElasticSearch集群是CP还是AP？
                    ES集群出现分区时，故障节点会被剔除集群，数据分片会重新分配到其他节点，保证数据的一致性，因此是高一致性，低可用性的性质，属于CP。
        BASE理论：
            BASE理论是对CAP的一种解决思路，包含三个思想：
                Basically Available（基本可用）：分布式系统在出现故障时，允许损失部分可用性，即保证核心可用。
                Soft State（软状态）：在一定时间内，允许出现中间状态，比如临时的不一致状态。
                Eventually Consistent（最终一致性）：虽然无法保证强一致性，但是在软状态结束后，最终达到数据一致。
        分布式事物模型：
            解决分布式事物，各个子系统之间必须能感知到彼此的事物状态,才能保证状态一致,因此需要一个事物协调者来协调每一个事物的参与者(子系统事物)
            这里的子系统事物,成为分支事物;有关联的各个分支事物在一起称为全局事物
        演示分布式事物：
            创建数据库名为seata_demo，然后导入 lib/day7/sql/seata-demo.sql
            导入 lib/day7/seata-demo 项目
            启动nacos，所有微服务。
            访问：http://localhost:8082/order?userId=user202103032042012&commodityCode=100202003032041&count=2&money=200 返回值为1则成功启动。
        
    初识Seata：
        Seata是2019年1月蚂蚁金服和阿里巴巴共同开源的分布式事物解决方案，致力于提供高性能和简单易用的分布式事务服务，为用户打造一站式的分布式解决方案。
        官网地址：https://seata.io/en-us/index.html，其中的文档，博客中提供了大量的使用说明，源码分析。
            
        Seata的架构：
            Seata事务管理中有三个重要的角色：
                TC（Transaction Coordinator）事务协调者：维护全局和分支事物的状态, 协调全局事物提交或回滚。
                TM(Transaction Manager) 事物管理器：定义全局事物的范围,开始全局事物,提交或回滚全局事物。
                RM(Resource Manager) 资源管理器：管理分支事物处理的资源,与TC交谈以注册分支事物和报告分支事物的状态,并驱动分支事提交或回滚
            Seata提供了四种不同的分布式事物解决方案：
                XA模式：强一致性分阶段事务模式，牺牲了一定的可用性，无业务侵入。
                TCC模式：最终一致的分阶段事务模式,有业务侵入。
                AT模式: 最终一致的分阶段事务模式, 无业务侵入, 也是Seata的默认模式
                SAGA模式: 长事物模式, 有业务侵入。
        部署TC服务：
            详细请看：lib/day7/seata的部署和集成.md
            所录结构：lib/day7/seata-server-1.4.2
                bin     运行脚本
                conf    配置文件
                lib     依赖库
                logs    日志文件
            修改配置：conf/registry.conf文件：
                registry {
                    # tc服务的注册中心类，这里选择nacos，也可以是eureka、zookeeper等
                    type = "nacos"
                    nacos {
                        # seata tc 服务注册到 nacos的服务名称，可以自定义
                        application = "seata-tc-server"
                        serverAddr = "127.0.0.1:8848"
                        group = "DEFAULT_GROUP"
                        namespace = ""
                        cluster = "SH"
                        username = "nacos"
                        password = "nacos"
                    }
                }
                config {
                    # 读取tc服务端的配置文件的方式，这里是从nacos配置中心读取，这样如果tc是集群，可以共享配置
                    type = "nacos"
                    # 配置nacos地址等信息
                    nacos {
                        serverAddr = "127.0.0.1:8848"
                        namespace = ""
                        group = "SEATA_GROUP"
                        username = "nacos"
                        password = "nacos"
                        dataId = "seataServer.properties"
                    }
                }
            在nacos中添加配置：
                特别注意，为了让tc服务的集群可以共享配置，我们选择了nacos作为统一配置中心。因此服务端配置文件seataServer.properties文件需要在nacos中配好。
                配置内容如下：
                    # 数据存储方式，db代表数据库
                    store.mode=db
                    store.db.datasource=druid
                    store.db.dbType=mysql
                    store.db.driverClassName=com.mysql.jdbc.Driver
                    store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true
                    store.db.user=root
                    store.db.password=lcz19930316
                    store.db.minConn=5
                    store.db.maxConn=30
                    store.db.globalTable=global_table
                    store.db.branchTable=branch_table
                    store.db.queryLimit=100
                    store.db.lockTable=lock_table
                    store.db.maxWait=5000
                    # 事务、日志等配置
                    server.recovery.committingRetryPeriod=1000
                    server.recovery.asynCommittingRetryPeriod=1000
                    server.recovery.rollbackingRetryPeriod=1000
                    server.recovery.timeoutRetryPeriod=1000
                    server.maxCommitRetryTimeout=-1
                    server.maxRollbackRetryTimeout=-1
                    server.rollbackRetryTimeoutUnlockEnable=false
                    server.undo.logSaveDays=7
                    server.undo.logDeletePeriod=86400000
                    
                    # 客户端与服务端传输方式
                    transport.serialization=seata
                    transport.compressor=none
                    # 关闭metrics功能，提高性能
                    metrics.enabled=false
                    metrics.registryType=compact
                    metrics.exporterList=prometheus
                    metrics.exporterPrometheusPort=9898
                ==其中的数据库地址、用户名、密码都需要修改成你自己的数据库信息。==
                tc服务在管理分布式事务时，需要记录事务相关数据到数据库中，需要提前创建好这些表。
                所以根据以上的数据库信息新建seata数据库，并执行 lib/day7/sql/seata-tc-server.sql
            启动TC服务：
                进入bin目录，运行其中的seata-server.sh即可
                启动成功后，seata-server应该已经注册到nacos注册中心了。
                打开浏览器，访问nacos地址：http://localhost:8888，然后进入服务列表页面，可以看到seata-tc-server的信息：

        微服务集成Seata：
            操作步骤：
                在storage-aervice中引入Seata相关依赖：
                配置storage-aervice/application.yml，让微服务通过注册中心，找到seata-tc-server：
                    seata:
                        registry: # TC服务注册中心配置，微服务根据这些信息去注册中心获取tc服务地址
                            # 参考TC服务自己的registry.conf中的配置。
                            # 包括：地址，namespace，group，application-name，cluster
                            type: nacos
                            nacos: # TC
                                server-addr: 127.0.0.1:8888
                                namespace: ""
                                group: DEFAULT_GROUP
                                application: seata-tc-server # tc服务中的服务名称
                                username: nacos
                                password: nacos
                        tx-service-group: seata-demo # 事务组，根据这个获取tc服务的cluster名称
                        service:
                            vgroup-mapping: # 事务组与cluster的映射关系
                            seata-demo: SH
                account-service 和 order-service同样按着以上的方法进行配置。
                重启服务后，在seata服务（tc服务）的控制台（seata-server.sh）可以看到更新一条 RM register success 和 TM register success的消息则表示成功。

    动手实践：
        XA模式：
            XA规范是X/Open组织定义的分布式事务处理（DTP，Distributed Transaction Processing）标准，XA规范描述了全局的TM与局部的RM之间的接口
            几乎所有的主流数据库都对XA规范提供了支持。它是分布式事务领域最早的一种标准。Seata的XA模式做了一些调整，但是大体相似。
            优点：
                事务的强一致性，满足ACID原则。
                常用数据库都支持，实现简单，并且没有代码侵入。
            缺点：
                因为一阶段需要锁定数据库资源，等待二阶段结束才释放，性能较差。
                依赖关系性数据库的实现事务。
            实现步骤：
                Seata的自动装配已经完成了XA模式的自动装配，实现非常简单，步骤如下：
                    修改applciation.yml文件（每个参与事务的微服务），开启XA模式：
                        seata:
                            data-source-proxy-mode: XA # 开启数据源代理的XA模式
                    发起全局事务的入口方法添加一个@GlobalTransactional注解即可，本例中是OrderServiceImpl中的create方法，其他service方法中只需要声明@Transactional注解即可：
                        @Override
                        @GlobalTransactional
                        public Long create(Order order) {
                            // 创建订单
                            orderMapper.insert(order);
                            try {
                                // 扣用户余额
                                accountClient.deduct(order.getUserId(), order.getMoney());
                                // 扣库存
                                storageClient.deduct(order.getCommodityCode(), order.getCount());
                    
                            } catch (FeignException e) {
                                log.error("下单失败，原因:{}", e.contentUTF8(), e);
                                throw new RuntimeException(e.contentUTF8(), e);
                            }
                            return order.getId();
                        }
                    重启服务并测试:
                        PostMan执行 http://localhost:8082/order?userId=user202103032042012&commodityCode=100202003032041&count=10&money=200 
                        这个创建订单接口，同时关注三张表：account_tbl，order_tbl，storage_tbl，接口中的count参数输入10，但是金额不够1000，所以订单服务失败
                        订单服务失败后，account服务 和 storage都不会成功了，都会进行事务回滚。三张表的数据不会变，至此验证成功。
                        
        AT模式：
            AT模式同样是分阶段提交的事务模型，不过弥补了XA模型中资源锁定周期过长的缺陷。
            AT模式与XA模式最大的区别是什么：
                XA模式第一阶段不提交事物，锁定资源；AT模式一阶段直接提交，不锁定资源。
                XA模式依赖数据库机制实现回滚，AT模式利用数据快照（undo log）实现数据回滚。
                XA模式强一致，AT模式最终一致。
            AT模式的脏写问题解决：
                AT模式写的隔离：马马虎虎没听懂，这里指定CSDN了解原理和工作流程吧。
                全局锁：由TC记录当前正在操作某行数据的事务,该事物持有全局锁,具备执行权。
            AT模式的优点：
                一阶段完成直接提交事物，释放数据库资源，性能比较好。
                利用全局锁实现读写隔离。
                没有代码侵入，框架自动完成回滚和提交。
            AT模式的缺点：
                两阶段之间的属于软状态，属于最终一致。
                框架的快照功能会影响性能，但比XA模式要好很多。
            实现步骤：
                AT模式中的快照生成，回滚等动作都是由框架自动完成的，没有任何代码侵入，因此实现非常简单。
                导入lib/day7/sql/seata-at.sql文件，其中lock_table导入到TC服务关联的数据库（seata库），undo_log表导入到微服务关联的数据库（seata_demo）
                修改application.yml文件，将事物模式改为AT模式即可：
                    seata:
                        data-source-proxy-mode: AT # 开启数据源代理的AT模式
                重启服务并测试：
                    和XA是同样的效果。同成功同失败。
        TCC模式：
            TCC模式与AT模式非常相似,每阶段都是独立事务,不同的是TCC通过人工编码来实现数据恢复.需要实现三个方法:
                Try: 资源的检测和预留
                Confirm: 完成资源操作业务; 要求Try成功Confirm一定要能成功
                Cancel: 预留资源释放, 可以理解为try的方向操作
            TCC模式的优点：
                一阶段完成直接提交事物，释放数据库资源，性能好。
                相比AT模型，不用生成快照，无需使用全局锁，性能最强。
                不依赖数据事务，而是依赖补偿操作，可以用于非事务型数据库。
            TCC模式的缺点：
                有代码侵入（需手写代码），需要人为编写try，Confirm和Cancel接口，太麻烦。
                软状态，事务是最终一致的
                需要考虑Confirm和Cancel的失败情况，做好幂等处理。
            需求：
                改造account-service服务，利用TCC实现分布式事务，这里只改account服务，其他服务仍沿用AT模式，这几种模式可以混着用，因为它们都属于Seata内部的实现。
                
                修改account-service，编写try，confirm，cancel逻辑
                try业务：添加冻结金额，扣减可用金额。
                confirm业务：删除冻结金额。
                cancel业务：删除冻结金额，恢复可用金额。
                代码层面考虑：
                    保证confirm和cancel接口的幂等性：
                        即调用一次和多次的结果是一致的，不会因为重复调用而出问题，具体通过业务进行判断，是否执行过了，执行过就不再执行即可。
                    允许空回滚：
                        当某分支事物的try阶段阻塞时,可能导致全局事务超时而触发二阶段的cancel操作.在未执行try操作时先执行了cancel操作,这时cancel不能做回滚, 这就是空回滚
                    拒绝业务悬挂：
                        对于已经空回滚的业务,如果以后继续执行try,就永远不可能confirm或cancel,这就是业务悬挂.应当阻止执行空回滚后的try操作,避免悬挂
                        所以为了实现空回滚，方式业务悬挂，以及幂等性要求。我们必须在数据库记录冻结金额的同时，记录当前事务id和执行状态。
                        为此需要设计一张表 lib/day7/sql/account_freeze_tbl （在seata_demo库中执行）
                            xid：事务ID，user_id：用户id，freeze_money：冻结金额，state：TCC的状态（Try，Confirm，Cancel）
            实现步骤：
                TCC的Try,Confirm,Cancel方法都需要在注解中基于注解来声明，声明接口在 account-service/.../service/AccountTCCService
                    @LocalTCC
                    public interface AccountTCCService {
                        /**
                        * name表示try方法
                        * commitMethod表示confirm方法
                        * rollbackMethod表示cancel方法
                        */
                        @TwoPhaseBusinessAction(name = "deduct", commitMethod = "confirm", rollbackMethod = "cancel")
                        void deduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                        @BusinessActionContextParameter(paramName = "money") int money);
                
                        /** 这两个方法是获取事务信息和参数信息的，前提是必须要在Try的方法中指定 @BusinessActionContextParameter 这个注解 */
                        boolean confirm(BusinessActionContext ctx);
                
                        boolean cancel(BusinessActionContext ctx);
                    }
                在impl中实现该接口，书写业务逻辑：
                    @Slf4j
                    @Service
                    public class AccountTCCServiceImpl implements AccountTCCService {
                    
                        @Autowired
                        private AccountMapper accountMapper;
                        @Autowired
                        private AccountFreezeMapper accountFreezeMapper;
                    
                        @Override
                        @Transactional
                        public void deduct(String userId, int money) {
                    
                            // 获取事务ID
                            String xid = RootContext.getXID();
                    
                            // 判断业务悬挂
                            // 判读accountFreeze是否有冻结记录，如果有，一定是CANCEL执行过，就变成悬挂业务了，要拒绝业务。
                            AccountFreeze oldAccountFreeze = accountFreezeMapper.selectById(xid);
                            if (oldAccountFreeze != null) {
                                // CANCEL执行过，要拒绝业务。
                                return;
                            }
                    
                            // 扣减可用金额
                            accountMapper.deduct(userId, money);
                            // 记录冻结金额，事务状态。
                            AccountFreeze accountFreeze = new AccountFreeze();
                            accountFreeze.setUserId(userId);
                            accountFreeze.setFreezeMoney(money);
                            accountFreeze.setState(AccountFreeze.State.TRY);
                            accountFreeze.setXid(xid);
                            accountFreezeMapper.insert(accountFreeze);
                    
                        }
                    
                        /**
                         * confirm提交事务逻辑
                         * @param ctx
                         * @return
                         */
                        @Override
                        public boolean confirm(BusinessActionContext ctx) {
                            // 获取事务ID
                            String xid = ctx.getXid();
                    
                            // 根据ID删除冻结记录
                            int count = accountFreezeMapper.deleteById(xid);
                            return count == 1;
                        }
                    
                        /**
                         * cancel回滚事务逻辑
                         * @param ctx
                         * @return
                         */
                        @Override
                        public boolean cancel(BusinessActionContext ctx) {
                    
                            // 获取xid 和 userId
                            String xid = ctx.getXid();
                            String userId = ctx.getActionContext("userId").toString();
                            // 查询冻结记录
                            AccountFreeze accountFreeze = accountFreezeMapper.selectById(xid);
                    
                            // 空回滚的判断，判断accountFreeze是否为Null。
                            if (accountFreeze == null) {
                                // 为Null证明try没执行，需要空回滚。
                                accountFreeze.setUserId(userId);
                                accountFreeze.setFreezeMoney(0);
                                accountFreeze.setState(AccountFreeze.State.CANCEL);
                                accountFreeze.setXid(xid);
                                accountFreezeMapper.insert(accountFreeze);
                                return true;
                            }
                    
                            // 判断幂等
                            if (accountFreeze.getState() == AccountFreeze.State.CANCEL) {
                                // 证明已经处理过一次CANCEL了，无需重复处理。
                                return true;
                            }
                    
                            // 恢复可用余额
                            accountMapper.refund(accountFreeze.getUserId(), accountFreeze.getFreezeMoney());
                    
                            // 将冻结金额清零，状态改为CANCEL
                            accountFreeze.setFreezeMoney(0);
                            accountFreeze.setState(AccountFreeze.State.CANCEL);
                    
                            // 更新操作
                            int count = accountFreezeMapper.updateById(accountFreeze);
                            return count == 1;
                        }
                    }
                修改web/AccountController
                    @Autowired
                    // private AccountService accountService;
                    private AccountTCCService accountService;
                重启服务测试：
                    操作同XA和AT模式。
            这里有点懵懂，如若具体结果不是预期，请差文档关联自己业务逻辑自行实现。
                   
 
        SAGA模式：
            SAGA是Seata提供的长事物解决方案，也分为两个阶段：
                一阶段：直接提交本地事物。
                二阶段：成功则什么都不做，失败则通过编写补偿业务来回滚。
            SAGA优点：
                事务参与者可以基于事件驱动实现异步调用,吞吐高
                一阶段直接提交事务,无锁,性能好
                不用编写TCC中的三个阶段,实现简单
            SAGA缺点：
                软状态持续时间不确定,时效性差
                没有锁,没有事务隔离,会有脏写
        总结和对比：
            SAGA模式一般应用较少，TCC和AT模式结合使用是应对大多数场景需求的首选。
            四种模式对比和应用场景请看：
                https://upload-images.jianshu.io/upload_images/67572-26012ff5fe55f1e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1200/format/webp
            



    




拾.Redis
    
    Redis是一种开源的高性能键值对存储数据库，支持多种数据结构，如字符串、哈希表、列表、集合、有序集合等。
    它常用于分布式缓存、队列等方面，具有快速、可靠、灵活等特点。Redis支持主从复制、哨兵模式和集群模式，可以满足不同规模和可靠性需求的应用场景。
    除此之外，Redis还提供了丰富的命令和扩展功能，如Lua脚本执行、过期时间设置、发布/订阅功能等。
    
    单点Redis的问题：
        数据丢失问题，Redis是内存存储，服务宕机或重启等情况可能会丢失数据。
            解决：实现Redis数据持久化。
        并发能力问题，Redis是内存存储，并发能力很强，但是毕竟是单节点，诸如618这种过高并发场景就显得无力了。
            解决：搭建主从集群，实现读写分离。
        故障恢复问题，如果Redis宕机，导致的服务不可用，不能影响其他服务的正常使用，需要一种自动的故障恢复手段，需要边运行边修复。而单点Redis无法做到
            解决：利用Redis哨兵，实现健康监测和自动恢复。
        存储能力问题，Redis基于内存，单节点能存储的数据量难以满足海量数据的需求。
            解决：搭建分片集群，利用插槽机制实现动态扩容。
    
    
    Redis数据持久化
        RDB持久化：
            RDB全称（Redis Database Backup file）Redis数据备份文件，也被叫做数据快照，简单来说就是把内存中的所有数据
            都记录到磁盘中，当Redis实例故障重启后，从磁盘读取快照文件，恢复数据。
                快照文件被称为RDB文件，默认是保存在当前运行目录。
                    redis-cli
                    save：由Redis主进程来执行RDB，会阻塞所有命令，最终返回OK则算保存成功。
                    bgsave：开启子进程执行RDB，避免主进程受影响，回车后控制台会立即返回 Background saving started。
                Redis停机时会自动执行一次RDB。
                安装步骤：
                    首先需要在Linux系统中安装一个Redis，如果尚未安装的同学，请参考资料 /lib/day7/Redis集群.md
                    这里推荐一个MacOS下的SSH连接工具：SSH Term Pro
                    CentOS 安装 Redis：（https://technixleo.com/install-redis-server-centos-alma-rhel/）
                        yum install -y gcc tcl
                            安装Redis所需依赖。
                        sudo dnf update --nogpgcheck
                            更新系统软件包。
                        sudo dnf -y install redis --nogpgcheck
                            安装Redis
                        sudo systemctl start redis | redis-server
                            启动Redis
                        sudo systemctl enable redis
                            启用Redis
                        sudo systemctl stop redis
                            停止Redis
                        systemctl status redis
                            查看Redis状态
                        redis-cli ping
                            测试Redis是否可以正常连接
                        redis-cli
                            连接Redis
                    MacOS 安装 Redis：
                        brew install redis
                            安装Redis
                        brew services start redis | redis-server
                            启动Redis
                        brew services stop redis
                            停止Redis
                        brew services info redis
                            查看Redis状态
                        redis-cli ping
                            测试Redis是否可以正常连接
                        redis-cli
                            连接Redis
                        brew install --cask another-redis-desktop-manager
                        安装Redis客户端可视化工具（Another Redis Desktop Manager）
                操作步骤：
                    在github获取和redis-cli相对应的版本（https://github.com/redis/redis/releases?page=3）
                    redis查看版本：
                        redis-cli
                        info
                    放到CentOS的 /tmp 目录下。
                    解压 redis-6.2.7.zip 目录：
                        tar -xvf redis-6.2.7.zip
                    解压后，进入redis目录：
                        cd redis-6.2.7
                    运行Redis编译命令：
                        make && make install
                    然后修改redis.conf文件中的一些配置：
                        # 绑定地址，默认是127.0.0.1，会导致只能在本地访问。修改为0.0.0.0则可以在任意IP访问
                        bind 0.0.0.0
                        # 数据库数量，设置为1
                        databases 1
                    启动Redis：
                        redis-server redis.conf
                    停止redis服务：
                        redis-cli shutdown
                    测试链接：
                        redis-cli ping
                    当ctrl+c执行后，就会返回 Saving the final RDB snapshot before exiting. DB saved on disk 这样的一段信息
                        此时在 redis-server命令所执行的目录下会生成一个dump.rdb文件，这个就是redis的缓存文件，再次启动redis后，数据就会恢复
                        而恢复的数据就是从这个dump.rdb文件中读取的。所以redis默认就有这个持久化机制的。
                    在Redis内部有触发RDB的机制，可以在redis.conf文件中找到。格式如下：
                        # 以下含义表示的是：900秒内，如果至少有1个key被修改，则执行bgsave，如果是 save "" 则表示禁用RDB。
                        # save 900 1
                        save 5 1 # 这里配置个5秒内一次的修改执行bgsave吧。
                    RDB的其他配置可以在redis.conf文件中设置：
                        # 是否压缩，建议不开启，压缩也会消耗cpu，磁盘其实不值钱。看情况如果cpu资源很充足，那么就可以开启压缩模式。
                        rdbcompression yes
                        # RDB文件名称
                        dbfilename dump.rdb
                        # 文件保存的路径目录
                        dir ./
                    再次重新redis-server后，每5秒内执行 set [key] 时
                        控制台就会返回一条信息：1 changes in 5 seconds. Saving...
                        Background saving started by pid 277240
                        意思就是在5秒内 后台异步保存了这次修改，同时当停止redis后，就会保存到配置的test.rdb文件中（原来是dump.rdb）
                总结：
                    RDB方式bgsave的基本流程？
                        1。fork主进程得到一个子进程，共享内存空间。
                        2。子进程读取内存数据并写入新的RDB文件。
                        3。用新的RDB文件替换旧的RDB文件。
                    RDB会在什么时候执行？save 60 1000代表什么含义？
                        默认是服务停止执行。
                        代表60秒内至少执行1000次修改则触发RDB。
                    RDB的缺点？
                        RDB执行间隔时间长，两次RDB之间写入数据有丢失的风险。
                        fork子进程，压缩，写出RDB文件都比较耗时。

        AOF持久化：
            AOF全称为（Append Only File）追加文件，Redis处理文件的每一个写命令都会记录在AOF文件，可以看作是命令日志文件。
            AOF默认是关闭的，需要修改redis.conf配置文件来开启AOF。
                # 是否开启AOF功能，默认是no
                appendonly yes
                # AOF文件的名称
                appendfilename "appendonly.aof"
            AOF的命令记录频率也可以通过redis.conf文件来配：
                # 每执行一次写命令，立即记录到AOF文件
                # 同步刷盘，可靠性高，几乎不丢失数据，但性能影响大。
                appendfsync always
                # 写命令执行完先放入AOF缓冲区, 然后表示每隔1秒将缓冲区数据写到AOF文件,是默认方案
                # 每秒刷盘，性能适中，最多丢失1秒内的数据。
                appendfsync everysec
                # 写命令执行完先放入AOF缓冲区, 由操作系统决定何时将缓冲区内容写回磁盘
                # 操作系统控制写入磁盘，性能最好，可靠性差，可能丢失大量数据。
                appendfsync no
            因为AOF是记录命令, AOF文件会比RDB文件大多, 而且AOF会记录对同一个key的多次写操作, 但只有最后一次写操作才有意义, 可以通过执行bgrewriteaof命令, 可以让AOF文件执行重写功能, 用最少的命令达到相同的效果.
            Redis也会在触发阈值时自动重写AOF文件，阈值也可以在redis.conf中配置：
                # AOF文件比上次文件 增长超过多少百分比，触发重写。
                auto-aof-rewrite-percentage 100
                # AOF文件体积最小多大以上就触发重写
                auto-aof-rewrite-min-size 64mb
            
            总结：
                RDB和AOF各有自己的特点，如果对数据安全性要求较高，在实际开发中往往会结合来使用。
                更明细对比请见：
                    https://blog.csdn.net/weixin_43783509/article/details/89838537?ops_request_misc=&request_id=&biz_id=102&utm_term=RDB%E5%92%8CAOF%E5%8C%BA%E5%88%AB&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-2-89838537.nonecase&spm=1018.2226.3001.4187

    Redis主从：
        搭建主从架构：
            单节点Redis的并发能力是有上限的，要进一步提高Redis的并发能力，就需要搭建主从集群，实现读写分离。
            Redis的集群往往都是主从集群，一个主节点（master）多个从节点（slave/replica）
            因为Redis大多数的应用场景都是读多写少的场景，所以Redis是主从的集群，而不是负载均衡的集群。
            所以一般是在master节点上执行写操作，压力较大的读操作就分发到slave或replica从节点上执行。
            所需要保证的是Redis客户端不管访问哪个从节点，数据结果必须是相同的，那么就需要主节点的数据同步到每一个从节点。
            这就是Redis主从架构的基本模式（最基本也要三台Redis服务，一主两从）
            slave是redis5.0以前从节点的叫法。replica是redis5.0以后从节点的叫法。
            操作步骤：
                详见：lib/day7/Redis集群.md
            
        主从数据同步原理：
            主从第一次同步是全量同步
                master如何判断slave是不是第一次同步数据？这里有两个重要的概念：
                    Replication Id：简称replid，是数据集的标记，ID一致说明是同一个数据集，每一个master都有一个唯一的replid，slave则会继承master节点的replid
                    offset：偏移量，随着记录在repl_baklog中的数据增多而逐渐增大。slave完成同步时也会记录当前同步的offset。
                        如果slave的offset小于master的offset，说明slave数据落后于master，需要更新。
                    因此slave做数据同步，必须向master声明自己的replid和offset，master才能判断到底需要同步哪些数据。
            如果slave重启后同步，则执行增量同步
                repl_baklog大小有上限, 写满后会覆盖最早的数据. 如果slave断开时间过久, 导致尚未备份的数据被覆盖, 则无法基于log做增量同步, 只能再次全量同步
                这种增量同步的问题是没有办法避免的。只能是尽可能的通过Redis主从集群的优化减少概率。
            可以从以下几个方面来优化Redis主从集群：
                在master中配置 repl-diskless-sync yes 启用无磁盘复制（无RDB文件复制），避免全量同步时的磁盘IO。
                    这种应用场景就是磁盘比较慢，但是网络非常快时，可以开启为yes。
                控制单节点Redis的内存上限
                    单节点的内存占用不要太大，减少RDB导致的过多磁盘IO。
                适当提高repl_baklog的大小，发现slave宕机时尽快实现故障恢复, 尽可能避免全量同步
                限制一个master上的slave节点数量,如果实在是太多slave, 则可以采用主-从-从链式结构,减少master压力
                    主节点 A1 为master，
                    从节点 B1 为slave，slaveof A1，以A1作为主节点
                    从节点 B2 为slave，slaveof A1，以A1作为主节点
                    从节点 C1 为slave，slaveof B1，以B1作为主节点
                    从节点 C2 为slave，slaveof B2，以B2作为主节点
                    以上规则就是主-从-从的链式关系，从节点也可以作为其他从节点的主节点。
            总结：
                Redis中的全量同步和增量同步的区别？
                    全量同步：master将完整内存数据生成RDB, 发送RDB到slave. 后续命令则记录在repl_baklog, 做个发送给slave
                    增量同步：slave提交自己的offset到master, master获取repl_baklog中从offset之后的命令给slave
                什么时候执行全量同步？
                    slave节点第一次连接到master时。
                    slave几点断开时间太久，repl_baklog中的offset已经被覆盖时
                什么时候执行增量同步？
                    slave节点断开又恢复，并且在repl_baklog中能找到offset时。
        
    Redis哨兵：            
        哨兵的作用和原理
            哨兵的作用：
                Redis提供了哨兵（Sentinel）机制来实现主从集群的自动故障恢复。哨兵的结构和作用如下：
                    监控：Sentinel会不断检查Redis的master和slave是否按预期工作。
                    自动故障恢复：如果master故障，Sentinel会将一个slave提升为master. 当故障实例恢复后也以新的master为主
                服务状态监控：
                    Sentinel基于心跳机制监测服务状态, 每隔1秒向集群的每个实例发送ping命令
                        主观下线: 如果某Sentinel节点发现某实例为在规定时间响应, 则认为该实例主观下线
                        客观下线: 若超过指定数量(quorum)的Sentinel都认为该实例主观下线, 则该实例客观下线（客观事实）. quorum值最好超过Sentinel实例数量的一半
                选举新的Master：
                    一旦发现master故障,sentinel需要在slave中选择一个作为新的master,选择依据如下:
                        首先会判断slave节点与master节点断开时间长短,如果超过指定值(down-after-millisecond*10)则会排出该slave节点
                        然后判断slave节点的slave-priority值,越小优先级越高,如果是0则永不参与选举
                        如果slave-prority一样,则判断slave节点的offset值, 越大说明数据越新,优先级越高（最重要的依据）
                        最后是判断slave节点的运行ID大小, 越小优先级越高（最不重要的依据）
                实现故障转移：
                    当选中了其中一个slave为新的master后(例如slave1),故障的转移的步骤如下:
                        sentinel给备选节点的slave1节点发送slaveof no one 命令, 让该节点成为master
                        sentinel给所有其他slave发送slaveof 172.16.168.130 7002命令, 让这些slave成为新master的从节点, 开始从新的master上同步数据
                        最后，sentinel将故障节点标记为slave,当故障节点恢复后会自己成为新的master的slave节点
                
            
        搭建哨兵集群
            详见：lib/day7/Redis集群.md
            
        RedisTemplate的哨兵模式
            在Sentinel集群监管下的Redis主从集群, 其节点会因为自动故障转移而发生变化, Redis的客户端必须感知这种变化, 即使更新链接信息.
            Spring的RedisTemplate底层利用lettuce实现了节点的感知和自动切换
            操作步骤：（redis-demo）
                在pom文件中引入redis的starter依赖：
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-data-redis</artifactId>
                    </dependency>
                在配置文件application.yaml中指定sentinel相关信息：
                    配置的并不是Redis集群地址，而是Sentinel的地址。因为Redis主从的地址有变更的可能，不能写死，所以Redis客户端，不需要知道Redis集群地址，只配置redis的sentinel（哨兵）地址即可。
                    配置好后主从切换等所有工作都是由RedisTemplate客户端全自动完成的。
                    spring:
                        redis:
                            sentinel:
                                master: mymaster
                                nodes:
                                    - 172.16.168.130:27001
                                    - 172.16.168.130:27002
                                    - 172.16.168.130:27003
                配置读写分离，为了方便就在启动类中配置：
                    @Bean
                    public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {
                        /**
                         * 匿名内部类写法：
                         * 因为这个接口内部只有customize这一个方法，所以可以直接 new 这个接口 LettuceClientConfigurationBuilderCustomizer
                         */
                        return new LettuceClientConfigurationBuilderCustomizer() {
                            @Override
                            public void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigurationBuilder) {
                                clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
                            }
                        };
                
                        /**
                         * Lambda表达式写法：
                         */
                        // return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
                    }

                    这里的ReadFrom是配置Redis的读取策略, 是一个枚举, 包括下面选择:
                        MASTER: 从主节点读
                        MASTER_PREFERRED: 优先从master节点读取, master不可用才读取replica
                        REPLICA: 从slave(replica)节点读取
                        REPLICA_PREFERRED:优先从slave(replica)节点读取, 所有的slave都不可用才读取master
                
                访问controller进行验证：http://localhost:8080/get/num
                    redis-cli -p 7001
                        get num
                        999
                    这个的get/num其实就是 redis中的num值：999。
                    此时查看Idea中的日志，大致意思为：选中了7002这个从节点执行读取操作。
                访问controller进行验证：http://localhost:8080/set/num/666，返回success表示成功。
                    此时查看Idea中的日志，大致意思为：选中了7003这个主节点执行写入操作。
                将7003这个主节点停掉后重启，RedisTemplate是可以动态发现主从节点变更并切换的。
                    停止redis7003这个节点后，主节点重新选举为7002。idea中会有日志信息打印。
                    无需重启项目，重新访问以上两个controller，可以发现，读操作现在由7002这个新的主节点进行执行。写操作由7001或7003两个从节点的任意一个进行执行。

            
    Redis分片集群
        搭建分片集群
            主从和哨兵可以解决高可用，高并发读的问题，但是依然有两个问题需要解决：
                海量数据存储问题
                高并发写的问题
                使用分片集群可以解决上述问题，分片集群特征：
                    集群中有多个master，每个master保存不同数据。
                    每个master都可以有多个slave节点。
                    master之间通过ping监测彼此健康状态。
                        之前是通过Sentinel哨兵进行监测，现在master之间互相监测，起到哨兵的效果。
                    客户端请求可以访问集群的任意节点，最终都会被转发到正确节点上。
                        具备了哨兵的所有机制，所以就不在需要哨兵机制了。
            搭建步骤：
                详见：lib/day7/Redis集群.md
                
        散列插槽
            Redis会把每一个master节点映射到0~16383共16384个插槽(hash slot)上, 查看集群信息时就能看到
            数据key不是与节点绑定, 而是与插槽绑定. redis会根据key的有效部分计算插槽值, 分两种情况:
                key中包含"{}", 且"{}"中至少包含1个字符, "{}"中的部分是有效部分
                key中不包含"{}", 整个key就是有效部分
                例如: key是num, 那么就根据num计算, 如果是{lee}num, 则根据lee计算. 
                计算方式是利用CRC16算法得到一个hash值, 然后对16384取余, 得到的结果就是slot值.
                因为每个节点有固定的一部分solt值，那么就可以通过slot值知道存在于哪个节点上。
            Redis为何跟插槽绑定而不是根节点绑定呢？
                因为Redis的主节点是可能会出现宕机的情况的，或者是集群扩容增加的节点，或者是集群伸缩删除的节点，都有可能使节点宕机。
                如果一个节点宕机了数据就会跟着丢失。但如果跟插槽绑定后，如果节点宕机时，就可以将节点对应的插槽转移到活着的节点。
                集群扩容时，也可以将插槽转移，这样数据跟着插槽走，就可以永远找到数据所在的位置。
            思考：
                如何将同一类数据固定的保存在同一个Redis实例（节点）？
                    这一类数据使用相同的有效部分，例如key都以{typeId}为前缀
                    语法：set {typeId}num 111

        集群伸缩
            集群伸缩：可以动态的增加或者删除节点。
            添加一个节点到集群：
                redis-cli --cluster 命令提供了很多操作集群的命令，可以通过 help 查看：
                    create：创建节点
                    add-node：添加节点
                需求：
                    启动一个新的Redis实例，端口为7004：
                        cd /tmp
                        mkdir 7004
                        cp redis.conf 7004
                            拷贝redis.conf文件到7004目录下
                        sed -i s/6379/7004/g 7004/redis.conf
                            修改7004/redis.conf文件中的所有6379的端口为7004
                        redis-server 7004/redis.conf
                        ps -ef | grep redis
                            查看7004实例是否成功创建
                        redis-cli --cluster add-node 172.16.168.130:7004 172.16.168.130:7001
                            通过172.16.168.130:7001这个节点找到集群，并添加172.16.168.130:7004节点到集群中。
                        redis-cli -p 7001 cluster nodes
                            查看7004是否成功添加到集群中了
                    添加7004到之前的集群，并作为一个master节点：
                        命令同上
                    给7004节点分配插槽，使得num这个key存储在7004实例：
                        redis-cli --cluster reshard 172.16.168.130:7001
                            重新分片，通过7001节点与集群建立联系然后进行插槽分配
                        How many slots du you want to move(from 1 to 16384)? 3000
                            你想移动多少插槽数量？
                            解释：这里选择3000，因为num在2765这个插槽上，在0和3000之间的一个数量。
                        what is the receiving node ID? 7004的nodeID
                            接收的节点ID是什么？
                            解释：这里意思为 接受这些插槽的节点是哪个？（节点ID）
                        Please enter all the source node IDs.
                            Type "all" to use all the nodes as source nodes for the hash slots.
                            Type "done" once you entered all the source node IDs.
                            Source node #1: 7001的nodeID
                            Source node #2: done
                            解读：
                                输入all就将插槽分配到所有节点上
                                输入done则所有的源节点ID的输入。
                            解释：这里意思为：选择哪个节点作为数据源拷贝到7004？
                                2765插槽在7001节点上，所以要选择7001这个节点作为数据源。
                        Do you want to proceed with the proposed reshard plan(yes/no)? yes
                            是否想要继续执行重新分配的打算？
                        redis-cli -p 7001 --cluster nodes
                            查看7004的的插槽是否重新成功分配到3000
                            30641df6eb77eb711953dbee25f27d3ac1cb82b3 172.16.168.130:7004@17004 master - 0 1685197160751 7 connected 0-2999
                            可以看到日志信息中的7004之后有 connected 0-2999 表示分配成功。

        故障转移
            分片集群虽然没有哨兵，但是也具备故障转移的功能。
            当集群中有一个master宕机会发生什么呢？
                之前7004的节点已经删除了，删除方式通过 redis-cli --cluster help 执行查看命令帮助文档吧。
                
                watch redis-cli -p 7001 cluster nodes
                    监控集群状态：
                redis-cli -p 7002 shutdown
                    停止7002节点
                返回监控的命令中可以实时看到7002这个节点 从 connected 变为 disconnected状态。并且主节点分配到了8001这个节点上。
                redis-server 7002/redis.conf
                    再次启动7002节点
                返回监控的命令中可以查看状态，7002 以从节点（slave）的身份再次成功连接。
                全程都是自动故障转移。
            手动故障转移：
                首先要有一个新的从节点。
                利用cluster failover命令可以手动让集群中的某个master宕机，切换到执行cluster failover命令的这个slave节点上，实现无感知的数据迁移。
                其流程如下：
                    手动的failover支持三种不同的模式：
                        缺省：默认的流程，首先master拒绝任何请求，master返回offset给slave，等待数据offset与master一致，开始故障转移，原来slave标记自己为master，向其他节点广播故障转移的结果。
                        force：省略了对offset的一致性校验。（谨慎使用）
                        takeover：直接进行向其他节点广播。忽略了数据一致性，master状态和其他master意见等步骤。（谨慎使用）
                需求：在7002这个slave节点上执行手动故障转移，重新夺回master地位。
                    步骤如下：
                        利用redis-cli连接7002这个节点：
                            redis-cli -p 7002
                        执行cluster failover命令：
                            CLUSTER FAILVOER
                        也可以一步到位，等同于以上的两步骤：
                            redis-cli -p 7002 CLUSTER FAILVOER

        RedisTemplate访问分片集群
            RedisTemplate底层同样基于lettuce实现了分片集群的支持，而且使用的步骤与哨兵模式基本一致：
                引入redis的starter依赖
                    参见：RedisTemplate的哨兵模式
                配置分片集群地址（application.yml)
                    与哨兵模式相比，其中只有分片集群的配置方式略有差异，差异如下：
                        spring:
                            redis:
                                cluster:
                                    nodes:
                                        - 172.16.168.130:7001
                                        - 172.16.168.130:7002
                                        - 172.16.168.130:7003
                                        - 172.16.168.130:8001
                                        - 172.16.168.130:8002
                                        - 172.16.168.130:8003
                配置读写分离
                    参见：RedisTemplate的哨兵模式
            访问：http://localhost:8080/get/num
                返回123，查看控制台是从8003这个从节点上读的，说明是自动支持读写分离的，主节点只管写，从节点只管读。
            访问：http://localhost:8080/set/num/123
                返回success，查看控制台从7001这个主节点上写的。而不是在从节点上写的。至此验证完毕。










拾.多级缓存
    
    多级缓存：亿级流量的缓存方案
    缓存的作用：减轻数据库的压力。缩短服务的响应时间，从而提高服务的并发能力。
    传统缓存的问题：
        传统的缓存策略一般是请求到达Tomcat后，先查询Redis，如果命中则直接返回，如果未命中则查询数据库，存在下面的问题：
            所以大多数请求都由Redis进行处理，只有少数请求到达数据库，很大程度减少数据库压力。
            1。请求要经过Tomcat处理，Tomcat的性能成为整个系统的瓶颈。
            2。Redis缓存因为淘汰策略有过期的可能性的，所以当部分缓存失效时，会对数据库产生冲击。
    多级缓存方案：
        多级缓存就是充分的利用请求处理的每一个环节，分别添加缓存，减轻Tomcat压力，提升服务性能：
            比如请求会通过的客户端缓存，Nginx集群缓存，然后通过nginx也可以直接访问Redis，无需通过Tomcat，以减轻Tomcat的压力，Tomcat进程缓存又称JVM进程缓存，当所有的缓存都未能命中时才访问数据库。
            这个流程链中服务端需要掌握的技能：
                Tomcat进程缓存（JVM进程缓存）
                Nginx内部编程语言（Lua）
                Lua掌握之后就可以实现Nginx本地缓存，Redis缓存，Tomcat缓存等等多级缓存方案了。
                缓存同步策略，数据库和缓存之间做同步。
    
    JVM进程缓存：
        导入商品案例：
            参考：lib/day8/案例导入说明.md，所属项目：item-service
            案例导入说明.md 中的 nginx目录位于MacOS的：/usr/local/nginx
            
            
        初识Caffeine：
            本地进程缓存：
                缓存在日常开发中启着至关重要的作用, 由于是存储在内存中, 数据的读取速度是非常快的, 能大量减少对数据库的访问, 减少数据库的压力, 我们把缓存分为两类：
                    分布式缓存，例如Redis：
                        优点：存储容量更大，可靠性更好，可以在集群之间共享
                        缺点：从Tomcat像Redis的请求访问缓存有网络开销
                        场景：缓存数据量较大，可靠性要求较高，需要在集群间共享
                    进程本地缓存，例如HashMap，GuavaCache：
                        优点：读取本地内存，没有网络开销，速度更快。
                        缺点：存储容量有限，可靠性较低，无法共享
                        场景：性能要求较高，缓存数据量较小
                实际上进程本地缓存是作为Redis分布式缓存的一种补充使用的，企业开发中一定是两者结合使用的。
                Caffeine是一个基于java8开发的，提供了近乎最佳命中率的高性能的本地缓存库. 目前spring内部的缓存使用的就是Caffeine.
                Caffeine的Github地址：https://github.com/ben-manes/caffeine
                Caffeine示例：
                    通过item-service项目中的单元测试来学习Caffeine的使用：(CaffeineTest.testBasicOps)
                        /**
                         * 基本用法测试
                         */
                        @Test
                        void testBasicOps() {
                            Cache<Object, Object> cache = Caffeine.newBuilder().build();
                            // 存数据
                            cache.put("girlfriend","leechenhui");
                            // 取数据
                            String girlfriend = (String) cache.getIfPresent("girlfriend");
                            System.out.println("girlfriend = " + girlfriend);
                            // 取数据，如果未命中，则执行第二个参数查询数据库
                            Object defaultGF = cache.get("defaultGF", key -> {
                                // 根据key取数据库查询数据。
                                return "liuyan";
                            });
                            // 小技巧记录：soutv ==> System.out.println("defaultGF = " + defaultGF);
                            System.out.println("defaultGF = " + defaultGF);
                        }
                Caffeine提供了三种缓存驱逐策略：
                    基于容量：设置缓存的数量上限
                        @Test
                        void testEvictByNum() throws InterruptedException {
                            // 创建缓存对象
                            Cache<String, String> cache = Caffeine.newBuilder()
                                    // 设置缓存大小上限为 1
                                    .maximumSize(1)
                                    .build();
                            // 存数据
                            cache.put("gf1", "柳岩");
                            cache.put("gf2", "范冰冰");
                            cache.put("gf3", "迪丽热巴");
                            // 延迟10ms，给清理线程一点时间
                            Thread.sleep(10L);
                            // 获取数据
                            System.out.println("gf1: " + cache.getIfPresent("gf1"));
                            System.out.println("gf2: " + cache.getIfPresent("gf2"));
                            System.out.println("gf3: " + cache.getIfPresent("gf3"));
                        }
                    基于时间：设置缓存的有效时间
                        @Test
                        void testEvictByTime() throws InterruptedException {
                            // 创建缓存对象
                            Cache<String, String> cache = Caffeine.newBuilder()
                                    .expireAfterWrite(Duration.ofSeconds(1)) // 设置缓存有效期为 10 秒
                                    .build();
                            // 存数据
                            cache.put("gf", "柳岩");
                            // 获取数据
                            System.out.println("gf: " + cache.getIfPresent("gf"));
                            // 休眠一会儿
                            Thread.sleep(1200L);
                            System.out.println("gf: " + cache.getIfPresent("gf"));
                        }
                    基于引用：设置缓存为软引用或弱引用, 利用GC来回收缓存数据。
                        性能较差, 不建议使用
                    在默认情况下，当一个缓存元素过期的时候，Caffeine不会自动立即的将其清理和驱逐，而是再一次读或写操作后，或者在空闲时间完成对失效数据的驱逐。

        实现进程缓存：
            需求：实现商品查询的本地进程缓存
                利用Caffeine实现以下需求：
                    给根据ID查询商品的业务添加缓存，缓存未命中时查询数据库
                    给根据ID查询商品库存的业务添加缓存，缓存未命中时查询数据库
                    缓存初始化大小为100
                    缓存上限为10000
                操作步骤：
                    编写Caffeine的配置类：
                        @Configuration
                        public class CaffeineConfig {
                            @Bean
                            public Cache<Long, Item> itemCache() {
                                // 缓存初始化大小为100，缓存上限为10000
                                return Caffeine.newBuilder().initialCapacity(100).maximumSize(10000).build();
                            }
                            @Bean
                            public Cache<Long, ItemStock> stockCache() {
                                // 缓存初始化大小为100，缓存上限为10000
                                return Caffeine.newBuilder().initialCapacity(100).maximumSize(10000).build();
                            }
                        }
                    在controller中对查询业务进行JVM进程缓存处理。
                        @Autowired
                        private Cache<Long, Item> itemCache;
                        @Autowired
                        private Cache<Long, ItemStock> stockCache;
                        
                        @GetMapping("/{id}")
                        public Item findById(@PathVariable("id") Long id){
                            // 给根据ID查询商品的业务添加缓存，缓存未命中时查询数据库
                            return itemCache.get(id, key -> itemService.query()
                                    .ne("status", 3).eq("id", key)
                                    .one()
                            );
                        }
                    
                        @GetMapping("/stock/{id}")
                        public ItemStock findStockById(@PathVariable("id") Long id){
                            // 给根据ID查询商品库存的业务添加缓存，缓存未命中时查询数据库
                            return stockCache.get(id, key -> stockService.getById(key));
                        }
                    
                访问验证：
                    访问 http://localhost:8081/item/10001：
                        第一次会打印如下信息（数据库sql查询的信息）
                            ==> Preparing: SELECT id,name,title,price,image,category,brand,spec,status,create_time,update_time FROM tb_item WHERE (status <> ? AND id = ?)
                            ==> Parameters: 3(Integer), 10001(Long)
                            ==> Total: 1
                        第二次再次刷新查询后直接读取缓存，以上的信息不会打印，证明第二次是从缓存中读取的数据，而非从数据库读取的。
                    访问 http://localhost:8081/item/stock/10001：
                        第一次会打印如下信息（数据库sql查询的信息）
                            ==> Preparing: SELECT id,name,title,price,image,category,brand,spec,status,create_time,update_time FROM tb_item WHERE (status <> ? AND id = ?)
                            ==> Parameters: 3(Integer), 10001(Long)
                            ==> Total: 1
                        第二次再次刷新查询后直接读取缓存，以上的信息不会打印，证明第二次是从缓存中读取的数据，而非从数据库读取的。
                    
        
    Lua：
        上个章节实现了Nginx方向代理到Nginx集群处理本地缓存和Jvm的进程缓存。
        这个章节由于要在Nginx集群中使用本地缓存，对Redis 或 Tomcat（Jvm进程缓存）缓存的查询。
        所以要在Nginx中做业务编码，而在Nginx中做业务逻辑就需要一门新的语言：Lua
        
        初识Lua
            Lua是一中轻量小巧的脚本语言，用C语言编写并以源代码形式开放，其设计的目的是为了嵌入应用程序中。
            C语言和Lua是可以相互之间做引用的，从而为应用提供灵活的扩展和定制功能。官网：https://www.lua.org/
            注意：因为CentOS环境默认有Lua环境，所以之后步骤全程在CentOS9中操作：
                在 /learn/lua 目录下新建hello.lua文件：
                    touch hello.lua
                添加内容：
                    print("hello world")
                    或：
                    vim hello.lua
                运行：
                    lua hello.lua
        
            数据类型：
                nil：用于表示一个无效值（在条件表达式中相当于flase）
                boolean：同JavaScript
                number：表示双精度类型的实浮点数，小数，整数都用number表示。
                string：同JavaScript
                function：由C或者Lua编写的函数
                table：Lua中的表（table）其实是一个关联数组，数组的索引可以是数字，字符串或表类型，所以可以看做table既是array也是map。
                补充：
                    可以在命令行直接输入 lua，即可进入lua编辑模式。
                    可以利用type函数测试给定变量的类型：
                        pring(type('hello world'))
                        string
                        pring(type(10.4*3))
                        number
            变量：
                lua声明变量的时候，并不需要指定数据类型：
                    声明字符串：
                        local str = 'hello'
                    声明数字：
                        local num = 123
                    声明布尔类型：
                        local flag = true
                    声明 table 作为 arr：
                        local arr = {'java', 'python', 'lua'}
                    声明 table 作为 map：
                        local map = {name='jack', age=20}
                补充：
                    local表示局部变量声明，没有local表示全局变量声明。
                    访问数组：print(arr[1])
                    注意：数组下标是从1开始计的。
                    访问Map：print(map['name'])
                    访问Map：print(map.name)
                    字符串拼接：
                        local str = 'hello' .. 'world'
                        print(str)
                        helloworld
            循环：
                table可以利用for循环来遍历：
                    遍历数组：
                        -- 声明数组
                        arr = {'java', 'python', 'lua'}
                        -- 遍历数组
                        for ind,val in ipairs(arr) do
                            print(ind,val)
                        end
                        解读：
                            -- 是lua中的语法注释。
                            ind 和 val 是变量名可以随便起，只要不是关键字即可。
                            ipairs()是针对数组的解析函数。
                            do等同{，end等同}。
                    遍历Map：
                        -- 声明Map
                        map = {name='jack', age=20}
                        -- 遍历Map
                        for key,val in pairs(map) do
                            print(key,val)
                        end
                        解读：
                            其他同上，如果是Map的话第一个就是key，而不是index了。
                            pairs()是对map的解析函数。
            函数：
                定义函数的语法：
                    function foo(argument1, argument2, argument3) 
                        -- 函数体
                        return 'value'
                    end
                示例：
                    function foo(arr)
                        for ind,val in ipairs(arr) do
                            print(ind,val)
                        end
                    end
                注意：
                    function的起使位不能有 do，也就是没有 { 这前半个花括号
            条件控制：
                关键字解释：
                    if：不多说了
                    else：不多说了
                    then：类似do，用以表示前半个花口号 "{"
                    end：用以表示后半个花括号 "}"
                    语法结构：
                        if(布尔表达式)
                        then
                            -- 布尔表达式为 ture 时执行该语句块
                        else
                            -- 布尔表达式为 false 时执行该语句块
                        end
                    示例：
                        
                逻辑运算符：
                    and：与
                    or：或
                    not：非
                    
    多级缓存：
        OpenResty：
            OpenResty是基于Nginx的高性能Web平台。用于方便的搭建能够处理超高并发，扩展性极高的动态Web应用，Web服务和动态网关
                特点：
                    具备Nginx的完整功能
                    基于Lua语言进行扩展，集成了大量精良的Lua库，第三方模块。
                    允许使用Lua自定义业务逻辑，自定义库。
                官网地址：https://openresty.org/cn/
                安装流程见：lib/day8/安装OpenResty.md
                    注意这个流程中将 CentoS 的nginx环境变量目录配到了openResty下的nginx中了。
                实现案例：
                    商品查询页的查询案例：
                        注意本机（MacOS）Nginx的服务是用于转发用的。
                            nginx 目录位于 /usr/local/nginx
                        远程（CentOS）Nginx的集群是用于业务开发的。
                            nginx 目录位于 /usr/local/openresty/nginx
                        商品详情页面目前是假数据。在控制台可以看到查询商品信息的请求：
                            http://localhost/api/item/10001
                            这个请求最终会被本机MacOS的Nginx方向代理到 远程的CentOS的 OpenResty nginx 的业务集群中去。
                                # OpenResty(Nginx)的业务集群,用于Nginx本地缓存,Redis缓存,Tomcat查询.
                                upstream nginx-cluster{
                                    server 172.16.168.130:8081;
                                }
                                server {
                                    listen       80;
                                    server_name  localhost;
                            
                                    location /api {
                                        proxy_pass http://nginx-cluster;
                                    }
                            
                                    location / {
                                        root   html;
                                        index  index.html index.htm;
                                    }
                            
                                    error_page   500 502 503 504  /50x.html;
                                    location = /50x.html {
                                        root   html;
                                    }
                                }
                    需求：在虚拟机OpenResty中接受这个请求，并返回一段商品的假数据：
                        修改nginx.conf文件
                            在nginx.conf的http下面，添加对OpenResty的Lua模块加载：
                                #lua 模块
                                lua_package_path "/usr/local/openresty/lualib/?.lua;;";
                                #c模块     
                                lua_package_cpath "/usr/local/openresty/lualib/?.so;;";  
                            在nginx.conf的server下面，添加对/api/itme这个路径监听：
                                # 监听 /api/item 这个地址
                                location /api/item {
                                    # 响应类型.
                                    default_type application/json;
                                    # 响应结果由 /lua/item.lua 这个文件决定. 注意 /lua/item.lua 这个路径会从nginx目录下找，所以去新建这个文件（nginx/lua/item.lua）
                                    content_by_lua_file lua/item.lua;
                                }
                        编写item.lua文件
                            item.lua 的内容如下：
                                ngx.say('{"id":10001,"name":"SALSA AIR","title":"RIMOWA 30寸托运箱拉杆箱 SALSA AIR系列果绿色 820.70.36.4","price":10000,"image":"https://m.360buyimg.com/mobilecms/s720x720_jfs/t6934/364/1195375010/84676/e9f2c55f/597ece38N0ddcbc77.jpg!q70.jpg.webp","category":"拉杆箱","brand":"RIMOWA","spec":"","status":1,"createTime":"2019-04-30T16:00:00.000+00:00","updateTime":"2019-04-30T16:00:00.000+00:00","stock":2999,"sold":31290}')
                                注意：这里把 title 和 price 字段更改了，原来item.html中的假数据在（MacOS的Nginx的item.html中）。
                            重新加载配置：
                                nginx -s reload
                            然后可以看到：
                                price（价格） 和 title（标题） 这两个字段变更了。

        请求参数处理：
            OpenResty提供了各种API用来获取不同类型的请求参数：
                路径占位符：（/item/10001）
                请求头：（id：10001）
                Get请求参数：（?id=10001）
                Post请求参数：（id=1001）
                JSON参数：（{"id":1001}）
            实现案例：
                http://localhost/api/item/10001
                在OpenResty中接收这个请求，并获取路径中的id信息，拼接到结果的json字符串中返回。
                    在虚拟机的 /usr/local/openresty/nginx/lua/itme.lua 文件修改：
                        -- 获取路径参数
                        local id = ngx.var[1];
                        -- 返回结果
                        ngx.say('{"id":' .. id .. ',"name":"SALSA AIR","title":"RIMOWA 30寸托运箱拉杆箱 SALSA AIR系列果绿色 820.70.36.4","price":10000,"image":"https://m.360buyimg.com/mobilecms/s720x720_jfs/t6934/364/1195375010/84676/e9f2c55f/597ece38N0ddcbc77.jpg!q70.jpg.webp","category":"拉杆箱","brand":"RIMOWA","spec":"","status":1,"createTime":"2019-04-30T16:00:00.000+00:00","updateTime":"2019-04-30T16:00:00.000+00:00","stock":2999,"sold":31290}')
                    在虚拟机的 /usr/local/openresty/nginx/conf/nginx.conf 文件修改：
                        # 这里是路径占位符 ~ 表示RegExp正则匹配。
                        location ~ /api/item/(\d+) {
                            # 响应类型.
                            default_type application/json;
                            # 响应结果由 /lua/item.lua 这个文件决定. 注意 /lua/item.lua 这个路径会从nginx目录下找，所以取新建这个文件（nginx/lua/item.lua）
                            content_by_lua_file lua/item.lua;
                        }
                    保存后重启 nginx -s reload
                    http://localhost/item.html?id=10003 可以看到返回的id字段是实时变更的了。

        查询Tomcat：
            现在开始处理对Tomcat的请求。来获取Tomcat的进程缓存（JVM进程缓存）。
            由于虚拟机和本机的地址不同。这里要在虚拟机访问本机需要的到本机IP进行访问
            注意：CentOS地址为 172.16.168.130，本机地址为：172.16.168.1
            这是铁律：ip前三位是相同的，本机的IP地址最后以为永远是1。虚拟机：xxx.xxx.xxx.xxx，本机：xxx.xxx.xxx.1
            实现案例：
                获取请求中的商品id信息，根据ID向Tomcat查询商品信息：
                    封装Http请求函数：（/usr/local/openresty/lualib/common.lua）
                        -- 封装函数，发送http请求，并解析响应
                        local function read_http(path, params)
                            local resp = ngx.location.capture(path,{
                                method = ngx.HTTP_GET,
                                args = params,
                            })
                            
                            if not resp then
                                -- 记录错误信息，返回404
                                ngx.log(ngx.ERR, "http not found, path: ", path , ", args: ", args)
                                ngx.exit(404)
                            end
                            return resp.body
                        end
                        -- 将方法导出
                        local _M = {  
                            read_http = read_http
                        }
                        return _M
                    拦截发起的请求,反向代理到本机的目标服务器：（/usr/local/openresty/nginx/conf/nginx.conf）
                        # 拦截发起的请求,反向代理到本机的目标服务器
                        location /item {
                            proxy_pass http://172.16.168.1:8081;
                        }
                    根据ID向Tomcat服务发请求，查询商品信息：（/usr/local/openresty/nginx/lua/item.lua）
                        -- 导入common.lua函数库,这里直接是在 lualib 目录下, 就可以直接写lualib目录下的所有文件名.
                        local common = require('common');
                        local read_http = common.read_http;
                        -- 获取路径参数
                        local id = ngx.var[1];
                        -- 查询商品信息
                        local itemJson = read_http("/item/" .. id, nil);
                        -- 查询库存信息
                        local itemStockJson = read_http("/item/stock/" .. id, nil);
                        -- 返回结果
                        ngx.say(itemJson);
                    根据ID向Tomcat服务发请求，查询库存信息：（/usr/local/openresty/nginx/lua/item.lua）
                        同上
                        此时访问 http://localhost/item.html?id=10003 id在5之内随便测试，可以看到是从 本机 172.16.168.1:8081 的 item-service 中获取的真实数据。
                    组装商品信息，库存信息，序列化为JSON格式并返回：（/usr/local/openresty/nginx/lua/item.lua）
                        -- JSON转换为Lua的Table格式
                        local item = cjson.decode(itemJson)
                        local itemStock = cjson.decode(itemStockJson)
                        -- 组合数据
                        item.stock = itemStock.stock
                        item.sold = itemStock.sold
                        -- 把Item序列化为JSON返回
                        ngx.say(cjson.encode(item));
                        此时再次访问 http://localhost/item.html?id=10003 可以看到 stock 和 sold 字段都有了返回值。
                Tomcat的集群配置和负载均衡配置：    
                    在openResty中对nginx进行集群配置：（/usr/local/openresty/nginx/conf/nginx.conf）
                        # tomcat集群, nginx会自动做负载均衡, 默认是轮询策略.
                        # hash $request_uri 解释: $request_uri就是请求路径, 是一种负载均衡（默认轮询机制不变）算法, 即:只要请求路径没有变, 那么它就保证一个请求永远访问同一台tomcat服务器.
                        # 为什么这样做, 因为tomcat服务之间的进程缓存是无法共享的. 所以一个请求访问同一台tomcat服务器用来保证缓存生效.
                        # 所以如果没有这个$request_uri配置, 同一个请求对多台tomcat服务器做请求,那么每台tomcat服务器都要对这个请求进行缓存,很没必要,效率极差.
                        # 保证一个请求永远对应一台tomcat服务器, 这就是 hash $request_uri; 的作用.
                        upstream tomcat-cluster {
                            hash $request_uri;
                            server 172.16.168.1:8081;
                            server 172.16.168.1:8082;
                        }
                        
                        # 这里从原地址改为集群的地址，原地址配置：http://172.16.168.1:8081;
                        location /item {
                            proxy_pass http://tomcat-cluster;
                        }
                    在item-service服务启动8082端口，这里作用的是第二台tomcat服务：
                        选择 Edit Configurations...
                        将ItemApplication复制一份为ItemApplication2
                        修改配置：VM Options：-Dserver.port=8082  ==>  Apply  ==>  OK
                    测试：
                        http://localhost/item.html?id=10001
                        结果负载到了8082这台服务器上，清空Idea日志后，再刷新十次，8081也不会有日志，他永远不会再负载到8081上了，这就是 hash $request_uri 的作用。
                        当然8082也不会再有日志了，因为做过进程缓存了。然后切换id再次访问重复测试，因为默认轮询的机制，也同样必然会在两个服务之间轮询访问。多次验证没有问题。
                    
        Redis缓存预热：
            按着最初多级缓存的设想，请求到达OpenResty之后不能直接查询Tomcat，应该优先查询Redis，在Redis缓存未命中时，再查询Tomcat。
            所以这个章节就要加入Redis缓存的相关功能了。但是Redis缓存会面临一个问题，即：冷启动，缓存预热。
            冷启动：服务刚刚启动时，Redis中并没有缓存，如果所有商品数据都在第一次查询时添加缓存，可能会给数据库带来较大压力。
            解决方案：缓存预热。
            缓存预热：在实际开发中，我们可以利用大数据统计用户访问的热点数据（常用数据），在项目启动时将这些热点数据提前查询并保存到Redis中。
            注：我们数据较少，可以在启动时将所有数据都放入缓存中。
            操作步骤：
                在虚拟机用Docker安装Redis（之前是在CentOS系统直接安装的Redis，这里使用docker安装）
                    docker run --name myredis -p 6379:6379 --network host -d redis redis-server --appendonly yes
                    注意：这里如果无法在本机Mac上连接到虚拟机中的docker服务（首先保证IP是通的），就加入--network host这个参数。
                在itme-service服务中引入Redis依赖
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-data-redis</artifactId>
                    </dependency>
                配置Redis地址
                    spring:
                        redis:
                            host: 172.16.168.130
                编写初始化缓存的配置类：
                    @Configuration
                    public class RedisHandler implements InitializingBean {
                    
                        @Autowired
                        private StringRedisTemplate redisTemplate;
                    
                        @Autowired
                        private IItemService iItemService;
                        @Autowired
                        private IItemStockService iItemStockService;
                    
                        // 静态常量工具，用以序列化和反序列化Json
                        private static final ObjectMapper MAPPER = new ObjectMapper();
                    
                        /**
                         * InitializingBean.afterPropertiesSet
                         * 这个Bean的afterPropertiesSet方法会在 bean（RedisHandler）创建完，且@Autowired注入成功之后执行。
                         * 那么它就可以在项目启动后就执行，这样来实现缓存预热预存。
                         * @throws Exception
                         */
                        @Override
                        public void afterPropertiesSet() throws Exception {
                            /**
                             * 初始化缓存
                             */
                            // 1.1查询商品信息
                            List<Item> itemList = iItemService.list();
                            // 1.2放入缓存
                            for (Item item : itemList) {
                                // item序列化为JSON
                                String itemJson = MAPPER.writeValueAsString(item);
                                // 存入Redis，一般我们不能直接存ID，因为有冲突的可能，所以这里加一个 item:id: 的前缀。
                                redisTemplate.opsForValue().set("item:id:" + item.getId(), itemJson);
                            }
                    
                            // 2.1查询库存信息
                            List<ItemStock> itemStockList = iItemStockService.list();
                            // 2.2放入缓存，步骤同上。
                            for (ItemStock itemStock : itemStockList) {
                                String itemStockJson = MAPPER.writeValueAsString(itemStock);
                                redisTemplate.opsForValue().set("item:stock:id:" + itemStock.getId(), itemStockJson);
                            }
                        }
                    }

        查询Redis缓存：
            OpenResty的Redis模块：
                OpenResty提供了操作Redis的模块，只需要引入该模块就能直接使用：
                    引入Redis模块，并初始化Redis对象：（/usr/local/openresty/lualib/common.lua）
                    封装函数，用来释放Redis连接，其实是放入连接池：
                    封装函数，从Redis读取数据并返回：
                        /usr/local/openresty/lualib/common.lua:
                            -- 导入Redis模块(这里指的是resty/redis.lua) 
                            local redis = require("resty.redis");
                            -- 初始化Redis
                            local red = redis:new();
                            -- 设置超时时间
                            red:set_timeouts(1000,1000,1000);
    
                            -- 关闭redis连接的工具方法，其实是放入连接池
                            local function close_redis(red)
                                local pool_max_idle_time = 10000 -- 连接的空闲时间，单位是毫秒
                                local pool_size = 100 --连接池大小
                                local ok, err = red:set_keepalive(pool_max_idle_time, pool_size)
                                if not ok then
                                    ngx.log(ngx.ERR, "放入redis连接池失败: ", err)
                                end
                            end
    
                            -- 查询redis的方法 ip和port是redis地址，key是查询的key
                            local function read_redis(ip, port, key)
                                -- 获取一个连接
                                local ok, err = red:connect(ip, port)
                                if not ok then
                                    ngx.log(ngx.ERR, "连接redis失败 : ", err)
                                    return nil
                                end
                                -- 查询redis
                                local resp, err = red:get(key)
                                -- 查询失败处理
                                if not resp then
                                    ngx.log(ngx.ERR, "查询Redis失败: ", err, ", key = " , key)
                                end
                                --得到的数据为空处理
                                if resp == ngx.null then
                                    resp = nil
                                    ngx.log(ngx.ERR, "查询Redis数据为空, key = ", key)
                                end
                                close_redis(red)
                                return resp
                            end
    
                            -- 将方法导出
                            local _M = {
                                read_http = read_http,
                                read_redis = read_redis
                            }
                            return _M

                        /usr/local/openresty/nginx/lua:
                            -- 导入cjson库, 即: lualib目录下的 cjson.so 这个包. 它是OpenResty提供的用来处理JSON的序列化和反序列化的.
                            local cjson = require('cjson');
                            -- 导入common.lua函数库,这里直接是在 lualib 目录下, 就可以直接写lualib目录下的所有文件名.
                            local common = require('common');
                            local read_http = common.read_http;
                            local read_redis = common.read_redis;
                            
                            -- 封装查询函数:Redis缓存中是否有该条数据,如果没有则查询数据库.
                            function read_data(key, path, params)
                                -- 查询Redis(openresty和redis同在虚拟机中,所以这里可以直接使用"127.0.0.1")
                                local resp = read_redis("127.0.0.1", 6379, key);
                                -- 判断查询结果
                                if not resp then
                                    ngx.log("Redis查询失败,尝试查询http, key",key);
                                    -- redis查询失败,查询http(查询Tomcat)
                                    resp = read_http(path, params)
                                end
                                return resp;
                            end
                            
                            -- 获取路径参数
                            local id = ngx.var[1];
                            -- 查询商品信息
                            local itemJson = read_data("item:id:" .. id, "/item/" .. id, nil);
                            -- 查询库存信息
                            local itemStockJson = read_data("item:stock:id:" .. id, "/item/stock/" .. id, nil);
                            -- JSON转换为Lua的Table格式
                            local item = cjson.decode(itemJson)
                            local itemStock = cjson.decode(itemStockJson)
                            -- 组合数据
                            item.stock = itemStock.stock
                            item.sold = itemStock.sold
                            -- 把Item序列化为JSON返回
                            ngx.say(cjson.encode(item));
                    测试：
                        重启Nginx以更新配置，然后停掉Tomcat服务后，再次刷新 http://localhost/item.html?id=10003
                        这个地址进行测试：如果能够正常返回数据则表示成功。
                        因为（Redis缓存预热）章节已经把所有数据导入Redis缓存中，所以能够正常从Redis中读取数据。

        Nginx本地缓存：
            前面的所有小章节，已经实现了多级缓存的本地缓存集群搭建，优先查询Redis缓存，Tomcat进程缓存，和MacOS本机对的单机Nginx反向代理到CentOS的nginx缓存集群。
            那么这个章节就要实现在这个Ngixn业务缓存集群中进行nginx的本地缓存了。
            OpenResty为Nginx提供了shard dict（共享词典）的功能，可以在nginx的多个worker之间共享数据，实现缓存功能。
                补充：在nginx中往往有一个master进程和多个worker进程，多个worker进程可以处理请求，它们的共享就等于是共享内存了，
                    但是这也只是在nginx内部做共享，如果部署了多台openResty，那么它们之间是无法共享的。
                需求：
                    修改item.lua中的read_data函数，优先查询本地缓存，为命中时再查询Redis，Tomcat。
                    查询Redis或Tomcat成功后，将数据写入本地缓存，并设置有效期。
                    商品基本信息，有效期30分钟（1800）；商品库存信息配置有效期为1分钟（60）。
                        因为商品基本信息是属于常年不怎么变化的数据，而库存相关的数据就会变化的频率比较高，所以有效期尽可能设置的短一点。
                操作步骤：
                    开启共享字典，在nginx.conf的http下添加配置：
                        # 添加共享词典, 开启Nginx本地缓存, 缓存名称是item_cache, 缓存大小为150m, 因为CentOS系统配置时的内存不大所以这里给小点,150m足够了.
                        lua_shared_dict item_cache 150m;
                    在/usr/local/openresty/nginx/lua/item.lua中操作共享字典：
                        -- 导入共享词典, 开启Nginx本地缓存, 缓存名称是item_cache, 缓存大小为150m.
                        local item_cache = ngx.shared.item_cache;
                        -- 封装查询函数:Redis缓存中是否有该条数据,如果没有则查询数据库.
                        local function read_data(key, expire,path, params)
                            -- 查询Nginx本地缓存
                            local val = item_cache:get(key);
                            if not val then
                                ngx.log(ngx.ERR, "本地缓存查询失败, 尝试查询Redis, key", key);
                                -- 查询Redis(openresty和redis同在虚拟机中,所以这里可以直接使用"127.0.0.1")
                                val = read_redis("127.0.0.1", 6379, key);
                                -- 判断查询结果
                                if not val then
                                    ngx.log(ngx.ERR, "Redis查询失败,尝试查询http, key",key);
                                    -- redis查询失败,查询http(查询Tomcat)
                                    val = read_http(path, params)
                                end
                            end
                            -- 查询成功后先把数据写入Nginx本地缓存.参数为:键,值,有效期.
                            -- 设置有效期可以在有效期内进行清除, 避免缓存越存越多, 如果expire为0时,那么将永久有效.
                            item_cache:set(key,val,expire)
                            return val;
                        end
                    测试：
                        重启Nginx以更新配置，并监控日志，以方便查看在ngixn中编写的lua程序的日志打印信息和报错信息。
                            tail -f logs /usr/local/openresty/nginx/logs/error.log
                            访问：http://localhost/item.html?id=10001
                            第一次访问ID为10001的商品时，日志会有Lua中输出的日志信息：本地缓存查询失败, 尝试查询Redis, keyitem:id:10003
                            第二次访问id为10001的就已经存入nginx本地缓存了，就不会有走Redis缓存查询和http缓存查询（Tomcat缓存）了。
                            那么这些已经缓存到Nginx本地的数据，即使是停止Redis服务，停止Tomcat服务，停止数据库MySql服务，都不会受到影响。
                            支持验证完毕！
    缓存同步策略：
        常见方式有三种：
            设置有效期: 给缓存设置有效期, 到期后自动删除. 再次查询时更新
                优势：简单，方便。
                缺点：时效性差，缓存过期之前可能不一致。
                场景：更新频率低，时效性要求低的业务。
            同步双写：在修改数据库的同时，直接修改缓存数据
                优势：时效性强，缓存与数据库强一致。
                缺点：有代码侵入，耦合度高。
                场景：对一致性，时效性要求较高的缓存数据。
            异步通知：修改数据库时发送事件通知，相关服务监听到通知后修改缓存数据。
                优势：低耦合，可以同时通知多个缓存服务。
                缺点：时效性一般，可能存在中间不一致的状态。
                场景：时效性要求一般，有多个服务需要同步。
                方案：
                    基于MQ的异步通知
                    基于Canal的异步通知（相比MQ的异步通知，代码侵入更低，耦合性更低）
        Canal：
            Canal是阿里巴巴旗下的一款开源项目，基于Java开发，基于数据库增量日志解析，提供增量数据订阅&消费，Github地址：https://github.com/alibaba/canal
            Canal是基于mysql的主从同步来实现的，MySql主从同步的原理如下:
                Mysql master 将数据变更写入二进制日志文件(binary log), 其中记录的数据叫做binary log events
                Mysql slave 将 master 的 binary log events 拷贝到它的中继日志(relay log)
                Mysql Slave 重放 relay log 中事件, 将数据变更反映射它自己的数据，也就是说主节点做了哪些Sql，从节点也做哪些Sql。这样来保证数据的一致性。
            Canal实际上就是把自己伪装成Mysql的一个slave节点，从而监听master的binary log变化。
                再把得到的变化信息通知给Canal的客户端，进而完成对其他服务或数据库（如Redis，MQ，ES，Mysql）的同步
            所以要使用Canal，还要实现mysql的主从同步，所以他的部署会相对麻烦一点。
                安装配置Canal请参考：lib/day8/安装Canal.md
                监听canal：
                    docker exec -it canal bash
                        tail -f /canal-server/logs/canal/canal.log
                            canal的运行日志监听
                        tail -f /canal-server/logs/heima/heima.log
                            heima的运行日志监听
                    exit
            Canal提供了各种语言的客户端，当Canal监听到Binlog变化时，会通知canal的客户端。在Tomcat编写canal客户端，监听到变化后，更新Redis。但官方提供的API比较麻烦复杂的。
            所以我们这里使用Github上开源的第三方的客户端：canal-starter（跟srpintboot整合的，拿来即用）。地址：https://github.com/NormanGyllenhaal/canal-client
                引入依赖：
                    <!--引入canal依赖-->
                    <dependency>
                        <groupId>top.javatool</groupId>
                        <artifactId>canal-spring-boot-starter</artifactId>
                        <version>1.2.1-RELEASE</version>
                    </dependency>
                编写配置：
                    canal:
                        destination: heima
                        server: 172.16.168.130:11111
                编写监听器，监听Canal消息：
                    修改pojo.Item实体类：
                        @Data
                        @TableName("tb_item")
                        public class Item {
                            @TableId(type = IdType.AUTO)
                            @Id
                            private Long id;//商品id
                            private String name;//商品名称
                            private String title;//商品标题
                            private Long price;//价格（分）
                            private String image;//商品图片
                            private String category;//分类名称
                            private String brand;//品牌名称
                            private String spec;//规格
                            private Integer status;//商品状态 1-正常，2-下架
                            private Date createTime;//创建时间
                            private Date updateTime;//更新时间
                            @TableField(exist = false)
                            @Transient // 表示不属于这张表中的字段。
                            private Integer stock;
                            @TableField(exist = false)
                            @Transient
                            private Integer sold;
                        }
                    编写Canal监听器：（canal.ItemHandler）
                        // canal的声明表的注解
                        @CanalTable("tb_item")
                        @Component
                        public class ItemHandler implements EntryHandler<Item> {
                            @Autowired
                            private RedisHandler redisHandler;
                            @Autowired
                            private Cache<Long, Item> itemCache;
                            /**
                             * 每当数据库进行了增删改，就会把对应的数据传递到这些方法。
                             * @param item
                             */
                            @Override
                            public void insert(Item item) {
                                // 写数据到JVM进程缓存
                                itemCache.put(item.getId(), item);
                                // 写数据到Redis缓存
                                redisHandler.saveItem(item);
                            }
                            @Override
                            public void update(Item before, Item after) {
                                // 更新数据到JVM进程缓存
                                itemCache.put(after.getId(), after);
                                // 更新数据到Redis缓存
                                redisHandler.saveItem(after);
                            }
                            @Override
                            public void delete(Item item) {
                                // 删除数据到JVM进程缓存
                                itemCache.invalidate(item.getId());
                                // 删除数据到Redis缓存
                                redisHandler.deleteItem(item.getId());
                            }
                        }
                    封装Reids的设值和删除操作：（config.RedisHandler）
                        /**
                         * redis缓存新增操作封装
                         * @param item
                         */
                        public void saveItem(Item item) {
                            try {
                                String json = MAPPER.writeValueAsString(item);
                                redisTemplate.opsForValue().set("item:id:" + item.getId(), json);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        /**
                         * redis缓存删除操作封装
                         * @param id
                         */
                        public void deleteItem(Long id) {
                            redisTemplate.delete("item:id:" + id);
                        }
                    重启测试：
                        重启之后控制台会一直打印信息，则就证明Canal已经建立起了连接。
                        因为只是对Redis和JVM（Tomcat）进程缓存做了修改，所以不太容易在页面（nginx代理的地址页面）上看到。
                        可以直接在浏览器访问接口：http://localhost:8081/item/10001 进行测试。
                        然后直接访问：http://localhost:8081 就有一个增删改查的页面。可以对数据进行CRUD操作。
                        那么在这里随便更新一条数据之后，Idea中会有一次同步Redis缓存数据相关的日志打印
                        那么再次访问 http://localhost:8081/item/10001 这个接口（首先会访问缓存数据），查看是否成功同步了刚才对于数据的修改。
                        注：这个章节没有走通，因为canal在centOS的平台docker上 无法对 macOS m1芯片做支持，所以 canal 的服务没有启动成功，以上是详细步骤。












拾壹.服务异步通讯RabbitMQ的高级特性

    MQ的常见问题：
        消息可靠性问题：（消息的可靠性）
            如何确保发送的消息至少被消费一次。
        延迟消息问题：（死信交换机）
            如何实现消息的延迟投递，延迟发送。例如：企业微信会议设定半小时后的通知，就是延迟消息。
        消息堆积问题：（惰性队列）
            如果在高并发的场景下，消息的发送越来越多，消费者根本忙不过来，就会产生消息堆积，那么如何即决百万消息堆积，无法及时消费的问题。
        高可用问题：（MQ集群）
            如何避免单点的MQ故障而导致的不可用问题，解决方案：MQ集群。
    
    所以针对以上的问题，本章节会学习如上的四个问题处理方案。
    消息的可靠性：
        消息从生产者发送到exchange，再到queue，再到消费者，有哪些导致消息丢失的可能性？
            发送时丢失：
                生产者发送的消息未达到exchange
                消息到达exchange后未达到queue
            MQ宕机，queue将消息丢失
            consumer接收到消息后未消费就宕机
        解决消息的可靠性，自然要解决以上三类丢失问题，细分为如下几个章节：
            生产者消息确认
                用以保证生产者不会把消息弄丢
                RabbitMQ提供了pulbisher confirm机制来避免消息发送到MQ过程中丢失。消息发送到MQ以后，会返回一个结果给发送者，表示消息是否处理成功。结果有两种请求：
                    publisher confirm，发送者确认：
                        消息成功投递到交换机，返回ack（acknowledge）。
                        消息未投递到交换机，返回nack（no acknowledge）。
                    publisher return，发送者回执：
                        消息投递到交换机，但没有路由到队列，也是返回ack，及路由失败的原因。
                    总结：
                        publisher confirm：ACK：表示成功。publisher到exchange成功，exchange到queue的路由也成功了。
                        publisher confirm：NACK：表示失败：publisher到exchange失败了。
                        publisher return：ACK：表示失败：publisher到exchange成功，但是exchange到queue的路由失败了。
                    注意：确认机制发送消息时，需要给每一个消息设置一个全局唯一ID，以区分不同消息，避免ack冲突。
                对应项目：mq-advanced-demo
                    首先在CentOS中安装并启动rabbitmq：（后面换成了本地MacOS运行了，因为虚拟机中的TCP和AMQP始终无法连接的问题无法解决）
                        docker run \
                            -e RABBITMQ_DEFAULT_USER=leechenze \
                            -e RABBITMQ_DEFAULT_PASS=930316 \
                            --name myrabbitmq \
                            --hostname mq1 \
                            -p 15672:15672 \
                            -p 5672:5672 \
                            -d \
                            rabbitmq:3.12-rc-management
                        命令解读：
                            -e RABBITMQ_DEFAULT_USER 和 RABBITMQ_DEFAULT_PASS是配置用户名和密码的环境变量，后续访问mq和登陆管理平台都需要账号和密码。
                            --hostname 是配置主机名，单机可以不用配置，但是如果后续集群部署就必要配置主机名了。                    
                            -p 15672是RabbitMQ提供的一个UI界面，管理平台的端口。
                            -p 5672是后续做消息通讯的一个端口，发消息和收消息都是通过5672
                        访问：172.16.168.130:15672然后登录
                    consumer和publisher中的application.yml配置修改：
                        logging:
                            pattern:
                                dateformat: HH:mm:ss:SSS
                            level:
                                cn.itcast: debug
                        spring:
                            rabbitmq:
                                host: 172.16.168.130 # rabbitMQ的ip地址
                                port: 5672 # 端口
                                username: leechenze
                                password: 930316
                                virtual-host: /
                    在 http://172.16.168.130:15672 添加simple.queue队列。
                SpringAMQP实现生产者确认：
                    在publisher这个微服务中的application.yml中添加配置：
                        publisher-confirm-type: correlated
                        publisher-returns: true
                        template:
                          mandatory: true
                            配置说明：
                                publish-confirm-type: 开启publisher-confirm, 这里支持两种类型:
                                    simple: 同步等待confirm结果, 直到超时
                                    correlated: 异步回调, 定义confirmCallback, MQ返回结果时会回调这个ConfirmCallback
                                publish-returns：开启publish-return功能（同样是基于callback机制, 不过是定义ReturnCallback）
                                template.mandatory: 定义消息路由失败时的策略. ture：则调用ReturnCallback，才能看到路由失败的消息回执； false: 则直接丢弃消息。
                    每个RabbitTemplate只能配置一个ReturnCallback，因此需要在项目启动过程中配置：（publisher/../config/CommonConfig）
                        @Slf4j
                        @Configuration
                        public class CommonConfig implements ApplicationContextAware {
                        
                            @Override
                            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                                // 获取RabbitTemplate对象
                                RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
                                // 配置ReturnCallback
                                rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                                    @Override
                                    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                                        // 记录日志：（第一个参数为错误消息，消息中的{}是关键字，会将后续参数挨个匹配到第一个字符串中的{}关键字中去）
                                        log.error("消息发送到队列失败，响应码：{}, 失败原因：{}, 交换机: {}, 路由Key: {}, 消息：{}", replyCode, replyText, exchange, routingKey, message);
                                        // 如果有需要的话，重发消息。
                                        
                                    }
                                });
                            }
                        }
                    发送消息，指定消息ID，消息ConfirmCallback：(publisher/../test/../SpringAMQPTest)
                        @Slf4j
                        @RunWith(SpringRunner.class)
                        @SpringBootTest
                        public class SpringAmqpTest {
                        @Autowired
                        private RabbitTemplate rabbitTemplate;
                        
                            @Test
                            public void testSendMessage2SimpleQueue() throws InterruptedException {
                                // 准备发送的消息
                                String message = "hello, spring amqp!";
                                // 准备RoutingKey
                                String routingKey = "simple.test";
                                // 准备CorrelationData
                                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                                // 准备ConfirmCallback（这里可以用lambda表达式来表示，但是为了能看清参数这里就不用lambda的方式了）
                                correlationData.getFuture().addCallback(new SuccessCallback<CorrelationData.Confirm>() {
                                    @Override
                                    public void onSuccess(CorrelationData.Confirm confirm) {
                                        // 判断结果
                                        if (confirm.isAck()) {
                                            // ACK
                                            log.debug("消息投递到交换机成功！消息ID：{}", correlationData.getId());
                                        } else {
                                            // NACK
                                            log.error("消息投递到交换机失败！消息ID：{}", correlationData.getId());
                                        }
                                    }
                                }, new FailureCallback() {
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        // 记录日志
                                        log.error("消息投递到交换机成功，但是路由到队列失败", throwable);
                                        // 重发消息
                        
                                    }
                                });
                        
                                // 发送消息
                                rabbitTemplate.convertAndSend("amq.topic", routingKey, message, correlationData);
                                
                            }
                        }
                    运行 SpringAMQPTest 测试发送消息：
                        Idea控制台中输出消息：消息投递到交换机成功！消息ID：72dd57d7-c1b2-4211-bec3-95cd8e46738b 则为成功，否则失败。
                    此时访问 http://127.0.0.1:15672/#/queues
                        可以看到simple.queue队列中接收到有一条消息
                    测试失败情况：
                        修改 SpringAMQPTest 的发送消息模块
                        // 发送消息
                        rabbitTemplate.convertAndSend("amq.topic", routingKey, message, correlationData);
                        或是将amq.topic填错，或是将routingkey填错，Idea控制台都会输出一条日志为：消息发送到队列失败，响应码：{}, 失败原因：{}, 交换机: {}, 路由Key: {}, 消息：{}
                    至此生产者消息确认全部验证完毕。

            消息持久化
                确保MQ的消息持久化到硬盘，避免RabbitMQ重启后配置和数据丢失。
                MQ默认是内存存储消息，开启交换机或队列的持久化功能（Durability）可以确保缓存在MQ中的消息不会丢失。
                    交换机持久化：（consumer/../CommonConfig）
                        // 在消费者中编码，因为消费者（consumer）在启动时可以创建队列和交换机。
                        @Bean
                        public DirectExchange simpleDirect() {
                            // 参数为：交换机名称，是否持久化，当交换机没有queue绑定时是否自动删除该交换机
                            return new DirectExchange("simple.direct", true, false);
                        }
                    队列持久化：（consumer/../CommonConfig）
                        @Bean
                        public Queue simpleQueue() {
                            return QueueBuilder.durable("simple.queue").build();
                        }
                    消息持久化：（publisher/../test/../SpringAMQPTest）
                        // SpringAMQP中的消息默认是持久的，可以通过MessageProperties中的DeliveryMode来指定：
                        @Test
                        public void testDurableMessage() {
                            // 准备消息
                            Message message = MessageBuilder
                                    .withBody("hello, exchange and queue for durable".getBytes(StandardCharsets.UTF_8))
                                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                            // 发送消息（注意这里直接发送到队列中，不通过交换机了，两个参数为队列名 和 发布的消息）
                            rabbitTemplate.convertAndSend("simple.queue",message);
                        }
                    重启测试可以发现无论是交换机或队列或消息都依然存在。
                小结：其实SpringAMQP当中的 交换机，队列，消息 这些其实默认都是持久的。 
                    
            消费者消息确认
                RabbitMQ支持消费者确认机制，即：消费者处理消息后可以向MQ发送ack回执，MQ收到ack回执后才会删除该消息，而SpringAMQP则允许配置三种模式：
                    manual：手动ack，需要在业务代码结束后，调用api发送ack。（不太推荐）
                    auto：自动ack，由spring监测listener代码（即消费者的业务逻辑）是否出现异常，没有异常则返回ack；抛出异常则返回nack。（推荐）
                    none：关闭ack，MQ假定消费者获取消息后会成功处理，因此消息投递后立即被删除。
                配置方式是修改consumer的application.yml文件，添加下面配置：
                    spring:
                        rabbitmq:
                            listener:
                                simple:
                                    prefetch: 1
                                    acknowledge-mode: none
                测试：（consumer/../listener/SpringRabbitListener）
                    // 在 http://127.0.0.1:15672/#/queues 队列中手动发布一段消息，以触发以下程序的断点。
                    @RabbitListener(queues = "simple.queue")
                    public void listenSimpleQueue(String msg) {
                        System.out.println("消费者接收到simple.queue的消息：【" + msg + "】");
                        // 此处打断点进行测试，并编写一个异常。
                        System.out.println(1 / 0);
                        log.info("消费者处理消息成功");
                    }
                    None：
                        进入断点，消息收到后，在 http://127.0.0.1:15672/#/queues 再次刷新消息都会马上丢失。
                    auto：
                        进入断点，继续断点，遇见异常（System.out.println(1 / 0)），如果放行，那么就会因为异常一直处理失败，并且一直重新投递信息，进入死循环。
                        所以针对这个问题，就要对消费失败的重试机制进行处理了。
                
            消费失败重试机制
                如上个章节：当消费者异常后，消息会不断的requeue（重新入队）到队列，再重新发送给消费者，然后再次异常，再次requeue，无限循环，导致MQ的消息处理飙升，带来压力。
                我们可以利用Spring的retry机制，在消费者出现异常时，利用本地重试，而不是无限制的requeue到mq队列。
                    在consumer的application.yml文件中添加配置如下：
                        spring:
                            rabbitmq:
                                listener:
                                    simple:
                                        prefetch: 1
                                        acknowledge-mode: none
                                        retry: 
                                          enabled: true # 开启消费者失败重试
                                          initial-interval: 1000 # 初始的失败等待时长为1秒
                                          multiplier: 3 # 再次失败的等待时长倍数, 下次等待时长 = multiplier * initial-interal
                                          max-attempts: 4 # 最大重试次数
                                          stateless: true # true无状态，false有状态，如果业务中包含事务，这里必须改为false。
                                          max-interval: 10000 # 最大等待时长为10秒。
                    启动测试，修改（consumer/../listener/SpringRabbitListener）：
                        // 在 http://127.0.0.1:15672/#/queues 队列中手动发布一段消息，以触发以下程序的断点。
                        @RabbitListener(queues = "simple.queue")
                        public void listenSimpleQueue(String msg) {
                            // 这里改为日志记录方法打印，方便看时间是否与配置匹配。
                            log.debug("消费者接收到simple.queue的消息：【" + msg + "】");
                            // 此处打断点进行测试，并编写一个异常。
                            System.out.println(1 / 0);
                            log.info("消费者处理消息成功");
                        }
                        可以看到日志信息：
                            18:08:25:177 DEBUG 59097 --- SpringRabbitListener     : 消费者接收到simple.queue的消息：【hello, rabbit】
                            18:08:26:181 DEBUG 59097 --- SpringRabbitListener     : 消费者接收到simple.queue的消息：【hello, rabbit】
                            18:08:29:188 DEBUG 59097 --- SpringRabbitListener     : 消费者接收到simple.queue的消息：【hello, rabbit】
                            18:08:38:194 DEBUG 59097 --- SpringRabbitListener     : 消费者接收到simple.queue的消息：【hello, rabbit】
                        可以看到时间间隔为：25，26，29，38。
                        和配置的等待时长倍数为 3，并且最多重试 4 次，和配置吻合了。
                        继续看下一行日志信息：
                            Retries exhausted for message (Body:'[B@5421a300(byte[13])' MessageProperties ...
                        意思为重试次数耗尽了，那么此时消息就会被拒绝，也说明默认情况下，次数耗尽会把消息丢弃。
                        
                消费者失败消息处理策略：
                    在开启重试模式后，重试次数耗尽，如果消息依然失败，如果是重要的休息，不能直接丢弃，需要有MessageRecoverer接口来处理，它包含三种不同的实现：
                        RejectAndDontRequeueRecoverer：重试耗尽后，直接reject，丢弃消息。默认就是这种方式。
                        ImmediateRequeueMessageRecoverer：重试耗尽后，返回nack，消息重新入队。
                        RepublishMessageRecoverer：重试耗尽后，将失败的消息投递到指定的交换机。（推荐）
                    RepublishMessageRecoverer处理模式：
                        首先定义接收失败消息的交换机，队列及其绑定关系：（consumer/../config/ErrorMessageConfig）
                            /**
                             * 定义异常消息的交换机
                             */
                            @Bean
                            public DirectExchange errorMessageExchange() {
                                return new DirectExchange("error.direct");
                            }
                        
                            /**
                             * 定义异常消息队列
                             */
                            @Bean
                            public Queue errorQueue() {
                                return new Queue("error.queue");
                            }
                        
                            /**
                             * 绑定交换机和队列
                             * @return
                             */
                            @Bean
                            public Binding errorMessageBinding() {
                                return BindingBuilder.bind(errorQueue()).to(errorMessageExchange()).with("error.key");
                            }
                        然后，定义RepublishMessageRecoverer（异常消息处理器）：（consumer/../config/ErrorMessageConfig）
                            /**
                             * 异常消息处理器（消费者失败消息的处理策略）
                             * @param rabbitTemplate
                             * @return
                             */
                            @Bean
                            public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
                                // 参数分别为：rabbitTemplate，异常消息的交换机，异常消息的RoutingKey。
                                return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error.key");
                            }
                        测试：
                            可以看到 http://127.0.0.1:15672/#/queues 中的 error.direct 和 error.queue 都创建好了。
                            在simple.queue中发布一条消息，即可看到四条日志信息（稍等一会儿）。
                            继续看下一行日志：
                                Republishing failed message to exchange 'error.direct' with routing key error.key
                                那么就证明重试次数耗尽后，成功将消费者的失败消息传递给了error.direct的交换机中的error.queue队列了。
                                并且可以看到除了接收到的消息外，在请求头中：还会将报错信息带入！非常方便。
        
    死信交换机：
        ... here ...
        
        
    惰性队列：
        
    MQ集群：
        
    
            
                
















