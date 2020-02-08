package cn.caofanqi.security.web.controller;

import cn.caofanqi.security.pojo.dto.OrderDTO;
import cn.caofanqi.security.pojo.dto.PriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单控制层
 *
 * @author caofanqi
 * @date 2020/1/31 14:26
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OAuth2RestTemplate oAuth2RestTemplate;

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal String username) {
        log.info("username is :{}", username);
        PriceDTO price = oAuth2RestTemplate.getForObject("http://127.0.0.1:9070/prices/" + orderDTO.getProductId(), PriceDTO.class);
        log.info("price is : {}", price.getPrice());
        return orderDTO;
    }


    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable Long id, @AuthenticationPrincipal String username) {
        log.info("username is :{}", username);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setProductId(5 * id);
        return orderDTO;
    }

}
