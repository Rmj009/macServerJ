package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ProductFamily {
    private long ID;
    private String Name;
    private String Remark;
    private List<ProductDevice> productDevices;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}

