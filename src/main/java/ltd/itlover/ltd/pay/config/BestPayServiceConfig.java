package ltd.itlover.ltd.pay.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TangBaoLiang
 * @date 2022/3/12
 * @email developert163@163.com
 **/
@Configuration
public class BestPayServiceConfig {
    @Value("${winxin-pay.app-id}")
    private String appId;
    @Value("${winxin-pay.merchant-id}")
    private String merchantId;
    @Value("${winxin-pay.merchant-key}")
    private String merchantKey;
    @Value("${winxin-pay.notify-url}")
    private String notifyUrl;

    @Bean
    public BestPayService bestPayService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(appId);
        wxPayConfig.setMchId(merchantId);
        wxPayConfig.setMchKey(merchantKey);
        wxPayConfig.setNotifyUrl(notifyUrl);
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        return bestPayService;
    }
}
