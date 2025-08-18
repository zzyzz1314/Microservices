package cn.zwh.ymcc.repository;

import cn.zwh.ymcc.doc.CourseDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * CourseDoc: 课程文档类型
 * Long: 课程id类型
 *
 */
@Repository
public interface CourseESRepository extends ElasticsearchRepository<CourseDoc, Long> {

}
