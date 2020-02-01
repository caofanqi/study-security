## 1、为什么要使用https

​		以用户注册为例，通过数据校验，可以保证用户传给我们的密码是完整有效的。数据进来之后，通过我们的处理，存放到数据库中的密码是经过加密的，也是安全的。但是还有一点，就是用户的请求在到达我们应用之前的一个安全，怎么来保证？用户注册的请求，在到达服务器之前，就被别人给截获了。用户名和密码被别人截获了，实际上我们应用程序后面做什么都没用了，因为别人已经知道了用户名和密码了。要保证者之间的安全要使用https。

## 2、什么是https

​		HTTPS （全称：Hyper Text Transfer Protocol over SecureSocket Layer），是以安全为目标的 HTTP 通道，在HTTP的基础上通过传输加密和身份认证保证了传输过程的安全性。HTTPS 在HTTP 的基础下加入SSL
层，HTTPS 的安全基础是 SSL，因此加密的详细内容就需要 SSL。 HTTPS 存在不同于HTTP 的默认端口及一个加密/身份验证层（在 HTTP与 TCP 之间）。这个系统提供了身份验证与加密通讯方法。它被广泛用于万维网上安全敏感的通讯，例如交易支付等方面 。

## 3、https主要干了什么

​		3.1、客户端和服务端在传输数据之前,会对双方进行身份认证 ，认证成功建立连接。

​		3.2、数据传输的机密性，一旦安全连接建立以后，在传输的过程中，会对数据进行加密，那么就算在中间拿到了https传输的数据，也都是经过加密的。

## 4、SpringBoot使用https

​		4.1、使用java的keytool生成自签证书

​	keytool -genkeypair -alias cfq -keyalg RSA  -keystore D:\keys\cfq.key

​		4.2、SpringBoot修改相关配置

```
server:
  port: 8443
  ssl:
    key-store: classpath:cfq.key
    key-store-password: 123456
    key-password: 123456
```

## 5、springboot同时支持https和http

```
@Bean
public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    tomcat.addAdditionalTomcatConnectors(createStandardConnector());
    return tomcat;
}

private Connector createStandardConnector() {
    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setPort(port);
    return connector;
}
```

## 6、Springboot同时支持http强制跳转https

```
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                // 强制使用https
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        //添加http
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    /**
     *  配置http
     */
    private Connector createStandardConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(port);
        //http重定向到https时的https端口号
        connector.setRedirectPort(serverProperties.getPort());
        return connector;
    }
```