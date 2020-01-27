package cn.caofanqi.security.validation.constraints;

import cn.caofanqi.security.validation.validator.CustomConstraintValidatorForList;
import cn.caofanqi.security.validation.validator.CustomConstraintValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验注解，具体的校验逻辑由@Constraint进行指定，可以指定多个
 *
 * @author caofanqi
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {CustomConstraintValidatorForString.class, CustomConstraintValidatorForList.class})
public @interface CustomConstraint {

    String message() default "自定义校验注解";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value() default 0;


}
