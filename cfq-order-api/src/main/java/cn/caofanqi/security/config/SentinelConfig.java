package cn.caofanqi.security.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Sentinel 规则创建，实现ApplicationListener监听ContextRefreshedEvent，系统启动完成后就会执行
 *
 * @author caofanqi
 * @date 2020/2/11 15:50
 */
@Component
public class SentinelConfig  implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //设置资源名
        rule.setResource("createOrder");
        //根据QPS进行限流
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置每秒只能有一个请求通过.
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
