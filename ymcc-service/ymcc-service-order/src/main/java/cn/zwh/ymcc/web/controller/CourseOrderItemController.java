package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.ICourseOrderItemService;
import cn.zwh.ymcc.domain.CourseOrderItem;
import cn.zwh.ymcc.query.CourseOrderItemQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courseOrderItem")
public class CourseOrderItemController {

    @Autowired
    public ICourseOrderItemService courseOrderItemService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseOrderItem courseOrderItem){
        if(courseOrderItem.getId()!=null){
            courseOrderItemService.updateById(courseOrderItem);
        }else{
            courseOrderItemService.insert(courseOrderItem);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseOrderItemService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseOrderItemService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseOrderItemService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseOrderItemQuery query){
        Page<CourseOrderItem> page = new Page<CourseOrderItem>(query.getPage(),query.getRows());
        page = courseOrderItemService.selectPage(page);
        return JSONResult.success(new PageList<CourseOrderItem>(page.getTotal(),page.getRecords()));
    }
}
