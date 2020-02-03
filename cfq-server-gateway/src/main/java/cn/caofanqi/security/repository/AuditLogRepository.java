package cn.caofanqi.security.repository;

import cn.caofanqi.security.pojo.doo.AuditLogDO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * 审计日志Repository
 *
 * @author caofanqi
 * @date 2020/2/3 0:01
 */
public interface AuditLogRepository extends JpaRepositoryImplementation<AuditLogDO, Long> {
}
