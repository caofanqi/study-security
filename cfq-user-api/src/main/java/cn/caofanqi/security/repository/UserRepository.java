package cn.caofanqi.security.repository;

import cn.caofanqi.security.pojo.doo.UserDO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

/**
 * 用户持久层接口
 * @author caofanqi
 * @date 2020/1/20 14:58
 */
public interface UserRepository extends JpaRepositoryImplementation<UserDO,Long> {


    List<UserDO> findByName(String name);

    UserDO findByUsername(String username);
}
