package cn.caofanqi.security.validation.validator;

import cn.caofanqi.security.validation.constraints.CustomConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义注解校验器，实现ConstraintValidator接口，并指定要校验的注解和要校验的类型，
 * initialize可以获得注解上的属性，进行初始化；isValid校验逻辑。
 *
 * @author caofanqi
 * @version 1.0.0
 * @date 2020/1/27 23:17
 */
@Slf4j
public class CustomConstraintValidatorForString implements ConstraintValidator<CustomConstraint,String> {

    private int value;

    @Override
    public void initialize(CustomConstraint constraintAnnotation) {
        value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        log.info("自定义校验的参数值是：{}，注解上的值是：{}",value,this.value);
        return true;
    }


}
