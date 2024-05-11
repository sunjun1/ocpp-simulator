package com.incarcloud.handle.resp;

import cn.hutool.json.JSONUtil;
import com.incarcloud.handle.OcppMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 未知消息处理
 * @author chenzhao
 * @since  2023/11/24 17:53
 * @version 1.0
 */
@Slf4j
@Service
public class UnknownRespMsgHandler extends BaseOcppRespMsgHandler<String> {
    /**
     * 处理ocpp消息
     *
     * @param ocppMessage 消息
     */
    @Override
    public void handle(OcppMessage ocppMessage) {
        log.info("未知应答消息->{}", JSONUtil.toJsonStr(ocppMessage));
    }

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    public String msgType() {
        return "UnknownRespMsg";
    }
}
