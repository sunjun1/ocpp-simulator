package com.incarcloud.handle.message;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.incarcloud.type.ChargePointErrorCode;
import com.incarcloud.type.ChargePointStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jack
 */
@Data
public class StatusNotificationReq {
    private Integer connectorId; // 枪Id，如果为0，则是桩
    private ChargePointErrorCode errorCode; // 错误代码
    private String info; // 错误信息(option)
    private ChargePointStatus status; // 充电桩状态
//    private Date timestamp; // 上报时间(option)
    @JsonFormat(pattern = DatePattern.UTC_PATTERN)
    private LocalDateTime timestamp; // 上报时间(option)
    private String vendorId; // 供应商Id(option)
    private String vendorErrorCode; // 供应商错误代码(供应商自定义的)(option)
}
