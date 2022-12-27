package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product_TrayPosition {
    private long ID;
    private Product_TestConfiguration Product_TestConfiguration;
    private Product_MacAddress_ResultBinding Product_MacAddress_ResultBinding;
    private Product_Tray Product_Tray;
    private int MatrixIndex;
    private int X;
    private int Y;
    private String ErrorCode;
    private Timestamp CreatedTime;
}
