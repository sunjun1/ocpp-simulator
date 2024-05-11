/*
    OCPP 1.6 JSON 
    C/C++ Description 
*/

#include <time.h>

// Basic Type defination 

// CiString[20] String is case insensitive.
#define CI_STRING_20_LENGTH             20

// CiString[25] String is case insensitive.
#define CI_STRING_25_LENGTH             25

// CiString[50] String is case insensitive.
#define CI_STRING_50_LENGTH             50

// CiString[255] String is case insensitive.
#define CI_STRING_255_LENGTH            255

// CiString[500] String is case insensitive.
#define CI_STRING_500_LENGTH            500

typedef time_t dateTime;
typedef unsigned long integer;
typedef float decimal;
typedef enum {False = 0, True} boolean;


/* ---------------------------------------------------------------------------- 
                            Authorize
 ----------------------------------------------------------------------------*/

// Authorize.json
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "AuthorizeRequest",
//     "type": "object",
//     "properties": {
//         "idTag": {
//             "type": "string",
//             "maxLength": 20
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "idTag"
//     ]
// }
typedef struct {
    char str[CI_STRING_20_LENGTH + 1];  // CiString20Type IdToken is case insensitive.
} IdToken;

typedef struct {
    IdToken idTag;                      // Required. This contains the identifier that needs to be authorized.
} AuthorizeRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "AuthorizeResponse",
//     "type": "object",
//     "properties": {
//         "idTagInfo": {
//             "type": "object",
//             "properties": {
//                 "status": {
//                     "type": "string",
//                     "enum": [
//                         "Accepted",
//                         "Blocked",
//                         "Expired",
//                         "Invalid",
//                         "ConcurrentTx"
//                     ]
//                 },
//                 "expiryDate": {
//                     "type": "string",
//                     "format": "date-time"
//                 },
//                 "parentIdTag": {
//                     "type": "string",
//                     "maxLength": 20
//                 }
//             },
//             "required": [
//                 "status"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "idTagInfo"
//     ]
// }
typedef enum {    
    Accepted = 0,           // Identifier is allowed for charging.
    Blocked,                // Identifier has been blocked. Not allowed for charging.
    Expired,                // Identifier has expired. Not allowed for charging.
    Invalid,                // Identifier is unknown. Not allowed for charging.
    ConcurrentTx,           // Identifier is already involved in another transaction and multiple transactions are not allowed. (Only relevant for a StartTransaction.req.)
    TOTAL_NUM
} AuthorizationStatus;

typedef struct {
    AuthorizationStatus status;     // Required. This contains whether the idTag has been accepted or not by the Central System.        
    IdToken parentIdTag;            // Optional. This contains the parent-identifier.
    dateTime expiryDate;             // Optional. This contains the date at which idTag should be removed from the Authorization Cache.
} IdTagInfo;

typedef struct {
    IdTagInfo idTagInfo;                        // Required. This contains information about authorization status, expiry and parent id.
} AuthorizeResponse;


/* ---------------------------------------------------------------------------- 
                            BootNotification
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "BootNotificationRequest",
//     "type": "object",
//     "properties": {
//         "chargePointVendor": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "chargePointModel": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "chargePointSerialNumber": {
//             "type": "string",
//             "maxLength": 25
//         },
//         "chargeBoxSerialNumber": {
//             "type": "string",
//             "maxLength": 25
//         },
//         "firmwareVersion": {
//             "type": "string",
//             "maxLength": 50
//         },
//         "iccid": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "imsi": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "meterType": {
//             "type": "string",
//             "maxLength": 25
//         },
//         "meterSerialNumber": {
//             "type": "string",
//             "maxLength": 25
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "chargePointVendor",
//         "chargePointModel"
//     ]
// }
typedef struct {
    char chargePointVendor[CI_STRING_20_LENGTH + 1];        // Required. This contains a value that identifies the vendor of the ChargePoint.
    char chargePointModel[CI_STRING_20_LENGTH + 1];         // Required. This contains a value that identifies the model of the ChargePoint.
    char chargePointSerialNumber[CI_STRING_25_LENGTH + 1];  // Optional. This contains a value that identifies the serial number of the Charge Point.
    char chargeBoxSerialNumber[CI_STRING_25_LENGTH + 1];    // Optional. This contains a value that identifies the serial number of the Charge Box inside the Charge Point. Deprecated, will be removed in future version
    char firmwareVersion[CI_STRING_50_LENGTH + 1];          // Optional. This contains the firmware version of the Charge Point.
    char iccid[CI_STRING_25_LENGTH + 1];                    // Optional. This contains the ICCID of the modem’s SIM card.
    char imsi[CI_STRING_20_LENGTH + 1];                     // Optional. This contains the IMSI of the modem’s SIM card.
    char meterType[CI_STRING_25_LENGTH + 1];                // Optional. This contains the type of the main electrical meter of the Charge Point.
    char meterSerialNumber[CI_STRING_25_LENGTH + 1];        // Optional. This contains the serial number of the main electrical meter of the Charge Point.
} BootNotificationRequest;


// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "BootNotificationResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Pending",
//                 "Rejected"
//             ]
//         },
//         "currentTime": {
//             "type": "string",
//             "format": "date-time"
//         },
//         "interval": {
//             "type": "number"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status",
//         "currentTime",
//         "interval"
//     ]
// }
typedef enum {
    Accepted = 0,       // Charge point is accepted by Central System.
    Pending,            // Central System is not yet ready to accept the Charge Point. Central System may send messages to retrieve information or
                        // prepare the Charge Point.
    Rejected,           // Charge point is not accepted by Central System. This may happen when the Charge Point id is not known by Central System.
    TOTAL_NUM
} RegistrationStatus;

typedef struct 
{
    RegistrationStatus status;  // Required. This contains whether the Charge Point has been registered within the System Central.
    integer interval;           // Required. When RegistrationStatus is Accepted, this contains the heartbeat
                                // interval in seconds. If the Central System returns something other than
                                // Accepted, the value of the interval field indicates the minimum wait time before
                                // sending a next BootNotification request.
    dateTime currentTime;       // Required. This contains the Central System’s current time.
} BootNotificationResponse;

/* ---------------------------------------------------------------------------- 
                            CancelReservation
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "CancelReservationRequest",
//     "type": "object",
//     "properties": {
//         "reservationId": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "reservationId"
//     ]
// }
typedef struct 
{
    integer reservationId;      // Required. Id of the reservation to cancel.    
} CancelReservationRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "CancelReservationResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,           // Reservation for the identifier has been cancelled.
    Rejected,               // Reservation could not be cancelled, because there is no reservation active for the identifier.
    TOTAL_NUM
} CancelReservationStatus;

typedef struct {
    CancelReservationStatus status;     // Required. This indicates the success or failure of the cancelling of
                                        // a reservation by Central System.
} CancelReservationResponse;


/* ---------------------------------------------------------------------------- 
                            ChangeAvailability
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ChangeAvailabilityRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
//         "type": {
//             "type": "string",
//             "enum": [
//                 "Inoperative",
//                 "Operative"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
//         "type"
//     ]
// }
typedef enum {
    Inoperative = 0,        // Charge point is not available for charging.
    Operative,              // Charge point is available for charging.
    TOTAL_NUM
} AvailabilityType; 

typedef struct {
    integer type;       // Required. The id of the connector for which availability needs to change. Id '0'
                        // (zero) is used if the availability of the Charge Point and all its connectors needs to change.
    AvailabilityType type;  // Required. This contains the type of availability change that the Charge Point should perform.
};

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ChangeAvailabilityResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected",
//                 "Scheduled"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,           // Request has been accepted and will be executed.
    Rejected,               // Request has not been accepted and will not be executed.
    Scheduled,              // Request has been accepted and will be executed when transaction(s) in progress have finished.
    TOTAL_NUM
} AvailabilityStatus;

typedef struct 
{
    AvailabilityStatus status;  // Required. This indicates whether the Charge Point is able to perform the availability change
} ChangeAvailabilityResponse;


/* ---------------------------------------------------------------------------- 
                            ChangeConfiguration
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ChangeConfigurationRequest",
//     "type": "object",
//     "properties": {
//         "key": {
//             "type": "string",
//             "maxLength": 50
//         },
//         "value": {
//             "type": "string",
//             "maxLength": 500
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "key",
//         "value"
//     ]
// }
typedef struct 
{
    char key[CI_STRING_50_LENGTH + 1];          // Required. The name of the configuration setting to change.
                                                // See for standard configuration key names and associated values
    char * value;                               // CiString500Type 1..1 Required. The new value as string for the setting.
                                                // See for standard configuration key names and associated values
} ChangeConfigurationRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ChangeConfigurationResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected",
//                 "RebootRequired",
//                 "NotSupported"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,           // Configuration key is supported and setting has been changed.
    Rejected,               // Configuration key is supported, but setting could not be changed.
    RebootRequired,         // Configuration key is supported and setting has been changed, but change will be available after reboot (Charge Point will not reboot itself)
    NotSupported,           // Configuration key is not supported.
    TOTAL_NUM
} ConfigurationStatus;

typedef struct {
    ConfigurationStatus status; // Required. Returns whether configuration change has been accepted
} ChangeConfigurationResponse;

/* ---------------------------------------------------------------------------- 
                            ChangeConfiguration
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ClearCacheRequest",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct 
{
    integer dummy;          // No fields are defined.
} ClearCacheRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ClearCacheResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,           // Command has been executed.
    Rejected,               // Command has not been executed.
    TOTAL_NUM
} ClearCacheStatus;

typedef struct {
    ClearCacheStatus status;    // Required. Accepted if the Charge Point has executed the request, otherwise rejected.
} ClearCacheResponse;
/* ---------------------------------------------------------------------------- 
                            ClearChargingProfile
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ClearChargingProfileRequest",
//     "type": "object",
//     "properties": {
//         "id": {
//             "type": "integer"
//         },
//         "connectorId": {
//             "type": "integer"
//         },
//         "chargingProfilePurpose": {
//             "type": "string",
//             "enum": [
//                 "ChargePointMaxProfile",
//                 "TxDefaultProfile",
//                 "TxProfile"
//             ]
//         },
//         "stackLevel": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false
// }
typedef enum {
    ChargePointMaxProfile = 0,          // Configuration for the maximum power or current available for an entire Charge Point.
    TxDefaultProfile,                   // Default profile *that can be configured in the Charge Point. When a new transaction is started, this profile
                                        // SHALL be used, unless it was a transaction that was started by a RemoteStartTransaction.req with a
                                        // ChargeProfile that is accepted by the Charge Point.
    TxProfile,                          // Profile with constraints to be imposed by the Charge Point on the current transaction, or on a new transaction
                                        // when this is started via a RemoteStartTransaction.req with a ChargeProfile. A profile with this purpose SHALL
                                        //cease to be valid when the transaction terminates.    
    TOTAL_NUM
} ChargingProfilePurposeType;

typedef struct {
    integer id;                 // Optional. The ID of the charging profile to clear.
    integer connectorId;        // Optional. Specifies the ID of the connector for which to clear
                                // charging profiles. A connectorId of zero (0) specifies the charging
                                // profile for the overall Charge Point. Absence of this parameter
                                // means the clearing applies to all charging profiles that match the
                                // other criteria in the request.
    ChargingProfilePurposeType chargingProfilePurpose;  // Optional. Specifies to purpose of the charging profiles that will be
                                                        // cleared, if they meet the other criteria in the request.
    integer stackLevel;         // Optional. specifies the stackLevel for which charging profiles will
                                // be cleared, if they meet the other criteria in the request
} ClearChargingProfileRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ClearChargingProfileResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Unknown"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,           // Request has been accepted and will be executed.
    Unknown,                // No Charging Profile(s) were found matching the request.
    TOTAL_NUM
} ClearChargingProfileStatus;

typedef struct {
    ClearChargingProfileStatus status;  // Required. Indicates if the Charge Point was able to execute the request.
} ClearChargingProfileResponse;

/* ---------------------------------------------------------------------------- 
                            DataTransfer
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "DataTransferRequest",
//     "type": "object",
//     "properties": {
//         "vendorId": {
//             "type": "string",
//             "maxLength": 255
//         },
//         "messageId": {
//             "type": "string",
//             "maxLength": 50
//         },
//         "data": {
//             "type": "string"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "vendorId"
//     ]
// }
typedef struct {
    char * vendorId;        // CiString255Type 1..1 Required. This identifies the Vendor specific implementation
    char * messageId;       // CiString50Type 0..1 Optional. Additional identification field
    char * data;            // Text Length undefined 0..1 Optional. Data without specified length or format.
} DataTransferRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "DataTransferResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected",
//                 "UnknownMessageId",
//                 "UnknownVendorId"
//             ]
//         },
//         "data": {
//             "type": "string"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,           // Message has been accepted and the contained request is accepted.
    Rejected,               // Message has been accepted but the contained request is rejected.
    UnknownMessageId,       // Message could not be interpreted due to unknown messageId string.
    TOTAL_NUM
} DataTransferStatus;

typedef struct {
    DataTransferStatus status;      // 1..1 Required. This indicates the success or failure of the data transfer.
    char * data;                    // Text Length undefined 0..1 Optional. Data in response to request.
} DataTransferResponse;

/* ---------------------------------------------------------------------------- 
                            DiagnosticsStatusNotification
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "DiagnosticsStatusNotificationRequest",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Idle",
//                 "Uploaded",
//                 "UploadFailed",
//                 "Uploading"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Idle = 0,       // Charge Point is not performing diagnostics related tasks. Status Idle SHALL only be used as in a
                    // DiagnosticsStatusNotification.req that was triggered by a TriggerMessage.req
    Uploaded,       // Diagnostics information has been uploaded.
    UploadFailed,   // Uploading of diagnostics failed.
    Uploading,      // File is being uploaded.
    TOTAL_NUM
} DiagnosticsStatus;

typedef struct {
    DiagnosticsStatus status;   //  1..1 Required. This contains the status of the diagnostics upload
} DiagnosticsStatusNotificationRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "DiagnosticsStatusNotificationResponse",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct 
{
    integer dummy;      // No fields are defined.
} DiagnosticsStatusNotificationResponse;

/* ---------------------------------------------------------------------------- 
                            FirmwareStatusNotification
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "FirmwareStatusNotificationRequest",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Downloaded",
//                 "DownloadFailed",
//                 "Downloading",
//                 "Idle",
//                 "InstallationFailed",
//                 "Installing",
//                 "Installed"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Downloaded = 0,     // New firmware has been downloaded by Charge Point.
    DownloadFailed,     // Charge point failed to download firmware.
    Downloading,        // Firmware is being downloaded.
    Idle,               // Charge Point is not performing firmware update related tasks. Status Idle SHALL only be used as in a
                        // FirmwareStatusNotification.req that was triggered by a TriggerMessage.req
    InstallationFailed, // Installation of new firmware has failed.
    Installing,         // Firmware is being installed.
    Installed,          // New firmware has successfully been installed in charge point.
    TOTAL_NUM
} FirmwareStatus;

typedef struct {
    FirmwareStatus status;  // Required. This contains the progress status of the firmware installation.
} FirmwareStatusNotificationRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "FirmwareStatusNotificationResponse",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct 
{
    int dummy;          // No fields are defined.
} FirmwareStatusNotificationResponse;

/* ---------------------------------------------------------------------------- 
                            GetCompositeSchedule
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetCompositeScheduleRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//         	"type": "integer"
//         },
// 	"duration": {
// 		"type": "integer"
// 	},
// 	"chargingRateUnit": {
// 		"type": "string",
// 		"enum": [
// 			"A",
// 			"W"
// 			]
// 		}
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
// 	    "duration"
//     ]
// }
typedef enum {
    W = 0,      // Watts (power).
                // This is the TOTAL allowed charging power.
                // If used for AC Charging, the phase current should be calculated via: Current per phase = Power / (Line Voltage * Number of
                // Phases). The "Line Voltage" used in the calculation is not the measured voltage, but the set voltage for the area (hence, 230 of
                // 110 volt). The "Number of Phases" is the numberPhases from the ChargingSchedulePeriod.
                // It is usually more convenient to use this for DC charging.
                // Note that if numberPhases in a ChargingSchedulePeriod is absent, 3 SHALL be assumed.
    A,          // Amperes (current).
                // The amount of Ampere per phase, not the sum of all phases.
                // It is usually more convenient to use this for AC charging.
} ChargingRateUnitType;

typedef struct {
    integer connectorId;    // Required. The ID of the Connector for which the schedule is
                            // requested. When ConnectorId=0, the Charge Point will calculate
                            // the expected consumption for the grid connection.
    integer duration;       // Required. Time in seconds. length of requested schedule
    ChargingRateUnitType chargingRateUnit;  // Optional. Can be used to force a power or current profile
} GetCompositeScheduleRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetCompositeScheduleResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected"
//             ]
//         },
// 	"connectorId": {
// 		"type": "integer"
//         },
// 	"scheduleStart": {
// 		"type": "string",
// 		"format": "date-time"
// 	},
// 	"chargingSchedule": {
// 		"type": "object",
// 		"properties": {
// 			"duration": {
// 				"type": "integer"
// 			},
// 			"startSchedule": {
// 				"type": "string",
// 				"format": "date-time"
// 			},
// 			"chargingRateUnit": {
// 				"type": "string",
// 				"enum": [
// 					"A",
// 					"W"
// 					]
// 			},
// 			"chargingSchedulePeriod": {
// 				"type": "array",
// 				"items": {
// 					"type": "object",
// 					"properties": {
// 						"startPeriod": {
// 							"type": "integer"
// 						},
// 						"limit": {
// 							"type": "number",
// 						    "multipleOf" : 0.1
// 						},
// 						"numberPhases": {
// 							"type": "integer"
// 						}
// 					},
// 					"required": [
// 						"startPeriod",
// 						"limit"
// 						]
// 				}
// 			},
// 			"minChargingRate": {
// 				"type": "number",
// 				"multipleOf" : 0.1
// 			}
// 		},
// 		"required": [
// 			"chargingRateUnit",
// 			"chargingSchedulePeriod"
// 		]
// 		}
// 	},
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,       // Request has been accepted and will be executed.
    Rejected,           // Request has not been accepted and will not be executed.
} GetCompositeScheduleStatus;

typedef struct {
    integer startPeriod;    // 1..1 Required. Start of the period, in seconds from the start of schedule. The value of
                            // StartPeriod also defines the stop time of the previous period.
    decimal limit;         // 1..1 Required. Charging rate limit during the schedule period, in the applicable
                            // chargingRateUnit, for example in Amperes or Watts. Accepts at most one digit
                            // fraction (e.g. 8.1).
    integer numberPhases;  //  0..1 Optional. The number of phases that can be used for charging. If a number of
                            // phases is needed, numberPhases=3 will be assumed unless another number is given.
} ChargingSchedulePeriod;

typedef struct 
{
    integer duration;           // Optional. Duration of the charging schedule in seconds. If the
                                // duration is left empty, the last period will continue indefinitely or
                                // until end of the transaction in case startSchedule is absent.
    dateTime startSchedule;     // Optional. Starting point of an absolute schedule. If absent the
                                // schedule will be relative to start of charging.
    ChargingRateUnitType chargingRateUnit;  // Required. The unit of measure Limit is expressed in.

    integer chargingSchedulePeriodArrayLength;
    ChargingSchedulePeriod * chargingSchedulePeriod;  // 1..* Required. List of ChargingSchedulePeriod elements defining
                                // maximum power or current usage over time. The startSchedule of
                                // the first ChargingSchedulePeriod SHALL always be 0.
    decimal minChargingRate;    //  0..1 Optional. Minimum charging rate supported by the electric
                                // vehicle. The unit of measure is defined by the chargingRateUnit.
                                // This parameter is intended to be used by a local smart charging
                                // algorithm to optimize the power allocation for in the case a
                                // charging process is inefficient at lower charging rates. Accepts at
                                // most one digit fraction (e.g. 8.1)
};

typedef struct {
    GetCompositeScheduleStatus status;      // Required. Status of the request. The Charge Point will indicate if it
                                            // was able to process the request
    integer connectorId;                    // Optional. The charging schedule contained in this notification
                                            // applies to a Connector.
    dateTime scheduleStart;                 // Optional. Time. Periods contained in the charging profile are
                                            // relative to this point in time.
                                            // If status is "Rejected", this field may be absent.
    ChargingSchedule chargingSchedule;      // Optional. Planned Composite Charging Schedule, the energy
                                            // consumption over time. Always relative to ScheduleStart.
                                            // If status is "Rejected", this field may be absent.
} GetCompositeScheduleResponse;

/* ---------------------------------------------------------------------------- 
                            GetConfiguration
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetConfigurationRequest",
//     "type": "object",
//     "properties": {
//         "key": {
//             "type": "array",
//             "items": {
//                 "type": "string",
// 				"maxLength": 50
//             }
//         }
//     },
//     "additionalProperties": false
// }
typedef struct 
{
    integer keyArrayLength;
    char (* key)[CI_STRING_50_LENGTH + 1];    // 0..* Optional. List of keys for which the configuration value is requested.
} GetConfigurationRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetConfigurationResponse",
//     "type": "object",
//     "properties": {
//         "configurationKey": {
//             "type": "array",
//             "items": {
//                 "type": "object",
//                 "properties": {
//                     "key": {
//                         "type": "string",
// 						"maxLength": 50
//                     },
//                     "readonly": {
//                         "type": "boolean"
//                     },
//                     "value": {
//                         "type": "string",
// 						"maxLength": 500
//                     }
//                 },
//                 "required": [
//                     "key",
//                     "readonly"
//                 ]
//             }
//         },
//         "unknownKey": {
//             "type": "array",
//             "items": {
//                 "type": "string",
// 				"maxLength": 50
//             }
//         }
//     },
//     "additionalProperties": false
// }
typedef struct 
{
    char key[CI_STRING_50_LENGTH + 1];  // 1..1 Required.
    boolean readonly;                   // 1..1 Required. False if the value can be set with the ChangeConfiguration message.
    char * value;                       // CiString500Type 0..1 Optional. If key is known but not set, this field may be absent.
} KeyValue;

typedef struct {
    integer configurationKeyArrayLength;
    KeyValue * configurationKey;        // 0..* Optional. List of requested or known keys
    
    integer unknownKeyArrayLength;
    char (* unknownKey)[CI_STRING_50_LENGTH + 1];   // CiString50Type 0..* Optional. Requested keys that are unknown
} GetConfigurationResponse;
/* ---------------------------------------------------------------------------- 
                            GetDiagnostics
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetDiagnosticsRequest",
//     "type": "object",
//     "properties": {
//         "location": {
//             "type": "string",
//             "format": "uri"
//         },
//         "retries": {
//             "type": "integer"
//         },
//         "retryInterval": {
//             "type": "integer"
//         },
//         "startTime": {
//             "type": "string",
//             "format": "date-time"
//         },
//         "stopTime": {
//             "type": "string",
//             "format": "date-time"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "location"
//     ]
// }
typedef struct {
    char * location;        // anyURI 1..1 Required. This contains the location (directory) where the diagnostics file shall be uploaded to.
    integer retries;        //  0..1 Optional. This specifies how many times Charge Point must try to upload the
                            // diagnostics before giving up. If this field is not present, it is left to Charge Point
                            // to decide how many times it wants to retry.
    integer retryInterval;  // 0..1 Optional. The interval in seconds after which a retry may be attempted. If this
                            // field is not present, it is left to Charge Point to decide how long to wait between attempts.
    dateTime startTime;     // 0..1 Optional. This contains the date and time of the oldest logging information to
                            // include in the diagnostics.
    dateTime stopTime;      // 0..1 Optional. This contains the date and time of the latest logging information to
                            // include in the diagnostics.
} GetDiagnosticsRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetDiagnosticsResponse",
//     "type": "object",
//     "properties": {
//         "fileName": {
//             "type": "string",
//             "maxLength": 255
//         }
//     },
//     "additionalProperties": false
// }
typedef struct {
    char * fileName;        // CiString255Type 0..1 Optional. This contains the name of the file with diagnostic information that will
                            // be uploaded. This field is not present when no diagnostic information is
                            // available
} GetDiagnosticsResponse;
/* ---------------------------------------------------------------------------- 
                            GetLocalListVersion
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetLocalListVersionRequest",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct {
    integer dummy;          // No fields are defined.
} GetLocalListVersionRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "GetLocalListVersionResponse",
//     "type": "object",
//     "properties": {
//         "listVersion": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "listVersion"
//     ]
// }
typedef struct {
    integer listVersion;    // 1..1 Required. This contains the current version number of the local authorization list in the Charge Point.
} GetLocalListVersionResponse;

/* ---------------------------------------------------------------------------- 
                            Heartbeat
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "HeartbeatRequest",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct {
    integer dummy;          // No fields are defined.
} HeartbeatRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "HeartbeatResponse",
//     "type": "object",
//     "properties": {
//         "currentTime": {
//             "type": "string",
//             "format": "date-time"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "currentTime"
//     ]
// }
typedef struct {
    dateTime currentTime;   // 1..1 Required. This contains the current time of the Central System.
} HeartbeatResponse;

/* ---------------------------------------------------------------------------- 
                            MeterValues
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "MeterValuesRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
//         "transactionId": {
//             "type": "integer"
//         },
//         "meterValue": {
//             "type": "array",
//             "items": {
//                 "type": "object",
//                 "properties": {
//                     "timestamp": {
//                         "type": "string",
//                         "format": "date-time"
//                     },
// 					"sampledValue": {
// 						"type": "array",
// 						"items": {
// 							"type": "object",
// 							"properties": {
//                                 "value": {
//                                     "type": "string"
//                                 },
// 								"context": {
// 									"type": "string",
// 									"enum": [
// 										"Interruption.Begin",
// 										"Interruption.End",
// 										"Sample.Clock",
// 										"Sample.Periodic",
// 										"Transaction.Begin",
// 										"Transaction.End",
// 										"Trigger",
// 										"Other"
// 									]
// 								},
// 								"format": {
// 									"type": "string",
// 									"enum": [
// 										"Raw",
// 										"SignedData"
// 									]
// 								},
// 								"measurand": {
// 									"type": "string",
// 									"enum": [
// 										"Energy.Active.Export.Register",
// 										"Energy.Active.Import.Register",
// 										"Energy.Reactive.Export.Register",
// 										"Energy.Reactive.Import.Register",
// 										"Energy.Active.Export.Interval",
// 										"Energy.Active.Import.Interval",
// 										"Energy.Reactive.Export.Interval",
// 										"Energy.Reactive.Import.Interval",
// 										"Power.Active.Export",
// 										"Power.Active.Import",
// 										"Power.Offered",
// 										"Power.Reactive.Export",
// 										"Power.Reactive.Import",
// 										"Power.Factor",
// 										"Current.Import",
// 										"Current.Export",
// 										"Current.Offered",
// 										"Voltage",
// 										"Frequency",
// 										"Temperature",
// 										"SoC",
// 										"RPM"
// 									]
// 								},
// 								"phase": {
// 									"type": "string",
// 									"enum": [
// 										"L1",
// 										"L2",
// 										"L3",
// 										"N",
// 										"L1-N",
// 										"L2-N",
// 										"L3-N",
// 										"L1-L2",
// 										"L2-L3",
// 										"L3-L1"
// 									]
// 								},
// 								"location": {
// 									"type": "string",
// 									"enum": [
// 										"Cable",
// 										"EV",
// 										"Inlet",
// 										"Outlet",
// 										"Body"
// 									]
// 								},
// 								"unit": {
// 									"type": "string",
// 									"enum": [
// 										"Wh",
// 										"kWh",
// 										"varh",
// 										"kvarh",
// 										"W",
// 										"kW",
// 										"VA",
// 										"kVA",
// 										"var",
// 										"kvar",
// 										"A",
// 										"V",
// 										"K",
// 										"Celsius",
// 										"Fahrenheit",
// 										"Percent"
// 									]
// 								}
// 							},
// 							"required": [
// 								"value"
// 							]
// 						}
// 					}
// 				},
//                 "required": [
//                     "timestamp",
// 					"sampledValue"
//                 ]		
// 			}
// 		}
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
// 		"meterValue"
// 	]
// }
typedef enum {
    Interruption_Begin = 0,     // Value taken at start of interruption.
    Interruption_End,           // Value taken when resuming after interruption.
    Other,                      // Value for any other situations.
    Sample_Clock,               // Value taken at clock aligned interval.
    Sample_Periodic,            // Value taken as periodic sample relative to start time of transaction.
    Transaction_Begin,          // Value taken at start of transaction.
    Transaction_End,            // Value taken at end of transaction.
    Trigger,                    // Value taken in response to a TriggerMessage.req

    TOTAL_NUM
} ReadingContext;

typedef enum {
    Raw = 0,                    // Data is to be interpreted as integer/decimal numeric data.
    SignedData,                 // Data is represented as a signed binary data block, encoded as hex data.
} ValueFormat;

typedef enum {
    Current_Export = 0,         // Instantaneous current flow from EV
    Current_Import,             // Instantaneous current flow to EV
    Current_Offered,            // Maximum current offered to EV
    Energy_Active_Export_Register,  // Numerical value read from the "active electrical energy" (Wh or kWh) register of the (most authoritative)
                                    // electrical meter measuring energy exported (to the grid).
    Energy_Active_Import_Register,  // Numerical value read from the "active electrical energy" (Wh or kWh) register of the (most authoritative)
                                    // electrical meter measuring energy imported (from the grid supply).
    Energy_Reactive_Export_Register,// Numerical value read from the "reactive electrical energy" (VARh or kVARh) register of the (most
                                    // authoritative) electrical meter measuring energy exported (to the grid).
    Energy_Reactive_Import_Register,// Numerical value read from the "reactive electrical energy" (VARh or kVARh) register of the (most
                                    // authoritative) electrical meter measuring energy imported (from the grid supply).
    Energy_Active_Export_Interval,  // Absolute amount of "active electrical energy" (Wh or kWh) exported (to the grid) during an associated time
                                    // "interval", specified by a Metervalues ReadingContext, and applicable interval duration configuration values
                                    // (in seconds) for "ClockAlignedDataInterval" and "MeterValueSampleInterval".
    Energy_Active_Import_Interval,  // Absolute amount of "active electrical energy" (Wh or kWh) imported (from the grid supply) during an
                                    // associated time "interval", specified by a Metervalues ReadingContext, and applicable interval duration
                                    // configuration values (in seconds) for "ClockAlignedDataInterval" and "MeterValueSampleInterval".
    Energy_Reactive_Export_Interval,// Absolute amount of "reactive electrical energy" (VARh or kVARh) exported (to the grid) during an associated
                                    // time "interval", specified by a Metervalues ReadingContext, and applicable interval duration configuration
                                    // values (in seconds) for "ClockAlignedDataInterval" and "MeterValueSampleInterval".
    Energy_Reactive_Import_Interval,// Absolute amount of "reactive electrical energy" (VARh or kVARh) imported (from the grid supply) during an
                                    // associated time "interval", specified by a Metervalues ReadingContext, and applicable interval duration
                                    // configuration values (in seconds) for "ClockAlignedDataInterval" and "MeterValueSampleInterval".
    Frequency,                      // Instantaneous reading of powerline frequency. NOTE: OCPP 1.6 does not have a UnitOfMeasure for
                                    // frequency, the UnitOfMeasure for any SampledValue with measurand: Frequency is Hertz.
    Power_Active_Export,            // Instantaneous active power exported by EV. (W or kW)
    Power_Active_Import,            // Instantaneous active power imported by EV. (W or kW)
    Power_Factor,                   // Instantaneous power factor of total energy flow
    Power_Offered,                  // Maximum power offered to EV
    Power_Reactive_Export,          // Instantaneous reactive power exported by EV. (var or kvar)
    Power_Reactive_Import,          // Instantaneous reactive power imported by EV. (var or kvar)
    RPM,                            // Fan speed in RPM
    SoC,                            // State of charge of charging vehicle in percentage
    Temperature,                    // Temperature reading inside Charge Point.
    Voltage,                        // Instantaneous AC RMS supply voltage
} Measurand;

typedef enum {
    L1,                         // Measured on L1
    L2,                         // Measured on L2
    L3,                         // Measured on L3
    N,                          // Measured on Neutral
    L1_N,                       // Measured on L1 with respect to Neutral conductor
    L2_N,                       // Measured on L2 with respect to Neutral conductor
    L3_N,                       // Measured on L3 with respect to Neutral conductor
    L1_L2,                      // Measured between L1 and L2
    L2_L3,                      // Measured between L2 and L3
    L3_L1,                      // Measured between L3 and L1
} Phase;

typedef enum {
    Body,                       // Measurement inside body of Charge Point (e.g. Temperature)
    Cable,                      // Measurement taken from cable between EV and Charge Point
    EV,                         // Measurement taken by EV
    Inlet,                      // Measurement at network (“grid”) inlet connection
    Outlet,                     // Measurement at a Connector. Default value
} Location;

typedef enum {
    Wh,                         // Watt-hours (energy). Default.
    kWh,                        // kiloWatt-hours (energy).
    varh,                       // Var-hours (reactive energy).
    kvarh,                      // kilovar-hours (reactive energy).
    W,                          // Watts (power).
    kW,                         // kilowatts (power).
    VA,                         // VoltAmpere (apparent power).
    kVA,                        // kiloVolt Ampere (apparent power).
    var,                        // Vars (reactive power).
    kvar,                       // kilovars (reactive power).
    A,                          // Amperes (current).
    V,                          // Voltage (r.m.s. AC).
    Celsius,                    // Degrees (temperature).
    Fahrenheit,                 // Degrees (temperature).
    K,                          // Degrees Kelvin (temperature).
    Percent,                    // Percentage.
} UnitOfMeasure;

typedef struct {
    char * value;               // String 1..1 Required. Value as a “Raw” (decimal) number or “SignedData”. Field Type is
                                // “string” to allow for digitally signed data readings. Decimal numeric values are
                                // also acceptable to allow fractional values for measurands such as Temperature
                                // and Current.
    ReadingContext context;     // 0..1 Optional. Type of detail value: start, end or sample. Default = “Sample.Periodic”
    ValueFormat format;         // 0..1 Optional. Raw or signed data. Default = “Raw”
    Measurand measurand;        // 0..1 Optional. Type of measurement. Default = “Energy.Active.Import.Register”
    Phase phase;                // 0..1 Optional. indicates how the measured value is to be interpreted. For instance
                                // between L1 and neutral (L1-N) Please note that not all values of phase are
                                // applicable to all Measurands. When phase is absent, the measured value is
                                // interpreted as an overall value.
    Location location;          // 0..1 Optional. Location of measurement. Default=”Outlet”
    UnitOfMeasure unit;         // 0..1 Optional. Unit of the value. Default = “Wh” if the (default) measurand is an “Energy” type.    
} SampledValue;


typedef struct {
    dateTime timestamp;             // 1..1 Required. Timestamp for measured value(s).
    
    integer sampledValueArrayLength;
    SampledValue * sampledValue;    // 1..* Required. One or more measured values
} MeterValue;

typedef struct {
    integer connectorId;    // connectorId >= 0
                            // 1..1 Required. This contains a number (>0) designating a connector of the Charge
                            // Point.‘0’ (zero) is used to designate the main powermeter.
    integer transactionId;  // 0..1 Optional. The transaction to which these meter samples are related.
    
    integer meterValueArrayLength;
    MeterValue * meterValue;    // 1..* Required. The sampled meter values with timestamps.
} MeterValuesRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "MeterValuesResponse",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct {
    int dummy;              // No fields are defined
} MeterValuesResponse;

/* ---------------------------------------------------------------------------- 
                            RemoteStartTransaction
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "RemoteStartTransactionRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
//         "idTag": {
//             "type": "string",
//             "maxLength": 20
//         },
// 		"chargingProfile": {
// 			"type": "object",
// 			"properties": {
// 				"chargingProfileId": {
// 					"type": "integer"
// 				},
// 				"transactionId": {
// 					"type": "integer"
// 				},
// 				"stackLevel": {
// 					"type": "integer"
// 				},
// 				"chargingProfilePurpose": {
// 					"type": "string",
// 					"enum": [
// 						"ChargePointMaxProfile",
// 						"TxDefaultProfile",
// 						"TxProfile"
// 					]
// 				},
// 				"chargingProfileKind": {
// 					"type": "string",
// 					"enum": [
// 						"Absolute",
// 						"Recurring",
// 						"Relative"
// 					]
// 				},
// 				"recurrencyKind": {
// 					"type": "string",
// 					"enum": [
// 						"Daily",
// 						"Weekly"
// 					]
// 				},
// 				"validFrom": {
// 					"type": "string",
// 					"format": "date-time"
// 				},
// 				"validTo": {
// 					"type": "string",
// 					"format": "date-time"
// 				},
// 				"chargingSchedule": {
// 					"type": "object",
// 					"properties": {
// 						"duration": {
// 							"type": "integer"
// 						},
// 						"startSchedule": {
// 							"type": "string",
// 							"format": "date-time"
// 						},
// 						"chargingRateUnit": {
// 							"type": "string",
// 							"enum": [
// 								"A",
// 								"W"
// 							]
// 						},
// 						"chargingSchedulePeriod": {
// 							"type": "array",
// 							"items": {
// 								"type": "object",
// 								"properties": {
// 									"startPeriod": {
// 										"type": "integer"
// 									},
// 									"limit": {
// 										"type": "number",
// 										"multipleOf" : 0.1
// 									},
// 									"numberPhases": {
// 										"type": "integer"
// 									}
// 								},
// 								"required": [
// 									"startPeriod",
// 									"limit"
// 								]
// 							}
// 						},
// 						"minChargingRate": {
// 							"type": "number",
// 							"multipleOf" : 0.1
// 						}
// 					},
// 					"required": [
// 						"chargingRateUnit",
// 						"chargingSchedulePeriod"
// 					]
// 				}
// 			},
// 			"required": [
// 				"chargingProfileId",
// 				"stackLevel",
// 				"chargingProfilePurpose",
// 				"chargingProfileKind",
// 				"chargingSchedule"
// 			]
// 		}
// 	},
//     "additionalProperties": false,
//     "required": [
//         "idTag"
//     ]
// }
typedef enum {
    Absolute,   // Schedule periods are relative to a fixed point in time defined in the schedule.
    Recurring,  // The schedule restarts periodically at the first schedule period.
    Relative,   // Schedule periods are relative to a situation-specific start point (such as the start of a Transaction) that is determined by the charge point.
} ChargingProfileKindType;

typedef enum {
    Daily,      // The schedule restarts every 24 hours, at the same time as in the startSchedule. 
    Weekly,     // The schedule restarts every 7 days, at the same time and day-of-the-week as in the startSchedule.
} RecurrencyKindType;

typedef struct {
    integer duration;   // 0..1 Optional. Duration of the charging schedule in seconds. If the
                        // duration is left empty, the last period will continue indefinitely or
                        // until end of the transaction in case startSchedule is absent.
    dateTime startSchedule; // 0..1 Optional. Starting point of an absolute schedule. If absent the
                            // schedule will be relative to start of charging.
    ChargingRateUnitType chargingRateUnit;  // 1..1 Required. The unit of measure Limit is expressed in.
    ChargingSchedulePeriod chargingSchedulePeriod;  // 1..* Required. List of ChargingSchedulePeriod elements defining
                                                    // maximum power or current usage over time. The startSchedule of
                                                    // the first ChargingSchedulePeriod SHALL always be 0.
    decimal minChargingRate;    // 0..1 Optional. Minimum charging rate supported by the electric
                                // vehicle. The unit of measure is defined by the chargingRateUnit.
                                // This parameter is intended to be used by a local smart charging
                                // algorithm to optimize the power allocation for in the case a
                                // charging process is inefficient at lower charging rates. Accepts at
                                // most one digit fraction (e.g. 8.1)
} ChargingSchedule;

typedef struct 
{
    integer chargingProfileId;      // 1..1 Required. Unique identifier for this profile.
    integer transactionId;          // 0..1 Optional. Only valid if ChargingProfilePurpose is set to TxProfile,
                                    // the transactionId MAY be used to match the profile to a specific transaction.
    integer stackLevel;             //  >=0 1..1 Required. Value determining level in hierarchy stack of profiles.
                                    // Higher values have precedence over lower values. Lowest level is 0.
    ChargingProfilePurposeType chargingProfilePurpose;  // 1..1 Required. Defines the purpose of the schedule transferred by this message.
    ChargingProfileKindType chargingProfileKind;        // 1..1 Required. Indicates the kind of schedule.
    RecurrencyKindType recurrencyKind;                  // 0..1 Optional. Indicates the start point of a recurrence.
    dateTime validFrom;             // 0..1 Optional. Point in time at which the profile starts to be valid. If
                                    //absent, the profile is valid as soon as it is received by the Charge Point.
    dateTime validTo;               // 0..1 Optional. Point in time at which the profile stops to be valid. If
                                    // absent, the profile is valid until it is replaced by another profile.
    ChargingSchedule chargingSchedule;                  // 1..1 Required. Contains limits for the available power or current over time.                                
} ChargingProfile;


typedef struct {
    integer connectorId;    // 0..1 Optional. Number of the connector on which to start the transaction.
                            // ConnectorId SHALL be > 0
    IdToken idTag;          // 1..1 Required. The identifier that Charge Point must use to start a transaction.
    ChargingProfile chargingProfile;    // 0..1 Optional. Charging Profile to be used by the Charge Point for the requested
                                        // transaction. ChargingProfilePurpose MUST be set to TxProfile
} RemoteStartTransactionRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "RemoteStartTransactionResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted,   // Command will be executed.
    Rejected,   // Command will not be executed.
} RemoteStartStopStatus;

typedef struct {
    RemoteStartStopStatus status;   // 1..1 Required. Status indicating whether Charge Point accepts the
                                    // request to start a transaction.
} RemoteStartTransactionResponse;

/* ---------------------------------------------------------------------------- 
                            RemoteStopTransaction
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "RemoteStopTransactionRequest",
//     "type": "object",
//     "properties": {
//         "transactionId": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "transactionId"
//     ]
// }
typedef struct {
    integer transactionId; // 1..1 Required. The identifier of the transaction which Charge Point is requested to stop.
} RemoteStopTransactionRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "RemoteStopTransactionResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef struct {
    RemoteStartStopStatus status;   // 1..1 Required. Status indicating whether Charge Point accepts the
                                    // request to stop a transaction.
} RemoteStopTransactionResponse;

/* ---------------------------------------------------------------------------- 
                            ReserveNow
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ReserveNowRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
//         "expiryDate": {
//             "type": "string",
//             "format": "date-time"
//         },
//         "idTag": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "parentIdTag": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "reservationId": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
//         "expiryDate",
//         "idTag",
//         "reservationId"
//     ]
// }
typedef struct {
    integer connectorId;    // connectorId >= 0
                            // 1..1 Required. This contains the id of the connector to be reserved. A value of 0
                            // means that the reservation is not for a specific connector.
    dateTime expiryDate;    // 1..1 Required. This contains the date and time when the reservation ends.
    IdToken idTag;          // 1..1 Required. The identifier for which the Charge Point has to reserve a connector.
    IdToken parentIdTag;    // 0..1 Optional. The parent idTag.
    integer reservationId;  // 1..1 Required. Unique id for this reservation.
} ReserveNowRequest;
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ReserveNowResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Faulted",
//                 "Occupied",
//                 "Rejected",
//                 "Unavailable"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted,       // Reservation has been made.
    Faulted,        // Reservation has not been made, because connectors or specified connector are in a faulted state.
    Occupied,       // Reservation has not been made. All connectors or the specified connector are occupied.
    Rejected,       // Reservation has not been made. Charge Point is not configured to accept reservations.
    Unavailable,    // Reservation has not been made, because connectors or specified connector are in an unavailable state.
} ReservationStatus;

typedef struct {
    ReservationStatus status;   // 1..1 Required. This indicates the success or failure of the reservation.
} ReserveNowResponse;

/* ---------------------------------------------------------------------------- 
                            Reset
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ResetRequest",
//     "type": "object",
//     "properties": {
//         "type": {
//             "type": "string",
//             "enum": [
//                 "Hard",
//                 "Soft"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "type"
//     ]
// }
typedef enum {
    Hard,   // Restart (all) the hardware, the Charge Point is not required to gracefully stop ongoing transaction. If possible the Charge Point
            // sends a StopTransaction.req for previously ongoing transactions after having restarted and having been accepted by the
            // Central System via a BootNotification.conf. This is a last resort solution for a not correctly functioning Charge Point, by sending
            // a "hard" reset, (queued) information might get lost.
    Soft,   // Stop ongoing transactions gracefully and sending StopTransaction.req for every ongoing transaction. It should then restart the
            // application software (if possible, otherwise restart the processor/controller).
} ResetType;

typedef struct {
    ResetType type; // 1..1 Required. This contains the type of reset that the Charge Point should perform.
} ResetRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "ResetResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted,   // Command will be executed.
    Rejected,   // Command will not be executed.
} ResetStatus;

typedef struct {
    ResetStatus status;     // 1..1 Required. This indicates whether the Charge Point is able to perform the reset.
} ResetResponse;

/* ---------------------------------------------------------------------------- 
                            SendLocalList
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "SendLocalListRequest",
//     "type": "object",
//     "properties": {
//         "listVersion": {
//             "type": "integer"
//         },
//         "localAuthorizationList": {
//             "type": "array",
//             "items": {
//                 "type": "object",
//                 "properties": {
//                     "idTag": {
//                         "type": "string",
//                         "maxLength": 20
//                     },
//                     "idTagInfo": {
//                         "type": "object",
// 						"expiryDate": {
// 							"type": "string",
// 							"format": "date-time"
// 						},
// 						"parentIdTag": {
// 							"type": "string",
// 							"maxLength": 20
// 						},
//                         "properties": {
//                             "status": {
//                                 "type": "string",
//                                 "enum": [
//                                     "Accepted",
//                                     "Blocked",
//                                     "Expired",
//                                     "Invalid",
//                                     "ConcurrentTx"
//                                 ]
//                             }
//                         },
//                         "required": [
//                             "status"
//                         ]
//                     }
//                 },
//                 "required": [
//                     "idTag"
//                 ]
//             }
//         },
// 		"updateType": {
//             "type": "string",
//             "enum": [
//                 "Differential",
//                 "Full"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "listVersion",
//         "updateType"
//     ]
// }
typedef struct {
    IdToken idTag;          // 1..1 Required. The identifier to which this authorization applies.
    IdTagInfo idTagInfo;    // 0..1 Optional. (Required when UpdateType is Full) This contains information about
                            // authorization status, expiry and parent id. For a Differential update the following
                            // applies: If this element is present, then this entry SHALL be added or updated in
                            // the Local Authorization List. If this element is absent, than the entry for this
                            // idtag in the Local Authorization List SHALL be deleted.
} AuthorizationData;

typedef enum {
    Differential,   // Indicates that the current Local Authorization List must be updated with the values in this message.
    Full,           // Indicates that the current Local Authorization List must be replaced by the values in this message.
} UpdateType;

typedef struct {
    integer listVersion;    // 1..1 Required. In case of a full update this is the version number of the
                            // full list. In case of a differential update it is the version number of
                            // the list after the update has been applied.
    
    integer localAuthorizationListArrayLength;
    AuthorizationData * localAuthorizationList;   // 0..* Optional. In case of a full update this contains the list of values
                            // that form the new local authorization list. In case of a differential
                            // update it contains the changes to be applied to the local
                            // authorization list in the Charge Point. Maximum number of
                            // AuthorizationData elements is available in the configuration key:
                            // SendLocalListMaxLength
    UpdateType updateType;  // 1..1 Required. This contains the type of update (full or differential) of
                            // this request.
} SendLocalListRequest;


/* ---------------------------------------------------------------------------- 
                            SetChargingProfile
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "SetChargingProfileRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
// 		"csChargingProfiles": {
// 			"type": "object",
// 			"properties": {
// 				"chargingProfileId": {
// 					"type": "integer"
// 				},
// 				"transactionId": {
// 					"type": "integer"
// 				},
// 				"stackLevel": {
// 					"type": "integer"
// 				},
// 				"chargingProfilePurpose": {
// 					"type": "string",
// 					"enum": [
// 						"ChargePointMaxProfile",
// 						"TxDefaultProfile",
// 						"TxProfile"
// 					]
// 				},
// 				"chargingProfileKind": {
// 					"type": "string",
// 					"enum": [
// 						"Absolute",
// 						"Recurring",
// 						"Relative"
// 					]
// 				},
// 				"recurrencyKind": {
// 					"type": "string",
// 					"enum": [
// 						"Daily",
// 						"Weekly"
// 					]
// 				},
// 				"validFrom": {
// 					"type": "string",
// 					"format": "date-time"
// 				},
// 				"validTo": {
// 					"type": "string",
// 					"format": "date-time"
// 				},
// 				"chargingSchedule": {
// 					"type": "object",
// 					"properties": {
// 						"duration": {
// 							"type": "integer"
// 						},
// 						"startSchedule": {
// 							"type": "string",
// 							"format": "date-time"
// 						},
// 						"chargingRateUnit": {
// 							"type": "string",
// 							"enum": [
// 								"A",
// 								"W"
// 							]
// 						},
// 						"chargingSchedulePeriod": {
// 							"type": "array",
// 							"items": {
// 								"type": "object",
// 								"properties": {
// 									"startPeriod": {
// 										"type": "integer"
// 									},
// 								"limit": {
// 									"type": "number",
// 									"multipleOf" : 0.1
// 								},
// 								"numberPhases": {
// 										"type": "integer"
// 									}
// 								},
// 								"required": [
// 									"startPeriod",
// 									"limit"
// 								]
// 							}
// 						},
// 						"minChargingRate": {
// 							"type": "number",
// 							"multipleOf" : 0.1
// 						}
// 					},
// 					"required": [
// 						"chargingRateUnit",
// 						"chargingSchedulePeriod"
// 					]
// 				}
// 			},
// 			"required": [
// 				"chargingProfileId",
// 				"stackLevel",
// 				"chargingProfilePurpose",
// 				"chargingProfileKind",
// 				"chargingSchedule"
// 			]
// 		}
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
// 		"csChargingProfiles"
//     ]
// }
typedef struct {
    integer connectorId;    // 1..1 Required. The connector to which the charging profile applies. If connectorId = 0,
                            // the message contains an overall limit for the Charge Point.
    ChargingProfile csChargingProfiles; // 1..1 Required. The charging profile to be set at the Charge Point.
} SetChargingProfileRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "SetChargingProfileResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected",
//                 "NotSupported"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,   // Request has been accepted and will be executed.
    Rejected,       // Request has not been accepted and will not be executed.
    NotSupported,   // Charge Point indicates that the request is not supported.
} ChargingProfileStatus;

typedef struct 
{
    ChargingProfileStatus status;   // 1..1 Required. Returns whether the Charge Point has been able to process the
                                    // message successfully. This does not guarantee the schedule will be followed to
                                    // the letter. There might be other constraints the Charge Point may need to take
                                    // into account.
} SetChargingProfileResponse;


/* ---------------------------------------------------------------------------- 
                            StartTransaction
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "StartTransactionRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
//         "idTag": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "meterStart": {
//             "type": "integer"
//         },
//         "reservationId": {
//             "type": "integer"
//         },
//         "timestamp": {
//             "type": "string",
//             "format": "date-time"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
//         "idTag",
//         "meterStart",
//         "timestamp"
//     ]
// }
typedef struct {
    integer connectorId;    // connectorId > 0
                            // 1..1 Required. This identifies which connector of the Charge Point is used.
    IdToken idTag;          // 1..1 Required. This contains the identifier for which a transaction has to be started.
    integer meterStart;     // 1..1 Required. This contains the meter value in Wh for the connector at start of the transaction.
    integer reservationId;  // 0..1 Optional. This contains the id of the reservation that terminates as a result of this transaction.
    dateTime timestamp;     // 1..1 Required. This contains the date and time on which the transaction is started.
} StartTransactionRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "StartTransactionResponse",
//     "type": "object",
//     "properties": {
//         "idTagInfo": {
//             "type": "object",
//             "properties": {
//                 "expiryDate": {
//                     "type": "string",
//                     "format": "date-time"
//                 },
//                 "parentIdTag": {
//                     "type": "string",
//                     "maxLength": 20
//                 },
//                 "status": {
//                     "type": "string",
//                     "enum": [
//                         "Accepted",
//                         "Blocked",
//                         "Expired",
//                         "Invalid",
//                         "ConcurrentTx"
//                     ]
//                 }
//             },
//             "required": [
//                 "status"
//             ]
//         },
// 		"transactionId": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "idTagInfo",
//         "transactionId"
//     ]
// }
typedef struct {
    IdTagInfo idTagInfo;    // 1..1 Required. This contains information about authorization status, expiry and parent id.
    integer transactionId;  // 1..1 Required. This contains the transaction id supplied by the Central System.
} StartTransactionResponse;

/* ---------------------------------------------------------------------------- 
                            StatusNotification
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "StatusNotificationRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         },
//         "errorCode": {
//             "type": "string",
//             "enum": [
//                 "ConnectorLockFailure",
//                 "EVCommunicationError",
//                 "GroundFailure",
//                 "HighTemperature",
//                 "InternalError",
//                 "LocalListConflict",
//                 "NoError",
//                 "OtherError",
//                 "OverCurrentFailure",
//                 "PowerMeterFailure",
//                 "PowerSwitchFailure",
//                 "ReaderFailure",
//                 "ResetFailure",
//                 "UnderVoltage",
//                 "OverVoltage",
//                 "WeakSignal"
//             ]
//         },
//         "info": {
//             "type": "string",
//             "maxLength": 50
//         },
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Available",
//                 "Preparing",
//                 "Charging",
//                 "SuspendedEVSE",
//                 "SuspendedEV",
//                 "Finishing",
//                 "Reserved",
//                 "Unavailable",
//                 "Faulted"
//             ]
//         },
//         "timestamp": {
//             "type": "string",
//             "format": "date-time"
//         },
//         "vendorId": {
//             "type": "string",
//             "maxLength": 255
//         },
//         "vendorErrorCode": {
//             "type": "string",
//             "maxLength": 50
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId",
//         "errorCode",
//         "status"
//     ]
// }
typedef enum {
    ConnectorLockFailure = 0,   // Failure to lock or unlock connector.
    EVCommunicationError,       // Communication failure with the vehicle, might be Mode 3 or other communication protocol problem. This is
                                // not a real error in the sense that the Charge Point doesn’t need to go to the faulted state. Instead, it should go
                                // to the SuspendedEVSE state.
    GroundFailure,              // Ground fault circuit interrupter has been activated.
    HighTemperature,            // Temperature inside Charge Point is too high.
    InternalError,              // Error in internal hard- or software component.
    LocalListConflict,          // The authorization information received from the Central System is in conflict with the LocalAuthorizationList.
    NoError,                    // No error to report.
    OtherError,                 // Other type of error. More information in vendorErrorCode.
    OverCurrentFailure,         // Over current protection device has tripped.
    OverVoltage,                // Voltage has risen above an acceptable level.
    PowerMeterFailure,          // Failure to read electrical/energy/power meter.
    PowerSwitchFailure,         // Failure to control power switch.
    ReaderFailure,              // Failure with idTag reader.
    ResetFailure,               // Unable to perform a reset.
    UnderVoltage,               // Voltage has dropped below an acceptable level.
    WeakSignal,                 // Wireless communication device reports a weak signal.
} ChargePointErrorCode;

typedef enum {
    Available = 0,  // When a Connector becomes available for a new user (Operative)
    Preparing,      // When a Connector becomes no longer available for a new user but there is no ongoing Transaction (yet). Typically a Connector
                    // is in preparing state when a user presents a tag, inserts a cable or a vehicle occupies the parking bay
                    // (Operative)
    Charging,       // When the contactor of a Connector closes, allowing the vehicle to charge
                    // (Operative)
    SuspendedEVSE,  // When the EV is connected to the EVSE but the EVSE is not offering energy to the EV, e.g. due to a smart charging restriction,
                    // local supply power constraints, or as the result of StartTransaction.conf indicating that charging is not allowed etc.
                    // (Operative)
    SuspendedEV,    // When the EV is connected to the EVSE and the EVSE is offering energy but the EV is not taking any energy.
                    // (Operative)
    Finishing,      // When a Transaction has stopped at a Connector, but the Connector is not yet available for a new user, e.g. the cable has not
                    // been removed or the vehicle has not left the parking bay
                    // (Operative)
    Reserved,       // When a Connector becomes reserved as a result of a Reserve Now command
                    // (Operative)
    Unavailable,    // When a Connector becomes unavailable as the result of a Change Availability command or an event upon which the Charge
                    // Point transitions to unavailable at its discretion. Upon receipt of a Change Availability command, the status MAY change
                    // immediately or the change MAY be scheduled. When scheduled, the Status Notification shall be send when the availability
                    // change becomes effective
                    // (Inoperative)
    Faulted,        // When a Charge Point or connector has reported an error and is not available for energy delivery . (Inoperative).
} ChargePointStatus;

typedef struct {
    integer connectorId;    // connectorId >= 0
                            // 1..1 Required. The id of the connector for which the status is reported.
                            // Id '0' (zero) is used if the status is for the Charge Point main controller.
    ChargePointErrorCode errorCode; // 1..1 Required. This contains the error code reported by the Charge Point.
    char info[CI_STRING_50_LENGTH + 1]; // CiString50Type 0..1 Optional. Additional free format information related to the error.
    ChargePointStatus status;   // 1..1 Required. This contains the current status of the Charge Point.
    dateTime timestamp;         // 0..1 Optional. The time for which the status is reported. If absent time
                                // of receipt of the message will be assumed.
    char *vendorId;             // CiString255Type 0..1 Optional. This identifies the vendor-specific implementation.
    char *vendorErrorCode;      // CiString50Type 0..1 Optional. This contains the vendor-specific error code.
} StatusNotificationRequest;
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "StatusNotificationResponse",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct {
    integer dummy;      // No fields are defined.
} StatusNotificationResponse;

/* ---------------------------------------------------------------------------- 
                            StopTransaction
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "StopTransactionRequest",
//     "type": "object",
//     "properties": {
//         "idTag": {
//             "type": "string",
//             "maxLength": 20
//         },
//         "meterStop": {
//             "type": "integer"
//         },
//         "timestamp": {
//             "type": "string",
//             "format": "date-time"
//         },
//         "transactionId": {
//             "type": "integer"
//         },
// 		"reason": {
// 			"type": "string",
// 			"enum": [
// 				"EmergencyStop",
// 				"EVDisconnected",
// 				"HardReset",
// 				"Local",
// 				"Other",
// 				"PowerLoss",
// 				"Reboot",
// 				"Remote",
// 				"SoftReset",
// 				"UnlockCommand",
// 				"DeAuthorized"
// 			]
// 		},
// 		"transactionData": {
// 			"type": "array",
// 			"items": {
// 				"type": "object",
// 				"properties": {
// 					"timestamp": {
// 						"type": "string",
// 						"format": "date-time"
// 					},
// 					"sampledValue": {
// 						"type": "array",
// 						"items": {
// 							"type": "object",
// 							"properties": {
// 								"value": {
// 									"type": "string"
// 								},
// 								"context": {
// 									"type": "string",
// 									"enum": [
// 										"Interruption.Begin",
// 										"Interruption.End",
// 										"Sample.Clock",
// 										"Sample.Periodic",
// 										"Transaction.Begin",
// 										"Transaction.End",
// 										"Trigger",
// 										"Other"
// 									]
// 								},	
// 								"format": {
// 									"type": "string",
// 									"enum": [
// 										"Raw",
// 										"SignedData"
// 									]
// 								},
// 								"measurand": {
// 									"type": "string",
// 									"enum": [
// 										"Energy.Active.Export.Register",
// 										"Energy.Active.Import.Register",
// 										"Energy.Reactive.Export.Register",
// 										"Energy.Reactive.Import.Register",
// 										"Energy.Active.Export.Interval",
// 										"Energy.Active.Import.Interval",
// 										"Energy.Reactive.Export.Interval",
// 										"Energy.Reactive.Import.Interval",
// 										"Power.Active.Export",
// 										"Power.Active.Import",
// 										"Power.Offered",
// 										"Power.Reactive.Export",
// 										"Power.Reactive.Import",
// 										"Power.Factor",
// 										"Current.Import",
// 										"Current.Export",
// 										"Current.Offered",
// 										"Voltage",
// 										"Frequency",
// 										"Temperature",
// 										"SoC",
// 										"RPM"
// 									]
// 								},
// 								"phase": {
// 									"type": "string",
// 									"enum": [
// 										"L1",
// 										"L2",
// 										"L3",
// 										"N",
// 										"L1-N",
// 										"L2-N",
// 										"L3-N",
// 										"L1-L2",
// 										"L2-L3",
// 										"L3-L1"
// 									]
// 								},
// 								"location": {
// 									"type": "string",
// 									"enum": [
// 										"Cable",
// 										"EV",
// 										"Inlet",
// 										"Outlet",
// 										"Body"
// 									]
// 								},
// 								"unit": {
// 									"type": "string",
// 									"enum": [
// 										"Wh",
// 										"kWh",
// 										"varh",
// 										"kvarh",
// 										"W",
// 										"kW",
// 										"VA",
// 										"kVA",
// 										"var",
// 										"kvar",
// 										"A",
// 										"V",
// 										"K",
// 										"Celsius",
// 										"Fahrenheit",
// 										"Percent"
// 									]
// 								}
// 							},
// 							"required": [
// 								"value"
// 							]
// 						}
// 					}
// 				},
// 				"required": [
// 					"timestamp",
// 					"sampledValue"
// 				]
// 			}
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "transactionId",
//         "timestamp",
//         "meterStop"
//     ]
// }
typedef enum {
    DeAuthorized = 0,   // The transaction was stopped because of the authorization status in a StartTransaction.conf
    EmergencyStop,      // Emergency stop button was used.
    EVDisconnected,     // disconnecting of cable, vehicle moved away from inductive charge unit.
    HardReset,          // A hard reset command was received.
    Local,              // Stopped locally on request of the user at the Charge Point. This is a regular termination of a transaction. Examples: presenting
                        // an RFID tag, pressing a button to stop.
    Other,              // Any other reason.
    PowerLossm,         // Complete loss of power.
    Reboot,             // A locally initiated reset/reboot occurred. (for instance watchdog kicked in)
    Remote,             // Stopped remotely on request of the user. This is a regular termination of a transaction. Examples: termination using a
                        // smartphone app, exceeding a (non local) prepaid credit.
    SoftReset,          // A soft reset command was received.
    UnlockCommand,      // Central System sent an Unlock Connector command.
} Reason;

typedef struct {
    IdToken idTag;      // 0..1 Optional. This contains the identifier which requested to stop the charging. It is
                        // optional because a Charge Point may terminate charging without the presence
                        // of an idTag, e.g. in case of a reset. A Charge Point SHALL send the idTag if known.
    integer meterStop;  // 1..1 Required. This contains the meter value in Wh for the connector at end of the transaction.
    dateTime timestamp; // 1..1 Required. This contains the date and time on which the transaction is stopped.
    integer transactionId;  // 1..1 Required. This contains the transaction-id as received by the StartTransaction.conf.
    Reason reason;      // 0..1 Optional. This contains the reason why the transaction was stopped. MAY only
                        // be omitted when the Reason is "Local".
    
    integer transactionDataArrayLength;                   
    MeterValue * transactionData;   // 0..* Optional. This contains transaction usage details relevant for billing purposes.
} StopTransactionRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "StopTransactionResponse",
//     "type": "object",
//     "properties": {
//         "idTagInfo": {
//             "type": "object",
//             "properties": {
//                 "expiryDate": {
//                     "type": "string",
//                     "format": "date-time"
//                 },
//                 "parentIdTag": {
//                     "type": "string",
//                     "maxLength": 20
//                 },
//                 "status": {
//                     "type": "string",
//                     "enum": [
//                         "Accepted",
//                         "Blocked",
//                         "Expired",
//                         "Invalid",
//                         "ConcurrentTx"
//                     ]
//                 }
//             },
//             "required": [
//                 "status"
//             ]
//         }
//     },
//     "additionalProperties": false
// }
typedef struct 
{
    IdTagInfo idTagInfo;    // 0..1 Optional. This contains information about authorization status, expiry and
                            // parent id. It is optional, because a transaction may have been stopped without
                            // an identifier.
} StopTransactionResponse;

/* ---------------------------------------------------------------------------- 
                            TriggerMessage
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "TriggerMessageRequest",
//     "type": "object",
//     "properties": {
// 		"requestedMessage": {
// 				"type": "string",
// 				"enum": [
// 					"BootNotification",
// 					"DiagnosticsStatusNotification",
// 					"FirmwareStatusNotification",
// 					"Heartbeat",
// 					"MeterValues",
// 					"StatusNotification"
// 				]
// 			},
//         "connectorId": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "requestedMessage"
//     ]
// }
typedef enum {
    BootNotification = 0,           // To trigger a BootNotification request
    DiagnosticsStatusNotification,  // To trigger a DiagnosticsStatusNotification request
    FirmwareStatusNotification,     // To trigger a FirmwareStatusNotification request
    Heartbeat,                      // To trigger a Heartbeat request
    MeterValues,                    // To trigger a MeterValues request
    StatusNotification,             // To trigger a StatusNotification request
} MessageTrigger;

typedef struct 
{
    MessageTrigger requestedMessage;    // 1..1 Required.
    integer connectorId;                // connectorId > 0
                                        // 0..1 Optional. Only filled in when request applies to a specific connector.    
} TriggerMessageRequest;


// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "TriggerMessageResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Accepted",
//                 "Rejected",
// 				"NotImplemented"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Accepted = 0,       // Requested notification will be sent.
    Rejected,           // Requested notification will not be sent.
    NotImplemented,     // Requested notification cannot be sent because it is either not implemented or unknown.
} TriggerMessageStatus;

typedef struct 
{
    TriggerMessageStatus status;    // 1..1 Required. Indicates whether the Charge Point will send the requested
                                    // notification or not.
} TriggerMessageResponse;

/* ---------------------------------------------------------------------------- 
                            UnlockConnector
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "UnlockConnectorRequest",
//     "type": "object",
//     "properties": {
//         "connectorId": {
//             "type": "integer"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "connectorId"
//     ]
// }
typedef struct {
    integer connectorId;    // connectorId > 0
                            // 1..1 Required. This contains the identifier of the connector to be unlocked.
} UnlockConnectorRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "UnlockConnectorResponse",
//     "type": "object",
//     "properties": {
//         "status": {
//             "type": "string",
//             "enum": [
//                 "Unlocked",
//                 "UnlockFailed",
//                 "NotSupported"
//             ]
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "status"
//     ]
// }
typedef enum {
    Unlocked = 0,   // Connector has successfully been unlocked.
    UnlockFailed,   // Failed to unlock the connector: The Charge Point has tried to unlock the connector and has detected that the connector is still
                    // locked or the unlock mechanism failed.
    NotSupported,   // Charge Point has no connector lock, or ConnectorId is unknown.
} UnlockStatus;

typedef struct {
    UnlockStatus status;    // 1..1 Required. This indicates whether the Charge Point has unlocked the connector.    
} UnlockConnectorResponse;

/* ---------------------------------------------------------------------------- 
                            UpdateFirmware
 ----------------------------------------------------------------------------*/
// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "UpdateFirmwareRequest",
//     "type": "object",
//     "properties": {
//         "location": {
//             "type": "string",
//             "format": "uri"
//         },
//         "retries": {
//             "type": "number"
//         },
//         "retrieveDate": {
//             "type": "string",
//             "format": "date-time"
//         },
//         "retryInterval": {
//             "type": "number"
//         }
//     },
//     "additionalProperties": false,
//     "required": [
//         "location",
//         "retrieveDate"
//     ]
// }
typedef struct {
    char * location;    // anyURI 1..1 Required. This contains a string containing a URI pointing to a location from
                        // which to retrieve the firmware.
    integer retries;    // 0..1 Optional. This specifies how many times Charge Point must try to download the
                        // firmware before giving up. If this field is not present, it is left to Charge Point to
                        // decide how many times it wants to retry.
    dateTime retrieveDate;  // 1..1 Required. This contains the date and time after which the Charge Point is
                            // allowed to retrieve the (new) firmware.
    integer retryInterval;  // 0..1 Optional. The interval in seconds after which a retry may be attempted. If this
                            // field is not present, it is left to Charge Point to decide how long to wait between
                            // attempts.
} UpdateFirmwareRequest;

// {
//     "$schema": "http://json-schema.org/draft-04/schema#",
//     "title": "UpdateFirmwareResponse",
//     "type": "object",
//     "properties": {},
//     "additionalProperties": false
// }
typedef struct {
    integer dummy;          // No fields are defined.
} UpdateFirmwareResponse;



