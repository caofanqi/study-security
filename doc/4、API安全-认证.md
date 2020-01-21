## 1、什么是认证

​		认证是指我们去验证用户身份是否合法的过程。

## 2、认证和登陆的区别

​		很多人会把认证和登陆混为一谈，其实两者完全是两个概念。认证不是登陆，登陆是指用户获取身份证明的一个过程，认证是指我们去验证这个用户身份是否合法的过程。

​		登陆的行为，往往只发生一次，登陆成功后，会保存一段时间用户信息。而认证，每次请求去调用业务逻辑的时候都会去执行。

​		再有就是登陆一旦有问题，就不会继续往下走，比如说，登陆时用户名或密码填写错误了，这时会进行报错或返回，不会继续往下执行。而认证的话，不管认证信息是否正确，在整个安全机制链路中还是会继续往下执行（如下图），让后面的审计机制，去记录这次身份认证的结果是什么样子的。最终请求是不是可以被通过，要由授权来决定。而不是由认证来决定的。比如说当前请求没有用户认证信息或者认证机制根本没生效，但是这个请求，有可能是可以正常访问的。比如说首页，获取商品信息等。

![1](./image/认证.png)

## 3、HttpBasic认证

​		基于http协议的认证方式由很多，这里，我们先了解一下最简单的HttpBasic认证，这是一个最基础的认证。HttpBasic验证方案是在 [RFC 7617](https://tools.ietf.org/html/rfc7617)中规定的，在该方案中，使用用户的 ID/密码作为凭证信息，并且使用 base64 算法进行编码。

​		步骤：3.1、用冒号将用户名和密码进行拼接（如：aladdin:opensesame）。

​					3.2、将第一步生成的结果用 base64 方式编码(YWxhZGRpbjpvcGVuc2VzYW1l)。

​					3.3、将编码后的字符串拼接上验证类型Basic 放入到请求头Authorization中（Authorization: Basic YWxhZGRpbjpvcGVuc2VzYW1l）

​		注意：对于需要在浏览器进行弹出输入用户名和密码框的，要在相应头中添加WWW-Authenticate并且Http状态码为401。

​		优点：简单，方便，所有的主流浏览器都支持。

​		缺点：Base64编码并不是一种加密方法或者hashing方法！这种方法的安全性与明文发送等同（base64可以逆向解码）。“基本验证”方案需要与HTTPS协议配合使用。



http身份认证参考文档：<https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Authentication>

请求头Authorization参考文档：<https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Authorization>

## 4、代码示例

​		见cfq-user-api工程BasicAuthorizationFilter、UserController的get方法
