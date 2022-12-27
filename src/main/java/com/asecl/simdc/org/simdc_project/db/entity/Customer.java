package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Customer {
    private long ID;
    private String Name;
    private String Phone;
    private String Remark;
    private User CreatedOwner;
    private List<TestConfiguration> TestConfigurations;
    private Timestamp CreatedTime;
}

