package com.incarcloud.core;

/**
 * @author jack
 * @Date 2020/7/28
 */
public final class OcppContents {
    public static final int INDEX_MESSAGEID = 0;
    public static final int TYPENUMBER_CALL = 2;
    public static final int INDEX_CALL_ACTION = 2;
    public static final int INDEX_CALL_PAYLOAD = 3;

    public static final int TYPENUMBER_CALLRESULT = 3;
    public static final int INDEX_CALLRESULT_PAYLOAD = 2;

    public static final int TYPENUMBER_CALLERROR = 4;
    public static final int INDEX_CALLERROR_ERRORCODE = 2;
    public static final int INDEX_CALLERROR_DESCRIPTION = 3;
    public static final int INDEX_CALLERROR_PAYLOAD = 4;


//    public static final String MSG_DOWN_QUEUE_NAME = "q.evcp-down-message-test";
//    public static final String MSG_UP_QUEUE_NAME = "q.evcp-up-message-test";

    public static final int INDEX_UNIQUEID = 1;
    public static final String CALL_FORMAT = "[2,\"%s\",\"%s\",%s]";
    public static final String CALL_RESULT_FORMAT = "[3,\"%s\",%s]";
    public static final String CALL_ERROR_FORMAT = "[4,\"%s\",\"%s\",\"%s\",%s]";
    public final static String Authorize = "Authorize";
    public final static String BootNotification = "BootNotification";
    public final static String StartTransaction = "StartTransaction";
    public final static String StopTransaction = "StopTransaction";
    public final static String StatusNotification = "StatusNotification";
    public final static String Heartbeat = "Heartbeat";
    public final static String MeterValues = "MeterValues";
    public final static String FirmwareStatusNotification = "FirmwareStatusNotification";
    public final static String DiagnosticsStatusNotification = "DiagnosticsStatusNotification";
    public final static String DataTransfer = "DataTransfer";
    public final static String RemoteStartTransaction = "RemoteStartTransaction";
    public final static String RemoteStopTransaction = "RemoteStopTransaction";
    public final static String Reset = "Reset";
    public final static String ClearCache = "ClearCache";
    public final static String GetLocalListVersion = "GetLocalListVersion";
    public final static String SendLocalList = "SendLocalList";
    public final static String ChangeAvailability = "ChangeAvailability";
    public final static String ChangeConfiguration = "ChangeConfiguration";
    public final static String GetConfiguration = "GetConfiguration";
    public final static String TriggerMessage = "TriggerMessage";
    public final static String UnlockConnector = "UnlockConnector";
    public final static String ClearChargingProfile = "ClearChargingProfile";
    public final static String SetChargingProfile = "SetChargingProfile";
    public final static String GetCompositeSchedule = "GetCompositeSchedule";
    public final static String UpdateFirmware = "UpdateFirmware";
    public final static String GetDiagnostics = "GetDiagnostics";
    public final static String ReserveNow = "ReserveNow";
    public final static String CancelReservation = "CancelReservation";

}
