package com.incarcloud.handle.message;

import com.incarcloud.type.MeterValue;
import lombok.Data;

import java.util.List;

/**
 * @author jack
 */
@Data
public class MeterValuesReq {
    private Integer connectorId; // 1..1
    private Integer transactionId; // 0..1
    private List<MeterValue> meterValue; // 1..*
}
