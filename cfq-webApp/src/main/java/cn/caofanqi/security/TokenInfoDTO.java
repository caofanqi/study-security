package cn.caofanqi.security;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * token信息
 *
 * @author caofanqi
 * @date 2020/2/5 19:11
 */
@Data
public class TokenInfoDTO {

    private String access_token;

    private String refresh_token;

    private String token_type;

    private Long expires_in;

    private String scope;

    private LocalDateTime expires;

    /**
     * 初始化access_token过期时间，提前5秒，防止请求耗时误差
     */
    public TokenInfoDTO init() {
        this.expires = LocalDateTime.now().plusSeconds(this.expires_in - 5);
        return this;
    }

    /**
     * 判断access_token是否过期
     */
    public boolean isExpires(){
        return this.expires.isBefore(LocalDateTime.now());
    }

}
