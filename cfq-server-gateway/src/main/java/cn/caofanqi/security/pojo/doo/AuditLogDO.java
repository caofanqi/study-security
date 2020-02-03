package cn.caofanqi.security.pojo.doo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 审计日志
 *
 * @author caofanqi
 * @date 2020/1/28 22:55
 */
@Data
@Entity
@Table(name = "audit_log")
@EntityListeners(value = AuditingEntityListener.class)
public class AuditLogDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String httpMethod;

    private String path;

    private Integer httpStatus;

    private String username;

    @CreatedDate
    private LocalDateTime requestTime;

    @LastModifiedDate
    private LocalDateTime responseTime;

    private String errorMessage;

}
