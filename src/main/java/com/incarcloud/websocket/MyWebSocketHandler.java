package com.incarcloud.websocket;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.incarcloud.constants.Constant;
import com.incarcloud.core.MsgDirectionEnum;
import com.incarcloud.core.OcppContents;
import com.incarcloud.handle.OcppMessage;
import com.incarcloud.handle.OcppResultType;
import com.incarcloud.handle.message.*;
import com.incarcloud.type.*;
import com.incarcloud.utils.OcppJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.incarcloud.core.OcppContents.*;

@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private ObjectMapper objectMapper;

    public MyWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 初始化任务
        log.info("Connected!");
        Constant.runningFlag = true;
        Constant.currentSession = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage  message) {
        try {
            // 接收消息
            String msg = message.getPayload();
//        log.info("Received: " + msg);

            ArrayNode contentJson = (ArrayNode) OcppJsonUtil.readTree(msg);
            //  消息流向 2-请求 3-应答
            int messageTypeId = contentJson.get(0).asInt();
            if (messageTypeId == OcppMessageType.CALL.getType()) {
                // 消息唯一id
                String uniqueId = contentJson.get(1).asText();
                // 消息类型
                String action = contentJson.get(2).asText();
                // 消息体
                String payload = contentJson.get(3).toString();
                boolean flag = OcppJsonUtil.check(payload, action);
                if (!flag) {
                    log.error("Json check is failed");
                } else {
                    OcppMessage ocppMessage = OcppMessage.builder()
                            .deviceId(Constant.deviceId)
                            .messageTypeId(messageTypeId)
                            .dateTime(new Date())
                            .direction(MsgDirectionEnum.CLINET.getDescription())
                            .uniqueId(uniqueId)
                            .action(action)
                            .payload(payload)
                            .build();
                    String req = this.objectMapper.writeValueAsString(ocppMessage);
                    log.info("收到请求,转换后的消息,消息类型->[{}],->{}", OcppMessageType.CALL.getDetail(), req);
//                this.mongoTemplate.insert(ocppMessage, "iot-data");
//                this.evcpMsgService.sendMessage(req);
                    if (OcppContents.ReserveNow.equals(action)) {
                        if (Constant.reserveThread != null) {
                            // 有预约存在
                            ReserveNowConf reserveNowConf = new ReserveNowConf();
                            reserveNowConf.setStatus(ReservationStatus.Rejected);

                            String respPayload = this.objectMapper.writeValueAsString(reserveNowConf);

                            String resp = OcppMessage.makeCallResult(uniqueId, OcppContents.ReserveNow, respPayload);

                            Constant.currentSession.sendMessage(new TextMessage(resp));
                        } else {
                            ReserveNowReq reserveNowReq = JSONUtil.toBean(payload, ReserveNowReq.class);

                            Constant.reserveId = reserveNowReq.getReservationId();

                            // 平台发起预约
                            ReserveNowConf reserveNowConf = new ReserveNowConf();
                            reserveNowConf.setStatus(ReservationStatus.Accepted);

                            String respPayload = this.objectMapper.writeValueAsString(reserveNowConf);

                            String resp = OcppMessage.makeCallResult(uniqueId, OcppContents.ReserveNow, respPayload);

                            Constant.currentSession.sendMessage(new TextMessage(resp));

                            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

                            // 上报枪的状态为预约状态
                            StatusNotificationReq statusNotificationReq = new StatusNotificationReq();
                            statusNotificationReq.setConnectorId(1);
                            statusNotificationReq.setErrorCode(ChargePointErrorCode.NoError);
                            statusNotificationReq.setStatus(ChargePointStatus.Reserved);
                            statusNotificationReq.setTimestamp(now);

                            String statusNotificationPayload = this.objectMapper.writeValueAsString(statusNotificationReq);

                            String msgUUid = UUID.randomUUID().toString();
                            String statusNotificationMsg = OcppMessage.makeCall(msgUUid, StatusNotification, statusNotificationPayload);

                            Constant.currentSession.sendMessage(new TextMessage(statusNotificationMsg));

                            // 计算预约超时时间
                            LocalDateTime expiryDate = reserveNowReq.getExpiryDate();
                            Duration duration = Duration.between(now, expiryDate);
                            long seconds = duration.toSeconds();

                            Constant.reserveThread = new Thread(()->{
                                try {
                                    log.info("--------->预约超时等待:" + seconds);
                                    Thread.sleep(seconds * 1000);
                                    // 上报枪的状态为预约状态
                                    StatusNotificationReq statusNotificationReq1 = new StatusNotificationReq();
                                    statusNotificationReq1.setConnectorId(1);
                                    statusNotificationReq1.setErrorCode(ChargePointErrorCode.NoError);
                                    statusNotificationReq1.setStatus(ChargePointStatus.Finishing);
                                    statusNotificationReq1.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));

                                    String statusNotificationPayload1 = this.objectMapper.writeValueAsString(statusNotificationReq1);

                                    String msgUUid1 = UUID.randomUUID().toString();
                                    String statusNotificationMsg1 = OcppMessage.makeCall(msgUUid1, StatusNotification, statusNotificationPayload1);

                                    Constant.reserveId = null;
                                    Constant.currentSession.sendMessage(new TextMessage(statusNotificationMsg1));
                                    Constant.reserveThread = null;
                                    Thread.currentThread().interrupt(); // 可选，重新设置中断状态
                                    log.info("--------->预约超时");
                                } catch (Exception e){
                                    e.printStackTrace();
                                    Thread.currentThread().interrupt(); // 可选，重新设置中断状态
                                }
                            });
                            Constant.reserveThread.start();
                        }
                    } else if (CancelReservation.equals(action)) {
                        // 平台发起取消预约
                        CancelReservationReq cancelReservationReq = JSONUtil.toBean(payload, CancelReservationReq.class);

                        CancelReservationConf cancelReservationConf = new CancelReservationConf();
                        cancelReservationConf.setStatus(CancelReservationStatus.Accepted);

                        String respPayload = this.objectMapper.writeValueAsString(cancelReservationConf);
                        String resp = OcppMessage.makeCallResult(uniqueId, OcppContents.CancelReservation, respPayload);
                        Constant.currentSession.sendMessage(new TextMessage(resp));

                        // 中断预约超时线程
                        if (Constant.reserveThread != null) {
                            Constant.reserveThread.interrupt();
                            Constant.reserveId = null;
                            Constant.reserveThread = null;
                        }
                    } else if (RemoteStartTransaction.equals(action)) {
                        if (Constant.meterValueService != null) {
                            // 在充电中
                            RemoteStartTransactionConf remoteStartTransactionConf = new RemoteStartTransactionConf();
                            remoteStartTransactionConf.setStatus(RemoteStartStopStatus.Rejected);

                            String respPayload = this.objectMapper.writeValueAsString(remoteStartTransactionConf);

                            String resp = OcppMessage.makeCallResult(uniqueId, RemoteStartTransaction, respPayload);
                            Constant.currentSession.sendMessage(new TextMessage(resp));
                        } else {
                            // 平台发起远程充电应答
                            RemoteStartTransactionConf remoteStartTransactionConf = new RemoteStartTransactionConf();
                            remoteStartTransactionConf.setStatus(RemoteStartStopStatus.Accepted);

                            String respPayload = this.objectMapper.writeValueAsString(remoteStartTransactionConf);

                            String resp = OcppMessage.makeCallResult(uniqueId, RemoteStartTransaction, respPayload);
                            Constant.currentSession.sendMessage(new TextMessage(resp));

                            RemoteStartTransactionReq remoteStartTransactionReq = this.objectMapper.readValue(payload, RemoteStartTransactionReq.class);

                            // 等待1秒主动发送startTransaction
                            Thread.sleep(1000);

                            StartTransactionReq startTransactionReq = new StartTransactionReq();
                            startTransactionReq.setConnectorId(remoteStartTransactionReq.getConnectorId());
                            startTransactionReq.setIdTag(remoteStartTransactionReq.getIdTag());
                            startTransactionReq.setMeterStart(100);
                            startTransactionReq.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
                            // 获取预约id
                            startTransactionReq.setReservationId(Constant.reserveId);

                            String startTransactionPayload = this.objectMapper.writeValueAsString(startTransactionReq);

                            String msgUUid = UUID.randomUUID().toString();
                            String sendTransactionMsg = OcppMessage.makeCall(msgUUid, OcppContents.StartTransaction, startTransactionPayload);
                            Constant.currentSession.sendMessage(new TextMessage(sendTransactionMsg));

                            // 判断是否有预约存在
                            if (Constant.reserveThread != null) {
                                // 中断预约超时线程
                                Constant.reserveThread.interrupt();
                                Constant.reserveId = null;
                                Constant.reserveThread = null;
                            }

                            // 缓存StartTransaction数据
                            OcppResultType ocppResultType = new OcppResultType();
                            ocppResultType.setAction(OcppContents.StartTransaction);
                            ocppResultType.setPayload(startTransactionPayload);
                            ocppResultType.setUniqueId(msgUUid);
                            Constant.ocppResultMap.put(msgUUid, ocppResultType);
                        }
                    } else if (RemoteStopTransaction.equals(action)) {
                        // 平台发起远程停止充电应答
                        RemoteStopTransactionConf remoteStopTransactionConf = new RemoteStopTransactionConf();
                        remoteStopTransactionConf.setStatus(RemoteStartStopStatus.Accepted);

                        String respPayload = this.objectMapper.writeValueAsString(remoteStopTransactionConf);

                        String resp = OcppMessage.makeCallResult(uniqueId, RemoteStopTransaction, respPayload);
                        Constant.currentSession.sendMessage(new TextMessage(resp));

                        RemoteStopTransactionReq remoteStopTransactionReq = JSONUtil.toBean(payload, RemoteStopTransactionReq.class);

                        // 等待1秒主动发送stopTransaction
                        Thread.sleep(1000);

                        // 平台应答StopTransaction
                        if (Constant.meterValueService != null) {
                            Constant.meterValueService.shutdown();
                            Constant.meterValueService = null;
                        }

                        // 发起StopTransaction
                        StartTransactionReq startTransactionReq = Constant.chargingReqMap.get(remoteStopTransactionReq.getTransactionId());

                        AtomicInteger valAto = Constant.meterValMap.get(remoteStopTransactionReq.getTransactionId());

                        StopTransactionReq stopTransactionReq = new StopTransactionReq();
                        stopTransactionReq.setIdTag(startTransactionReq.getIdTag());
                        stopTransactionReq.setMeterStop(valAto.get());
                        stopTransactionReq.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
                        stopTransactionReq.setTransactionId(remoteStopTransactionReq.getTransactionId());
                        stopTransactionReq.setReason(Reason.Remote);
                        List<MeterValue> meterValues = new ArrayList<>();
                        stopTransactionReq.setTransactionData(meterValues);

                        MeterValue meterValue = new MeterValue();
                        meterValues.add(meterValue);

                        meterValue.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
                        List<SampledValue> sampledValues = new ArrayList<>();
                        SampledValue sampledValue = new SampledValue();
                        AtomicInteger meterValAto = Constant.meterValMap.get(remoteStopTransactionReq.getTransactionId());
                        sampledValue.setValue(String.valueOf(meterValAto.get()));
                        sampledValue.setUnit(UnitOfMeasure.Wh);
                        sampledValue.setMeasurand(Measurand.Energy_Active_Import_Register);
                        sampledValues.add(sampledValue);
                        meterValue.setSampledValue(sampledValues);

                        String stopTransactionPayload = this.objectMapper.writeValueAsString(stopTransactionReq);

                        String msgUUid = UUID.randomUUID().toString();
                        String stopTransactionMsg = OcppMessage.makeCall(msgUUid, StopTransaction, stopTransactionPayload);

                        Constant.currentSession.sendMessage(new TextMessage(stopTransactionMsg));

                        // 缓存StopTransaction数据
                        OcppResultType ocppResultType = new OcppResultType();
                        ocppResultType.setAction(OcppContents.StopTransaction);
                        ocppResultType.setPayload(stopTransactionPayload);
                        ocppResultType.setUniqueId(msgUUid);
                        Constant.ocppResultMap.put(msgUUid, ocppResultType);

                        Constant.meterValMap.remove(remoteStopTransactionReq.getTransactionId());
                        Constant.chargingReqMap.remove(remoteStopTransactionReq.getTransactionId());
                    }
                }
            } else if (messageTypeId == OcppMessageType.CALL_RESULT.getType()) {
                // 消息唯一id
                String uniqueId = contentJson.get(1).asText();
                // 消息体
                String payload = contentJson.get(2).toString();
                String resp = this.objectMapper.writeValueAsString(OcppMessage.builder()
                        .deviceId(Constant.deviceId)
                        .dateTime(new Date())
                        .direction(MsgDirectionEnum.CLINET.getDescription())
                        .messageTypeId(messageTypeId)
                        .uniqueId(uniqueId)
                        .payload(payload)
                        .build());

                log.info("收到应答,转换后的消息,消息类型->[{}],->{}", OcppMessageType.CALL_RESULT.getDetail(), resp);

                OcppResultType ocppResultType = Constant.ocppResultMap.get(uniqueId);
                if (ocppResultType == null) {
                    log.info("应答消息=====>" + uniqueId);
                } else if (OcppContents.StartTransaction.equals(ocppResultType.getAction())) {

                    // 平台应答StartTransaction
                    StartTransactionConf startTransactionConf = JSONUtil.toBean(payload, StartTransactionConf.class);
                    // 主动上报枪的状态为充电状态
                    StatusNotificationReq statusNotificationReq = new StatusNotificationReq();
                    statusNotificationReq.setConnectorId(1);
                    statusNotificationReq.setErrorCode(ChargePointErrorCode.NoError);
                    statusNotificationReq.setStatus(ChargePointStatus.Charging);
                    statusNotificationReq.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));

                    String statusNotificationPayload = this.objectMapper.writeValueAsString(statusNotificationReq);

                    String statusNotificationUid = UUID.randomUUID().toString();
                    String statusNotificationMsg = OcppMessage.makeCall(statusNotificationUid, StatusNotification, statusNotificationPayload);

                    Constant.currentSession.sendMessage(new TextMessage(statusNotificationMsg));

                    OcppResultType statusNotificationType = new OcppResultType();
                    statusNotificationType.setAction(StatusNotification);
                    statusNotificationType.setPayload(statusNotificationPayload);
                    statusNotificationType.setUniqueId(statusNotificationUid);
                    Constant.ocppResultMap.put(statusNotificationUid, statusNotificationType);

                    StartTransactionReq startTransactionReq = JSONUtil.toBean(ocppResultType.getPayload(), StartTransactionReq.class);
                    Constant.chargingReqMap.put(startTransactionConf.getTransactionId(), startTransactionReq);

                    Constant.meterValueService = Executors.newScheduledThreadPool(1);
                    AtomicInteger meterValAto = new AtomicInteger(100);
                    Constant.meterValMap.put(startTransactionConf.getTransactionId(), meterValAto);
                    // 每间隔1分钟上报一次meterValue
                    Constant.meterValueService.scheduleAtFixedRate(() -> {
                        try {
                            Integer val = meterValAto.get();
                            MeterValuesReq meterValuesReq = new MeterValuesReq();
                            meterValuesReq.setConnectorId(1);
                            meterValuesReq.setTransactionId(startTransactionConf.getTransactionId());
                            List<MeterValue> meterValues = new ArrayList<>();
                            meterValuesReq.setMeterValue(meterValues);

                            MeterValue meterValue = new MeterValue();
                            meterValues.add(meterValue);

                            meterValue.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
                            List<SampledValue> sampledValues = new ArrayList<>();
                            SampledValue sampledValue = new SampledValue();
                            sampledValue.setValue(String.valueOf(val));
                            sampledValue.setUnit(UnitOfMeasure.Wh);
                            sampledValue.setMeasurand(Measurand.Energy_Active_Import_Register);
                            sampledValues.add(sampledValue);
                            meterValue.setSampledValue(sampledValues);

                            String meterValuesPayload = this.objectMapper.writeValueAsString(meterValuesReq);

                            String meterValuesMsg = OcppMessage.makeCall(UUID.randomUUID().toString(), MeterValues, meterValuesPayload);

                            meterValAto.addAndGet(Constant.chargingVal);
                            Constant.currentSession.sendMessage(new TextMessage(meterValuesMsg));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }, 0, 60, TimeUnit.SECONDS);
                } else if (StopTransaction.equals(ocppResultType.getAction())) {
                    // 主动上报枪的状态为充电完成
                    StatusNotificationReq statusNotificationReq = new StatusNotificationReq();
                    statusNotificationReq.setConnectorId(1);
                    statusNotificationReq.setErrorCode(ChargePointErrorCode.NoError);
                    statusNotificationReq.setStatus(ChargePointStatus.Finishing);
                    statusNotificationReq.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));

                    String statusNotificationPayload = this.objectMapper.writeValueAsString(statusNotificationReq);

                    String statusNotificationUid = UUID.randomUUID().toString();
                    String statusNotificationMsg = OcppMessage.makeCall(statusNotificationUid, StatusNotification, statusNotificationPayload);

                    Constant.currentSession.sendMessage(new TextMessage(statusNotificationMsg));
                }

//            this.evcpMsgService.sendMessage(resp);
            } else if (messageTypeId == OcppMessageType.CALL_RESULT_ERROR.getType()) {
                // 消息唯一id
                String uniqueId = contentJson.get(1).asText();
                OcppMessage ocppMessage = new OcppMessage();
                ocppMessage.setDeviceId(Constant.deviceId);
                ocppMessage.setMessageTypeId(messageTypeId);
                ocppMessage.setUniqueId(uniqueId);
                ocppMessage.setDirection(MsgDirectionEnum.CLINET.getDescription());
                ocppMessage.setDateTime(new Date());
                ocppMessage.setErrorCode(contentJson.get(INDEX_CALLERROR_ERRORCODE).asText());
                ocppMessage.setErrorDescription(contentJson.get(INDEX_CALLERROR_DESCRIPTION).asText());
                ocppMessage.setPayload(contentJson.get(INDEX_CALLERROR_PAYLOAD).toString());
                String resp = this.objectMapper.writeValueAsString(ocppMessage);

                log.info("错误消息,转换后的消息,消息类型->[{}],->{}", OcppMessageType.CALL_RESULT_ERROR.getDetail(), resp);
//            this.evcpMsgService.sendMessage(resp);
            } else {
                log.info("未知的消息类型->{}", message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Disconnected!");
        Constant.runningFlag = false;
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
