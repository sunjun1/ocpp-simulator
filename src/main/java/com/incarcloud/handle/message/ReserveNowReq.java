package com.incarcloud.handle.message;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jack
 * @Date 2019/12/27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReserveNowReq {
    private Integer connectorId;
//    @JsonFormat(pattern = DatePattern.UTC_PATTERN, timezone = "GMT+0", locale = "GMT+0", shape = JsonFormat.Shape.STRING)
    @JsonFormat(pattern = DatePattern.UTC_PATTERN, timezone = "UTC")
    private LocalDateTime expiryDate; // 有效期
    private String idTag;
    private String parentIdTag; // (option)
    private Integer reservationId; // 预约ID
}
