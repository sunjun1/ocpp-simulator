package com.incarcloud.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ocpp消息类型
 *
 * @author chenzhao
 */

@Getter
@AllArgsConstructor
public enum OcppMessageType {
    /**
     * 请求消息
     * [<MessageTypeId>, "<UniqueId>", "<Action>", {<Payload>}]
     */
    CALL(2, "请求消息"),

    /**
     * 应答消息
     * [<MessageTypeId>, "<UniqueId>", {<Payload>}]
     */
    CALL_RESULT(3, "应答消息"),

    /**
     * 错误消息
     * [<MessageTypeId>, "<UniqueId>", "<errorCode>", "<errorDescription>", {<errorDetails>}]
     */
    CALL_RESULT_ERROR(4, "错误消息");

    /**
     * 类型： 2=请求消息，3=应答消息，4=错误消息
     */
    private final int type;

    /**
     * 名称
     */
    private final String detail;


}
