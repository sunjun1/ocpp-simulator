package com.incarcloud.core;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 充电桩状态
 *
 * @author jack
 * @version 1.2.0
 */
@Getter
@AllArgsConstructor
public enum MsgDirectionEnum {
    SERVER(1, "server"),
    CLINET(2, "client"),
    UNKNOWN(99, "unknown");

    private Integer code;

    private String description;


    /**
     * 根据名称获取枚举对象
     *
     * @param name 名称
     * @return 枚举
     */
    public static MsgDirectionEnum instance(String name) {
        for (MsgDirectionEnum pointStatus : MsgDirectionEnum.values()) {
            if (pointStatus.name().equals(name)) {
                return pointStatus;
            }
        }
        return MsgDirectionEnum.UNKNOWN;
    }

    /**
     * 获取枚举名称
     *
     * @param code 编码
     * @return 名称
     */
    public static MsgDirectionEnum getMsg(Integer code) {
        if (ObjectUtil.isNull(code)) {
            return MsgDirectionEnum.UNKNOWN;
        }
        return Arrays.stream(MsgDirectionEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(MsgDirectionEnum.UNKNOWN);
    }
}
