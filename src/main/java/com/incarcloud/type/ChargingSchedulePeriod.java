package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author jack
 * @Date 2019/8/6
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargingSchedulePeriod {
    private Integer startPeriod; // 1..1 开始时间，单位：秒
    private Double limit; // 1..1 充电速率的限制（功率/电流），一位小数
    private Integer numberPhases; // 0..1 相位数量，默认3

    public static ChargingSchedulePeriod instance(int startPeriod, double limit) {
        ChargingSchedulePeriod period = new ChargingSchedulePeriod();
        period.setStartPeriod(startPeriod);
        period.setLimit(limit);
        return period;
    }
}
