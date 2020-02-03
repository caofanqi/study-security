package cn.caofanqi.security.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 *  校验令牌返回参数DTO
 *
 * @author caofanqi
 * @date 2020/2/2 23:36
 */
@Data
public class TokenInfoDTO {

    /**
     *  当前令牌是否可用
     */
    private Boolean active;

    /**
     * 令牌发给哪个客户端应用
     */
    private String client_id;

    /**
     * 能访问的资源服务器id数组
     */
    private String[] aud;

    /**
     * 令牌scope
     */
    private String[] scope;

    /**
     * 令牌过期时间
     */
    private Date exp;

    /**
     * 申请令牌的用户名
     */
    private String user_name;

    /**
     *  申请令牌用户的权限集合
     */
    private String[] authorities;

}
