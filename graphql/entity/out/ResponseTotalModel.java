package com.asecl.simdc.org.simdc_project.graphql.entity.out;

import lombok.Data;

@Data
public class ResponseTotalModel {
    private int Total;
    private int Fail;
    private int Pass;
    private int Unused;
    private int Using;
    private String LastMac;
    private String LastMacName;
    private String TrayName;
    private int TrayX;
    private int TrayY;
}
