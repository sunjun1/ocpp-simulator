package com.incarcloud.type;

import lombok.Data;

/**
 * 采样数据
 *
 * @author jack
 */
@Data
public class SampledValue {
    private String value; // 具体的数值
    private ReadingContext context; // 什么情况下采集的，默认Sample.Periodic(option)
    private ValueFormat format; // 默认Raw(option)
    private Measurand measurand; // 表示什么内容，默认Energy.Active.Import.Register(option)
    private Phase phase; // 相位 (option)
    private Location location; // 采样位置，默认Outlet (option)
    private UnitOfMeasure unit; // 测量单位 ，默认Wh（如果measurand=Energy）(option)
}
