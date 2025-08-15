package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.ICourseTeacherService;
import cn.zwh.ymcc.domain.CourseTeacher;
import cn.zwh.ymcc.query.CourseTeacherQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courseTeacher")
public class CourseTeacherController {

    @Autowired
    public ICourseTeacherService courseTeacherService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseTeacher courseTeacher){
        if(courseTeacher.getId()!=null){
            courseTeacherService.updateById(courseTeacher);
        }else{
            courseTeacherService.insert(courseTeacher);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseTeacherService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseTeacherService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseTeacherService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseTeacherQuery query){
        Page<CourseTeacher> page = new Page<CourseTeacher>(query.getPage(),query.getRows());
        page = courseTeacherService.selectPage(page);
        return JSONResult.success(new PageList<CourseTeacher>(page.getTotal(),page.getRecords()));
    }
}
