package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private long ID;
    private String EmployeeID;
    private String RealName;
    private String NickName;
    private String EMail;
    private String Phone;
    private Role Role;
    private String Password;
    private User AgreeUser;
    private User DisagreeUser;
    private short IsActived;
    private String DisagreeReason;
    private Timestamp LastActivedTime;
    private Timestamp LastDisagreeActiveTime;
}
