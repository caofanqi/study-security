## 1、Sentinel是什么

​		Sentinel是阿里巴巴在2018年开源的面向分布式服务架构的轻量级流量控制组件，主要以流量为切入点，从限流、流量整形、熔断降级、系统负载保护等多个维度来帮助您保障微服务的稳定性。

​		Sentinel里面涉及两个基本概念：资源和规则。a、资源是 Sentinel 的关键概念。它可以是 Java 应用程序中的任何内容，例如，由应用程序提供的服务，或由应用程序调用的其它应用提供的服务，甚至可以是一段代码。只要通过 Sentinel API 定义的代码，就是资源，能够被 Sentinel 保护起来。b、围绕资源的实时状态设定的规则，可以包括流量控制规则、熔断降级规则以及系统保护规则。所有规则可以动态实时调整。

​		Sentinel的使用可以分为两个部分：核心库 (Java客户端)和控制台(Dashboard)。a、核心库不依赖任何框架/库，能够运行与Java7以上的版本，对SpringCloud/Dubbo等框架也有较好的支持。b、控制台主要负责管理推送规则、监控、集群限流分配管理、机器发现等。

## 2、Sentinel客户端限流demo

2.1、在微服务中引入Sentinel依赖

2.2、定义资源

2.3、定义规则

## 3、Sentinel日志

[https://github.com/alibaba/Sentinel/wiki/%E6%97%A5%E5%BF%97](https://github.com/alibaba/Sentinel/wiki/日志)

## 4、Sentinel控制台(dashboard)的使用

[https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0#2-%E5%90%AF%E5%8A%A8%E6%8E%A7%E5%88%B6%E5%8F%B0](https://github.com/alibaba/Sentinel/wiki/控制台#2-启动控制台)

## 5、Sentinel对SpringCloud的支持

5.1、将之前导入的两个依赖替换为spring-cloud-starter-alibaba-sentinel

5.2、将jvm启动参数去掉，在配置文件中进行配置



