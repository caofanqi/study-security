package cn.caofanqi.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 审计过滤器
 *
 * @author caofanqi
 * @date 2020/2/9 22:06
 */
@Slf4j
public class GatewayAuditLogFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("1、create log for :{}", username);

        filterChain.doFilter(request, response);

        if (request.getAttribute("updateLog") == null) {
            log.info("2、update log to success");
        }

    }

}
