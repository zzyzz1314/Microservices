package cn.zwh.ymcc.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程索引库模型
 * @author zwh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "course", type = "_doc")
public class CourseDoc {
    @Id
    private Long id;

    //课程名称
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    //课程适用人群
    @Field(type = FieldType.Keyword)
    private String forUser;

    //课程类型id
    @Field(type = FieldType.Long)
    private Long courseTypeId;

    //课程等级
    @Field(type = FieldType.Keyword)
    private String gradeName;

    //开课时间
    @Field(type = FieldType.Date)
    private Date startTime;

    //结课时间
    @Field(type = FieldType.Date)
    private Date endTime;

    //封面
    @Field(type = FieldType.Keyword)
    private String pic;

    //上线时间
    @Field(type = FieldType.Date)
    private Date onlineTime;

    //课程讲师
    @Field(type = FieldType.Text)
    private String teacherName;

    //课程是否收费 收费1 免费2
    @Field(type = FieldType.Keyword)
    private Integer charge;

    //售价
    private BigDecimal price;

    //原价
    private BigDecimal priceOld;

    //销量
    @Field(type = FieldType.Integer)
    private Integer saleCount;

    //流量量
    @Field(type = FieldType.Integer)
    private Integer viewCount;

    //评论数
    @Field(type = FieldType.Integer)
    private Integer commentCount;
}
