package cn.caofanqi.security.repository;

import cn.caofanqi.security.pojo.doo.AuditLogDO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * 审计日志Repository
 * @author caofanqi
 * @date 2020/1/28 23:13
 */
public interface AuditLogRepository extends JpaRepositoryImplementation<AuditLogDO,Long> {
}
