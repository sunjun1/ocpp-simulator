package com.incarcloud.handle.resp;

import cn.hutool.json.JSONUtil;

import com.incarcloud.core.OcppContents;
import com.incarcloud.handle.OcppMessage;
import com.incarcloud.handle.OcppResultType;
import com.incarcloud.handle.message.ReservationStatus;
import com.incarcloud.handle.message.ReserveNowConf;
import com.incarcloud.handle.message.ReserveNowReq;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ReserveNowConf-预约充电
 *
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/24 15:25
 */
@Slf4j
@Service
@AllArgsConstructor
public class ReserveNowConfMsgHandler extends BaseOcppRespMsgHandler<ReserveNowConf> {

//    private final CpoSessionService cpoSessionService;
//
//    private final RedisCacheService redisCacheService;
//
//    private final OcpiFeignClientFactory ocpiFeignClientFactory;
//
//    private final CommonService commonService;

    /**
     * 消息处理
     *
     * @param reserveNowConf 消息体
     */
    @Override
    public void doHandle(OcppResultType cacheMsg, OcppMessage originMsg, ReserveNowConf reserveNowConf) {
        String payload = cacheMsg.getPayload();
        ReserveNowReq reserveNowReq = JSONUtil.toBean(payload, ReserveNowReq.class);
        log.info("预约充电[{}:{}]桩应答->{}", originMsg.getDeviceId(), reserveNowReq.getConnectorId(),
                JSONUtil.toJsonStr(reserveNowConf));
        ReservationStatus status = reserveNowConf.getStatus();
    }

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    public String msgType() {
        return OcppContents.ReserveNow;
    }
}
