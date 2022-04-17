package ltd.itlover.ltd.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import ltd.itlover.ltd.pay.service.IPayService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

import java.math.BigDecimal;


@SpringBootTest
@PropertySource("classpath:application.yaml")
class IPayServiceImplTest {
    @Resource
    private AmqpTemplate amqpTemplate;

    @Resource
    private IPayService iPayService;

    @Test
    void create() {
        iPayService.create(231L, BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }

    @Test
    void sendMsgToMq () throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            amqpTemplate.convertAndSend("payNotify", "支付成功！！！");
        }

    }
}