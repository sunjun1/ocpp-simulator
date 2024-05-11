package com.incarcloud.handle.message;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jack
 */
@Data
public class StartTransactionReq {
    private Integer connectorId; // 枪号
    private String idTag;  // 卡号
    private Integer meterStart; // 枪开始充电时的电量(wh)
    private Integer reservationId; // 预约号(option)
//    private Date timestamp; // 开始充电时间

    @JsonFormat(pattern = DatePattern.UTC_PATTERN)
    private LocalDateTime timestamp; // 开始充电时间
}
