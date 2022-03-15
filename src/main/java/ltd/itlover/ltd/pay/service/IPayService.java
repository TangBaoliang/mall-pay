package ltd.itlover.ltd.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import ltd.itlover.ltd.pay.pojo.PayInfo;

import java.math.BigDecimal;

/**
 * @author(作者) 唐宝亮
 * @date(日期) 2022/3/11
 **/
public interface IPayService {
    PayResponse create(Long orderId, BigDecimal amount, BestPayTypeEnum payTypeEnum);
    String asyncNotify(String notifyData);
    PayInfo getByOrderNo(Long orderNo);
}