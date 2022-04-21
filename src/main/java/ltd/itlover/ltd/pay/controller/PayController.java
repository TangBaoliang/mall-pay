package ltd.itlover.ltd.pay.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.pay.config.BestPayServiceConfig;
import ltd.itlover.ltd.pay.pojo.PayInfo;
import ltd.itlover.ltd.pay.service.IPayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author TangBaoLiang
 * @date 2022/3/12
 * @email developert163@163.com
 **/
@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Resource
    private IPayService payService;

    @Resource
    private BestPayServiceConfig bestPayServiceConfig;

    @GetMapping("/create")
    public ModelAndView payment(@ApiParam("付款金额") BigDecimal amount,
                          @ApiParam("付款订单id")  Long orderId,
                          @ApiParam("支付方式") @RequestParam("payType") BestPayTypeEnum payTypeEnum
    ) {
        PayResponse payResponse = payService.create(orderId, amount, payTypeEnum);
        ModelAndView modelAndView = new ModelAndView();
        switch (payTypeEnum) {
            case ALIPAY_PC: {
                modelAndView.setViewName("AliPayCode");
                modelAndView.addObject("body", payResponse.getBody());
                break;
            }
            case WXPAY_NATIVE: {
                modelAndView.setViewName("WxPayBarcode");
                modelAndView.addObject("qrcode", payResponse.getCodeUrl());
                modelAndView.addObject("returnUrl", bestPayServiceConfig.getReturnUrl());
                modelAndView.addObject("orderNo", orderId);
                break;
            }
            default:{
                throw new RuntimeException("not supported protoc");
            }
        }
        return modelAndView;
    }

    @PostMapping("/notify")
    @ResponseBody
    public String notifyPay(@RequestBody String notifyData) {
        log.info("异步回调 = {}", notifyData);
        return payService.asyncNotify(notifyData);
    }

    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderNo(@RequestParam("orderId") Long orderNo) {
        return payService.getByOrderNo(orderNo);
    }
}
