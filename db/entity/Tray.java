package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class  Tray {
    private long ID;
    private String Name;
    private TestConfiguration TestConfiguration;
    private Product_TestConfiguration Product_TestConfiguration;
    private TrayType TrayType;
    private int Width;
    private int Height;
    private int Order;
    private Timestamp CreatedTime;
}
