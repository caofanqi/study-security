package cn.caofanqi.security.web.controller;

import cn.caofanqi.security.pojo.dto.PriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 价格控制层
 *
 * @author caofanqi
 * @date 2020/1/31 14:44
 */
@Slf4j
@RestController
@RequestMapping("/prices")
public class PriceController {


    @GetMapping("/{id}")
    private PriceDTO get(@PathVariable Long id, @AuthenticationPrincipal String username) {
        log.info("productId is {}",id);
        log.info("username is :{}", username);
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setId(id);
        priceDTO.setPrice(new BigDecimal(100));
        return priceDTO;
    }

}
