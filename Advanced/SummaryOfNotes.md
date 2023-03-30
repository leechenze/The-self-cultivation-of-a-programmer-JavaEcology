博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;



零.Day1

    认识微服务
    分布式服务架构案例 
    Eureka注册中心
    Ribbon负载均衡原理
    Nacos注册中心
    
    认识微服务：
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
                
            Eureka注册中心：    
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

            Nacos注册中心：        
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


            Http客户端Feign：            
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
                    
壹.
















贰.
















叁.
















肆.
















伍.
















陆.
















柒.
















捌.
















玖.
















拾.















