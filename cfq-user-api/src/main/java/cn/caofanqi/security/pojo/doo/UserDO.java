package cn.caofanqi.security.pojo.doo;


import cn.caofanqi.security.pojo.dto.UserDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author caofanqi
 * @date 2020/1/20 13:08
 */
@Data
@Entity
@Table(name = "user")
public class UserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


    public UserDTO buildUserDTO(){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(this,userDTO);
        return userDTO;
    }

}
