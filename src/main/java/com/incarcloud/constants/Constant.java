package com.incarcloud.constants;

import com.incarcloud.handle.OcppResultType;
import com.incarcloud.handle.message.StartTransactionReq;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class Constant {

    public static String deviceId;

    public static Map<String, OcppResultType> ocppResultMap = new HashMap<>();

    public static Map<Integer, AtomicInteger> meterValMap = new HashMap<>();

    public static Thread reserveThread = null;
    public static Integer reserveId = null;

    public static Map<Integer, StartTransactionReq> chargingReqMap = new HashMap<>();

    public static ScheduledExecutorService meterValueService = null;

    public static Integer chargingVal;

    public static Boolean runningFlag = false;
    public static WebSocketSession currentSession = null;
}
