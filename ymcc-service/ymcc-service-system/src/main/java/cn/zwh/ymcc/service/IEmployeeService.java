package cn.zwh.ymcc.service;

import cn.zwh.ymcc.domain.Employee;
import cn.zwh.ymcc.query.EmployeeQuery;
import cn.zwh.ymcc.result.JSONResult;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whale
 * @since 2025-08-11
 */
public interface IEmployeeService extends IService<Employee> {


    JSONResult queryPage(EmployeeQuery query);
}
