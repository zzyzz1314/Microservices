package cn.zwh.ymcc;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.repository.CourseESRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private CourseESRepository courseESRepository;
    @Test
    public void createIndexTest(){
        elasticsearchRestTemplate.createIndex(CourseDoc.class);

        elasticsearchRestTemplate.putMapping(CourseDoc.class);
    }


    @Test
    public void save(){
        List<CourseDoc> courseDocs = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            CourseDoc courseDoc = new CourseDoc();
            courseDoc.setId(i);
            courseDoc.setName("测试课程" + i);
            courseDoc.setForUser("小白"+ i);
            courseDoc.setCourseTypeId(i);
            courseDocs.add(courseDoc);
        }
        Iterable<CourseDoc> docs = courseESRepository.saveAll(courseDocs);
        docs.forEach(courseDoc -> {
            log.info("保存成功：{}", courseDoc);
        });
    }

    @Test
    public void findAll(){
        Iterable<CourseDoc> docs = courseESRepository.findAll();
        docs.forEach(courseDoc -> {
            log.info("查询成功：{}", courseDoc);
        });
    }

    @Test
    public void deleteById(){
        courseESRepository.deleteById(9L);
    }

    @Test
    public void deleteAll(){
        courseESRepository.deleteAll();
    }
}
