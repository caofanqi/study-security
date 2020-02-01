package cn.caofanqi.security.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 价格DTO
 *
 * @author caofanqi
 * @date 2020/1/31 14:42
 */
@Data
public class PriceDTO {

    private Long id;

    private BigDecimal price;

}
