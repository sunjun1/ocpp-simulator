package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 什么情况下采集的
 *
 * @author jack
 */
public enum ReadingContext {
    @JsonProperty("Interruption.Begin")
    Interruption_Begin, // 在中断开始时获取的值
    @JsonProperty("Interruption.End")
    Interruption_End, // 在中断恢复时获取的值
    Other,
    @JsonProperty("Sample.Clock")
    Sample_Clock, // 相对于时钟时间间隔取值
    @JsonProperty("Sample.Periodic")
    Sample_Periodic, // 相对于充电开始时间间隔取值
    @JsonProperty("Transaction.Begin")
    Transaction_Begin, // 充电开始时获取的值
    @JsonProperty("Transaction.End")
    Transaction_End, // 充电结束时获取的值
    Trigger // 平台主动询问
}
