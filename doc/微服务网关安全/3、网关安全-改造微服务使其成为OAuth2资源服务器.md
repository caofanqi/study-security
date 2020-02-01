## 1、将微服务改造为OAuth2资源服务器

​		以订单服务为例，将其修改为OAuth2资源服务器

​	1.1、pom中添加spring-cloud-starter-oauth2依赖

​	1.2、编写资源服务器配置类，继承ResourceServerConfigurerAdapter类，添加@EnableResourceServer注解，重写ResourceServerConfigurerAdapter类中public void configure(ResourceServerSecurityConfigurer resources)方法，为资源服务器配置Id。

​	1.3、编写Web安全配置类，继承WebSecurityConfigurerAdapter类，添加@EnableWebSecurity注解，重写

authenticationManagerBean方法，使用OAuth2AuthenticationManager，并设置ResourceServerTokenServices，ResourceServerTokenServices使用RemoteTokenServices，为其设置客户端id，密码，校验token地址。

