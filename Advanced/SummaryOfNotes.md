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

        SpringCloud
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















