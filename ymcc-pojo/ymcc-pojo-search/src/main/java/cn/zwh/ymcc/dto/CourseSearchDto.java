package cn.zwh.ymcc.dto;

import cn.zwh.ymcc.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSearchDto extends BaseQuery {

    private String chargeName;

    private Long courseTypeId;

    private String gradeName;

    private BigDecimal priceMax;

    private BigDecimal priceMin;
}
