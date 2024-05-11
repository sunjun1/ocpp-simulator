package com.incarcloud.type;

/**
 * 停止事物的原因
 *
 * @author jack
 */
public enum Reason {
    DeAuthorized, // 授权失败
    EmergencyStop, // 紧急停止
    EVDisconnected, // 车辆断开连接
    HardReset, // 收到硬复位命令
    Local, // 本地停止（用户操作）
    Other,
    PowerLoss, // 断电
    Reboot, // 本地重启
    Remote, // 远程停止（用户操作）
    SoftReset, // 软件复位
    UnlockCommand // 收到解锁命令
}
