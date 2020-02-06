package cn.caofanqi.security;

import lombok.Data;

/**
 * token信息
 *
 * @author caofanqi
 * @date 2020/2/5 19:11
 */
@Data
public class TokenInfoDTO {

    private String access_token;

    private String token_type;

    private Long expires_in;

    private String scope;

}
