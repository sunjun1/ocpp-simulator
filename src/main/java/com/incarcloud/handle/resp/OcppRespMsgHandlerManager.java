package com.incarcloud.handle.resp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.incarcloud.core.Constant;
import com.incarcloud.handle.OcppResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.incarcloud.core.Constant.RedisNamespace.*;

/**
 * 协议消息解析管理器
 *
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/24 17:35
 */
@Component
public class OcppRespMsgHandlerManager implements IOcppRespMsgHandlerManager {
    private final Map<String, BaseOcppRespMsgHandler> msgHandlerMap;
//    private final StringRedisTemplate redisTemplate;
//    private final RedisCacheService redisCacheService;

    public static Map<String, String> dataMap = new HashMap<>();

    @Autowired
    public OcppRespMsgHandlerManager(List<BaseOcppRespMsgHandler> msgHandlerList) {
//        this.redisCacheService = redisCacheService;
        this.msgHandlerMap = msgHandlerList.stream()
                .collect(Collectors.toMap(BaseOcppRespMsgHandler::msgType, msgHandler -> msgHandler));

    }

    /**
     * 根据消息类型创建消息处理器
     *
     * @param uniqueId 消息id
     * @return 消息处理器
     */
    @Override
    public BaseOcppRespMsgHandler createMsgHandler(String uniqueId) {
        String key = Constant.RedisNamespace.appendKey(OCPP_MSG_TYPE, uniqueId);
//        String s = this.redisTemplate.opsForValue().get(key);
//        String s = redisCacheService.getCacheObject(key);
        String s = dataMap.get(key);
        if (StrUtil.isEmpty(s)) {
            return new UnknownRespMsgHandler();
        }
        OcppResultType ocppResultType = JSONUtil.toBean(s, OcppResultType.class);
        // 清除缓存
        //        this.redisTemplate.delete(key);
        return this.msgHandlerMap.getOrDefault(ocppResultType.getAction(), new UnknownRespMsgHandler());
    }
}
