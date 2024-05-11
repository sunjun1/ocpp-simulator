package com.incarcloud.handle.resp;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.incarcloud.core.Constant;
import com.incarcloud.handle.AbstractOcppMsgHandler;
import com.incarcloud.handle.OcppMessage;
import com.incarcloud.handle.OcppResultType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.incarcloud.core.Constant.RedisNamespace.*;

/**
 * @param <T> 消息体
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/24 15:47
 */
@Slf4j
public class BaseOcppRespMsgHandler<T> extends AbstractOcppMsgHandler<T> {

//    protected RedisTemplate<String, String> redisTemplate;
//    @Autowired
//    private RedisCacheService redisCacheService;

    public static Map<String, String> dataMap = new HashMap<>();

    /**
     * 处理ocpp消息
     *
     * @param ocppMessage 消息
     */
    @Override
    public void handle(OcppMessage ocppMessage) throws JsonProcessingException {
        String payload = ocppMessage.getPayload();
        T msg = this.objectMapper.readValue(payload, this.getGenericClass());
        String key = Constant.RedisNamespace.appendKey(OCPP_MSG_TYPE, ocppMessage.getUniqueId());
        String s = dataMap.get(key);
//        String s = this.redisTemplate.opsForValue().get(key);
//        String s = redisCacheService.getCacheObject(key);
        OcppResultType ocppResultType = JSONUtil.toBean(s, OcppResultType.class);
        this.doHandle(ocppResultType, ocppMessage, msg);
        // 清除缓存
//        this.redisTemplate.delete(key);
//        redisCacheService.deleteObject(key);
    }

    /**
     * 消息处理
     *
     * @param originMsg 原始消息
     * @param t         消息体
     */
    @Override
    public void doHandle(OcppMessage originMsg, T t) {
        log.info("未知消息1->{}", JSONUtil.toJsonStr(originMsg));
    }

    /**
     * 消息处理
     *
     * @param cacheMsg  缓存请求消息
     * @param originMsg 应答原始封包
     * @param t         消息体
     */
    public void doHandle(OcppResultType cacheMsg, OcppMessage originMsg, T t) {
        log.info("未知消息2->{}", JSONUtil.toJsonStr(originMsg));
    }


    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    public String msgType() {
        return "UnknownMsg";
    }
}
