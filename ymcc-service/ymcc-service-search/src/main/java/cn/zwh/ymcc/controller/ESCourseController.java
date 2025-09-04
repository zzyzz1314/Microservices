package cn.zwh.ymcc.controller;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.dto.CourseSearchDto;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import cn.zwh.ymcc.service.ESCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
//@RequestMapping("")
public class ESCourseController {
    @Autowired
    private ESCourseService esCourseService;
    /*
    * CourseDoc 实体类
    * */
    @PostMapping("/es/save")
    @PreAuthorize("hasAuthority('es:save')")
    public JSONResult save(@RequestBody List<CourseDoc> courseDocs) {
        esCourseService.save(courseDocs);
        return JSONResult.success();
    }

    /*
    * 课程搜索
    * */
    @PostMapping("/course/search")
    public JSONResult search(@RequestBody CourseSearchDto courseSearchDto){
        PageList<CourseDoc> courseDocs= esCourseService.search(courseSearchDto);
        return JSONResult.success(courseDocs);
    }
}
