## 1、修改项目使其基于浏览器cookie的SSO

1.1、修改回调方法，获得到token后，由存放到session改为存放到cookie

1.2、写一个CookieTokenFilter，将token从cookie中取出来

1.3、判断用户登陆状态，从网关中获取，MeFilter放到授权Filter之后

1.4、退出需要将cookie也删除

## 2、基于token的SSO优缺点

2.1、优点：

​		复杂度低，相对于基于session的SSO来说，只需要做access_token和refresh_token的过期处理。

​		不占用服务器资源，适合用户量特别大的系统。因为token存在浏览器cookie中，只有cookie中的refresh_token失效时，才会去认证服务器登陆。不需要认证服务器设置有效期很长的session。因为通过token就可以访问微服务。

2.2、缺点：

​		安全性低：token存在浏览器，有一定的风险。可以使用https，缩短access_token的有效期来防范。

​		可控性低：token存在浏览器，没办法主动失效掉。

​		跨域问题：cookie只能放在顶级域名下(caofanqi.cn)，只有二级域名（web.caofanqi.cn、order.caofanqi.cn）才可以做SSO。如果要与baidu.com做SSO的话，需要同时设置多个cookie。

