package com.asecl.simdc.org.simdc_project.db.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TestConfiguration {
    private long ID;
    private String LotCode;
    private String PID;
    private TestConfigurationStatus Status;
    private ProductDevice ProductDevice;
    private List<TestConfiguration_SW_FW_Binding> FwSwBinding;
    private MacDispatchType MacDispatchType;
    private Customer Customer;
    private List<DutDevice> DutDevices;
    private String ForceStopOPId;
    private User ForceStopUser;
    private String ForceStopRemark;
    private JSONObject ExtraJson;
    private JSONObject LogTitle;
    private JSONObject LogLimitUpper;
    private JSONObject LogLimitLower;
    private int TrayMode;
    private User CreatedOwner;
    private Timestamp FinishDate;
    private Timestamp CreatedTime;
}

