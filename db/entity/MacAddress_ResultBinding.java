package com.asecl.simdc.org.simdc_project.db.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MacAddress_ResultBinding {
    private long ID;
    private MacAddress Mac_ID;
    private String OPId;
    private String TestFlow;
    private DutDevice dutDevice;
    private TestResultStatus ResultStatus_ID;
    private JSONObject ResultSummary;
    private String Path;
    private String Barcode;
    private String Barcode_Vendor;
    private String SipSerialName;
    private TestConfiguration TestConfiguration_ID;
    private Timestamp CreatedTime;
}
