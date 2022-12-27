package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product_TestConfigurationUpgradeInput {
    private int forceUpdate;
    private String lotcode;
    private String productFamilyName;
    private String productDeviceName;
    private String groupPC;
    private String fwName;
    private String fwVersion;
    private String swName;
    private String swVersion;
    private String[] dutNames;
    private String ownerEmplayeeId;
}

//    device: String!
//            productFamilyName: String!
//            lotcodeName: String!
//            fwName: String
//            fwVersion: String
//            swName: String
//            swVersion: String
//            dutNames: [String]
