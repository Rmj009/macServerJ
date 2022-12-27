package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product_MacAddress {
    private long ID;
    private String LotCode;
    private String PO;
    private String Name;
    private String Address;
    private long AddressDecimal;
    private MacStatus MacStatus;
    private MacType MacType;
    private Product_TestConfiguration Product_TestConfiguration;
    private String SipSerialName;
    private String SipLicense;
    private Timestamp CreatedTime;
}
