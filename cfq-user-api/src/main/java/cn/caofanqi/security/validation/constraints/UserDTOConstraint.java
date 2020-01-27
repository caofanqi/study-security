package cn.caofanqi.security.validation.constraints;


import cn.caofanqi.security.validation.validator.UserDTOConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义类级别注解
 * @author caofanqi
 */
@Documented
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {UserDTOConstraintValidator.class})
public @interface UserDTOConstraint {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
