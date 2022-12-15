package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductDevice {
    private long ID;
    private String Name;
    private String Remark;
    private ProductFamily ProductFamily;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}
