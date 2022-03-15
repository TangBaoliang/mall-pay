package ltd.itlover.ltd.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author TangBaoLiang
 * @date 2022/3/13
 * @email developert163@163.com
 **/
@Getter
@AllArgsConstructor
public enum PayPlatformEnum {
    ALIPAY(1),
    WX(2);

    Integer code;

    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
        for (PayPlatformEnum value : PayPlatformEnum.values()) {
            if (value.name().equals(bestPayTypeEnum.getPlatform().name())) {
                return value;
            }
        }
        throw  new RuntimeException("不支持的支付方式");
    }
}
