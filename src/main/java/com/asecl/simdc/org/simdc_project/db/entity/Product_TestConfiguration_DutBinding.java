package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product_TestConfiguration_DutBinding {
    private long ID;
    private DutDevice DutDevice_ID;
    private Product_TestConfiguration Product_TestConfiguration_ID;
    private Timestamp CreatedTime;
}
