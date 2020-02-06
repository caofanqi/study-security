## 1、两个session，三个有效期

​		在上一节，实现的其实是基于session的sso，在该方案中有两个session，一个是客户端应用的session，一个是认证服务器的sessioin。一共有三个有效期，两个session的有效期，还有一个token令牌的有效期。他们的作用是如下：

1.1、客户端应用session的有效期，控制多长时间跳转一次认证服务器。

1.2、认证服务器session的有效期，控制多长时间需要用户输入一次用户名密码。

1.3、token有效期，控制登陆一次能访问多长时间微服务。

## 2、处理退出的用户体验问题

​		因为有这两个session，我们目前的退出逻辑只是将客户端应用的session失效掉，但是并没有将认证服务器的session失效掉，修改推出逻辑，退出时客户端应用和认证服务器两个session都失效掉。

springsecurity默认处理退出请求的是org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter过滤器，我们可以覆盖该类，修改内部html片段。

## 3、认证服务器使用spring-session实现session共享

​		认证服务器，在生产环境中，需要是高可用的，会集群部署，各节点需要进行session共享，我们使用spring-session来实现。

​		spring-session为我们提供了多种实现方式，有redis，jdbc等，我们使用jdbc来实现。

​		3.1、pom中引入依赖

​		3.2、在spring-session-jdbc中提供了各种数据库的建表语句，copy出来执行即可



spring-session官方文档：<https://docs.spring.io/spring-session/docs/2.2.1.RELEASE/reference/html5/>

spring-session官方示例：<https://github.com/spring-projects/spring-session/tree/2.2.1.RELEASE/spring-session-samples>





