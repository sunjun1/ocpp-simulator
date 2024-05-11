package com.incarcloud.handle.message;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.incarcloud.type.MeterValue;
import com.incarcloud.type.Reason;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author jack
 */
@Data
public class StopTransactionReq {
    private String idTag; // (option)
    private Integer meterStop; // 枪结束充电时的电量(wh)
//    private Date timestamp; // 结束时间
    @JsonFormat(pattern = DatePattern.UTC_PATTERN, timezone = "UTC")
    private LocalDateTime timestamp; // 结束时间
    private Integer transactionId; // 交易号
    private Reason reason; // 结束原因(option)
    private List<MeterValue> transactionData; // 包含与计费相关的数据(option)
}
