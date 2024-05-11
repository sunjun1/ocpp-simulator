package com.incarcloud.handle.message;

/**
 * 预约结果状态
 * @author jack
 * @Date 2019/12/27
 */
public enum ReservationStatus {
    Accepted, // 接受
    Faulted, // 故障
    Occupied, // 已占用
    Rejected // 拒绝
}
