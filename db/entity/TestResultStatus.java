package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestResultStatus {
    private long ID;
    private String Result;
    private Timestamp CreatedTime;
}
