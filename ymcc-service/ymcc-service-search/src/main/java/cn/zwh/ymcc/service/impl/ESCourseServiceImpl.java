package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.dto.CourseSearchDto;
import cn.zwh.ymcc.repository.CourseESRepository;
import cn.zwh.ymcc.repository.HighlightResultMapper;
import cn.zwh.ymcc.result.PageList;
import cn.zwh.ymcc.service.ESCourseService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESCourseServiceImpl implements ESCourseService {

    //不支持高亮显
    @Autowired
    private CourseESRepository courseESRepository;

    //支持高亮显
    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private HighlightResultMapper highlightResultMapper;

    @Override
    public void save(List<CourseDoc> courseDocs) {
        if (courseDocs != null && courseDocs.size() > 0) {
            Iterable<CourseDoc> docs = courseESRepository.saveAll(courseDocs);
            System.out.println("上传到es成功=================================");
        }
    }

    @Override
    public PageList<CourseDoc> search(CourseSearchDto param){


        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();


        if(StringUtils.isNotEmpty(param.getSortField())){
            String fieldName="price";
            switch(param.getSortField().toLowerCase()){
                case "xp" : fieldName = "onlineTime"; break;
                case "pl" : fieldName = "commentCount"; break;
                case "rq" : fieldName = "viewCount"; break;
                case "xl" : fieldName = "saleCount"; break;
            }

            SortOrder sortOrder = SortOrder.DESC;

            if(StringUtils.isNotEmpty(param.getSortType())&&
                    param.getSortType().equalsIgnoreCase("asc")){
                sortOrder = SortOrder.ASC;
            }

            // 指定查询条件
            builder.withSort(new FieldSortBuilder(fieldName).order(sortOrder));
        }

        // 分页
        builder.withPageable(PageRequest.of(param.getPage()-1,param.getRows()));

        // 条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 关键字
        if(StringUtils.isNotEmpty(param.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name",param.getKeyword()));
        }



        // 分类，等级，价格等。。
        if(param.getCourseTypeId()!=null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("courseTypeId",
                    param.getCourseTypeId()));
        }

        if(StringUtils.isNotEmpty(param.getGradeName())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("gradeName",
                    param.getGradeName()));
        }

        if(null != param.getPriceMin()){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").
                    gte(param.getPriceMin()));
        }

        if(null != param.getPriceMax()){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").
                    gte(param.getPriceMax()));
        }



        builder.withQuery(boolQueryBuilder);


        //Page<CourseDoc> page = courseESRepository.search(builder.build());

        HighlightBuilder.Field  highlightField = new HighlightBuilder.Field("name")
                .preTags("<span style='color:red'>")
                .postTags("</span>");

        builder.withHighlightFields(highlightField);
        //支持高亮显示
        AggregatedPage<CourseDoc> page = template.queryForPage(builder.build(), CourseDoc.class, highlightResultMapper);


        // 总条数，  每页数据
        return new PageList(page.getTotalElements(),page.getContent());

    }
}
