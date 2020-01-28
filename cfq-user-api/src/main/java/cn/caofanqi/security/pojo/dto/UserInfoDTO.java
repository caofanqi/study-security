package cn.caofanqi.security.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户详情，为了测试级联校验
 *
 * @author caofanqi
 * @date 2020/1/27 22:39
 */
@Data
public class UserInfoDTO {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp="^[1]([3-9])[0-9]{9}$",message="手机号格式不正确")
    private String phone;

}
