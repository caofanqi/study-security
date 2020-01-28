package cn.caofanqi.security.web.filter;

import cn.caofanqi.security.pojo.doo.AuditLogDO;
import cn.caofanqi.security.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于Filter的审计过滤器，与AuditLogInterceptor同时只能使用一个
 *
 * @author caofanqi
 * @date 2020/1/29 0:08
 */
@Slf4j
@Order(3)
@Component
public class AuditLogFilter extends OncePerRequestFilter {


    @Resource
    private AuditLogRepository auditLogRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("++++++3、审计++++++");

        AuditLogDO auditLogDO = new AuditLogDO();
        auditLogDO.setHttpMethod(request.getMethod());
        auditLogDO.setPath(request.getRequestURI());
        //放入持久化上下文中，供异常处理使用
        auditLogRepository.save(auditLogDO);
        request.setAttribute("auditLogId",auditLogDO.getId());

        // 执行请求
        filterChain.doFilter(request,response);

        // 执行完成，从持久化上下文中获取，并记录响应信息
        auditLogDO = auditLogRepository.findById(auditLogDO.getId()).get();
        auditLogDO.setHttpStatus(response.getStatus());

        auditLogRepository.save(auditLogDO);

    }

}
