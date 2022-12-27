package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RegisterType {
    private long ID;
    private String Name;
    private Timestamp CreatedTime;
}
