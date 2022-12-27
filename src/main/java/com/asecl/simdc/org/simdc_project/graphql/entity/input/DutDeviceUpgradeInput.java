package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DutDeviceUpgradeInput {
    private String originName;
    private String originProductDevice;
    private String originGroupPc;
    private String newProductDevice;
    private String newHostName;
    private String newRemark;
    private String newGroupPc;
}