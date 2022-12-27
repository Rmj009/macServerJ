package com.asecl.simdc.org.simdc_project.db.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Product_TestConfiguration {
    private long ID;
    private String LotCode;
    private String PID;
    private String PO;
    private TestConfigurationStatus Status;
    private ProductDevice ProductDevice;
    private List<Product_TestConfiguration_SW_FW_Binding> FwSwBinding;
    private Customer Customer;
    private List<DutDevice> DutDevices;
    private String ForceStopOPId;
    private String ForceStopRemark;
    private JSONObject ExtraJson;
    private JSONObject LogTitle;
    private JSONObject LogLimitUpper;
    private JSONObject LogLimitLower;
    private int TrayMode;
    private Timestamp FinishDate;
    private Timestamp CreatedTime;
}
