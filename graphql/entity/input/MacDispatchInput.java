package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacDispatchInput {
    private String productFamily;
    private String productDevice;
    private String lotCode;
    private String dutPCName;
    private String opId;
    private String testFlow;
    private String groupPC;
    private String testerEmplayeeId;
    private String barcode;
    private String sipserialname;
    private String barcode_vendor;
}
