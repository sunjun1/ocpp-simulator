package com.incarcloud.handle.message;

import lombok.Data;

/**
 * @author jack
 */
@Data
public class RemoteStopTransactionReq {
    private Integer transactionId; // 交易号
}
