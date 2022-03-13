package ltd.itlover.ltd.pay.service.impl;

import ltd.itlover.ltd.pay.service.IPayService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

import java.math.BigDecimal;


@SpringBootTest
@PropertySource("classpath:application.yaml")
class IPayServiceImplTest {

    @Resource
    private IPayService iPayService;

    @Test
    void create() {
        iPayService.create(231L, BigDecimal.valueOf(0.01));
    }
}