package com.incarcloud.type;

/**
 * 充电桩状态
 *
 * @author jack
 */
public enum ChargePointStatus {
    Available, // 可用的
    Preparing, // 准备的
    Charging, // 充电中
    SuspendedEVSE, // 桩因为某种原因暂时停止功率输出给车
    SuspendedEV, // 桩这边正常输出，但是车不接收功率，表现出来的现象就是有电压无电流
    Finishing, // 充电完成
    Reserved, // 被预约
    Unavailable, // 不可用
    Faulted; // 桩故障

    public boolean isFree() {
        return this.equals(Available) || this.equals(Preparing);
    }

    public boolean isBusy() {
        return this.equals(Charging)
                || this.equals(SuspendedEVSE)
                || this.equals(SuspendedEV)
                || this.equals(Finishing)
                || this.equals(Reserved);
    }

    public boolean isUnavailable() {
        return this.equals(Unavailable) || this.equals(Faulted);
    }

    public boolean isCharging() {
        return this.equals(Charging);
    }
}
