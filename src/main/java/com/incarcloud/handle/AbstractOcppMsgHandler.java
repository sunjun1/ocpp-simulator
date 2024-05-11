package com.incarcloud.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 消息处理器通用逻辑
 *
 * @param <T> 消息体
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/24 15:47
 */
public abstract class AbstractOcppMsgHandler<T> implements IOcppMessageHandler<T> {
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 处理ocpp消息
     *
     * @param ocppMessage 消息
     */
    @Override
    public void handle(OcppMessage ocppMessage) throws JsonProcessingException {
        String payload = ocppMessage.getPayload();
        T msg = this.objectMapper.readValue(payload, this.getGenericClass());
        this.doHandle(ocppMessage, msg);
    }

    /**
     * 消息处理
     *
     * @param originMsg 原始消息
     * @param t         消息体
     */
    public abstract void doHandle(OcppMessage originMsg, T t);


//    public Class<T> getGenericClass() {
//        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
//        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
//    }

    public Class<T> getGenericClass() {
        // 检查当前类是否为泛型类
        if (!(this.getClass().getGenericSuperclass() instanceof ParameterizedType)) {
            throw new IllegalStateException("The superclass of this class is not a parameterized type.");
        }

        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        // 检查是否有泛型参数
        if (actualTypeArguments.length == 0) {
            throw new IllegalStateException("The superclass of this class does not have any actual type arguments.");
        }

        // 检查泛型参数是否为Class类型
        if (!(actualTypeArguments[0] instanceof Class)) {
            throw new IllegalStateException("The first type argument of the superclass of this class is not a class type.");
        }

        // 转换泛型参数
        @SuppressWarnings("unchecked")
        Class<T> genericClass = (Class<T>) actualTypeArguments[0];
        return genericClass;
    }

}
