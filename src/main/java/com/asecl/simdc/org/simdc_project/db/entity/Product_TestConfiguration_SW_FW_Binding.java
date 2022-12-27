package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product_TestConfiguration_SW_FW_Binding {
    private long ID;
    private Product_TestConfiguration Product_TestConfiguration;
    private Software Software;
    private Firmware Firmware;
    private int IsActived;
    private Timestamp CreatedTime;
}
