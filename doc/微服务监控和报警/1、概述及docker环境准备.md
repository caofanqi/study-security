## 1、概述

![1](D:\Git\study\study-security\doc\微服务监控和报警\image\微服务架构.png)

​		前面我们一直都在学习微服务安全的一些东西，包括微服务本身的API安全、网关的安全，安全中心(认证服务器，权限服务等)，怎么实现SSO，然后学习了阿里的Sentinel来实现熔断限流，这些都是跟服务安全相关的一些知识点。做的都是保证服务不出问题的，但是服务不出问题是不可能的，在服务运行的过程中，肯定会遇到各种各样的问题，这时候，就需要一些可见性的保证，让我们可以及时的发现这些问题，来排查这些问题。

​	我们主要通过三套系统来对服务进行监控，在服务出问题时可以及时的进行定位解决问题。针对调用链监控(Tracing)学习Pinpoint，针对指标监控(Metrics)学习Prometheus，日志监控(Logging)学习ELK。

## 2、docker环境准备

自行安装docker环境，我这里用的是windows版，<https://www.runoob.com/docker/windows-docker-install.html>





