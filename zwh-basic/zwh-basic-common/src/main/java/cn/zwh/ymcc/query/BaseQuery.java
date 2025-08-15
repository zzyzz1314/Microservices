package cn.zwh.ymcc.query;

import lombok.Data;

@Data
public class BaseQuery {
    //关键字
    private String keyword;

    private Integer page=1;

    private Integer rows=10;

    private String sortField;

    private String sortType;
}
