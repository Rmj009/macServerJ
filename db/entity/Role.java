package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.*;

import java.sql.Timestamp;

@Data
public class Role {
    private long ID;
    private String Name;
    private Timestamp CreatedTime;
}
