## 1、Prometheus简介

​		Prometheus是具有活跃生态系统的开源系统监视和警报工具包。下图是Prometheus的体系结构及其某些生态系统组件。最核心的位置就是Prometheus server，主要的作用就是根据我们的配置去用于收集和存储时间序列数据。Service discovery服务的发现，通过Service discovery，Prometheus server就会知道去哪里采集数据，有两种方式，一种是静态的，通过文件去配；另外一种是动态的，可以通过zookeeper或者其他的配置中心，当里面的数据变化时，去不同的地方抓取数据。Jobs/exporters，一般是我们的应用提供的，供Prometheus server抓取数据，这里是拉模式，好处是，对于我们的应用来说，不需要知道Prometheus的服务在哪，只需要暴漏我们的数据就可以了。Pushgateway，是用来支持推模式的，因为有些时候，我们的一些数据并不是一直存在的，比如说定时任务的数据，我们把短活的数据推送到Pushgateway，供Prometheus server从Pushgateway拉取数据。到这里数据采集的组件一件介绍完毕了。数据采集来了之后都放到Prometheus server中，通过HTTP server将数据暴漏出来供前端的一些应用通过PromQL来查询使用，进行数据的可视化和导出，推荐使用的组件时Grafana。Alertmanager来做告警，告警的方式有很多种，email、微信、钉钉或者自己写的接口等，可以对Prometheus server中的时间序列数据定制一些规则，出发了规则会推送到Alertmanager，但是它并不会立刻告警，而是会评估几次，防止误报。

![1](D:\Git\study\study-security\doc\微服务监控和报警\image\Prometheus体系结构及某些生态系统组件.png)

## 2、Prometheus环境搭建

2.1、使用docker安装Prometheus

​		docker-compose -f docker-compose.yml up

2.2、SpringBoot整合Prometheus

​	2.2.1、添加SpringBoot Actuator 监控端点依赖

​	2.2.2、添加micrometer-registry-prometheus依赖

​	2.2.3、application.yml配置对外暴漏端点

​	 2.2.4、资源服务配置端点请求，不用身份验证

​	

