package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product_TestConfiguration_SW_FW_Binding_Log {
    private long ID;
    private Product_TestConfiguration_SW_FW_Binding Product_TestConfiguration_SW_FW_Binding;
    private String OPId;
    private String TestFlow;
    private Timestamp CreatedTime;
}
