package cn.caofanqi.security.web;

import cn.caofanqi.security.pojo.doo.AuditLogDO;
import cn.caofanqi.security.repository.AuditLogRepository;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常处理器
 *
 * @author caofanqi
 * @date 2020/1/27 21:17
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @Resource
    private AuditLogRepository auditLogRepository;

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

    /**
     *
     * @param e 系统异常
     * @return 系统异常及时间
     */
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> exceptionHandler(Exception e){
        /*
         *  如果有异常的化，将审计日志取出，记录异常信息
         */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        AuditLogDO auditLogDO = auditLogRepository.findById(auditLogId).orElse(new AuditLogDO());
        auditLogDO.setErrorMessage(e.getMessage());
        auditLogRepository.save(auditLogDO);

        Map<String, Object> info = Maps.newHashMap();
        info.put("message", e.getMessage());
        info.put("time", LocalDateTime.now());
        return info;
    }


}
