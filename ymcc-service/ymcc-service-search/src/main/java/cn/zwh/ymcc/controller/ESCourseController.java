package cn.zwh.ymcc.controller;

import cn.zwh.ymcc.doc.CourseDoc;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.ESCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/es")
public class ESCourseController {
    @Autowired
    private ESCourseService esCourseService;
    /*
    * CourseDoc 实体类
    * */
    @PostMapping("/save")
    public JSONResult save(@RequestBody List<CourseDoc> courseDocs) {
        esCourseService.save(courseDocs);
        return JSONResult.success();
    }
}
