package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jack
 * @Date 2019/8/6
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargingSchedule {
    private Integer duration; // 0..1 持续时间（秒）
    private Date startSchedule; // 0..1 绝对计划的起点
    private ChargingRateUnitType chargingRateUnit; // 1..1 充电速率的单位
    private List<ChargingSchedulePeriod> chargingSchedulePeriod; // 1..* 时间计划，（0代表0点）
    private Double minChargingRate; // 0..1 最低充电速率，一位小数

    public static ChargingSchedule instanceForPower(double power) {
        ChargingSchedule chargingSchedule = new ChargingSchedule();
        chargingSchedule.setChargingRateUnit(ChargingRateUnitType.W);
        List<ChargingSchedulePeriod> periodList = Stream.of(
                ChargingSchedulePeriod.instance(0, power)
        ).collect(Collectors.toList());
        chargingSchedule.setChargingSchedulePeriod(periodList);
        return chargingSchedule;
    }
}
