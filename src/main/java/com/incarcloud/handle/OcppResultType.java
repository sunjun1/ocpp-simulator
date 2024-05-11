package com.incarcloud.handle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * ocpp应答消类型
 *
 * @author chenzhao
 * @version 1.0
 * @since 2023/11/27 15:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OcppResultType implements Serializable {

    @Serial
    private static final long serialVersionUID = -7337994133655741016L;
    /**
     * 消息类型
     */
    private String action;
    /**
     * 唯一id
     */
    private String uniqueId;
    /**
     * 数据
     */
    private String payload;

}
