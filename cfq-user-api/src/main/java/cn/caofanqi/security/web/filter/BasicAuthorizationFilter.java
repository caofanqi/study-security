package cn.caofanqi.security.web.filter;

import cn.caofanqi.security.pojo.doo.UserDO;
import cn.caofanqi.security.repository.UserRepository;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static javax.swing.text.html.CSS.getAttribute;

/**
 * HttpBasic 认证
 *
 * @author caofanqi
 * @date 2020/1/21 15:10
 */
@Slf4j
@Order(2)
@Component
@SuppressWarnings("ALL")
public class BasicAuthorizationFilter extends OncePerRequestFilter {

    @Resource
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("++++++2、认证++++++");

        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(authorizationHeader)) {

            String token64 = StringUtils.substringAfter(authorizationHeader, "Basic ");

            if (StringUtils.isNotBlank(token64)) {
                try {
                    String token = new String(Base64Utils.decodeFromString(token64));
                    String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token, ":");
                    String username = items[0];
                    String password = items[1];

                    UserDO user = userRepository.findByUsername(username);

                    if (user != null && BCrypt.checkpw(password, user.getPassword())) {
//                    if (user != null && SCryptUtil.check(password,user.getPassword())) {
                        //认证通过,存放用户信息，对于使用httpBasic认证的，添加特殊标记
                        request.getSession().setAttribute("user", user.buildUserDTO());
                        request.getSession().setAttribute("httpBasic", Boolean.TRUE);
                    }

                } catch (Exception e) {
                    log.info("Basic Authorization Fail!");
                }
            }

        }

        //不管认证是否正确，继续往下走，是否可以访问，交给授权处理
        filterChain.doFilter(request, response);

        //执行完之后，如果是httpBasic方式认证，将session失效
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("httpBasic") != null){
            session.invalidate();
        }

    }

}
