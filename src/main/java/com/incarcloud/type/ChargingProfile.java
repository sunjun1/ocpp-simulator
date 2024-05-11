package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author jack
 * @Date 2019/8/6
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargingProfile {
    private Integer chargingProfileId; // 1..1 主键
    private Integer transactionId; // 0..1 交易号
    private Integer stackLevel; // 1..1 stackLevel >= 0 优先级
    private ChargingProfilePurposeType chargingProfilePurpose; // 1..1 用途
    private ChargingProfileKindType chargingProfileKind; // 1..1 调度类型
    private RecurrencyKindType recurrencyKind; // 0..1 循环方式
    private Date validFrom; // 0..1 生效时间Start
    private Date validTo; // 0..1 生效时间End
    private ChargingSchedule chargingSchedule; // 1..1 充电计划

    public static ChargingProfile instance(double power) {
        ChargingProfile profile = new ChargingProfile();
        profile.setChargingProfileId((int) (System.currentTimeMillis() / 1000));
        profile.setStackLevel(1);
        profile.setChargingProfilePurpose(ChargingProfilePurposeType.TxProfile);
        profile.setChargingProfileKind(ChargingProfileKindType.Relative);
        profile.setChargingSchedule(ChargingSchedule.instanceForPower(power));
        return profile;
    }
}
