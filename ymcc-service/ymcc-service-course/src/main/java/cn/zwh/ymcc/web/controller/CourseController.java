package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.dto.CourseInfoDto;
import cn.zwh.ymcc.dto.CourseSaveDto;
import cn.zwh.ymcc.service.ICourseService;
import cn.zwh.ymcc.domain.Course;
import cn.zwh.ymcc.query.CourseQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import cn.zwh.ymcc.vo.CourseDetailDataVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    public ICourseService courseService;

    /**
     * 根据课程id 查询课程info
     */
    @GetMapping("/info/{courseId}")
    public JSONResult info(@PathVariable("courseId") String courseIds) {
        CourseInfoDto courseInfoDto = courseService.info(courseIds);
        return JSONResult.success(courseInfoDto);
    }


    /**
     * 课程上线
     */
    @RequestMapping(value = "/onLineCourse", method = RequestMethod.POST)
    public JSONResult onLineCourse(@RequestBody List<Long> courseIds) {
        courseService.onLineCourse(courseIds);
        return JSONResult.success();
    }


    /**
     * 根据课程id 查询课程详情
     */
    @RequestMapping(value = "/detail/data/{courseId}", method = RequestMethod.GET)
    public JSONResult detail(@PathVariable("courseId") Long courseId) {
        CourseDetailDataVo vo = courseService.detail(courseId);
        return JSONResult.success(vo);
    }


    /**
     * 保存和修改公用的
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody @Valid CourseSaveDto courseSaveDto) {
        courseService.save(courseSaveDto);
        return JSONResult.success();
    }

    /**
     * 删除对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return JSONResult.success();
    }

    /**
     * 获取对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id") Long id) {
        return JSONResult.success(courseService.selectById(id));
    }


    /**
     * 查询所有对象
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONResult list() {
        return JSONResult.success(courseService.selectList(null));
    }


    /**
     * 带条件分页查询数据
     */
    @RequestMapping(value = "/pagelist", method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseQuery query) {
        Page<Course> page = new Page<Course>(query.getPage(), query.getRows());
        page = courseService.selectPage(page);
        return JSONResult.success(new PageList<Course>(page.getTotal(), page.getRecords()));
    }
}
