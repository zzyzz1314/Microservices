package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.dto.KillPlaceOrderDto;
import cn.zwh.ymcc.dto.PlaceOrderDto;
import cn.zwh.ymcc.dto.UpdateOrderStatusDto;
import cn.zwh.ymcc.service.ICourseOrderService;
import cn.zwh.ymcc.domain.CourseOrder;
import cn.zwh.ymcc.query.CourseOrderQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/courseOrder")
public class CourseOrderController {

    @Autowired
    public ICourseOrderService courseOrderService;

    @PostMapping("/killPlaceOrder")
    public JSONResult killPlaceOrder(@RequestBody KillPlaceOrderDto killPlaceOrderDto){
        String orderNo =courseOrderService.killPlaceOrder(killPlaceOrderDto);
        return JSONResult.success(orderNo);
    }

    /*
    * 根据订单号 修改订单状态
    * */
    @PostMapping("/updateOrderStatus")
    public JSONResult updateOrderStatus(@RequestBody UpdateOrderStatusDto updateOrderStatusDto){
        return courseOrderService.updateOrderStatus(updateOrderStatusDto);
    }


    @PostMapping("/placeOrder")
    public JSONResult placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        String orderNo =courseOrderService.placeOrder(placeOrderDto);
        return JSONResult.success(orderNo);
    }


    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseOrder courseOrder){
        if(courseOrder.getId()!=null){
            courseOrderService.updateById(courseOrder);
        }else{
            courseOrderService.insert(courseOrder);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseOrderService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseOrderService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseOrderService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseOrderQuery query){
        Page<CourseOrder> page = new Page<CourseOrder>(query.getPage(),query.getRows());
        page = courseOrderService.selectPage(page);
        return JSONResult.success(new PageList<CourseOrder>(page.getTotal(),page.getRecords()));
    }
}
