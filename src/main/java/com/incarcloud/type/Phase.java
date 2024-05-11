package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 相位
 * 电流相位用 L1、L2、L3 or L1-N、L2-N、L3-N
 * 电压相位用 L1-N、L2-N、L3-N
 * @author jack
 */
public enum Phase {
    L1,
    L2,
    L3,
    N,
    @JsonProperty("L1-N")
    L1_N,
    @JsonProperty("L2-N")
    L2_N,
    @JsonProperty("L3-N")
    L3_N,
    @JsonProperty("L1-L2")
    L1_L2,
    @JsonProperty("L2-L3")
    L2_L3,
    @JsonProperty("L3-L1")
    L3_L1
}
