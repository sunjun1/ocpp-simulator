package com.incarcloud.handle.message;

import lombok.Data;

/**
 * @author jack
 * @Date 2019/12/27
 */
@Data
public class CancelReservationReq {
    private Integer reservationId;
    public static CancelReservationReq instance(Integer reservationId) {
        CancelReservationReq req = new CancelReservationReq();
        req.setReservationId(reservationId);
        return req;
    }
}
