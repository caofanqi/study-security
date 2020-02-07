package cn.caofanqi.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 将session中的token取出放到请求头中
 *
 * @author caofanqi
 * @date 2020/2/6 0:34
 */
@Slf4j
@Component
public class SessionTokenFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        TokenInfoDTO token = (TokenInfoDTO) request.getSession().getAttribute("token");

        if (token != null) {
            String accessToken = token.getAccess_token();
            //判断access_token是否过期，需要刷新令牌
            if (token.isExpires()){
                String oauthTokenUrl = "http://gateway.caofanqi.cn:9010/token/oauth/token";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("webApp", "123456");

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.set("grant_type", "refresh_token");
                params.set("refresh_token", token.getRefresh_token());

                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

                try {
                    ResponseEntity<TokenInfoDTO> refreshTokenResult = restTemplate.exchange(oauthTokenUrl, HttpMethod.POST, httpEntity, TokenInfoDTO.class);
                    request.getSession().setAttribute("token", refreshTokenResult.getBody().init());
                    accessToken = refreshTokenResult.getBody().getAccess_token();
                    log.info("refresh_token......");
                }catch (Exception e){
                    //刷新令牌失败
                    log.info("token refresh fail");
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
                    requestContext.setResponseBody("{\"message\":\"token refresh fail\"}");
                }
            }

            requestContext.addZuulRequestHeader("Authorization","bearer " + accessToken);
        }

        return null;
    }
}
