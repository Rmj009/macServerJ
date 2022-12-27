package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Product_Tray {
    private long ID;
    private String Name;
    private Product_TestConfiguration Product_TestConfiguration;
    private TrayType TrayType;
    private int Width;
    private int Height;
    private int TOrder;
    private Timestamp CreatedTime;
}
