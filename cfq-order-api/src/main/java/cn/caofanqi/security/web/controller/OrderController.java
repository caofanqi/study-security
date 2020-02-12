package cn.caofanqi.security.web.controller;

import cn.caofanqi.security.pojo.dto.OrderDTO;
import cn.caofanqi.security.pojo.dto.PriceDTO;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @SentinelResource(value = "createOrder", blockHandler = "createOrderOnBlock")
    @PreAuthorize("#oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    public OrderDTO create(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal String username) throws InterruptedException {
        // 被保护的逻辑
        log.info("username is :{}", username);
//        PriceDTO price = oAuth2RestTemplate.getForObject("http://127.0.0.1:9070/prices/" + orderDTO.getProductId(), PriceDTO.class);
//        log.info("price is : {}", price.getPrice());
        Thread.sleep(50);
        return orderDTO;

    }

    public OrderDTO createOrderOnBlock(OrderDTO orderDTO, String username, BlockException e) {
        log.info("createOrder blocked by :{}",e.getClass().getSimpleName());
        OrderDTO defaultOrderDTO = new OrderDTO();
        defaultOrderDTO.setId(999L);
        defaultOrderDTO.setProductId(999L);
        return defaultOrderDTO;
    }


    @GetMapping("/{id}")
    @SentinelResource(value = "getOrder")
    public OrderDTO get(@PathVariable Long id, @AuthenticationPrincipal String username) {
        log.info("username is :{}", username);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setProductId(5 * id);
        return orderDTO;
    }

}
