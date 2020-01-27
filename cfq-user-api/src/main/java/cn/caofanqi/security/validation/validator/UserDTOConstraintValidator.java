package cn.caofanqi.security.validation.validator;

import cn.caofanqi.security.pojo.dto.UserDTO;
import cn.caofanqi.security.validation.constraints.UserDTOConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义UserDTO类校验器
 *
 * @author caofanqi
 * @date 2020/1/28 1:36
 */
@Slf4j
public class UserDTOConstraintValidator implements ConstraintValidator<UserDTOConstraint, UserDTO> {


    @Override
    public boolean isValid(UserDTO value, ConstraintValidatorContext context) {

        if (value.getName().equals(value.getUsername())){
            /*
             * 更改错误消息
             */
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("名称不能和用户名相同")
                    .addPropertyNode("name").addConstraintViolation();

            return false;
        }

        return true;
    }
}
