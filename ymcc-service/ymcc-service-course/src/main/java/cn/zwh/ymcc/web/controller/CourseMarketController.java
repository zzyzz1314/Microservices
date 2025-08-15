package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.ICourseMarketService;
import cn.zwh.ymcc.domain.CourseMarket;
import cn.zwh.ymcc.query.CourseMarketQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courseMarket")
public class CourseMarketController {

    @Autowired
    public ICourseMarketService courseMarketService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseMarket courseMarket){
        if(courseMarket.getId()!=null){
            courseMarketService.updateById(courseMarket);
        }else{
            courseMarketService.insert(courseMarket);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseMarketService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseMarketService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseMarketService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseMarketQuery query){
        Page<CourseMarket> page = new Page<CourseMarket>(query.getPage(),query.getRows());
        page = courseMarketService.selectPage(page);
        return JSONResult.success(new PageList<CourseMarket>(page.getTotal(),page.getRecords()));
    }
}
