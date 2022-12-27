package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DutDevice {
    private long ID;
    private String HostName;
    private String ProductDevice;
    private String Remark;
    private String GroupPC;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}

