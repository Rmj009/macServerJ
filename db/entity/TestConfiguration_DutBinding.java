package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestConfiguration_DutBinding {
    private long ID;
    private DutDevice DutDevice_ID;
    private TestConfiguration TestConfiguration_ID;
    private Timestamp CreatedTime;
}
