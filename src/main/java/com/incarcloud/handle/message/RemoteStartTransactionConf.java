package com.incarcloud.handle.message;

import lombok.Data;

/**
 * @author jack
 */
@Data
public class RemoteStartTransactionConf {
    private RemoteStartStopStatus status; // 远程开始状态
}
