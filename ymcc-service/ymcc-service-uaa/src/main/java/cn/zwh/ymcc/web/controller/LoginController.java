package cn.zwh.ymcc.web.controller;

import cn.zwh.ymcc.dto.LoginDto;
import cn.zwh.ymcc.service.ILoginService;
import cn.zwh.ymcc.domain.Login;
import cn.zwh.ymcc.query.LoginQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public ILoginService loginService;

    /**
    * 登录
    */
    @RequestMapping(value="/common",method= RequestMethod.POST)
    public JSONResult login(@RequestBody @Valid LoginDto loginDto){
        Map<String,Object> map =loginService.login(loginDto);
        return JSONResult.success(map);
    }

    /**
    * 注册
    */
    @RequestMapping(value="/register",method= RequestMethod.POST)
    public JSONResult register(@RequestBody Login login){
        loginService.insert(login);
        return JSONResult.success(login);
    }


    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody Login login){
        if(login.getId()!=null){
            loginService.updateById(login);
        }else{
            loginService.insert(login);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        loginService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(loginService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @PreAuthorize("hasAnyAuthority('login:list','employee:update')")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(loginService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody LoginQuery query){
        Page<Login> page = new Page<Login>(query.getPage(),query.getRows());
        page = loginService.selectPage(page);
        return JSONResult.success(new PageList<Login>(page.getTotal(),page.getRecords()));
    }
}
