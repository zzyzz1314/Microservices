package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.IPayOrderService;
import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.query.PayOrderQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payOrder")
public class PayOrderController {

    @Autowired
    public IPayOrderService payOrderService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody PayOrder payOrder){
        if(payOrder.getId()!=null){
            payOrderService.updateById(payOrder);
        }else{
            payOrderService.insert(payOrder);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        payOrderService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(payOrderService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(payOrderService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody PayOrderQuery query){
        Page<PayOrder> page = new Page<PayOrder>(query.getPage(),query.getRows());
        page = payOrderService.selectPage(page);
        return JSONResult.success(new PageList<PayOrder>(page.getTotal(),page.getRecords()));
    }
}
