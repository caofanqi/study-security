package cn.caofanqi.security.validation.validator;

import cn.caofanqi.security.validation.constraints.CustomConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 自定义注解校验器
 *
 * @author caofanqi
 * @version 1.0.0
 * @date 2020/1/27 23:17
 */
@Slf4j
public class CustomConstraintValidatorForList implements ConstraintValidator<CustomConstraint, List> {

    private int value;

    @Override
    public void initialize(CustomConstraint constraintAnnotation) {
        value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        log.info("自定义校验的参数值是：{}，注解上的值是：{}",value,this.value);
        return true;
    }


}
