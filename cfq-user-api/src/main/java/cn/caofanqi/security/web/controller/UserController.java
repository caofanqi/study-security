package cn.caofanqi.security.web.controller;

import cn.caofanqi.security.pojo.dto.UserDTO;
import cn.caofanqi.security.service.UserService;
import cn.caofanqi.security.validation.groups.Create;
import cn.caofanqi.security.validation.groups.Login;
import cn.caofanqi.security.validation.groups.Update;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户控制层
 *
 * @author caofanqi
 * @date 2020/1/20 13:05
 */
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Validated(Login.class) UserDTO userDTO,HttpServletRequest request) {
        return userService.login(userDTO,request);
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

    @GetMapping
    public List<UserDTO> query(String name) {
        return userService.query(name);
    }


    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*
         * 控制只有自己才能查看自己的信息
         */
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (!user.getId().equals(id)){
            //只能查看自己的用户信息，不是本人，返回403
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Forbidden");
            response.getWriter().flush();
            return null;
        }

        return userService.get(id);
    }

    @PostMapping
    public UserDTO create(@RequestBody @Validated(Create.class) UserDTO userDTO){
        return userService.create(userDTO);
    }

    @PutMapping
    public UserDTO update(@RequestBody @Validated(Update.class) UserDTO userDTO) {
        return userService.update(userDTO);
    }


    @PostMapping("/batch")
    @Validated(Create.class)
    public void batchCreate(@RequestBody  List<@Valid UserDTO> userDTOS){
        userService.batchCreate(userDTOS);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        int i = 1 / 0 ;
    }

}
