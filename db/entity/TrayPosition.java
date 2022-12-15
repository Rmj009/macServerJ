package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TrayPosition {
    private long ID;
    private TestConfiguration TestConfiguration;
    private MacAddress_ResultBinding ResultBinding;
    private Tray Tray;
    private int MatrixIndex;
    private int X;
    private int Y;
    private String ErrorCode;
    private Timestamp CreatedTime;
}
