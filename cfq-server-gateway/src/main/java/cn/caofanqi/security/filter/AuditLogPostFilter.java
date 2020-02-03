package cn.caofanqi.security.filter;

import cn.caofanqi.security.pojo.doo.AuditLogDO;
import cn.caofanqi.security.pojo.dto.TokenInfoDTO;
import cn.caofanqi.security.repository.AuditLogRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 审计日志过滤器
 *
 * @author caofanqi
 * @date 2020/2/2 23:59
 */
@Slf4j
@Component
public class AuditLogPostFilter extends ZuulFilter {

    @Resource
    private AuditLogRepository auditLogRepository;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("++++++post审计++++++");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        Long auditLogId = (Long) request.getAttribute("auditLogId");
        Optional<AuditLogDO> auditLogOp = auditLogRepository.findById(auditLogId);
        AuditLogDO auditLogDO = auditLogOp.orElse(new AuditLogDO());
        auditLogDO.setHttpStatus(requestContext.getResponseStatusCode());
        if (requestContext.getThrowable()!= null){
            auditLogDO.setErrorMessage(requestContext.getThrowable().getMessage());
        }
        auditLogRepository.save(auditLogDO);

        return null;
    }

}
