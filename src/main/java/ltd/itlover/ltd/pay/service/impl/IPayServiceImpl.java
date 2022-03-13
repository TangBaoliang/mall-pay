package ltd.itlover.ltd.pay.service.impl;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.pay.service.IPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author(作者) 唐宝亮
 * @date(日期) 2022/3/11
 **/
@Service
@Slf4j
public class IPayServiceImpl implements IPayService {
    @Resource
    private BestPayService bestPayService;

    @Override
    public PayResponse create( Long orderId,BigDecimal amount) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("测试");
        payRequest.setOrderId(orderId.toString());
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);
        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public String asyncNotify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info(payResponse.getOrderId() + "支付成功payResponse={}", payResponse);

        return "{  \n" +
                "    \"code\": \"SUCCESS\",\n" +
                "    \"message\": \"成功\"\n" +
                "}";
    }
}
