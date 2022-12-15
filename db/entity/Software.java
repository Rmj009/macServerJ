package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Software {
    private long ID;
    private String Name;
    private String Version;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}
