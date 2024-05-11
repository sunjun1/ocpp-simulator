package com.incarcloud.handle;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * ocpp消息处理器
 *
 * @param <T> ocpp消息类型
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/24 15:07
 */
public interface IOcppMessageHandler<T> {
    /**
     * 处理ocpp消息
     *
     * @param ocppMessage 消息
     */
    void handle(OcppMessage ocppMessage) throws JsonProcessingException;

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    String msgType();
}
