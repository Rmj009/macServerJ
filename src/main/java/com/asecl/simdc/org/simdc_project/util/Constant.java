package com.asecl.simdc.org.simdc_project.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Constant {
    public static final String MAC_TYPE_WIFI = "Wifi";
    public static final String MAC_TYPE_BT = "BT";
    public static final String EnumIncreateType = "EnumIncreateType";
    public static final String EnumIncreateType_NormalRotateId = "NormalRotateId";
    public static final String EnumIncreateType_Normal = "Normal";
    public static final String EnumIncreateType_OddAndEvenRotateId = "OddAndEvenRotateId";
    public static final String EnumIncreateType_OddAndEven = "OddAndEven";
    public static final String EnumIncreateType_OddRotateId = "OddRotateId";
    public static final String EnumIncreateType_Odd = "Odd";
    public static final String EnumIncreateType_EvenRotateId = "EvenRotateId";
    public static final String EnumIncreateType_Even = "Even";
    public static final String EnumMacType = "EnumMacType";
    public static final String EnumMacType_Wifi = "Wifi";
    public static final String EnumMacType_BT = "BT";

    public static final String TestResult_Pass = "Pass";
    public static final String TestResult_Fail = "Fail";

    public static final String TrayType_Pass = "Pass";
    public static final String TrayType_FailStart = "FailStart";
    public static final String TrayType_FailStart1 = TrayType_FailStart + "1";
    public static final String TrayType_FailStart2 = TrayType_FailStart + "2";
    public static final String TrayType_FailStart3 = TrayType_FailStart + "3";
    public static final String TrayType_FailStart4 = TrayType_FailStart + "4";
    public static final String TrayType_FailStart5 = TrayType_FailStart + "5";
    public static final String TrayType_FailStart6 = TrayType_FailStart + "6";
    public static final String TrayType_FailStart7 = TrayType_FailStart + "7";
    public static final String TrayType_FailStart8 = TrayType_FailStart + "8";
    public static final String TrayType_FailStart9 = TrayType_FailStart + "9";
//    public static final int SQL_Batch_Count = 5000;

//    public static final short Mac_Unused_Status = 0;

    public static String FW_DIR_PATH = "";

    public static final String TestConfigStatus_TestPrepare = "TestPrepare";
    public static final String TestConfigStatus_Testing = "Testing";
    public static final String TestConfigStatus_TestFinish = "TestFinish";
    public static final String TestConfigStatus_TestFail = "TestFail";

    public static final String MacStatus_UsePrepare = "Unused";
    public static final String MacStatus_Using = "Using";
    public static final String MacStatus_Used = "Used";
    public static final String MacStatus_UsedFail = "UsedFail";

    public static Lock MAC_OP_LOCK = new ReentrantLock();

    public static final String FW_URL = "/Firmware/";
    public static final String LOG_URL = "/Log/";

}
