package com.incarcloud.handle.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incarcloud.type.AuthorizationStatus;
import com.incarcloud.type.IdTagInfo;
import lombok.Data;

/**
 * @author jack
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StartTransactionConf {
    private IdTagInfo idTagInfo; // 1..1 卡授权信息
    private Integer transactionId; // 1..1 交易号

    public static StartTransactionConf instance(Integer transactionId, IdTagInfo idTagInfo) {
        StartTransactionConf conf = new StartTransactionConf();
        conf.setTransactionId(transactionId);
        conf.setIdTagInfo(idTagInfo);
        return conf;
    }

    public static StartTransactionConf instance(Integer transactionId, AuthorizationStatus status) {
        StartTransactionConf conf = new StartTransactionConf();
        conf.setTransactionId(transactionId);
        IdTagInfo idTagInfo = new IdTagInfo();
        conf.setIdTagInfo(idTagInfo);
        idTagInfo.setStatus(status);
        return conf;
    }

    public static StartTransactionConf instance(String parentIdTag, Integer transactionId, AuthorizationStatus status) {
        StartTransactionConf conf = new StartTransactionConf();
        conf.setTransactionId(transactionId);
        IdTagInfo idTagInfo = new IdTagInfo();
        conf.setIdTagInfo(idTagInfo);
        idTagInfo.setStatus(status);
        idTagInfo.setParentIdTag(parentIdTag);
        return conf;
    }

    public static StartTransactionConf instanceError() {
        StartTransactionConf conf = new StartTransactionConf();
        IdTagInfo idTagInfo = new IdTagInfo();
        conf.setIdTagInfo(idTagInfo);
        idTagInfo.setStatus(AuthorizationStatus.Invalid);
        conf.setTransactionId(0);
        return conf;
    }
}
