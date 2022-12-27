package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Firmware {
    private long ID;
    private String Name;
    private String Version;
    private String Path;
    private String Remark;
    private String MD5;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}

