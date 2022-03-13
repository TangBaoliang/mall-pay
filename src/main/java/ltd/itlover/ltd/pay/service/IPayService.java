package ltd.itlover.ltd.pay.service;

import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author(作者) 唐宝亮
 * @date(日期) 2022/3/11
 **/
public interface IPayService {
    PayResponse create(Long orderId, BigDecimal amount);
    String asyncNotify(String notifyData);
}