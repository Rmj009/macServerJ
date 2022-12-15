package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product_TestConfigurationCreationInput {
    private String lotcode;
    private String pid;
    private String po;
    private String testFlow;
    private String testDutMode;
    private String testPGMName;
    private String testLoadBoard;
    private String testMode;
    private String productFamilyName;
    private String productDeviceName;
    private String groupPC;
    private String customerName;
    private String macDispatchType;
    private String macName;
    private String macType;
    private String macStart;
    private int macRotatIdStart;
    private int macCount;
    private int autogen;
    private String ownerEmplayeeId;
    private String opId;
    private String fwName;
    private String fwVersion;
    private String swName;
    private String swVersion;
    private JSONObject logTitle;
    private JSONObject logLimitUpper;
    private JSONObject logLimitLower;
    private int trayMode;
    private JSONObject trayData;
    private int trayWidth;
    private int trayHeight;
}

