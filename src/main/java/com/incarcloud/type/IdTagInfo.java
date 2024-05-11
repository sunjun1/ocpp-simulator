package com.incarcloud.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 卡授权信息
 *
 * @author jack
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdTagInfo {
    private Date expiryDate; // 失效时间(option)
    private String parentIdTag; // 组ID（同组内的卡可以互相操作，类似于家庭组）(option)
    private AuthorizationStatus status;

    public static IdTagInfo instance(AuthorizationStatus status) {
        IdTagInfo idTagInfo = new IdTagInfo();
        idTagInfo.setStatus(status);
        return idTagInfo;
    }

    public static IdTagInfo instance(String parentIdTag, AuthorizationStatus status) {
        IdTagInfo idTagInfo = new IdTagInfo();
        idTagInfo.setParentIdTag(parentIdTag);
        idTagInfo.setStatus(status);
        return idTagInfo;
    }
}
