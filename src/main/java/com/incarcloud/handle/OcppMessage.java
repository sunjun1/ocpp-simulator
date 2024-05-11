package com.incarcloud.handle;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.incarcloud.type.OcppMessageType;
import com.incarcloud.utils.OcppJsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.util.Date;

import static com.incarcloud.core.OcppContents.*;

/**
 * @author chenzhao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Document(collection = "iot-data")
public class OcppMessage {
    @Id
    String id;
    /**
     * 设备id
     */
    String deviceId;

    /**
     * 数据流向 server/client
     */
    String direction;

    /**
     * 消息接收时间
     */
    @JsonFormat(pattern = DatePattern.UTC_PATTERN, timezone = "GMT+0", locale = "GMT+0", shape = JsonFormat.Shape.STRING)
    private Date dateTime;
    /**
     * 消息流向 2-主动发起 3-被动应答 4-异常信息
     */
    private Integer messageTypeId;
    /**
     * 消息唯一id
     */
    private String uniqueId;
    /**
     * 消息类型
     */
    private String action;
    /**
     * 消息体
     */
    private String payload;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorDescription;


    /**
     * 组装应答消息
     *
     * @param uniqueId 唯一id
     * @param action   动作
     * @param payload  消息体
     * @return 应答
     */
    public static OcppMessage makeOcppCallResult(String deviceId, String uniqueId, String action, String payload) {
        OcppMessage req = new OcppMessage();
        req.setAction(action);
        req.setMessageTypeId(OcppMessageType.CALL_RESULT.getType());
        req.setUniqueId(uniqueId);
        req.setDeviceId(deviceId);
        req.setPayload(payload);

        return req;
    }

    /**
     * 组装请求消息
     *
     * @param action  动作
     * @param payload 消息体
     * @return 请求
     */
    public static OcppMessage makeOcppCall(String deviceId, String action, String payload) {
        OcppMessage req = new OcppMessage();
        req.setAction(action);
        req.setMessageTypeId(OcppMessageType.CALL.getType());
        req.setUniqueId(UUID.fastUUID().toString());
        req.setDeviceId(deviceId);
        req.setPayload(payload);

        return req;
    }

    /**
     * 组装错误消息
     *
     * @param deviceId         设备id
     * @param errorCode        错误码
     * @param errorDescription 错误描述
     * @return 错误消息
     */
    public static OcppMessage makeOcppCallError(
            String deviceId, String errorCode, String errorDescription) {
        OcppMessage error = new OcppMessage();
        error.setMessageTypeId(OcppMessageType.CALL_RESULT_ERROR.getType());
        error.setUniqueId(UUID.randomUUID().toString());
        error.setDeviceId(deviceId);
        error.setErrorCode(errorCode);
        error.setErrorDescription(errorDescription);

        return error;

    }


    /**
     * 组装应答消息
     *
     * @param uniqueId 唯一id
     * @param action   动作
     * @param payload  消息体
     * @return 应答
     */
    public static String makeCallResult(String uniqueId, String action, Object payload) {
        return String.format(CALL_RESULT_FORMAT, uniqueId, payload);
    }

    /**
     * 组装请求消息
     *
     * @param uniqueId 唯一id
     * @param action   动作
     * @param payload  消息体
     * @return 请求
     */
    public static String makeCall(String uniqueId, String action, Object payload) {
        return String.format(CALL_FORMAT, uniqueId, action, payload);
    }

    /**
     * 组装错误消息
     *
     * @param uniqueId         唯一id
     * @param action           动作
     * @param errorCode        错误码
     * @param errorDescription 错误描述
     * @return 错误消息
     */
    public static String makeCallError(
            String uniqueId, String action, String errorCode, String errorDescription) {
        return String.format(CALL_ERROR_FORMAT, uniqueId, errorCode, errorDescription, "{}");
    }

    public static OcppMessage parse(String json) {
        OcppMessage message = new OcppMessage();
        try {
            ArrayNode array = (ArrayNode) OcppJsonUtil.readTree(json);
            String uniqueId = array.get(INDEX_UNIQUEID).asText();
            int msgTypeId = array.get(INDEX_MESSAGEID).asInt();
            if (msgTypeId == OcppMessageType.CALL.getType()) {
                message.setAction(array.get(INDEX_CALL_ACTION).asText());
                message.setPayload(array.get(INDEX_CALL_PAYLOAD).toString());
            } else if (msgTypeId == OcppMessageType.CALL_RESULT.getType()) {
                message.setPayload(array.get(INDEX_CALLRESULT_PAYLOAD).toString());
            } else if (msgTypeId == OcppMessageType.CALL_RESULT_ERROR.getType()) {
                message.setErrorCode(array.get(INDEX_CALLERROR_ERRORCODE).asText());
                message.setErrorDescription(array.get(INDEX_CALLERROR_DESCRIPTION).asText());
                message.setPayload(array.get(INDEX_CALLERROR_PAYLOAD).toString());
            } else {
                log.error("Unknown message type of message: {}", json);
                return null;
            }
            message.setMessageTypeId(msgTypeId);
            message.setUniqueId(uniqueId);
        } catch (Exception e) {
            log.error("Exception while parsing message: {}", json);
            return null;
        }

        return message;
    }

//    public static void main(String[] args) {
//        //        String json1 = "[2,\"16999779847135748\",\"BootNotification\",{\"chargeBoxSerialNumber\":\"20080601-20200924\",\"chargePointModel\":\"IOC001\",\"chargePointSerialNumber\":\"test\",\"chargePointVendor\":\"iocharger\",\"firmwareVersion\":\"23082301-20122101-20121402\"}]";
//        //        String json2 = "[3,\"1699948938910950\",{\"currentTime\":\"2023-11-14T16:02:19.056+08:00\"}]";
//        //        String json3 = "[4,\"1699948938910950\",\"NotImplemented\",\"\",{}]";
//        //        OcppMessage parse = parse(json1);
//        //        OcppMessage parse1 = parse(json2);
//        //        OcppMessage parse2 = parse(json3);
//        //        System.out.println(parse);
//        //        System.out.println(parse1);
//        //        System.out.println(parse2);
//
//
//        OcppMessage ocppMessage = new OcppMessage();
//        ocppMessage.setDateTime(new Date());
//        ocppMessage.setAction("1");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String json = objectMapper.writeValueAsString(ocppMessage);
//            System.out.println(json);
//        } catch (Exception e) {
//
//        }
//    }


}
