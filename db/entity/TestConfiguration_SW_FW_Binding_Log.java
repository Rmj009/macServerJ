package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestConfiguration_SW_FW_Binding_Log {
    private long ID;
    private TestConfiguration_SW_FW_Binding TestConfiguration_SW_FW_Binding;
    private String OPId;
    private String TestFlow;
    private Timestamp CreatedTime;
}
