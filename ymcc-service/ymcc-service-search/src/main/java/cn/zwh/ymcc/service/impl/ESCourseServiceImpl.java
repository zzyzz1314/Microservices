package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.repository.CourseESRepository;
import cn.zwh.ymcc.service.ESCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESCourseServiceImpl implements ESCourseService {
    @Autowired
    private CourseESRepository courseESRepository;

    @Override
    public void save(List<CourseDoc> courseDocs) {
        if (courseDocs != null && courseDocs.size() > 0) {
            Iterable<CourseDoc> docs = courseESRepository.saveAll(courseDocs);
            System.out.println("上传到es成功=================================");
        }
    }
}
