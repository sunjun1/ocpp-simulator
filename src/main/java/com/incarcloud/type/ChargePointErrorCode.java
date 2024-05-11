package com.incarcloud.type;

/**
 * 充电桩错误代码
 *
 * @author jack
 */
public enum ChargePointErrorCode {
    ConnectorLockFailure, // 锁定/解锁枪失败
    EVCommunicationError, // 与车辆通信失败（不是一个真正的错误）
    GroundFailure, // 接地故障断路器已被激活
    HighTemperature, // 桩温度过高
    InternalError, // 内部硬件或软件组件的错误
    LocalListConflict, // 桩与平台授权信息冲突
    NoError, // 没有错误报告
    OtherError, // 其他错误（其他错误参照vendorErrorCode，为供应商自定义错误）
    OverCurrentFailure, // 过电流保护装置跳闸
    OverVoltage, // 电压过高
    PowerMeterFailure, // 电表读取失败
    PowerSwitchFailure, // 电源开关控制失败
    ReaderFailure, // 卡号读取失败
    ResetFailure, // 充电桩重启失败
    UnderVoltage, // 电压过低
    WeakSignal // 信号弱
}
