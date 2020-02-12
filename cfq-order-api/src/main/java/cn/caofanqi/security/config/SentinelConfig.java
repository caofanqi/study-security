package cn.caofanqi.security.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 从远程配置中心获取规则，进行配置
 *
 * @author caofanqi
 * @date 2020/2/11 15:50
 */
@Component
public class SentinelConfig{

    @Value("${sentinel.zookeeper.address}")
    private String remoteAddress;

    @Value("${sentinel.zookeeper.path}")
    private String path;

    @Value("${spring.application.name}")
    private String appName;


    @PostConstruct
    public void loadRules(){
        //流控规则数据源
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(remoteAddress, path + "/" + appName,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        //....
    }

}
