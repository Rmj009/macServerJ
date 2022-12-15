package com.asecl.simdc.org.simdc_project.db.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class MacAddress {
    private long ID;
    private String Name;
    private String Address;
    private long AddressDecimal;
    private MacStatus MacStatus;
    private MacType MacType;
    private TestConfiguration TestConfiguration;
    private User TestUser;
    private User CreatedOwner;
    private Timestamp CreatedTime;
}

