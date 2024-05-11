package com.incarcloud.handle.message;

import lombok.Data;

/**
 * @author jack
 */
@Data
public class RemoteStopTransactionConf {
    private RemoteStartStopStatus status; // 远程停止状态
}
