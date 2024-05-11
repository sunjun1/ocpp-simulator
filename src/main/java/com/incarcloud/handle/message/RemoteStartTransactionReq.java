package com.incarcloud.handle.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incarcloud.type.ChargingProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jack
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteStartTransactionReq implements Serializable {
    private Integer connectorId; // 枪号(option)
    private String idTag; // 卡号
    private ChargingProfile chargingProfile; // option

    public static RemoteStartTransactionReq instance(Integer connectorId, String idTag) {
        RemoteStartTransactionReq req = new RemoteStartTransactionReq();
        req.setConnectorId(connectorId);
        req.setIdTag(idTag);
        return req;
    }
}
