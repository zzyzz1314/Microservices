package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.service.IMessageStationService;
import cn.zwh.ymcc.domain.MessageStation;
import cn.zwh.ymcc.query.MessageStationQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messageStation")
public class MessageStationController {

    @Autowired
    public IMessageStationService messageStationService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody MessageStation messageStation){
        if(messageStation.getId()!=null){
            messageStationService.updateById(messageStation);
        }else{
            messageStationService.insert(messageStation);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        messageStationService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(messageStationService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(messageStationService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody MessageStationQuery query){
        Page<MessageStation> page = new Page<MessageStation>(query.getPage(),query.getRows());
        page = messageStationService.selectPage(page);
        return JSONResult.success(new PageList<MessageStation>(page.getTotal(),page.getRecords()));
    }
}
