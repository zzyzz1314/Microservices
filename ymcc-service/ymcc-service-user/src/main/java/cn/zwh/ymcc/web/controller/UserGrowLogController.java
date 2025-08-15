package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.IUserGrowLogService;
import cn.zwh.ymcc.domain.UserGrowLog;
import cn.zwh.ymcc.query.UserGrowLogQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userGrowLog")
public class UserGrowLogController {

    @Autowired
    public IUserGrowLogService userGrowLogService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody UserGrowLog userGrowLog){
        if(userGrowLog.getId()!=null){
            userGrowLogService.updateById(userGrowLog);
        }else{
            userGrowLogService.insert(userGrowLog);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        userGrowLogService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(userGrowLogService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(userGrowLogService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody UserGrowLogQuery query){
        Page<UserGrowLog> page = new Page<UserGrowLog>(query.getPage(),query.getRows());
        page = userGrowLogService.selectPage(page);
        return JSONResult.success(new PageList<UserGrowLog>(page.getTotal(),page.getRecords()));
    }
}
