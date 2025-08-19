package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.ICourseTypeService;
import cn.zwh.ymcc.domain.CourseType;
import cn.zwh.ymcc.query.CourseTypeQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import cn.zwh.ymcc.vo.CourseTypeCrumbsVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseType")
public class CourseTypeController {

    @Autowired
    public ICourseTypeService courseTypeService;

    @GetMapping("/treeData")
    public JSONResult treeData(){
        List<CourseType> nodeList = courseTypeService.getTreeData();
        return JSONResult.success(nodeList);
    }

    @GetMapping("/crumbs/{courseTypeId}")
    public JSONResult crumbs(@PathVariable Long courseTypeId){
        List<CourseTypeCrumbsVo> vos= courseTypeService.crumbs(courseTypeId);
        return JSONResult.success(vos);
    }


    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseType courseType){
        if(courseType.getId()!=null){
            courseTypeService.updateById(courseType);
        }else{
            courseTypeService.insert(courseType);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseTypeService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseTypeService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseTypeService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseTypeQuery query){
        Page<CourseType> page = new Page<CourseType>(query.getPage(),query.getRows());
        page = courseTypeService.selectPage(page);
        return JSONResult.success(new PageList<CourseType>(page.getTotal(),page.getRecords()));
    }
}
