package com.asecl.simdc.org.simdc_project.graphql.entity.out;

import lombok.Data;

@Data
public class ResponseMacModel {
    private boolean Result;
    private boolean IsNoMac;
    private boolean IsFinish;
    private String Mac;
    private String Name;
    private String SipLicense;
}
