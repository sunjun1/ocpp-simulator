package com.incarcloud.handle.message;

import com.incarcloud.type.CancelReservationStatus;
import lombok.Data;

/**
 * @author jack
 * @Date 2019/12/27
 */
@Data
public class CancelReservationConf {
    private CancelReservationStatus status;
}
