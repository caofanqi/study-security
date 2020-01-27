package cn.caofanqi.security.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常处理器
 *
 * @author caofanqi
 * @date 2020/1/27 21:17
 */
@RestControllerAdvice
public class ControllerAdvice {


    /**
     * @param e 参数校验异常1
     * @return 具体不符合规范的参数及其原因
     */
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, String> exceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    /**
     * @param e 参数校验异常2
     * @return 具体不符合规范的路径及其原因
     */
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<Path, String> exceptionHandler(javax.validation.ConstraintViolationException e){
        return e.getConstraintViolations().stream()
                .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }


}
