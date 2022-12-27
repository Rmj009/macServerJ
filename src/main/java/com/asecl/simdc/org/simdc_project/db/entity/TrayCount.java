package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

import java.util.Optional;

@Data
public class TrayCount {
    private String TrayName;
    private long TrayId;
    private int OneTrayWidth;
    private int OneTrayHeight;
    private Integer LastMatrix;
}
