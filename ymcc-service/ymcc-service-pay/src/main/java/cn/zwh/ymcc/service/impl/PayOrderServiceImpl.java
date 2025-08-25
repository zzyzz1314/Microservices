package cn.zwh.ymcc.service.impl;

import cn.zwh.ymcc.OrderFeignAPI;
import cn.zwh.ymcc.domain.AlipayInfo;
import cn.zwh.ymcc.domain.PayFlow;
import cn.zwh.ymcc.domain.PayOrder;
import cn.zwh.ymcc.dto.AlipayNotifyDto;
import cn.zwh.ymcc.dto.ApplyDto;
import cn.zwh.ymcc.dto.UpdateOrderStatusDto;
import cn.zwh.ymcc.mapper.PayOrderMapper;
import cn.zwh.ymcc.result.JSONResult;
import cn.zwh.ymcc.service.IAlipayInfoService;
import cn.zwh.ymcc.service.IPayFlowService;
import cn.zwh.ymcc.service.IPayOrderService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeCancelResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author whale
 * @since 2025-08-23
 */
@Slf4j
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    @Autowired
    private IAlipayInfoService alipayInfoService;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private IPayFlowService payFlowService;

    @Autowired
    private OrderFeignAPI orderFeignAPI;

    @Override
    public String asyncNotify(@Valid AlipayNotifyDto alipayNotifyDto) {

        try {
            // 1. 参数校验

            // 2. 参数验签， 为了防止数据被纂改
            Map<String, String> parameters = JSONObject.parseObject(JSONObject.toJSONString(alipayNotifyDto), Map.class);
            Boolean flag = Factory.Payment.Common().verifyNotify(parameters);
            if (!flag) {
                return "error";
            }
            //1. 一定要校验
            //3. 商家需要验证该通知数据中的 out_trade_no orderNo 是否为商家系统中创建的订单号。
            //4. 判断total_amount 是否确实为该订单的实际金额（即商家订单创建时的金额）
            //5. 验证通知中的 seller_id（或者 seller_email) 是否为 out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            //6. 验证 app_id 是否为该商户本身。

            //7. 自己的业务
            //7.1 修改支付单的支付状态
            Date now = new Date();
            Integer payStatus = 0;
            if (alipayNotifyDto.getTrade_status().equals(AlipayNotifyDto.TRADE_CLOSED)) {
                payStatus = 2;
            }
            if (alipayNotifyDto.getTrade_status().equals(AlipayNotifyDto.TRADE_SUCCESS)
                    || alipayNotifyDto.getTrade_status().equals(AlipayNotifyDto.TRADE_FINISHED
            )) {
                payStatus = 1;
            }
            //参数一： 订单号 参数二： 更新时间 参数三： 支付状态
            payOrderMapper.updateStatusByOrderNo(alipayNotifyDto.getOut_trade_no(), now, payStatus);

            //增加流水记录
            PayFlow payFlow = new PayFlow();
            payFlow.setNotifyTime(now);
            payFlow.setSubject(alipayNotifyDto.getSubject());
            payFlow.setOutTradeNo(alipayNotifyDto.getOut_trade_no());
            payFlow.setTotalAmount(new BigDecimal(alipayNotifyDto.getTotal_amount()));
            payFlow.setTradeStatus(alipayNotifyDto.getTrade_status());
            payFlow.setCode(alipayNotifyDto.getCode());
            payFlow.setMsg(alipayNotifyDto.getMsg());
            payFlow.setPaySuccess(payStatus == 1 ? true : false);
            payFlow.setResultDesc("支付状态:" +
                    ((alipayNotifyDto.getTrade_status().equals(AlipayNotifyDto.TRADE_SUCCESS) ||
                            alipayNotifyDto.getTrade_status().equals(AlipayNotifyDto.TRADE_FINISHED))
                            ? "成功" : "失败"));
            payFlowService.insert(payFlow);
            //7.2 修改订单状态 === 订单服务
            //需要一个订单状态和订单号
            UpdateOrderStatusDto updateOrderStatusDto = new UpdateOrderStatusDto();
            updateOrderStatusDto.setOrderNo(alipayNotifyDto.getOut_trade_no());
            updateOrderStatusDto.setStatusOrder(payStatus);
            updateOrderStatusDto.setUpdateTime(now);
            JSONResult jsonResult = orderFeignAPI.updateOrderStatus(updateOrderStatusDto);
            log.info("订单状态更新结果:{}", jsonResult);

            // TODO 自己完善
            //7.3 t_course_user_learn ==> 增加一条记录  课程服务
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    public void cancelOrder(String orderNo) {
        if (!StringUtils.isEmpty(orderNo)){
            try {
                AlipayTradeCancelResponse response = Factory.Payment.Common().cancel(orderNo);

                log.info("关闭订单结果：{}", response);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("关闭订单异常：{}", e.getMessage());
            }
        }

    }

    @Override
    public String queryOrderStatus(String orderNo) {
        try {
            AlipayTradeQueryResponse query = Factory.Payment.Common().query(orderNo);
            return query.getHttpBody();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    @Override
    public PayOrder checkPayOrder(String orderNo) {

        if (StringUtils.isEmpty(orderNo)) {
            throw new RuntimeException("订单号不存在");
        }

        Wrapper<PayOrder> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no", orderNo);
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
        wrapper.eq("order_no", orderNo);
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

    public Config getOptions(AlipayInfo alipayInfo) {

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
