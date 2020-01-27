package cn.caofanqi.security.pojo.dto;


import cn.caofanqi.security.validation.constraints.CustomConstraint;
import cn.caofanqi.security.validation.constraints.UserDTOConstraint;
import cn.caofanqi.security.validation.groups.Create;
import cn.caofanqi.security.validation.groups.Update;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


/**
 * @author caofanqi
 * @date 2020/1/20 13:08
 */
@Data
@UserDTOConstraint(message = "default message")
public class UserDTO {

    @Null(groups = Create.class,message = "创建用户时，id必须为null")
    @NotNull(groups = Update.class,message = "修改用户时，必须有id")
    private Long id;

    @CustomConstraint(value = 123)
    @NotNull(message = "名称不能为空")
    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Valid
    @NotNull(message = "用户详情不能为空")
    private UserInfoDTO userInfo;


}
