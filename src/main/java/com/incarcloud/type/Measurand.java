package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用途
 * 表示的是这个值表示什么内容，例如电压，电流，功率，百分比，温度等
 *
 * @author jack
 */
public enum Measurand {
    @JsonProperty("Current.Export")
    Current_Export, // EV的瞬时输出电流
    @JsonProperty("Current.Import")
    Current_Import, // EV的瞬时输入电流
    @JsonProperty("Current.Offered")
    Current_Offered, // 给予EV最大的电流
    @JsonProperty("Energy.Active.Export.Register")
    Energy_Active_Export_Register, // EV输出的电量(电表里读的)
//    @JsonProperty("Energy.Active.Import.Register")
    @JsonProperty("Energy.Active.Import.Register")
    Energy_Active_Import_Register, // EV输入的电量(电表里读的)
    @JsonProperty("Energy.Reactive.Export.Register")
    Energy_Reactive_Export_Register, // EV输出的无功电量(电表里读的)
    @JsonProperty("Energy.Reactive.Import.Register")
    Energy_Reactive_Import_Register, // EV输入的无功电量(电表里读的)
    @JsonProperty("Energy.Active.Export.Interval")
    Energy_Active_Export_Interval, // "间隔"区间EV输出的电量
    @JsonProperty("Energy.Active.Import.Interval")
    Energy_Active_Import_Interval, // "间隔"区间EV输入的电量
    @JsonProperty("Energy.Reactive.Export.Interval")
    Energy_Reactive_Export_Interval, // "间隔"区间EV输出的无功电量
    @JsonProperty("Energy.Reactive.Import.Interval")
    Energy_Reactive_Import_Interval, // "间隔"区间EV输入的无功电量
    Frequency, // 频率
    @JsonProperty("Power.Active.Export")
    Power_Active_Export, // 电动汽车输出的瞬时有功功率。(W或千瓦)
    @JsonProperty("Power.Active.Import")
    Power_Active_Import, // 电动汽车进口瞬时有功功率。(W或千瓦)
    @JsonProperty("Power.Factor")
    Power_Factor, // 总能量流的瞬时功率因数
    @JsonProperty("Power.Offered")
    Power_Offered, // 提供给电动汽车的最大功率
    @JsonProperty("Power.Reactive.Export")
    Power_Reactive_Export, // 电动汽车输出的瞬时无功功率。(var或千乏)
    @JsonProperty("Power.Reactive.Import")
    Power_Reactive_Import, // 电动汽车进口瞬时无功功率。(var或千乏)
    RPM, // 转速
    SoC, // 充电车辆的充电状态(以百分比表示)
    Temperature, // 充电桩温度
    @JsonProperty("Voltage")
    Voltage // 交流电的有效电压
}
