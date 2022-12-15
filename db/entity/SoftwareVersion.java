package com.asecl.simdc.org.simdc_project.db.entity;

import com.asecl.simdc.org.simdc_project.db.entity.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SoftwareVersion {
    private long ID;
    private String Version;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}
