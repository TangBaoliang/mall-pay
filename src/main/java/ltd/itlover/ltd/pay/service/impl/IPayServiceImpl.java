package ltd.itlover.ltd.pay.service.impl;
import com.google.gson.Gson;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.sun.org.glassfish.external.statistics.annotations.Reset;
import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.pay.enums.PayPlatformEnum;
import ltd.itlover.ltd.pay.mapper.PayInfoMapper;
import ltd.itlover.ltd.pay.pojo.PayInfo;
import ltd.itlover.ltd.pay.pojo.PayInfoExample;
import ltd.itlover.ltd.pay.service.IPayService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author(作者) 唐宝亮
 * @date(日期) 2022/3/11
 **/
@Service
@Slf4j
public class IPayServiceImpl implements IPayService {
    private final static String QUEUE_PAY_NOTIFY = "payNotify";

    @Resource
    private PayInfoMapper payInfoMapper;

    @Resource
    private BestPayService bestPayService;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public PayResponse create( Long orderId,BigDecimal amount, BestPayTypeEnum payTypeEnum) {
        if (payTypeEnum != BestPayTypeEnum.ALIPAY_PC && payTypeEnum != BestPayTypeEnum.WXPAY_NATIVE) {
            throw new RuntimeException("不支持的支付方式");
        }
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(orderId);
        payInfo.setPayAmount(amount);
        payInfo.setPayPlatform(PayPlatformEnum.getByBestPayTypeEnum(payTypeEnum).getCode());
        payInfo.setPlatformStatus(OrderStatusEnum.NOTPAY.name());
        payInfo.setCreateTime(new Date());
        payInfoMapper.insertSelective(payInfo);
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("测试");
        payRequest.setOrderId(orderId.toString());
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(payTypeEnum);
        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public String asyncNotify(String notifyData) {
        String response = "";
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info(payResponse.getOrderId() + "支付成功payResponse={}", payResponse);
        PayInfoExample payInfoExample = new PayInfoExample();
        payInfoExample.createCriteria().andOrderNoEqualTo(Long.valueOf(payResponse.getOrderId()));
        List<PayInfo> list = payInfoMapper.selectByExample(payInfoExample);

        if (list == null || list.size() != 1) {
            throw new RuntimeException("通过orderNo查询到的结果是 null, ");
        }

        PayInfo payInfo = list.get(0);

        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                throw new RuntimeException("异步通知中的金额和数据库中的金额不一致, orderNo=" + payResponse.getOrderId());
            }

            //修改订单状态为已支付
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setUpdateTime(null);
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

        //TODO pay发送MQ消息， mall 接收MQ消息
        amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, new Gson().toJson(payInfo));

        switch (payResponse.getPayPlatformEnum()) {
            case WX: {
                response = "<xml>\n" +
                        "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                        "<return_msg><![CDATA[OK]]></return_msg>" +
                        "</xml>";
                break;
            }
            case ALIPAY: {
                response = "success";
                break;
            }
            default: {
                throw new RuntimeException("不支持此支付方式");
            }
        }

        return response;
    }

    @Override
    public PayInfo getByOrderNo(Long orderNo) {
        PayInfoExample payInfoExample = new PayInfoExample();
        payInfoExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<PayInfo> list = payInfoMapper.selectByExample(payInfoExample);
        if (list == null || list.size() != 1) {
            return null;
        }
        return list.get(0);
    }


}
