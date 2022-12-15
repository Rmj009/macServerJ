package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncIput {
    private int uploadLog;
    private String productFamily;
    private String productDevice;
    private String lotcode;
    private String errorCode;
    private String filename;
    private String macType;
    private String address;
    private String result;
    private JSONObject resultSummary;
    private String sipserialname;
    private String barcode;
    private String barcode_vendor;

    private boolean uploadLicense;
    private String license;

}
