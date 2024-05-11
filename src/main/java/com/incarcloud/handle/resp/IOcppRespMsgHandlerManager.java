package com.incarcloud.handle.resp;

/**
 * 消息处理管理器
 *
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/24 17:32
 */
public interface IOcppRespMsgHandlerManager {

    /**
     * 根据消息id创建消息处理器
     *
     * @param uniqueId 消息id
     * @return 消息处理器
     */
    BaseOcppRespMsgHandler createMsgHandler(String uniqueId);

}
