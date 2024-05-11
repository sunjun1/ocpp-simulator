package com.incarcloud.handle.message;

import com.incarcloud.type.AuthorizationStatus;
import com.incarcloud.type.IdTagInfo;
import lombok.Data;

/**
 * @author jack
 */
@Data
public class StopTransactionConf {
    private IdTagInfo idTagInfo; // 卡授权信息(option)

    public static StopTransactionConf instance(String parentIdTag) {
        StopTransactionConf conf = new StopTransactionConf();
        IdTagInfo idTagInfo = new IdTagInfo();
        conf.setIdTagInfo(idTagInfo);
        idTagInfo.setStatus(AuthorizationStatus.Accepted);
        idTagInfo.setParentIdTag(parentIdTag);
        return conf;
    }

    public static StopTransactionConf instanceAccepted() {
        StopTransactionConf conf = new StopTransactionConf();
        IdTagInfo idTagInfo = new IdTagInfo();
        conf.setIdTagInfo(idTagInfo);
        idTagInfo.setStatus(AuthorizationStatus.Accepted);
        return conf;
    }

    public static StopTransactionConf instanceError() {
        StopTransactionConf conf = new StopTransactionConf();
        IdTagInfo idTagInfo = new IdTagInfo();
        conf.setIdTagInfo(idTagInfo);
        idTagInfo.setStatus(AuthorizationStatus.Invalid);
        return conf;
    }
}
