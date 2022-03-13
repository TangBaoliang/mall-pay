package ltd.itlover.ltd.pay.controller;

import com.lly835.bestpay.model.PayResponse;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.pay.service.IPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/payment")
    public String payment(@ApiParam("付款金额") BigDecimal amount, @ApiParam("付款订单id")  Long orderId, Model model) {
        PayResponse payResponse = payService.create(orderId, amount);
        model.addAttribute("qrcode", payResponse.getCodeUrl());
        return "PayBarcode";
    }

    @RequestMapping("/notify")
    @ResponseBody
    public String notifyPay(@RequestBody String notifyData) {
        log.info("异步回调 = {}", notifyData);
        return payService.asyncNotify(notifyData);
    }
}
