package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDeviceUpgradeInput {
    private String originName;
    private String name;
    private String remark;
    private String originFamilyName;
    private String familyName;
}
