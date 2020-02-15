package cn.caofanqi.security.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 监控指标设置
 *
 * @author caofanqi
 * @date 2020/2/15 12:16
 */
@Configuration
public class PrometheusMetricsConfig {


    @Resource
    private PrometheusMeterRegistry prometheusMeterRegistry;

    /**
     *  用于统计请求总数
     */
    @Bean
    public Counter requestCounter() {
        return  Counter.build().name("order_requests_total").help("请求总数").labelNames("service","method","code")
                .register(prometheusMeterRegistry.getPrometheusRegistry());
    }

    /**
     *  用于统计TP50，TP90
     */
    @Bean
    public Summary requestLatency(){
        return Summary.build()
                .quantile(0.5, 0.05)
                .quantile(0.9, 0.01)
                .name("order_request_latency").help("请求延迟").labelNames("service","method","code")
                .register(prometheusMeterRegistry.getPrometheusRegistry());
    }

}
