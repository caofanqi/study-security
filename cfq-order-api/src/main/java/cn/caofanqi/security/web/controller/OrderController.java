package cn.caofanqi.security.web.controller;

import cn.caofanqi.security.pojo.dto.OrderDTO;
import cn.caofanqi.security.pojo.dto.PriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO, @RequestHeader String username) {
        log.info("username is :{}", username);
        PriceDTO price = restTemplate.getForObject("http://127.0.0.1:9070/prices/" + orderDTO.getProductId(), PriceDTO.class);
        log.info("price is : {}", price.getPrice());
        return orderDTO;
    }


    @GetMapping("/{productId}")
    public OrderDTO get(@PathVariable Long productId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(productId);
        return orderDTO;
    }

}
