package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Login {
    private long ID;
    private User LoginUser;
    private String JwtToken;
    private LoginType LoginType;
    private Timestamp LastModifyTime;
    private Timestamp CreatedTime;
}

