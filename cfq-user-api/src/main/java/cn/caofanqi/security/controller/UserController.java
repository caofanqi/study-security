package cn.caofanqi.security.controller;

import cn.caofanqi.security.pojo.dto.UserDTO;
import cn.caofanqi.security.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户控制层
 *
 * @author caofanqi
 * @date 2020/1/20 13:05
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping
    public List<UserDTO> query(String name) {
        return userService.query(name);
    }


    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO user){
        return null;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

    }


    @PutMapping
    public UserDTO update(@RequestBody UserDTO user) {
        return null;
    }


}
