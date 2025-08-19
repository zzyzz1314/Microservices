package cn.zwh.ymcc.service;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.dto.CourseSearchDto;
import cn.zwh.ymcc.result.PageList;

import java.util.List;

public interface ESCourseService {
    void save(List<CourseDoc> courseDocs);

    PageList<CourseDoc> search(CourseSearchDto courseSearchDto);
}
