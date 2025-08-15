package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.constants.ErrorCode;
import cn.zwh.ymcc.exception.GlobleBusinessException;
import cn.zwh.ymcc.service.IEmployeeService;
import cn.zwh.ymcc.domain.Employee;
import cn.zwh.ymcc.query.EmployeeQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.sun.javaws.exceptions.ErrorCodeResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    public IEmployeeService employeeService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody @Valid Employee employee){
        if(employee.getId()!=null){
            employeeService.updateById(employee);
        }else{
            employeeService.insert(employee);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        employeeService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(employeeService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        //int i=1/0;
        /*if ( true){
            throw new GlobleBusinessException(ErrorCode.NETWORK_ERROR.getMessage());
        }*/
        return JSONResult.success(employeeService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody EmployeeQuery query){
        Page<Employee> page = new Page<Employee>(query.getPage(),query.getRows());
        //员工条件查询补全-----作业
        if (query.getKeyword()!=null&&"".equals(query.getKeyword())){
            return employeeService.queryPage(query);
        }
        page = employeeService.selectPage(page);
        return JSONResult.success(new PageList<Employee>(page.getTotal(),page.getRecords()));
    }
}
