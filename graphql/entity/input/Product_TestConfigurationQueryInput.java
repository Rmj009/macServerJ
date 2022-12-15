package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product_TestConfigurationQueryInput {
    private String lotcode;
    private String productFamilyName;
    private String lotcodeName;
    private String fwName;
    private String fwVersion;
    private String swName;
    private String swVersion;
    private String customerName;
    private Timestamp startTime;
    private Timestamp endTime;
    private int pageNumber;
    private int pageSize;
}
