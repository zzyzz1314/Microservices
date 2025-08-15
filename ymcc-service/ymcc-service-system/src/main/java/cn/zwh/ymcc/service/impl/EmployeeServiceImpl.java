package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.Employee;
import cn.zwh.ymcc.mapper.EmployeeMapper;
import cn.zwh.ymcc.query.EmployeeQuery;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.result.PageList;
import cn.zwh.ymcc.service.IEmployeeService;
import com.alibaba.spring.util.WrapperUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-11
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public JSONResult queryPage(EmployeeQuery query) {
        Page<Employee> page = new Page<>(query.getPage(), query.getRows());
        String keyword = query.getKeyword();
        EntityWrapper<Employee> wrapper = new EntityWrapper<>();
        wrapper.like(keyword != null, "real_name", keyword);
        Page<Employee> list = selectPage(page, wrapper);
        return JSONResult.success(new PageList<Employee>(list.getTotal(), list.getRecords()));
    }
}
