package com.incarcloud.type;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 电表的值
 *
 * @author jack
 */
@Data
public class MeterValue {
//    private Date timestamp; // 1..1 采样时间
    @JsonFormat(pattern = DatePattern.UTC_PATTERN, timezone = "UTC")
    private LocalDateTime timestamp; // 1..1 采样时间
    private List<SampledValue> sampledValue; // 1..* 采样数据
}
