package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Register {
    private long ID;
    private User RegisterUser;
    private RegisterType RegisterType;
    private Timestamp CreatedTime;
}
