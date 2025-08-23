package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.IAlipayInfoService;
import cn.zwh.ymcc.domain.AlipayInfo;
import cn.zwh.ymcc.query.AlipayInfoQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alipayInfo")
public class AlipayInfoController {

    @Autowired
    public IAlipayInfoService alipayInfoService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody AlipayInfo alipayInfo){
        if(alipayInfo.getId()!=null){
            alipayInfoService.updateById(alipayInfo);
        }else{
            alipayInfoService.insert(alipayInfo);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        alipayInfoService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(alipayInfoService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(alipayInfoService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody AlipayInfoQuery query){
        Page<AlipayInfo> page = new Page<AlipayInfo>(query.getPage(),query.getRows());
        page = alipayInfoService.selectPage(page);
        return JSONResult.success(new PageList<AlipayInfo>(page.getTotal(),page.getRecords()));
    }
}
