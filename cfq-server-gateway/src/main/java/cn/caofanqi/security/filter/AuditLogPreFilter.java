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

/**
 * 审计日志过滤器
 *
 * @author caofanqi
 * @date 2020/2/2 23:59
 */
@Slf4j
@Component
public class AuditLogPreFilter extends ZuulFilter {

    @Resource
    private AuditLogRepository auditLogRepository;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("++++++pre审计++++++");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        TokenInfoDTO tokenInfo = (TokenInfoDTO) request.getAttribute("tokenInfo");
        String username = "anonymous";
        if (tokenInfo != null) {
            username = tokenInfo.getUser_name();
        }

        AuditLogDO auditLogDO = new AuditLogDO();
        auditLogDO.setPath(request.getRequestURI());
        auditLogDO.setHttpMethod(request.getMethod());
        auditLogDO.setUsername(username);
        auditLogRepository.saveAndFlush(auditLogDO);

        request.setAttribute("auditLogId",auditLogDO.getId());

        return null;
    }

}
