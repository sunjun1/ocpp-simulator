package com.incarcloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jackson配置
 *
 * @author chenzhao
 * @version 1.0
 * @since 2024-02-29 17:42
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * 非继承配置的Jackson初始化，不继承yaml默认配置
     */
    @Bean
    public ObjectMapper config() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        // 指令反序列化
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(AbstractCommand.class, commandDeserializer);
//        objectMapper.registerModule(module);

        return objectMapper;
    }

    /**
     * 继承配置的Jackson初始化
     */
    //    @Bean
    //    @Primary
    //    @ConditionalOnMissingBean(ObjectMapper.class)
    //    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
    //        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
    //        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //        objectMapper.registerModule(new JavaTimeModule());
    //        return objectMapper;
    //    }
}
