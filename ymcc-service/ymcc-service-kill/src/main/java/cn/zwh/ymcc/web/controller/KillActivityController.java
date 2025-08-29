package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.dto.KillActivityDto;
import cn.zwh.ymcc.service.IKillActivityService;
import cn.zwh.ymcc.domain.KillActivity;
import cn.zwh.ymcc.query.KillActivityQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/killActivity")
public class KillActivityController {

    @Autowired
    public IKillActivityService killActivityService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult save(@RequestBody KillActivityDto killActivityDto){

        killActivityService.save(killActivityDto);
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        killActivityService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(killActivityService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(killActivityService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody KillActivityQuery query){
        Page<KillActivity> page = new Page<KillActivity>(query.getPage(),query.getRows());
        page = killActivityService.selectPage(page);
        return JSONResult.success(new PageList<KillActivity>(page.getTotal(),page.getRecords()));
    }


    @RequestMapping(value = "/publish/{id}",method = RequestMethod.GET)
    public JSONResult publish(@PathVariable("id") Long id){
        killActivityService.publish(id);
        return JSONResult.success();
    }
}
