package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.IPayFlowService;
import cn.zwh.ymcc.domain.PayFlow;
import cn.zwh.ymcc.query.PayFlowQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payFlow")
public class PayFlowController {

    @Autowired
    public IPayFlowService payFlowService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody PayFlow payFlow){
        if(payFlow.getId()!=null){
            payFlowService.updateById(payFlow);
        }else{
            payFlowService.insert(payFlow);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        payFlowService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(payFlowService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(payFlowService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody PayFlowQuery query){
        Page<PayFlow> page = new Page<PayFlow>(query.getPage(),query.getRows());
        page = payFlowService.selectPage(page);
        return JSONResult.success(new PageList<PayFlow>(page.getTotal(),page.getRecords()));
    }
}
