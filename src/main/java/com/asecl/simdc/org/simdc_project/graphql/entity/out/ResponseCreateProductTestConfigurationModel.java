package com.asecl.simdc.org.simdc_project.graphql.entity.out;

import lombok.Data;

@Data
public class ResponseCreateProductTestConfigurationModel {
    private boolean Result;
    private String CurrentStatus;
    private String SwName;
    private String SwVersion;
    private String FwName;
    private String FwVersion;
}
