package ltd.itlover.ltd.pay.config;

import com.lly835.bestpay.config.AliPayConfig;
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
    @Value("${pay.winxin-pay.app-id}")
    private String appId;
    @Value("${pay.winxin-pay.merchant-id}")
    private String merchantId;
    @Value("${pay.winxin-pay.merchant-key}")
    private String merchantKey;
    @Value("${pay.notify-url}")
    private String notifyUrl;
    @Value("${pay.return-url}")
    private String returnUrl;

    @Value("${pay.ali-pay.ali-public-key}")
    private String aliPublicKey;
    @Value("${pay.ali-pay.private-key}")
    private String aliPrivateKey;
    @Value("${pay.ali-pay.app-id}")
    private String aliPayAppId;

    @Bean
    public BestPayService bestPayService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(appId);
        wxPayConfig.setMchId(merchantId);
        wxPayConfig.setMchKey(merchantKey);
        wxPayConfig.setNotifyUrl(notifyUrl);
        wxPayConfig.setReturnUrl(returnUrl);
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);

        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(aliPayAppId);
        aliPayConfig.setNotifyUrl(notifyUrl);
        aliPayConfig.setAliPayPublicKey(aliPublicKey);
        aliPayConfig.setPrivateKey(aliPrivateKey);
        aliPayConfig.setReturnUrl(returnUrl);
        bestPayService.setAliPayConfig(aliPayConfig);
        return bestPayService;
    }
}
