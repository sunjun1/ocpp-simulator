package com.incarcloud.type;

/**
 * 卡的授权状态
 *
 * @author jack
 */
public enum AuthorizationStatus {
    Accepted,
    Blocked, // 锁卡
    Expired, // 过期
    Invalid, // 无效卡
    ConcurrentTx // 正在充电
}
