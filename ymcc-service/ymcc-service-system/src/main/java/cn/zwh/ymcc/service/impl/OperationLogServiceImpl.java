package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.OperationLog;
import cn.zwh.ymcc.mapper.OperationLogMapper;
import cn.zwh.ymcc.service.IOperationLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-11
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
