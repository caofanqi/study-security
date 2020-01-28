package cn.caofanqi.security.web.interceptor;

import cn.caofanqi.security.pojo.doo.AuditLogDO;
import cn.caofanqi.security.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于Interceptor的审计拦截器 ，与AuditLogFilter同时只能使用一个
 *
 * @author caofanqi
 * @date 2020/1/28 23:12
 */
@Slf4j
//@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {


    @Resource
    private AuditLogRepository auditLogRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("++++++3、审计++++++");

        AuditLogDO auditLogDO = new AuditLogDO();
        auditLogDO.setHttpMethod(request.getMethod());
        auditLogDO.setPath(request.getRequestURI());

        auditLogRepository.save(auditLogDO);

        request.setAttribute("auditLogId",auditLogDO.getId());

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex){
        Long auditLogId = (Long) request.getAttribute("auditLogId");

        AuditLogDO auditLogDO = auditLogRepository.findById(auditLogId).orElse(new AuditLogDO());
        auditLogDO.setHttpStatus(response.getStatus());

        auditLogRepository.save(auditLogDO);

    }

}
