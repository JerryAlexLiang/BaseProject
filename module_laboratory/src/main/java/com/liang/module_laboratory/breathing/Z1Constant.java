//package com.liang.module_laboratory.breathing;
//
//public class Z1Constant {
//
//    public static int DEVICE_RESET = 2;
//    public static int START_TREATMENT = 10;
//    public static int STOP_TREATMENT = 11;
//    public static int AIRLEAK_START = 17;
//    public static int AIRLEAK_STOP = 18;
//    public static int ODOMETERS = 21;
//    public static int STAT_PRESSURE = 22;
//    public static int DATETIME_STAMP = 31;
//    public static int STAT_AHI = 32;
//    public static int APNEA_HYPOPNEA = 33;
//    public static int STAT_USAGE_BREATH = 34;
//    public static int STAT_USAGE_NONBREATH = 35;
//
//    public static final byte MSG_Ack = 0x06;
//    public static final byte MSG_Nak = 0x15;
//
//    //Errors when ACK
//    public static final byte MSG_UnknownMessage = (byte) 0xff;
//    public static final byte MSG_BadCheckSum = (byte) 0xfe;
//    public static final byte MSG_CommsAreLocked = (byte) 0xfd;
//    public static final byte MSG_PasswordDoesNotMatch = (byte) 0xfc;
//    public static final byte MSG_NotInStandby = (byte) 0xfb;
//    public static final byte MSG_ValueOutOfRange = (byte) 0xfa;
//    public static final byte MSG_FileError = (byte) 0xf9;
//    public static final byte MSG_NoResponse = (byte) 0xf8;
//    public static final byte MSG_ParameterLocked = (byte) 0xf7;
//    public static final byte MSG_NoFlowSensor = (byte) 0xf8;
//
//    public static final byte MESSAGE_LENGTH_NO_DATA = 3;
//    public static final byte MESSAGE_LENGTH_SET_BYTE = 4;
//    public static final byte MESSAGE_LENGTH_SET_PASSWORD = 11;
//    public static final byte MESSAGE_LENGTH_SET_PRESSURE = 4;
//    public static final byte MESSAGE_LENGTH_UPDATE_TIME = 9;
//
//    public static final byte MSG_IDX_COMMAND = 0;
//    public static final byte MSG_IDX_LENGTH = 1;
//    public static final byte MSG_IDX_DATA_OUT = 2;
//
//    public static final byte LENGTH_PASSWORD = 8;
//
//    public static final byte MSG_UpdateDateTime = 0x22;
//    public static final byte MSG_GET_PRESSURE = 0x30;
//    public static final byte MSG_GetDeviceID = 0x33;
//    public static final byte MSG_GetFirmwareVersion = 0x37;
//    public static final byte MSG_GetDateTime = 0x3F;
//    public static final byte MSG_SetLatchStatusTest = 0x5B;
//    public static final byte MSG_GetLCD_DisplayInfo1 = 0x60;
//    public static final byte MSG_RC_StartStopPress = 0x61;
//    public static final byte MSG_RC_StartStopHold = 0x62;
//    public static final byte MSG_RC_UpArrowPress = 0x63;
//    public static final byte MSG_RC_UpArrowHold = 0x64;
//    public static final byte MSG_RC_DownArrowPress = 0x65;
//    public static final byte MSG_RC_DownArrowHold = 0x66;
//    public static final byte MSG_RC_UpDownHold = 0x67;
//    public static final byte MSG_GetLCD_DisplayInfo2 = 0x68;
//    public static final byte MSG_SetPassword = 0x69;
//    public static final byte MSG_GetSetPasswordStatus = 0x6A;
//    public static final byte MSG_Bluetooth_LogIn = 0x6C;
//    public static final byte MSG_SetZ1_Treatment_Mode = 0x70;
//    public static final byte MSG_GetZ1_Treatment_Mode = 0x71;
//    public static final byte MSG_SetZ1_Breathe_Mode = 0x72;
//    public static final byte MSG_GetZ1_Breathe_Mode = 0x73;
//    public static final byte MSG_GetCPAPTreatmentPressure = 0x75;
//    public static final byte MSG_GetAPAPMaximumPressure = 0x77;
//    public static final byte MSG_GetAPAPMinimumPressure = 0x79;
//    public static final byte MSG_SetRampTime = 0x7A;
//    public static final byte MSG_GetRampTime = 0x7B;
//    public static final byte MSG_SetRampMinimumPressure = 0x7C;
//    public static final byte MSG_GetRampMinimumPressure = 0x7D;
//    public static final byte GET_BLUETOOTH_AVAIL = 0x7F;
//    public static final byte MSG_GetBatteryInfo = (byte) 0x8B;
//    public static final byte MSG_EventsUpload_Start = (byte) 0x98;
//    public static final byte MSG_EventsUpload_Next = (byte) 0x99;
//    public static final byte MSG_SetDeviceColor = (byte) 0xCB;
//    public static final byte MSG_GetDeviceColor = (byte) 0xCC;
//
//
//    //Z1 status
//    public static final int Z1_STATE_IDLE = 0;
//    public static final int Z1_STATE_WAITING_FOR_CONFIRM = 1;
//    public static final int Z1_STATE_CANCELLED_TIMEOUT = 2;
//    public static final int Z1_STATE_CANCELLED_BY_USER = 3;
//    public static final int Z1_STATE_CANCELLED_BECAUSE_NOT_IN_STANDBY = 4;
//    public static final int Z1_STATE_CONFIRMED = 5;
//
//
//}