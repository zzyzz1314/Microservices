package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.domain.AlipayInfo;
import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.dto.ApplyDto;
import cn.zwh.ymcc.mapper.PayOrderMapper;
import cn.zwh.ymcc.service.IAlipayInfoService;
import cn.zwh.ymcc.service.IPayOrderService;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-23
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    @Autowired
    private IAlipayInfoService alipayInfoService;

    @Override
    public PayOrder checkPayOrder(String orderNo) {

        if (StringUtils.isEmpty(orderNo)){
            throw new RuntimeException("订单号不存在");
        }

        Wrapper<PayOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no",orderNo);
        PayOrder payOrder = selectOne(wrapper);

        return payOrder;
    }

    @Override
    public String apply(ApplyDto applyDto) {

        //前端给了就用前端的，前端没给就用数据库的
        String returnUrl = applyDto.getCallUrl();
        String orderNo = applyDto.getOrderNo();
        String payType = applyDto.getPayType().toString();

        //查询数据库中的t_alipay_info
        List<AlipayInfo> alipayInfos = alipayInfoService.selectList(null);
        AlipayInfo alipayInfo = alipayInfos.get(0);
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions(alipayInfo));


        //根据订单号，查询支付单
        EntityWrapper<PayOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no",orderNo);
        PayOrder payOrder = selectOne(wrapper);

        try {
            AlipayTradePagePayResponse response = Factory.Payment.Page().pay(
                    payOrder.getSubject(),
                    orderNo,
                    payOrder.getAmount().toString(),
                    returnUrl);
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                return response.body;
            } else {
                System.err.println("调用失败，原因：" + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }

    public  Config getOptions(AlipayInfo alipayInfo) {

        Config config = new Config();
        config.protocol = alipayInfo.getProtocol();
        config.gatewayHost = alipayInfo.getGatewayHost();
        config.signType = alipayInfo.getSignType();
        config.appId = alipayInfo.getAppId();
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = alipayInfo.getMerchantPrivateKey();
        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        config.alipayPublicKey = alipayInfo.getAlipayPublicKey();
        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = alipayInfo.getNotifyUrl();
        return config;
    }

}
