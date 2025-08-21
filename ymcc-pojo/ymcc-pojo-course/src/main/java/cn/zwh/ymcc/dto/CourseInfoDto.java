package cn.zwh.ymcc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoDto {

    private BigDecimal totalAmount=new BigDecimal(0);
    private List<CourseDto> courseInfos=new ArrayList<>();
}
