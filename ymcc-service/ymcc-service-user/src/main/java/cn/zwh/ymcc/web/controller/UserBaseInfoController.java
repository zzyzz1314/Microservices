package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.IUserBaseInfoService;
import cn.zwh.ymcc.domain.UserBaseInfo;
import cn.zwh.ymcc.query.UserBaseInfoQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userBaseInfo")
public class UserBaseInfoController {

    @Autowired
    public IUserBaseInfoService userBaseInfoService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody UserBaseInfo userBaseInfo){
        if(userBaseInfo.getId()!=null){
            userBaseInfoService.updateById(userBaseInfo);
        }else{
            userBaseInfoService.insert(userBaseInfo);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        userBaseInfoService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(userBaseInfoService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(userBaseInfoService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody UserBaseInfoQuery query){
        Page<UserBaseInfo> page = new Page<UserBaseInfo>(query.getPage(),query.getRows());
        page = userBaseInfoService.selectPage(page);
        return JSONResult.success(new PageList<UserBaseInfo>(page.getTotal(),page.getRecords()));
    }
}
